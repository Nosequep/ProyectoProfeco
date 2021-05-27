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
import com.profeco.entidades.Sancion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dhtey
 */
public class SancionJpaController implements Serializable {

    public SancionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.profeco_AccesoDatosProfeco_jar_1.0-SNAPSHOTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sancion sancion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comercio idcomercio = sancion.getIdcomercio();
            if (idcomercio != null) {
                idcomercio = em.getReference(idcomercio.getClass(), idcomercio.getIdcomercio());
                sancion.setIdcomercio(idcomercio);
            }
            em.persist(sancion);
            if (idcomercio != null) {
                idcomercio.getSancionCollection().add(sancion);
                idcomercio = em.merge(idcomercio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sancion sancion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sancion persistentSancion = em.find(Sancion.class, sancion.getIdsancion());
            Comercio idcomercioOld = persistentSancion.getIdcomercio();
            Comercio idcomercioNew = sancion.getIdcomercio();
            if (idcomercioNew != null) {
                idcomercioNew = em.getReference(idcomercioNew.getClass(), idcomercioNew.getIdcomercio());
                sancion.setIdcomercio(idcomercioNew);
            }
            sancion = em.merge(sancion);
            if (idcomercioOld != null && !idcomercioOld.equals(idcomercioNew)) {
                idcomercioOld.getSancionCollection().remove(sancion);
                idcomercioOld = em.merge(idcomercioOld);
            }
            if (idcomercioNew != null && !idcomercioNew.equals(idcomercioOld)) {
                idcomercioNew.getSancionCollection().add(sancion);
                idcomercioNew = em.merge(idcomercioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sancion.getIdsancion();
                if (findSancion(id) == null) {
                    throw new NonexistentEntityException("The sancion with id " + id + " no longer exists.");
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
            Sancion sancion;
            try {
                sancion = em.getReference(Sancion.class, id);
                sancion.getIdsancion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sancion with id " + id + " no longer exists.", enfe);
            }
            Comercio idcomercio = sancion.getIdcomercio();
            if (idcomercio != null) {
                idcomercio.getSancionCollection().remove(sancion);
                idcomercio = em.merge(idcomercio);
            }
            em.remove(sancion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sancion> findSancionEntities() {
        return findSancionEntities(true, -1, -1);
    }

    public List<Sancion> findSancionEntities(int maxResults, int firstResult) {
        return findSancionEntities(false, maxResults, firstResult);
    }

    private List<Sancion> findSancionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sancion.class));
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

    public Sancion findSancion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sancion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSancionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sancion> rt = cq.from(Sancion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
