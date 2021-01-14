package Enero21_Final1;
//https://www.youtube.com/watch?v=IDaQMcnZnvY&ab_channel=SantiagoSosa

import java.util.Random;
import java.util.Scanner;

public class Matriz {


	private char[][] matriz = new char[3][3];


	public void Matriz() {	
	}
	
	public char[][] getMatriz() {
		return matriz;
	}

	
	public void setMatriz(char[][] matriz) {
		this.matriz = matriz;
	}

 
  public void matrizACero(){
      int e=0, i=0;
      for(i=0; i < 3; i++){
          for(e=0; e < 3; e++){
              matriz[i][e] = '-';
          }
      }

  } 

  
  
  
  
  public void rellenarCampo(int fila, int columna, char campo) {
		columna = columna - 1;
		fila = fila - 1;
		
		matriz[fila][columna] = campo;
	}
	
	
	
	
	
	public  void mostrarMatriz() {
		int i=0, e=0;

		System.out.println("\n\nMETODO MOSTRANDO:");
		System.out.println("\n         1              2               3          ");
		System.out.println("**************************************************");
		for(i =0; i < 3; i++) {
				System.out.print((i+1));
				for(e=0; e < 3; e++) {
				System.out.print("|\t" + matriz[i][e]);
				System.out.print("\t|");
			}
			System.out.println("\n");
		}
      System.out.println("**************************************************\n");
  }
	
	
/*	
	public int[] dondeJugoJugador() {
		
		int[] ya_jugoJugador = new int[9];
		int posicion = 0, j = 0;
		
		
		////////////////////
		rellenarCampo(1,3, 'X');		
		mostrarMatriz();
		
		
		//////////////////////////		
		
		for(int i=0; i < 3; i++) {
			for(int e=0; e < 3; e++) {
								
				if(matriz[i][e] == 'X') {
					ya_jugoJugador[j] = posicion+1;
					j++;
				}
				System.out.println("i="+i+"e="+e+"posicion="+posicion);
				posicion++;
			}
		}
		
		
		System.out.println("Viendo ya_jugojugador:");
		for(int i=0; i < 9; i++) {
			System.out.println("ya_jugoJugador["+ i+"]: " + ya_jugoJugador[i]);
		}
		
		
		return ya_jugoJugador;
	}
*/
	public boolean[] dondeJugoJugador() {
		
		boolean[] ya_jugoJugador = new boolean[9];
		int j = 0;
		
		
		////////////////////
		rellenarCampo(1,3, 'X');
		rellenarCampo(2,2, 'X');
		rellenarCampo(3,3, 'X');
		mostrarMatriz();
		//////////////////////////		
		
		for(int i=0; i < 3; i++) {
			for(int e=0; e < 3; e++) {
							
				if(matriz[i][e] == 'X') {
					ya_jugoJugador[j] = true;	
				}
				j++;
			}
		}
		
		
		System.out.println("Viendo ya_jugojugador:");
		for(int i=0; i < 9; i++) {
			System.out.println("ya_jugoJugador["+ i+"]: " + ya_jugoJugador[i]);
		}
		
		
		return ya_jugoJugador;
	}

	
	
	
	
	
/*
* JuegaMaquina genera un numero de columna y otro de fila y comprueba si se jugo o no en ese casillero
* Luego juega en ese casillero, rellenando el casillero con un 'O'
* No recibe argumentos
* Retorna un array con el numero de columna(posicion 0) y fila(posicion 1).
*/
	public int[] JuegaMaquina() {
		int columna, fila;
		int[] array = new int[2];
		boolean valor;
		
		do {
			fila = (int) Math.floor(Math.random()*3+1);
			columna = (int) Math.floor(Math.random()*3+1);

			System.out.println("La maquina jueva en [" + fila + "][" + columna + "]\n");
/*
			valor = validarCampo(columna, fila);
			if(valor == true) {
				System.out.println("No se jugo en ese casilero");
			}
			else {
				System.out.println("Ya se jugo en ese caseillero");
			}
*/		
		}while(valor = false);
		
		array[0] = columna;
		array[1] = fila;
		
		this.matriz[columna-1][fila-1] = 'O';		
		
		return array;
	}
	
	
	
	
	
	
	
	/*
	 * validarCampo comprueba si se jugó o no en un casillero 
	 * Devuelve FALSE si en ese casillero ya se jugo
	 * Si todavia no se jugo en ese casillero devuelve TRUE
	*/ 
	public boolean validarCampo(int columna, int fila) {
		columna = columna - 1;
		fila = fila - 1;
		boolean valor;
		
		if ( matriz[columna][fila] == 'X' || matriz[columna][fila] == 'O' ) {
			valor = false;
		}
		else {
          valor = true;
      }
		return valor;
	}
	

	
	

	
//este metodo recibe como parametro un caracter!!!!!!!!!!!! Para reutilizarlo en los dos jugadores
//retorna true si hay ganador
	public boolean comprobarFilas(char campo) {
		int contador=0, i = 0, e= 0;
		boolean val = false;
		for( i=0; i < 3; i++) {
			for (e=0; e < 3; e++) {
				
				if (matriz[i][e] == campo) {
					contador = contador +1;
				}
				if (contador == 3) {
					val = true;
				}
			}
			contador = 0;
		}
		return val;
	}
	
	
	
	// retorna true si hay ganador	
	public boolean comprobarColumnas(char campo) {
		int i=0, e=0, contador=0;
		boolean val = false;
		for(i=0; i < 3; i++) {
			for(e = 0; e < 3; e++ ) {
				if(matriz[e][i] == campo) {
					contador = contador + 1;
				}
				if(contador == 3) {
					val = true;
				}
			}
			contador = 0;		
		}
		return val;
	}
	
	
	
	
	
//retorna true si hay ganador	
	public boolean comprobarDiagonal1(char campo) {
		int i=0, e=0, contador=0;
		boolean val = false;
		for(i =0; i < 3; i++) {
			for (e=0; e< 3; e++) {
				
				if(matriz[i][e] == campo) {
					contador = contador + 1;
				}
				i = i + 1;
				if (contador == 3) {
					val = true;	
				}
			}
		}
		return val;
	}
	
	
	
	
	
	
	public boolean comprobarDiagonal2(char campo) {
		int i=0, e=0, contador=0;
		boolean val = false;
		for(i=0; i < 3; i++) {			
			for(e=2; e >= 0; e--) {
		
				if(matriz[i][e] == campo) {
					contador = contador +1;
				}
				
				i = i +1;
				if (contador == 3) {
					val = true;
				}
			}
		}	
		return val;
	}
	
	
	
	
	
	
	
	public boolean comprobarGanador(char campo) {

		int contador=0, i = 0, e= 0;
		boolean val = false;
		
			//compruebo si hay ganador en las filas
			for( i=0; i < 3; i++) {
				for (e=0; e < 3; e++) {
					if (matriz[i][e] == campo) {
						contador = contador +1;
					}
					if (contador == 3) {
						val = true;
					}
				}
				contador = 0;
			}
			if(val == true) {
				System.out.println("Gano el jugador logrando 3 en filas");
			}
			else {
			
			//compruebo si hay ganador en las columnas
				for(i=0; i < 3; i++) {
					for(e = 0; e < 3; e++ ) {
						if(matriz[e][i] == campo) {
							contador = contador + 1;
						}
						if(contador == 3) {
							val = true;
						}
					}
					contador = 0;		
				}
			if(val == true) {
				System.out.println("Gano el jugador logrando 3 en columnas");
			}
			else {
				
					//compruebo si hay ganador en diagonal 1
					for(i =0; i < 3; i++) {
						for(e=0; e< 3; e++) {
							if(matriz[i][e] == campo) {
								contador = contador + 1;
							}
							i = i + 1;
							if (contador == 3) {
								val = true;	
							}
						}
						contador = 0;
					}	
			if(val == true) {
				System.out.println("Gano el jugador logrando 3 en diagonal 1");
			}
			else {
				//compruebo diagonal 2
				for(i=0; i < 3; i++) {			
					for(e=2; e >= 0; e--) {				
						if(matriz[i][e] == campo) {
							contador = contador +1;
						}
						i = i +1;
						if(contador == 3) {
							val = true;
						}
					}
				}	
			
			if(val == true) {
				System.out.println("Gano el jugador logrando 3 en diagonal 2");
				}
			}
			contador = 0;
			}
			}
						
		return val;
	}
	
	
	
	
	
	
	
	
//se puede hacer un contador que cuente cuando una variable se va a escribir en un casillero
//cuando se completan los 9 casilleros y no hay ganador, HAY EMPATE
//y asi el programa es mas rapido y no se utiliza este metodo
	public boolean comprobarEmpate() {
		int i=0, e=0, contador=0;
		boolean val = false;
		for(i=0; i<3; i++) {
			for(e=0; e<3; e++) {
				
				if(matriz[i][e] == 'X' || matriz[i][e] == 'O') {
					contador = contador + 1;
				}
				
				if( contador == 9) {
					val = true;
				}	
				
			}
		}
		return val;
	}
	
	
	
	
	
	/*
	 * ComprobarCasillero devuelve false si el casillero esta vacio
	 * Si ya se juego en ese casillero devuelve true
	 * 
	*/
	public boolean comprobarCasillero(int fila, int columna) {
		boolean a = false;
		
		fila = fila - 1;
		columna = columna - 1;
		
		if(matriz[fila][columna] == 'X' || matriz[fila][columna] == 'O' ) {
			a = true;
		}
		
		return a;
	}
	
	
	
	
	
	public int comprobarGanadorEmpate(char campo) {
		
		int ganador = 0;
		int i=0, e=0, contador=0;

		
		if(campo == 'X') {	//verifica si gano el jugador 1
//			ganador = 1;
		
			//compruebo ganador en las filas
			for( i=0; i < 3; i++) {
				for (e=0; e < 3; e++) {
					if (matriz[i][e] == campo) {
						contador = contador +1;
					}
					if (contador == 3) {
						ganador = 1;
					}
				}
				contador = 0;
			}
			if(ganador == 1) {
				System.out.println("Gano el jugador logrando 3 en filas");
			}else {
				
					//compruebo si hay ganador en las columnas
					for(i=0; i < 3; i++) {
						for(e = 0; e < 3; e++ ) {
							if(matriz[e][i] == campo) {
							contador = contador + 1;
							}
							if(contador == 3) {
								ganador = 1;
							}
						}
						contador = 0;		
					}
					if(ganador == 1) {
						System.out.println("Gano el jugador logrando 3 en columnas");
					}else {
					
						//compruebo si hay ganador en diagonal 1
						for(i =0; i < 3; i++) {
							for(e=0; e< 3; e++) {
								if(matriz[i][e] == campo) {
									contador = contador + 1;
								}
								i = i + 1;
								if (contador == 3) {
									ganador = 1;	
								}
							}
							contador = 0;
						}	
						if(ganador == 1) {
							System.out.println("Gano el jugador logrando 3 en diagonal 1");
						}else {
						
							//compruebo diagonal 2
							for(i=0; i < 3; i++) {			
								for(e=2; e >= 0; e--) {				
									if(matriz[i][e] == campo) {
										contador = contador +1;
									}
									i = i +1;
									if(contador == 3) {
										ganador = 1;
									}
								}
							}	
						
						if(ganador == 1) {
							System.out.println("Gano el jugador logrando 3 en diagonal 2");
							}
							
						}
				}
			}
			
			
			
			
		
		
		
		}
		else {				//verifica si gano la maquina
//			ganador = 2;
			
			//compruebo ganador en las filas
			for( i=0; i < 3; i++) {
				for (e=0; e < 3; e++) {
					if (matriz[i][e] == campo) {
						contador = contador +1;
					}
					if (contador == 3) {
						ganador = 2;
					}
				}
				contador = 0;
			}
			if(ganador == 2) {
				System.out.println("Gano la maquina logrando 3 en filas");
			}else {
				
					//compruebo si hay ganador en las columnas
					for(i=0; i < 3; i++) {
						for(e = 0; e < 3; e++ ) {
							if(matriz[e][i] == campo) {
							contador = contador + 1;
							}
							if(contador == 3) {
								ganador = 2;
							}
						}
						contador = 0;		
					}
					if(ganador == 2) {
						System.out.println("Gano la maquina logrando 3 en columnas");
					}else {
					
						//compruebo si hay ganador en diagonal 1
						for(i =0; i < 3; i++) {
							for(e=0; e< 3; e++) {
								if(matriz[i][e] == campo) {
									contador = contador + 1;
								}
								i = i + 1;
								if (contador == 3) {
									ganador = 2;	
								}
							}
							contador = 0;
						}	
						if(ganador == 2) {
							System.out.println("Gano la maquina logrando 3 en diagonal 1");
						}else {
						
							//compruebo diagonal 2
							for(i=0; i < 3; i++) {			
								for(e=2; e >= 0; e--) {				
									if(matriz[i][e] == campo) {
										contador = contador +1;
									}
									i = i +1;
									if(contador == 3) {
										ganador = 2;
									}
								}
							}	
						
						if(ganador == 2) {
							System.out.println("Gano la maquina logrando 3 en diagonal 2");
							}
							
						}
					}
				}
			
		}
		if(ganador == 0) {	//sino gano ninguno de los dos verifica si hay empate
			
			
			for(i=0; i<3; i++) {
				for(e=0; e<3; e++) {		
					if(matriz[i][e] == 'X' || matriz[i][e] == 'O') {
						contador = contador + 1;
					}
					if( contador == 9) {
						ganador = 3;
					}
					if(ganador == 3) {
						System.out.println("Hay empate");
					}
					
				}
			}	
		}
		
		System.out.println("dentro de la funcion ganador = " + ganador);
		return ganador;
	}
	
	
	
	
	
	
	
}
