package entities;

public class Producto {
	private int id;
    private String nombre;
    private String comercio;
    private double precio;
    private double oferta;

    public Producto(int id, String nombre, String comercio, double precio, double oferta) {
        this.id = id;
        this.nombre = nombre;
        this.comercio = comercio;
        this.precio = precio;
        this.oferta = oferta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

	public double getOferta() {
		return oferta;
	}

	public void setOferta(double oferta) {
		this.oferta = oferta;
	}
    
    
	
}
