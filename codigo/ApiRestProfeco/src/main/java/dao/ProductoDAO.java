package dao;

import java.util.ArrayList;
import java.util.List;

import entities.Producto;
public class ProductoDAO {
	private ArrayList<Producto> productos;
    
    
    public ProductoDAO(){
        this.productos = new ArrayList<Producto>();
        
        this.productos.add(new Producto(1, "Chetis", "Tiendita de la esquina", 10, 0.1));
        this.productos.add(new Producto(2, "Koka kola", "Tiendita de la esquina", 11, 0));
        this.productos.add(new Producto(2, "Leche Llaki", "Tiendita de la esquina", 12, 0));
        this.productos.add(new Producto(3, "Donitas bombo", "Tiendita de la esquina", 13, 0));
        this.productos.add(new Producto(4, "Galletas", "Tiendita de la esquina", 14, 0));
        this.productos.add(new Producto(5, "Chetis", "Tiendita de la otra esquina", 15, 0));
        
    }
    
    public List<Producto> obtener(String nombre){
        ArrayList<Producto> nuevo = new ArrayList<Producto>();
        for(Producto p : productos){
            if(p.getNombre().equalsIgnoreCase(nombre)){
                nuevo.add(p);
            }
        }
        return nuevo;
    }
}
