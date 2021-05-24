/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.controladores;

import com.profeco.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.profeco.entidades.Comercio;
import com.profeco.entidades.Inconsistencias;
import com.profeco.entidades.Producto;
import com.profeco.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lenovo
 */
public class InconsistenciasJpaController implements Serializable {

    public InconsistenciasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.profeco_AccesoDatosProfeco_jar_1.0-SNAPSHOTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inconsistencias inconsistencias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comercio idcomercio = inconsistencias.getIdcomercio();
            if (idcomercio != null) {
                idcomercio = em.getReference(idcomercio.getClass(), idcomercio.getIdcomercio());
                inconsistencias.setIdcomercio(idcomercio);
            }
            Producto idproducto = inconsistencias.getIdproducto();
            if (idproducto != null) {
                idproducto = em.getReference(idproducto.getClass(), idproducto.getIdproducto());
                inconsistencias.setIdproducto(idproducto);
            }
            Usuario idusuario = inconsistencias.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
                inconsistencias.setIdusuario(idusuario);
            }
            em.persist(inconsistencias);
            if (idcomercio != null) {
                idcomercio.getInconsistenciasCollection().add(inconsistencias);
                idcomercio = em.merge(idcomercio);
            }
            if (idproducto != null) {
                idproducto.getInconsistenciasCollection().add(inconsistencias);
                idproducto = em.merge(idproducto);
            }
            if (idusuario != null) {
                idusuario.getInconsistenciasCollection().add(inconsistencias);
                idusuario = em.merge(idusuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inconsistencias inconsistencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inconsistencias persistentInconsistencias = em.find(Inconsistencias.class, inconsistencias.getIdreporte());
            Comercio idcomercioOld = persistentInconsistencias.getIdcomercio();
            Comercio idcomercioNew = inconsistencias.getIdcomercio();
            Producto idproductoOld = persistentInconsistencias.getIdproducto();
            Producto idproductoNew = inconsistencias.getIdproducto();
            Usuario idusuarioOld = persistentInconsistencias.getIdusuario();
            Usuario idusuarioNew = inconsistencias.getIdusuario();
            if (idcomercioNew != null) {
                idcomercioNew = em.getReference(idcomercioNew.getClass(), idcomercioNew.getIdcomercio());
                inconsistencias.setIdcomercio(idcomercioNew);
            }
            if (idproductoNew != null) {
                idproductoNew = em.getReference(idproductoNew.getClass(), idproductoNew.getIdproducto());
                inconsistencias.setIdproducto(idproductoNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                inconsistencias.setIdusuario(idusuarioNew);
            }
            inconsistencias = em.merge(inconsistencias);
            if (idcomercioOld != null && !idcomercioOld.equals(idcomercioNew)) {
                idcomercioOld.getInconsistenciasCollection().remove(inconsistencias);
                idcomercioOld = em.merge(idcomercioOld);
            }
            if (idcomercioNew != null && !idcomercioNew.equals(idcomercioOld)) {
                idcomercioNew.getInconsistenciasCollection().add(inconsistencias);
                idcomercioNew = em.merge(idcomercioNew);
            }
            if (idproductoOld != null && !idproductoOld.equals(idproductoNew)) {
                idproductoOld.getInconsistenciasCollection().remove(inconsistencias);
                idproductoOld = em.merge(idproductoOld);
            }
            if (idproductoNew != null && !idproductoNew.equals(idproductoOld)) {
                idproductoNew.getInconsistenciasCollection().add(inconsistencias);
                idproductoNew = em.merge(idproductoNew);
            }
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getInconsistenciasCollection().remove(inconsistencias);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getInconsistenciasCollection().add(inconsistencias);
                idusuarioNew = em.merge(idusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inconsistencias.getIdreporte();
                if (findInconsistencias(id) == null) {
                    throw new NonexistentEntityException("The inconsistencias with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inconsistencias inconsistencias;
            try {
                inconsistencias = em.getReference(Inconsistencias.class, id);
                inconsistencias.getIdreporte();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inconsistencias with id " + id + " no longer exists.", enfe);
            }
            Comercio idcomercio = inconsistencias.getIdcomercio();
            if (idcomercio != null) {
                idcomercio.getInconsistenciasCollection().remove(inconsistencias);
                idcomercio = em.merge(idcomercio);
            }
            Producto idproducto = inconsistencias.getIdproducto();
            if (idproducto != null) {
                idproducto.getInconsistenciasCollection().remove(inconsistencias);
                idproducto = em.merge(idproducto);
            }
            Usuario idusuario = inconsistencias.getIdusuario();
            if (idusuario != null) {
                idusuario.getInconsistenciasCollection().remove(inconsistencias);
                idusuario = em.merge(idusuario);
            }
            em.remove(inconsistencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inconsistencias> findInconsistenciasEntities() {
        return findInconsistenciasEntities(true, -1, -1);
    }

    public List<Inconsistencias> findInconsistenciasEntities(int maxResults, int firstResult) {
        return findInconsistenciasEntities(false, maxResults, firstResult);
    }

    private List<Inconsistencias> findInconsistenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inconsistencias.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Inconsistencias findInconsistencias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inconsistencias.class, id);
        } finally {
            em.close();
        }
    }

    public int getInconsistenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inconsistencias> rt = cq.from(Inconsistencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
