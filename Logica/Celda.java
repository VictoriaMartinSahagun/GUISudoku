package Logica;

public class Celda {
	private Integer valor;
	private Grafica entidadGrafica;
	private boolean estadoUbicacion;
	
	public Celda() {
		this.valor = null;
		this.entidadGrafica = new Grafica();
		this.estadoUbicacion = true;
	}
	
	public boolean getEstado() {
		return this.estadoUbicacion;
	}
	
	public void setEstado(boolean estado) {
		this.estadoUbicacion=estado;
	}
	
	public int getCantElementos() {
		return this.entidadGrafica.getImagenes().length;
	}
	
	
	public Integer getValue() {
		return this.valor;
	}
	
	public void setValue(Integer valor) {
		if (valor!=null && valor < this.getCantElementos()) {
			this.valor = valor;
			this.entidadGrafica.actualizar(this.valor);
		}else {
			this.valor = null;
		}
	}

	public Grafica getEntidadGrafica() {
		return entidadGrafica;
	}

}
