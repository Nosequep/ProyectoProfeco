/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dhtey
 */
@Entity
@Table(name = "sancion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sancion.findAll", query = "SELECT s FROM Sancion s"),
    @NamedQuery(name = "Sancion.findByIdsancion", query = "SELECT s FROM Sancion s WHERE s.idsancion = :idsancion"),
    @NamedQuery(name = "Sancion.findByCabecera", query = "SELECT s FROM Sancion s WHERE s.cabecera = :cabecera"),
    @NamedQuery(name = "Sancion.findByDescripcion", query = "SELECT s FROM Sancion s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Sancion.findByCastigos", query = "SELECT s FROM Sancion s WHERE s.castigos = :castigos"),
    @NamedQuery(name = "Sancion.findByMulta", query = "SELECT s FROM Sancion s WHERE s.multa = :multa")})
public class Sancion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsancion")
    private Integer idsancion;
    @Basic(optional = false)
    @Column(name = "cabecera")
    private String cabecera;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "castigos")
    private String castigos;
    @Basic(optional = false)
    @Column(name = "multa")
    private double multa;
    @JsonIgnore
    @JoinColumn(name = "idcomercio", referencedColumnName = "idcomercio")
    @ManyToOne
    private Comercio idcomercio;

    public Sancion() {
    }

    public Sancion(Integer idsancion) {
        this.idsancion = idsancion;
    }

    public Sancion(Integer idsancion, String cabecera, String descripcion, double multa) {
        this.idsancion = idsancion;
        this.cabecera = cabecera;
        this.descripcion = descripcion;
        this.multa = multa;
    }

    public Integer getIdsancion() {
        return idsancion;
    }

    public void setIdsancion(Integer idsancion) {
        this.idsancion = idsancion;
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

    public Comercio getIdcomercio() {
        return idcomercio;
    }

    public void setIdcomercio(Comercio idcomercio) {
        this.idcomercio = idcomercio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsancion != null ? idsancion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sancion)) {
            return false;
        }
        Sancion other = (Sancion) object;
        if ((this.idsancion == null && other.idsancion != null) || (this.idsancion != null && !this.idsancion.equals(other.idsancion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.profeco.entidades.Sancion[ idsancion=" + idsancion + " ]";
    }
    
}
