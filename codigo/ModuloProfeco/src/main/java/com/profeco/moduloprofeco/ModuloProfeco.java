/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.moduloprofeco;

import com.google.gson.Gson;
import com.profeco.controladores.SancionJpaController;
import com.profeco.entidades.Sancion;

/**
 *
 * @author Lenovo
 */
public class ModuloProfeco {
    
    public void subirSanciones(String sancion) throws Exception {
        SancionJpaController jpaController = new SancionJpaController();
        Gson gson =  new Gson();
        Sancion p = gson.fromJson(sancion, Sancion.class);
       
        jpaController.create(p);

    }
}
