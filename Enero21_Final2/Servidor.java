package Enero21_Final2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;


public class Servidor extends Thread{
	
	private char jugador = 'X';
	private char maquina = 'O';
	private static int PUERTO = 5011;
	
	Matriz matriz = new Matriz();
		
	@Override
	public void run() {	
		int colum, fil;
		boolean variaBool = false;		
		boolean valormaquina = false;
		int ganador=0; 
		/*
		 * ganador == 0 se esta jugando
		 * ganador == 1 gano jugadorOne 
		 * ganador == 2 gano maquina
		 * ganador == 3 empate
		 */
		
    	//socket para conectarse al servidor
		ServerSocket serverSocket;
		Socket socket;
		DataInputStream in;
		DataOutputStream out;

		File archivo = new File("/Documentos/Archivo.txt");	
		
		// Comienzo de la conexion al cliente
		try {
			serverSocket = new ServerSocket(PUERTO);
			
			System.out.println("Esperando un cliente...");
			socket = serverSocket.accept();

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("Cliente levantado.Esperando para comenzar a jugar....");		
			
			////////////////////////////////			
			SimpleDateFormat MI_FORMATO = new SimpleDateFormat("dd/MM/yyyy HM:mm:ss", Locale.getDefault());			
			Date ahora = new Date();
			String fecha = MI_FORMATO.format(ahora);
			System.out.println("Fecha de ahora: " + fecha);
			//////////////////////////////////////////////
			
			//confirmacion para empezar a jugar
			boolean jg = in.readBoolean();
			System.out.println("Comenzemos a jugar!!");
			
			//pone en todos los casilleros un guion para saber que en ese casillero aun no se jugo
			matriz.matrizACero();
			matriz.mostrarMatriz();
			
			do {
				
				do {
				
					System.out.println("Esperando al jugadorOne...");
				
					System.out.println("Esperando numero de fila...");
					fil = in.readInt();
				
					System.out.println("Esperando numero de columna...");
					colum = in.readInt();		
					System.out.println("\n\n");

					//comprueba si se jugo o no en el casillero elegido por el usuario
					variaBool = matriz.comprobarCasillero(fil, colum);					
					
					if(variaBool == true) {
						System.out.println("Ya se jugo en ese casillero");
					}
					//le envia al cliente el estado del casillero, si se jugo o no en el casillero elegido
					out.writeBoolean(variaBool);
				
				}while(variaBool == true);
				
				System.out.println("JugadorOne juega en fila = "+fil+", columna = "+colum);
				matriz.rellenarCampo(fil, colum, jugador);				
				matriz.mostrarMatriz();
			
				//comprueba si gano el jugadorOne
				ganador = matriz.comprobarGanadorEmpate(jugador);
				
				//le envia un numero al cliente para saber si gano o hay empate o si se sigue jugando
				out.writeInt(ganador);

				//si gano el jugadorOne termina, si hay empate termina. En caso contrario juega la maquina
				if(ganador > 0) {
					if(ganador == 1) {
						System.out.println("\nGano el jugadorOne");
					}
					else {
						if(ganador == 3) {
							System.out.println("\nHay empate");
						}
					}
				}
				else {
					System.out.println("juega maquina...");

					do {
						fil = (int) Math.floor(Math.random()*3+1);
						colum = (int) Math.floor(Math.random()*3+1);
					
						valormaquina = matriz.comprobarCasillero(fil, colum);
					}while(valormaquina == true);
					
					//envio el numero de fila y de columna al cliente
					out.writeInt(fil);
					out.writeInt(colum);
					
					System.out.println("Maquina juega en fila = " + fil + ", columna = "+ colum);
					matriz.rellenarCampo(fil, colum, maquina);
					matriz.mostrarMatriz();

					ganador = matriz.comprobarGanadorEmpate(maquina);
					out.writeInt(ganador);
					
					if(ganador > 0) {
						if(ganador == 2) {
							System.out.println("\nGano la maquina");
						}
						else {
							if(ganador == 3) {
								System.out.println("\nHay empate");
							}
						}
					}
				}
				}while(ganador == 0); 
				
			System.out.println("\nJuego Finalizado.");
			matriz.mostrarMatriz();
			
			socket.close();
			System.out.println("\nCliente cerrado.");

			serverSocket.close();
			System.out.println("Servidor cerrado.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	
	
	
	
	public static void main(String[] args) {
		Thread servidor = new Servidor();
		servidor.start();	
	}	
	
}

