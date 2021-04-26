/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componente.consumidor;

import dao.ProductoDAO;
import entities.Producto;
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
}
