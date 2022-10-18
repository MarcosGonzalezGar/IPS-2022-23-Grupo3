package giis.demo.model;

public class Recurso {

	private int cantidad;
	private String nombre;
	
	public Recurso(String nombre, int cantidad) {
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	@Override
	public String toString() {
		return "Recurso: " + nombre + " - Cantidad: " + cantidad;
	}
}