/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Producto;
import entities.Sancion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class SancionDAO {
    private ArrayList<Sancion> sancion;
    
    
    public SancionDAO(){
        this.sancion = new ArrayList();

        
    }
    
    public void agregar(Sancion sancion){
        this.sancion.add(sancion);
    }
    
    public List<Sancion> obtener(String nombre){
        return this.sancion;
    }
}
