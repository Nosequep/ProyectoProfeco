/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import com.profeco.controladores.SancionJpaController;
import com.profeco.entidades.Sancion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class SancionDAO {
    private List<Sancion> sancion;
    private SancionJpaController sancionJPA;
    
    
    public SancionDAO(){
        sancionJPA = new SancionJpaController();
        this.sancion = sancionJPA.findSancionEntities();

        
    }
    
    public void agregar(Sancion sancion){
        this.sancion.add(sancion);
    }
    
    public List<Sancion> obtener(String nombre){
        return this.sancion;
    }
}
