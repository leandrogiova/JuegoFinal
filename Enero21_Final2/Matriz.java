package Enero21_Final2;

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
	
	
	
	
	
	/*matrizACero pone un '-'(guion medio) en todos los casilleros para mostrar donde no se jugo
	 * No recibe argumentos.
	 * No devulve argumentos.
	 */
	public void matrizACero(){
		int e=0, i=0;
    
		for(i=0; i < 3; i++){
			for(e=0; e < 3; e++){
				matriz[i][e] = '-';
			}
		}
	} 


/*
 * rellenarCampo se encarga de marcar donde jugo cada jugador
 * Recibe 3 parametros:
 * 			fila = es el numero de la fila del casillero a jugar
 * 			columna = es el numero de la columna del casillero a jugar
 * 			campo = es el caracter. 'X' si juega jugadorOne y 'O' si juega la maquina(jugador='X'; maquina='O' )
 *	No devuelve argumentos
 */
public void rellenarCampo(int fila, int columna, char campo) {
		columna = columna - 1;
		fila = fila - 1;
		
		matriz[fila][columna] = campo;
	}
	
	
	
/*
 * Muestra la matriz y en donde se juego	
 */
	public  void mostrarMatriz() {
		int i=0, e=0;

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
	
	
	
	/*
	 * comprobarGanadorEmpate comprueba si hay ganador o si hay empate
	 * Recibe un argumento un char, ya sea del JugadorOne o de la maquina y comprueba si gano
	 * Recibe un char para la reutilizacion del codigo
	 * Retorna un numero entero, si retorna un:
	 * 			1 GANO jugadorOne
	 * 			2 GANO maquina
	 * 			3 EMAPATE
	 */
	
	public int comprobarGanadorEmpate(char campo) {
		
		int ganador = 0;
		int i=0, e=0, contador=0;

		
		if(campo == 'X') {	//verifica si gano el jugador 1	
			
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
			contador = 0;
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
		
		return ganador;
	}
	
	
	
}
