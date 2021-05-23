/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.profeco.entidades.Sancion;
import dao.SancionDAO;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Comercio {
    private SancionDAO daoSancion;
    
    public Comercio(SancionDAO daoSancion){
        this.daoSancion = daoSancion;
    }
    
    public List<Sancion> leerSanciones(){
        return this.daoSancion.obtener("todas");
    }
}
