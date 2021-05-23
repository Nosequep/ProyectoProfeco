/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.controladores;

import com.profeco.controladores.exceptions.NonexistentEntityException;
import com.profeco.controladores.exceptions.PreexistingEntityException;
import com.profeco.entidades.Calificacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.profeco.entidades.Comercio;
import com.profeco.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dhtey
 */
public class CalificacionJpaController implements Serializable {

    public CalificacionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.profeco_AccesoDatosProfeco_jar_1.0-SNAPSHOTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calificacion calificacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comercio idComercio = calificacion.getIdComercio();
            if (idComercio != null) {
                idComercio = em.getReference(idComercio.getClass(), idComercio.getIdcomercio());
                calificacion.setIdComercio(idComercio);
            }
            Usuario idUsuario = calificacion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdusuario());
                calificacion.setIdUsuario(idUsuario);
            }
            em.persist(calificacion);
            if (idComercio != null) {
                idComercio.getCalificacionCollection().add(calificacion);
                idComercio = em.merge(idComercio);
            }
            if (idUsuario != null) {
                idUsuario.getCalificacionCollection().add(calificacion);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCalificacion(calificacion.getIdCalificacion()) != null) {
                throw new PreexistingEntityException("Calificacion " + calificacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Calificacion calificacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificacion persistentCalificacion = em.find(Calificacion.class, calificacion.getIdCalificacion());
            Comercio idComercioOld = persistentCalificacion.getIdComercio();
            Comercio idComercioNew = calificacion.getIdComercio();
            Usuario idUsuarioOld = persistentCalificacion.getIdUsuario();
            Usuario idUsuarioNew = calificacion.getIdUsuario();
            if (idComercioNew != null) {
                idComercioNew = em.getReference(idComercioNew.getClass(), idComercioNew.getIdcomercio());
                calificacion.setIdComercio(idComercioNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdusuario());
                calificacion.setIdUsuario(idUsuarioNew);
            }
            calificacion = em.merge(calificacion);
            if (idComercioOld != null && !idComercioOld.equals(idComercioNew)) {
                idComercioOld.getCalificacionCollection().remove(calificacion);
                idComercioOld = em.merge(idComercioOld);
            }
            if (idComercioNew != null && !idComercioNew.equals(idComercioOld)) {
                idComercioNew.getCalificacionCollection().add(calificacion);
                idComercioNew = em.merge(idComercioNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getCalificacionCollection().remove(calificacion);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getCalificacionCollection().add(calificacion);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = calificacion.getIdCalificacion();
                if (findCalificacion(id) == null) {
                    throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.");
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
            Calificacion calificacion;
            try {
                calificacion = em.getReference(Calificacion.class, id);
                calificacion.getIdCalificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.", enfe);
            }
            Comercio idComercio = calificacion.getIdComercio();
            if (idComercio != null) {
                idComercio.getCalificacionCollection().remove(calificacion);
                idComercio = em.merge(idComercio);
            }
            Usuario idUsuario = calificacion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getCalificacionCollection().remove(calificacion);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(calificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Calificacion> findCalificacionEntities() {
        return findCalificacionEntities(true, -1, -1);
    }

    public List<Calificacion> findCalificacionEntities(int maxResults, int firstResult) {
        return findCalificacionEntities(false, maxResults, firstResult);
    }

    private List<Calificacion> findCalificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calificacion.class));
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

    public Calificacion findCalificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calificacion> rt = cq.from(Calificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
