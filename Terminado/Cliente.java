package Enero21_Final4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Cliente extends Thread{

	private int columna;
	private int fila;
	private char[][] matrizCliente = new char[3][3];
	private boolean revancha = false;
	
	private static String HOST = "localhost";
	private static int PUERTO = 5011;
	
	
	//contructor
	public void Jugador() {	
	}
	

	@Override
	public  void run() {

		int opc;					//variable del menu
    	boolean validarcasillero;	//viene del servidor, false si en ese casillero ya se jugo y se debe volver a pedir un numero de columna y fila  
		int ganador = 0;			//pasa a true cuando hay un ganador
		int juega = 0;				//varible utilizada para la revancha
		Scanner entrada = new Scanner(System.in);
		
		
    	//socket para conectarse al servidor
		Socket socket;
		DataOutputStream out;	//para enviar informacion al servidor
		DataInputStream in;		//para recibir inforacion del servidor
		
		
		
		// Comienzo de la conexion a servidor
		try {
			
			Cliente player = new Cliente();
			
			socket = new Socket(HOST, PUERTO);	// se conecta al servidor
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("Conectado al servidor");
			
			do {
				do {
					
					System.out.println("\n\n\n\n\n\nBIENVENIDO A JUGAR\n");
					System.out.print("1-Para jugar\n2-Para ver las reglas del juego\n3-Para salir\nIngrese una opcion: ");
					opc = entrada.nextInt();
	         
				} while(opc > 3);
				
				switch(opc){
	    			case 1:	    				
	    				System.out.println("\nEres el jugadorOne");   				
	    				do {
	    					if(revancha == true) {
	    						//player.matrizClienteAcero();
	    						revancha = false;
	    					}
	    					
		    				//Pone la matriz del Cliente en cero y la muestra en la pantalla del cliente	    					
		    				player.matrizClienteAcero();
		    				player.mostrarMatrizCliente();
	    					
	    					//El servidor espera un 1 para comenzar a jugar	    					
	    					juega = 1;
	    					out.writeInt(juega);
	    					do {    	
	    					
	    						do {
	    							//se pide que se ingrese el numero de fila
	    							fila = player.ingresarNumeroFilaColumna("Fila");
	    							out.writeInt(fila);		//se le envia el numero de fila al servidor
	    						
	    							//se pide que se ingrese el numero de columna
	    							columna = player.ingresarNumeroFilaColumna("columna");
	    							out.writeInt(columna);  //se le envia el numero de columna al servidor 				
	    					
	    							//el servidor envia true o false para saber si se jugo o no en ese casillero
	    							validarcasillero = in.readBoolean();
	    					
	    							if(validarcasillero == true) {
	    								System.out.println("Ya se jugo en ese casillero.");
	    							}
	    					}while(validarcasillero == true); 
	    				
	    					//rellena la matriz del cliente
		    				player.rellenarMatrizCliente(fila, columna, 'X');
	    					player.mostrarMatrizCliente();
	    							
	    					// espero una respuesta para ver si gano o no
	    					ganador = in.readInt(); 
	    						
	    					//comprueba si gano el jugadorOne, o hay empate, sino juega la maquina
	    					if(ganador > 0) {
	    						if(ganador == 1) {
	    							System.out.println("\nHAY GANADOR!! Enhorabuena, haz ganado!!!!\n\n");
	    						}
	    						if(ganador == 3) {
	    							System.out.println("\nEMAPATE\n\n");
	    						}
	    					}
	    					//El jugadorOne no gano, juega la maquina
	    					else {
	    						System.out.println("Esperando que juegue la maquina...");
	    						
	    						//El servidor envia los datos de la fila y la columna de la maquina
	    						fila = in.readInt();
	    						columna  = in.readInt();
	    						
	    						player.rellenarMatrizCliente(fila, columna, 'O');
	    						player.mostrarMatrizCliente();
	    						
	    						//El servidor envia el estado del juego, si gano la maquina o si hay empate
	    						ganador = in.readInt();
	    						if(ganador == 2) {
	    							System.out.println("\nGANO LA MAQUINA\n\n");
	    						}
	    						if(ganador == 3) {
	    							System.out.println("\nEMPATE\n\n");
	    						}
	    					}
	    					
	    				}while(ganador == 0);
	    				
	    				if(ganador == 2) {
	    					do {
	    						System.out.println("Desea jugar la revancha?\n1-Si   2-No");
	    						opc = entrada.nextInt();
	    					}while(opc < 1 || opc > 2);
	    					
	    					if(opc == 1) {
	    						revancha = true;
	    					}
	    				}else {	
	    					do {
	    						System.out.println("¿Desea jugar de nuevo?\n1-Si   2-No");
	    						opc = entrada.nextInt();
	    					}while(opc < 1 || opc > 2);
	    						    					
	    					if(opc == 1) {
	    						revancha = true;
	    					}
	    				}
	    				
	    				//Se le envia al servidor true o false para saber si se juega la revancha. Si se le envia true hay revancha
	    				out.writeBoolean(revancha);
	    				    				
	    				}while(revancha != false);
	    				break;
	    				
	    			case 2:	
	    				System.out.println("\n\nJugara un jugador contra otro. Cada uno tendra un turno para jugar.");
	    				System.out.println("Cada jugador solo debe colocar su símbolo(´X´, ´O´) una vez por turno y no debe ser sobre una casilla ya jugada.");
	    				System.out.println("En caso de que el jugador haga trampa el ganador será el otro.");
	    				System.out.println("Ganara el jugador que logre colocar tres simbolos seguidos ya sea en una fila, columna o en alguna diagonal.\nMucha suerte en tu juego.\n\n");
	    				break;
	    			
	    			case 3:
	    				//Cuando al servidor le llegue la variable juega = 2, cerrara el socket
	    				juega = 2;
	    				out.writeInt(juega);
	    				System.out.println("Socket cerrado");
	    				socket.close();
	    				System.out.println("FIN DEL PROGRAMA");
	    				break;
	    			default: System.out.println("Ingresaste una opcion invalida.\n\n");
	    				break;
				}
	    	
	    	}while(opc != 1 && opc < 3);
			
		} catch(IOException e) {
			System.out.println("Error\n");
		}
	}
	
	
	
	
	
	
	//MAIN
	public static void main(String[] args) {
		Thread cliente = new Cliente();
		cliente.start();
	}
	
	
	
	
	
	/*
	 * MatrizClienteAcero pone toda la matriz a cero, como es una matriz de tipo char le pone un '-'(guion medio)
	 * No recibe parametros ya que matrizCliente es una atributo de la clase
	 * No tiene ningun tipo de retorno
	 */
	public void matrizClienteAcero() {
		
		for(int i = 0; i < 3; i++) {
			for(int e = 0; e < 3; e++) {
				this.matrizCliente[i][e] = '-';
			}
		}
	
	}
	
	
	
	
	
	
	/*
	 * mostrarMatrizCliente muestra la matriz
	 * No recibe parametros ya que matrizCliente es un atributo de la clase
	 * No tiene ningun tipo de retorno
	 */
	public void mostrarMatrizCliente() {
		
		System.out.println("\n         1              2               3          ");
		System.out.println("**************************************************");
		for(int i =0; i < 3; i++) {
				System.out.print((i+1));
				for(int e=0; e < 3; e++) {
				System.out.print("|\t" + this.matrizCliente[i][e]);
				System.out.print("\t|");
			}
			System.out.println("\n");
		}
        System.out.println("**************************************************\n");	
	}

	
	



	public void rellenarMatrizCliente(int fila, int columna, char jugador) {
		this.matrizCliente[fila-1][columna-1] = jugador;
	}
	
	
	/*
	 * @parametros: Toma un string con le nombre de Columana o Fila
	 * Pensado para la reutilizacion del codigo.
	 * Esta funcion se usa tanto para ingresar un numero de Columna como de Fila.
	 * Retorna un int, el numero de fila o columna ingresado por terinal.
	 */
	public int ingresarNumeroFilaColumna(String FilaColumna) {
		Scanner entrada = new Scanner(System.in);
		int num=0;
	
		do {
			System.out.print("Ingrese un numero de " + FilaColumna + " a jugar: ");
			num = entrada.nextInt();
				
			if(num < 1) {
				System.out.println("Ingresaste un numero de columna menor a cero. Debes ingresar un numero entre 1 y 3.");
			}
			if(num > 3) {
				System.out.println("Ingresaste un numero mayor a 3. Debes ingresar un numero entre 1 y 3.");
			}
		}while (num > 3 || num < 1);
	
		entrada.nextLine();	//Limpiamos buffer de entrada
		return num;
		
	}

}
