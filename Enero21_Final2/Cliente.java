package Enero21_Final2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Cliente extends Thread{

	private int columna;
	private int fila;
	private char[][] matrizCliente = new char[3][3];

	private static String HOST = "localhost";
	private static int PUERTO = 5011;
	
	
	//contructor
	public void Jugador() {	
	}
	

	@Override
	public  void run() {

		int opc;					//variable del menu
    	boolean juega = false;		//envia un true o false al servidor para saber cuando se va a empezar a jugar
		boolean validarcasillero;	//viene del servidor, false si en ese casillero ya se jugo y se debe volver a pedir un numero de columna y fila  
		int ganador = 0;			//pasa a true cuando hay un ganador
		
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
					System.out.print("1-Para jugar\n2-Para ver Guia\n3-Para ver una partida como ejemplo\n4-Para salir\nIngrese una opcion: ");
					opc = entrada.nextInt();
	         
				} while(opc > 4);
				
				switch(opc){
	    			case 1:
		
	    				juega = true;
	    				out.writeBoolean(juega);

	    				System.out.println("\nEres el jugadorOne");
	    				player.mostrarMatrizCliente(matrizCliente);
	    				
	    				do {
	    					do {    
	    						//se pide que se ingrese el numero de fila
	    						fila = player.ingresarNumeroFilaColumna("Fila");
	    						out.writeInt(fila);		//se le envia el numero de fila al servidor
	    				
	    						
	    						columna = player.ingresarNumeroFilaColumna("columna");
	    						out.writeInt(columna);  //se le envia el numero de columna al servidor 				
	    					
	    						//el servidor envia true o false para saber si se jugo o no en ese casillero
	    						validarcasillero = in.readBoolean();
	    					
	    						if(validarcasillero == true) {
	    							System.out.println("Ya se jugo en ese casillero.");
	    						}
			
	    					}while(validarcasillero == true); 
	    				

	    					//rellena la matriz del cliente
	    					matrizCliente[fila-1][columna-1] = 'X';
		    				player.mostrarMatrizCliente(matrizCliente);
	    							
	    					// espero una respuesta para ver si gano o no
	    					ganador = in.readInt(); 
	    						
	    					//comprueba si gano el jugadorOne, o hay empate, sino juega la maquina
	    					if(ganador > 0) {
	    						if(ganador == 1) {
	    							System.out.println("\nHAY GANADOR!! En horabuena, haz ganado!!!!\n\n");
	    						}
	    						if(ganador == 3) {
	    							System.out.println("\nEMAPATE\n\n");
	    						}
	    					}
	    					else {
	    						System.out.println("Esperando que juegue la maquina...");
	    						
	    						fila = in.readInt();
	    						columna  = in.readInt();
	    						
	    						matrizCliente[fila-1][columna-1] = 'O';
	    						player.mostrarMatrizCliente(matrizCliente);
	    						
	    						ganador = in.readInt();
	    						if(ganador == 2) {
	    							System.out.println("\nGANO LA MAQUINA\n\n");
	    						}
	    						if(ganador == 3) {
	    							System.out.println("\nEMPATE\n\n");
	    						}
	    					}
	    					
	    				}while(ganador == 0);

	    				socket.close();
	    				
	    				break;
	    			case 2:	System.out.println("opcion 2: En opcion 2 va la explicacion del juego!");
	    				break;
	    			case 3:	System.out.println("opcion 3: En opcion 3 va una guia de juego. Juega el jugador 1 vs jugador 2 SOLOS   , lo programe con carteles y retrazos de tiempo!\n");
	    				break;
	    			case 4: System.out.println("FIN DEL PROGRAMA");
	    				break;
	    			default: System.out.println("Ingresaste una opcion invalida.\n\n");
	    				break;
				}
	    	
	    	}while(opc != 1 && opc < 4); //Si la expresión es falsa, el ciclo do-while finaliza
			
		} catch(IOException e) {
			System.out.println("Error\n");
		}
	}
	
	
	
	
	//MAIN
	public static void main(String[] args) {
		Thread cliente = new Cliente();
		cliente.start();
	}
	
	
	
	
	
	public void mostrarMatrizCliente(char[][] matriz) {
		
		System.out.println("\n         1              2               3          ");
		System.out.println("**************************************************");
		for(int i =0; i < 3; i++) {
				System.out.print((i+1));
				for(int e=0; e < 3; e++) {
				System.out.print("|\t" + matriz[i][e]);
				System.out.print("\t|");
			}
			System.out.println("\n");
		}
        System.out.println("**************************************************\n");	
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
