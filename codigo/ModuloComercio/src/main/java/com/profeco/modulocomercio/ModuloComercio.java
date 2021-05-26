/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.modulocomercio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.profeco.controladores.ProductoJpaController;
import com.profeco.controladores.SancionJpaController;
import com.profeco.controladores.exceptions.IllegalOrphanException;
import com.profeco.controladores.exceptions.NonexistentEntityException;
import com.profeco.entidades.Comercio;
import com.profeco.entidades.Producto;
import com.profeco.entidades.Sancion;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ModuloComercio {
    
    public void subirProducto(String producto){
        ProductoJpaController jpaController = new ProductoJpaController();
        Gson gson =  new Gson();
        Producto p = gson.fromJson(producto, Producto.class);
        jpaController.create(p);
    }
    
    public void eliminarProducto(String producto) throws IllegalOrphanException, NonexistentEntityException{
        ProductoJpaController jpaController = new ProductoJpaController();
        Gson gson =  new Gson();
        Producto p = gson.fromJson(producto, Producto.class);
        jpaController.destroy(p.getIdproducto());
    }
    
    public String desplegarSanciones() throws JsonProcessingException{
        SancionJpaController jpaController = new SancionJpaController();
        List<Sancion> sanciones =jpaController.findSancionEntities();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(sanciones);
        return jsonString;
    }
}
