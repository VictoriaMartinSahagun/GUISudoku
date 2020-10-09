package Logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;



public class Juego {
	private Celda [] [] tablero;
	private int cantFilas;
	private long tiempoInicio;
	
	public Juego() {
		tiempoInicio = System.currentTimeMillis();
		String linea;
		int numero;
		BufferedReader br;
		Scanner sc;
		Random rand = new Random();
		cantFilas=9;
		tablero = new Celda[cantFilas][cantFilas];
		br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/Archivos/InicioSudoku.txt")));
		try {
			for(int i=0; i<cantFilas && (linea=br.readLine())!=null;i++) {
				sc= new Scanner(linea);
				for(int j=0;j<cantFilas;j++) {
					numero=sc.nextInt();
					int randInt = rand.nextInt(8);
					tablero [i][j] = new Celda();
					if(randInt==0) {
						tablero[i][j].setValue(numero);
					}
				}
			}			
			br.close();
		}catch(IOException e) {}
	}
	
	public long getTiempoTranscurrido() {
		long tiempoActual = System.currentTimeMillis();
		long transcurrido = tiempoActual - tiempoInicio;
		return transcurrido;
	}
	
	public void setCelda(int i,int j, int numero) {
		if(i<cantFilas && j<cantFilas) {
			tablero[i][j].setValue(numero);	
		}
	}
	public Celda getCelda(int i, int j) {
		return this.tablero[i][j];
	}
	
	public int getCantFilas() {
		return this.cantFilas;
	}
	
	public void validarRespuesta(int i,int j) {
		int inicioF=0,inicioC=0;
		int numeroNuevo=tablero[i][j].getValue();
		for(int k=0;k<cantFilas;k++) {
			if(k!=j) {//verificar que en cada fila no se repitan numeros
				if(tablero[i][k].getValue()!=null) {
						if(tablero[i][k].getValue()==numeroNuevo) {
							tablero[i][j].setEstado(false);
							tablero[i][k].setEstado(false);
							System.out.println("falso"+i+".."+k+tablero[i][k].getValue());
						}else if(!tablero[i][k].getEstado()) {	//si la celda no fue puesta como falso en la verificacion actual y es falsa
							validarRespuesta(i,k);
						}
				}
			}
			if(k!=i) {
				if(tablero[k][j].getValue()!=null) {	//verifica que en cada columna no se repitan numeros
					if(tablero[k][j].getValue()==numeroNuevo) {
						tablero[i][j].setEstado(false);
						tablero[k][j].setEstado(false);
						System.out.println("falso"+"2"+k+"-"+j);
					}else if(!tablero[k][j].getEstado()) {	//si la celda no fue puesta como falso en la verificacion actual y es falsa
						validarRespuesta(k,j);
					}
				}
			}
		}
		//un mismo panel no puede contener los mismos numeros - supongo que los paneles son de 3*3
		while(inicioF < cantFilas-3) {	//busco la fila de inicio del panel
			if(inicioF+2>=i) {
				inicioF=inicioF+3;
			}
		}
		while(inicioC < cantFilas-3) {	//busco la columna de inicio del panel
			if(inicioC+2>=j) {
				inicioC=inicioC+3;
			}
		}
		for(int k=inicioF;k<inicioF+3;k++) {	
			for(int l=inicioC;l<inicioC+3;l++) {	
				if(k!=i && l!=j) {	//verifico los valores del panel
					if(tablero[k][l].getValue()!=null && tablero[k][l].getValue()==numeroNuevo) {
						tablero[i][j].setEstado(false);
						tablero[k][l].setEstado(false);
						System.out.println("falso"+"3"+k+"."+l);
					}else if(!tablero[k][l].getEstado()) {	//si la celda no fue puesta como falso en la verificacion actual y es falsa
						validarRespuesta(k,l);
					}
				}
			}
		}
	}
}
