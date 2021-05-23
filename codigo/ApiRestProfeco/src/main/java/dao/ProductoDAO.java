package dao;

import com.profeco.controladores.ProductoJpaController;
import com.profeco.entidades.Producto;
import java.util.ArrayList;
import java.util.List;


public class ProductoDAO {

    private List<Producto> productos;

    
    private ProductoJpaController productoJPA;
    
    public ProductoDAO() {
        productoJPA = new ProductoJpaController();
        this.productos = productoJPA.findProductoEntities();
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
    
    public List<Producto> obtenerOfertas(){
        ArrayList<Producto> ofertas = new ArrayList<Producto>();
        for (Producto producto : productos) {
            if (producto.getOferta() > 0.00) {
                ofertas.add(producto);
            }
        }
        return ofertas;
    }

    public Producto editarProducto(){
        return null;
    }
}
