package dao;

import java.util.ArrayList;
import java.util.List;

import entities.Producto;

public class ProductoDAO {

    private ArrayList<Producto> productos;

    public ProductoDAO() {
        this.productos = new ArrayList<Producto>();
    }

    public List<Producto> obtener(String nombre) {
        ArrayList<Producto> nuevo = new ArrayList<Producto>();
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                nuevo.add(p);
            }
        }
        return nuevo;
    }

    public Producto editarProducto(){
        return null;
    }
}
