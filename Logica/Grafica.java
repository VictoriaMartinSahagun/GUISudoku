package Logica;

import javax.swing.ImageIcon;

public class Grafica {
	private ImageIcon grafico;
	private String[] imagenes;
	
	public Grafica() {
		this.grafico = new ImageIcon();
		this.imagenes = new String[]{"/Archivos/cero.png","/Archivos/uno.png", "/Archivos/dos.png","/Archivos/tres.png","/Archivos/cuatro.png",
				"/Archivos/cinco.png","/Archivos/seis.png","/Archivos/siete.png","/Archivos/ocho.png","/Archivos/nueve.png"};
	}
	
	public void actualizar(int indice) {
		if (indice < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}
	
	public ImageIcon getGrafico() {
		return this.grafico;
	}
	
	public void setGrafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
	
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
}
