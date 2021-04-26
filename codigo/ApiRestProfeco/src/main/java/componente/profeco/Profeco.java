/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componente.profeco;


import com.google.gson.Gson;
import dao.SancionDAO;
import entities.Sancion;


/**
 *
 * @author Lenovo
 */
public class Profeco {
    private SancionDAO daoSancion;
    
    public Profeco(SancionDAO daoSancion){
        this.daoSancion = daoSancion;
    }
    
    public Sancion sancionar(String sancion){
        Gson gson = new Gson();
        Sancion s = gson.fromJson(sancion, Sancion.class);
        this.daoSancion.agregar(s);
        return s;
    }
}
