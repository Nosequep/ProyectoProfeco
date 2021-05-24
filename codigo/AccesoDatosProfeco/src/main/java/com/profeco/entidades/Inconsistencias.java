/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "inconsistencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inconsistencias.findAll", query = "SELECT i FROM Inconsistencias i"),
    @NamedQuery(name = "Inconsistencias.findByPrecioreal", query = "SELECT i FROM Inconsistencias i WHERE i.precioreal = :precioreal"),
    @NamedQuery(name = "Inconsistencias.findByPreciopublicado", query = "SELECT i FROM Inconsistencias i WHERE i.preciopublicado = :preciopublicado"),
    @NamedQuery(name = "Inconsistencias.findByFecha", query = "SELECT i FROM Inconsistencias i WHERE i.fecha = :fecha"),
    @NamedQuery(name = "Inconsistencias.findByIdreporte", query = "SELECT i FROM Inconsistencias i WHERE i.idreporte = :idreporte")})
public class Inconsistencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "precioreal")
    private double precioreal;
    @Basic(optional = false)
    @Column(name = "preciopublicado")
    private double preciopublicado;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreporte")
    private Integer idreporte;
    @JoinColumn(name = "idcomercio", referencedColumnName = "idcomercio")
    @ManyToOne(optional = false)
    private Comercio idcomercio;
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private Producto idproducto;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario idusuario;

    public Inconsistencias() {
    }

    public Inconsistencias(Integer idreporte) {
        this.idreporte = idreporte;
    }

    public Inconsistencias(Integer idreporte, double precioreal, double preciopublicado, Date fecha) {
        this.idreporte = idreporte;
        this.precioreal = precioreal;
        this.preciopublicado = preciopublicado;
        this.fecha = fecha;
    }

    public double getPrecioreal() {
        return precioreal;
    }

    public void setPrecioreal(double precioreal) {
        this.precioreal = precioreal;
    }

    public double getPreciopublicado() {
        return preciopublicado;
    }

    public void setPreciopublicado(double preciopublicado) {
        this.preciopublicado = preciopublicado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdreporte() {
        return idreporte;
    }

    public void setIdreporte(Integer idreporte) {
        this.idreporte = idreporte;
    }

    public Comercio getIdcomercio() {
        return idcomercio;
    }

    public void setIdcomercio(Comercio idcomercio) {
        this.idcomercio = idcomercio;
    }

    public Producto getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreporte != null ? idreporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inconsistencias)) {
            return false;
        }
        Inconsistencias other = (Inconsistencias) object;
        if ((this.idreporte == null && other.idreporte != null) || (this.idreporte != null && !this.idreporte.equals(other.idreporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.profeco.entidades.Inconsistencias[ idreporte=" + idreporte + " ]";
    }
    
}
