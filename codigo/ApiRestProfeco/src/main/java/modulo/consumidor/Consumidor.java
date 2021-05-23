/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo.consumidor;

import com.profeco.entidades.Producto;
import dao.ProductoDAO;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Consumidor {
    private ProductoDAO daoProducto;
    
    public Consumidor(ProductoDAO daoProducto){
        this.daoProducto = daoProducto;
    }
    
    public List<Producto> getProductByName(String nombre){
        return this.daoProducto.obtener(nombre);
    }
    
    public List<Producto> getOfertas(){
        return this.daoProducto.obtenerOfertas();
    }
}
