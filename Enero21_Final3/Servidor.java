package Enero21_Final3;

//https://www.youtube.com/watch?v=U3l9Y0vvOJg&ab_channel=JLeo

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servidor extends Thread{
	
	private char jugador = 'X';
	private char maquina = 'O';
	private static int PUERTO = 5000;
	
	Matriz matriz = new Matriz();
	
	@Override
	public void run() {	
		int colum, fil;
		String fecha;
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

		File archivo;
		FileWriter escribir;
		PrintWriter escribirArchivo;
		SimpleDateFormat MI_FORMATO;
		Date ahora;
		// Comienzo de la conexion al cliente
		try {
			serverSocket = new ServerSocket(PUERTO);
			archivo = new File("archivo.txt");
			
			System.out.println("Esperando un cliente...");
			socket = serverSocket.accept();

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("Cliente levantado.Esperando para comenzar a jugar....");		
			
			archivo = new File("archivo.txt");
			escribir = new FileWriter(archivo, true);
			escribirArchivo = new PrintWriter(escribir);
			if(!archivo.exists()) {
				archivo.createNewFile();
				escribirArchivo.print("DETALLES DE LAS PARTIDAS DEL JUEGO.");
			}
			
			MI_FORMATO = new SimpleDateFormat("\n\ndd/MM/yyyy, hh:mm:ss", Locale.getDefault());			
			ahora = new Date();
			fecha = MI_FORMATO.format(ahora);
			System.out.println(fecha);
			escribirArchivo.println(fecha + "\n\n\n\nTablero de juego.\n\n");
			
			//pone en todos los casilleros un guion para saber que en ese casillero aun no se jugo
			matriz.matrizACero();
			matriz.mostrarMatriz();
			
			escribirArchivo(matriz, archivo, escribirArchivo);

			//confirmacion para empezar a jugar
			boolean jg = in.readBoolean();
			System.out.println("Comenzemos a jugar!!");
						
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
				
				//Se actualiza la matriz con el movimiento de la maquina
				System.out.println("JugadorOne juega en fila = "+fil+", columna = "+colum);
				matriz.rellenarCampo(fil, colum, jugador);				
				matriz.mostrarMatriz();
				
				//Escribe en el archivo el moviemiento de la maquina
				escribirArchivo.print("\n\nJuega JugadorOne.\nJugadorOne juega en fila = " + fil + ", columna = " + colum);
				escribirArchivo(matriz, archivo, escribirArchivo);
				
				//comprueba si gano el jugadorOne
				ganador = matriz.comprobarGanadorEmpate(jugador);
				
				//le envia un numero al cliente para saber si gano o hay empate o si se sigue jugando
				out.writeInt(ganador);

				//si gano el jugadorOne termina, si hay empate termina. En caso contrario juega la maquina
				if(ganador > 0) {
					if(ganador == 1) {
						System.out.println("\nGano el jugadorOne");
						escribirArchivo.print("\n\nGANO JUGADOR_ONE!!!\n\n\n\n");
					}
					else {
						if(ganador == 3) {
							System.out.println("\nHay empate");
							escribirArchivo.print("\n\nHAY EMPATE!!!\n\n\n\n");
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
					
					//Se actualiza la matriz con el movimiento de la maquina
					System.out.println("Maquina juega en fila = " + fil + ", columna = "+ colum);
					matriz.rellenarCampo(fil, colum, maquina);
					matriz.mostrarMatriz();

					//Escribe en el archivo el moviemiento de la maquina
					escribirArchivo.print("\n\nJuega maquina.\nMaquina juega en fila = " + fil + ", columna = " + colum);
					escribirArchivo(matriz, archivo, escribirArchivo);

					ganador = matriz.comprobarGanadorEmpate(maquina);
					out.writeInt(ganador);
					
					if(ganador > 0) {
						if(ganador == 2) {
							System.out.println("\nGano la maquina");
							escribirArchivo.print("\n\nGANO MAQUINA!!!\n\n\n\n");
						}
						else {
							if(ganador == 3) {
								System.out.println("\nHay empate");
								escribirArchivo.print("\n\nHAY EMPATE!!!\n\n\n\n");
							}
						}
					}
				}
				}while(ganador == 0); 
				
			System.out.println("\nJuego Finalizado.");
			matriz.mostrarMatriz();
			
			escribir.close();
			escribirArchivo.close();
			
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

	
	
	
	
	public void escribirArchivo(Matriz matriz_, File archivo_, PrintWriter escribirArchivo_) {
		
		escribirArchivo_.println("\n\n   1     2     3   ");
		escribirArchivo_.println("*******************");
		for(int i = 0; i < 3; i++) {
			escribirArchivo_.print((i+1));
			for(int e = 0; e < 3; e++) {
				escribirArchivo_.print("|\t" + matriz.getMatrizCampo(matriz, i, e));
				escribirArchivo_.print("\t|");
			}
			escribirArchivo_.print("\n");
		}
		escribirArchivo_.print("*******************\n");
	}
	
	
}

