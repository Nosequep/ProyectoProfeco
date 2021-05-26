/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.moduloconsumidor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profeco.controladores.ProductoJpaController;
import com.profeco.entidades.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ModuloConsumidor {
    
    public String desplegarOfertas() throws JsonProcessingException{
        ProductoJpaController jpaController = new ProductoJpaController();
        List<Producto> productos = jpaController.findProductoEntities();
        List<Producto> ofertas = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getOferta()> 0.000 && producto.getOferta() <= 1) {
                ofertas.add(producto);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(ofertas);
        return jsonString;
    }
}
