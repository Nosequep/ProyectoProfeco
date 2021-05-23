/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dhtey
 */
@Entity
@Table(name = "comercio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comercio.findAll", query = "SELECT c FROM Comercio c"),
    @NamedQuery(name = "Comercio.findByIdcomercio", query = "SELECT c FROM Comercio c WHERE c.idcomercio = :idcomercio"),
    @NamedQuery(name = "Comercio.findByNombre", query = "SELECT c FROM Comercio c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Comercio.findByCalificacion", query = "SELECT c FROM Comercio c WHERE c.calificacion = :calificacion")})
public class Comercio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idcomercio")
    private Integer idcomercio;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "calificacion")
    private int calificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idComercio")
    private Collection<Calificacion> calificacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcomercio")
    private Collection<Inconsistencias> inconsistenciasCollection;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario idusuario;
    @OneToMany(mappedBy = "idcomercio")
    private Collection<Sancion> sancionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcomercio")
    private Collection<Producto> productoCollection;

    public Comercio() {
    }

    public Comercio(Integer idcomercio) {
        this.idcomercio = idcomercio;
    }

    public Comercio(Integer idcomercio, String nombre, int calificacion) {
        this.idcomercio = idcomercio;
        this.nombre = nombre;
        this.calificacion = calificacion;
    }

    public Integer getIdcomercio() {
        return idcomercio;
    }

    public void setIdcomercio(Integer idcomercio) {
        this.idcomercio = idcomercio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    @XmlTransient
    public Collection<Calificacion> getCalificacionCollection() {
        return calificacionCollection;
    }

    public void setCalificacionCollection(Collection<Calificacion> calificacionCollection) {
        this.calificacionCollection = calificacionCollection;
    }

    @XmlTransient
    public Collection<Inconsistencias> getInconsistenciasCollection() {
        return inconsistenciasCollection;
    }

    public void setInconsistenciasCollection(Collection<Inconsistencias> inconsistenciasCollection) {
        this.inconsistenciasCollection = inconsistenciasCollection;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @XmlTransient
    public Collection<Sancion> getSancionCollection() {
        return sancionCollection;
    }

    public void setSancionCollection(Collection<Sancion> sancionCollection) {
        this.sancionCollection = sancionCollection;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomercio != null ? idcomercio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comercio)) {
            return false;
        }
        Comercio other = (Comercio) object;
        if ((this.idcomercio == null && other.idcomercio != null) || (this.idcomercio != null && !this.idcomercio.equals(other.idcomercio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.profeco.entidades.Comercio[ idcomercio=" + idcomercio + " ]";
    }
    
}
