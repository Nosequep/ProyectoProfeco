/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba;

import Envia.Envia;
import Recibe.Recibe;

/**
 *
 * @author Dhtey
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Envia envia = new Envia("JUEGOS");
        Recibe recibe = new Recibe("JUEGOS");
        
//        System.out.println(recibe.recibe());
        
        envia.envia("Puto el que lo lea ");
//        while(recibe.recibe() != null){
            System.out.println(recibe.recibe());
//        }
        
        
        
//        envia.envia("prueba 1");
//        System.out.println(recibe.recibe());
//        envia.envia("prueba 2");
//        System.out.println(recibe.recibe());
//        envia.envia("prueba 3");
//        System.out.println(recibe.recibe());
//        envia.envia("prueba 4");
//        System.out.println(recibe.recibe());
//        envia.envia("prueba 5");
//        System.out.println(recibe.recibe());
    }
    
}
