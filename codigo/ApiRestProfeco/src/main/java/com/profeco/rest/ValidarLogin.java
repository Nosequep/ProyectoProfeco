/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.rest;

import com.profeco.controladores.UsuarioJpaController;
import com.profeco.entidades.Usuario;


/**
 *
 * @author Lenovo
 */
import java.util.List;
public class ValidarLogin {
    
    public static boolean validar(Usuario usuario){
        UsuarioJpaController jpa = new UsuarioJpaController();
        List<Usuario> usuarios = jpa.findUsuarioEntities();
        for(Usuario u : usuarios){
            if(u.getUsername().equals(usuario.getUsername()) && u.getContrasenia().equals(usuario.getContrasenia())){
                System.out.println("validacion exitosa");
                return true;
                
            }
        }
        return false;
    }
}
