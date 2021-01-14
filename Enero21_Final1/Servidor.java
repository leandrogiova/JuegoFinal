package Enero21_Final1;

//de aca en adelante empiezo a modificar

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

	private static int PUERTO = 5001;
		
	Matriz matriz = new Matriz();///////////////
		
	@Override
	public void run() {	
		int colum, fil;
		char jugador = 'X', maquina = 'O';
		boolean variaBool = false;
		int[] posMaquina = new int[2];
		
		boolean ganadorBool=false;
		int ganador=0; //ganador = 0, se esta jugando, 1= gano jugadorOne, 2 = gano maquina, 3 = empate
		boolean valormaquina = false;
		
		
		ServerSocket serverSocket;
		Socket socket;
		DataInputStream in;
		DataOutputStream out;

		File archivo = new File("/Documentos/Archivo.txt");	
		
		
		try {
			serverSocket = new ServerSocket(PUERTO);
			
			System.out.println("Esperando un cliente...");
			socket = serverSocket.accept();

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("Cliente levantado.Esperando para comenzar a jugar....");
		
			try {
				
			}catch(Exception e) {
				System.out.println("Erro al crear el archivo. " + e.toString());
			}
			
			
			
			////////////////////////////////			
			SimpleDateFormat MI_FORMATO = new SimpleDateFormat("dd/MM/yyyy HM:mm:ss", Locale.getDefault());			
			Date ahora = new Date();
			String fecha = MI_FORMATO.format(ahora);
			System.out.println("Fecha de ahora: " + fecha);
			//////////////////////////////////////////////
			
			
			
			//confirmacion para empezar a jugar
			boolean jg = in.readBoolean();
			System.out.println("Recibimos un true, comenzemos a jugar");
			
			
			//pone en todos los casilleros un guion para saber que en ese casillero aun no se jugo
			matriz.matrizACero();
			matriz.mostrarMatriz();
			
			
			
			do {
				
				do {
				
					System.out.println("Esperando al jugadorOne...");
				
					System.out.println("Esperando numero de fila...");
					fil = in.readInt();
					System.out.println("Recibimos numero de fila = " + fil);
				
				
					colum = in.readInt();
					System.out.println("Recibimos numero de columna = " + colum);		
				
					System.out.println("\n\n");

					variaBool = matriz.comprobarCasillero(fil, colum);					
					
					if(variaBool == true) {
						System.out.println("Ya se jugo en ese casillero");
					}
					else {
						System.out.println("No se jugo en ese casillero");
					}

				
					out.writeBoolean(variaBool);
				
				}while(variaBool == true);
			
				
				
				matriz.rellenarCampo(fil, colum, jugador);	
			
				matriz.mostrarMatriz();
			
//				variaBool = matriz.comprobarGanador(jugador);
				
				
				ganador = matriz.comprobarGanadorEmpate(jugador);
				out.writeInt(ganador);
				
				if(ganador > 0) {
					if(ganador == 1) {
						System.out.println("Gano el jugadorOne");
					}
					else {
						if(ganador == 3) {
							System.out.println("Hay empate");
						}
					}
				}
				else {
					System.out.println("juega maquina...");

					do {
						System.out.println("Generando numeros....");
						fil = (int) Math.floor(Math.random()*3+1);
						colum = (int) Math.floor(Math.random()*3+1);
						System.out.println("fila = " + fil + "\ncolumna = " + colum);
					
					
						valormaquina = matriz.comprobarCasillero(fil, colum);
						System.out.println("valormaquina = " + valormaquina);
					}while(valormaquina == true);
					
					//envio el numero de fila y de columna al cliente
					out.writeInt(fil);
					out.writeInt(colum);
					
					matriz.rellenarCampo(fil, colum, maquina);
					matriz.mostrarMatriz();
					System.out.println("Ya jugo maquina, comprobando ganador...");

					ganador = matriz.comprobarGanadorEmpate(maquina);
					out.writeInt(ganador);
					
					if(ganador > 0) {
						if(ganador == 2) {
							System.out.println("Gano la maquina");
						}
						else {
							if(ganador == 3) {
								System.out.println("Hay empate");
							}
						}
					}
				}
				}while(ganador == 0); 
				
///				
/*				
				
				
				if(variaBool == true) {
					ganador = 1;
					System.out.println("Gano el jugadorOne!\n\n");
					out.writeInt(ganador);
				}
				else {
					
					variaBool = matriz.comprobarEmpate();
					
					if(variaBool == true) {
						System.out.println("Hay empate!!\n\n");
						ganador = 3;
						out.writeInt(ganador);
					}
					else {
						//juega maquina
				
						System.out.println("juega maquina...");
						do {
							System.out.println("Generando numeros....");
							fil = (int) Math.floor(Math.random()*3+1);
							colum = (int) Math.floor(Math.random()*3+1);
							System.out.println("fila = " + fil + "\ncolumna = " + colum);
						
						
							valormaquina = matriz.comprobarCasillero(fil, colum);
							System.out.println("valormaquina = " + valormaquina);
						}while(valormaquina == true);
					
					
						matriz.rellenarCampo(fil, colum, maquina);
						matriz.mostrarMatriz();
						System.out.println("Ya jugo maquina, comprobando ganador...");
					
					
						variaBool = matriz.comprobarGanador(maquina);
						System.out.println("Avisandole al cliente si gano o no, ganador=" + ganador);
					
						
						if(variaBool == true){
							System.out.println("Fin del juego");
							ganador = 2;
							out.writeInt(ganador);
						}
						else {
							variaBool = matriz.comprobarEmpate();
							System.out.println("Estoy aca, no hay empate, deben seguir jugando");
							if(variaBool == true) {
								ganador = 3;
								out.writeInt(ganador);
							}
						
						}
/*
///					}
 					else {
						System.out.println("Juego finalizado");
				
						// puedo dejar al servidor esperando para ver
						// si se va a jugar de nuevo o no
						// y recien ahi termina o vuleve a empezar	
					}
//				
					}
				}
				}
				}while(ganador == 0); 
				//hasta que ganador true, cuando ganador es true termina el juego

////		
*/
			
			
/*
de aca			
			ganador = matriz.comprobarGanador(jugador);
			if(ganador == true) {
				System.out.println("\n\nHAY GANADOR!!!\n\n");
			}
			else {
				System.out.println("El juego sigue...");
			}
			
		
			ganador = matriz.comprobarColumnas(jugador);
			if(ganador == true) {
				System.out.println("Ganó el jugador");
			}
			else{
				ganador = matriz.comprobarFilas(jugador);
				if(ganador == true) {
					System.out.println("Ganó el jugador");
				}
				else {
					ganador = matriz.comprobarDiagonal1(jugador);
					if(ganador == true) {
						System.out.println("Ganó el jugador");	
					}
					else {
						ganador = matriz.comprobarDiagonal2(jugador);
						if(ganador == true) {
							System.out.println("Ganó el jugador");
						}
					}
				}
			}
		
			
			
			
			matriz.mostrarMatriz();
				
			//juega maquina
			System.out.println("Juega maquina...");
			posMaquina = matriz.JuegaMaquina();
			
			System.out.println("Enviando numero de columna y fila al cliente...");
			colum = posMaquina[0];
			fil = posMaquina[1];
			out.writeInt(colum);
			out.writeInt(fil);


hasta aca			
*/
			matriz.mostrarMatriz();
			
			
			
			
			socket.close();
			System.out.println("Cliente cerrado");

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


