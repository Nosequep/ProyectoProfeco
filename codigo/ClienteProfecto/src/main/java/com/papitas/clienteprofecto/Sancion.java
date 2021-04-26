/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitas.clienteprofecto;

/**
 *
 * @author Lenovo
 */
public class Sancion {
    private String cabecera;
    private String descripcion;
    private String castigos;
    private double multa;
    private String comercio;

    public Sancion(String cabecera, String descripcion, String castigos, double multa, String comercio) {
        this.cabecera = cabecera;
        this.descripcion = descripcion;
        this.castigos = castigos;
        this.multa = multa;
        this.comercio = comercio;
    }
    
    

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCastigos() {
        return castigos;
    }

    public void setCastigos(String castigos) {
        this.castigos = castigos;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }
    
    
}
