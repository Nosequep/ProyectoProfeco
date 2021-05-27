/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.controladores;

import com.profeco.controladores.exceptions.IllegalOrphanException;
import com.profeco.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.profeco.entidades.Comercio;
import com.profeco.entidades.Inconsistencias;
import com.profeco.entidades.Producto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dhtey
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.profeco_AccesoDatosProfeco_jar_1.0-SNAPSHOTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getInconsistenciasCollection() == null) {
            producto.setInconsistenciasCollection(new ArrayList<Inconsistencias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comercio idcomercio = producto.getIdcomercio();
            if (idcomercio != null) {
                idcomercio = em.getReference(idcomercio.getClass(), idcomercio.getIdcomercio());
                producto.setIdcomercio(idcomercio);
            }
            Collection<Inconsistencias> attachedInconsistenciasCollection = new ArrayList<Inconsistencias>();
            for (Inconsistencias inconsistenciasCollectionInconsistenciasToAttach : producto.getInconsistenciasCollection()) {
                inconsistenciasCollectionInconsistenciasToAttach = em.getReference(inconsistenciasCollectionInconsistenciasToAttach.getClass(), inconsistenciasCollectionInconsistenciasToAttach.getIdreporte());
                attachedInconsistenciasCollection.add(inconsistenciasCollectionInconsistenciasToAttach);
            }
            producto.setInconsistenciasCollection(attachedInconsistenciasCollection);
            em.persist(producto);
            if (idcomercio != null) {
                idcomercio.getProductoCollection().add(producto);
                idcomercio = em.merge(idcomercio);
            }
            for (Inconsistencias inconsistenciasCollectionInconsistencias : producto.getInconsistenciasCollection()) {
                Producto oldIdproductoOfInconsistenciasCollectionInconsistencias = inconsistenciasCollectionInconsistencias.getIdproducto();
                inconsistenciasCollectionInconsistencias.setIdproducto(producto);
                inconsistenciasCollectionInconsistencias = em.merge(inconsistenciasCollectionInconsistencias);
                if (oldIdproductoOfInconsistenciasCollectionInconsistencias != null) {
                    oldIdproductoOfInconsistenciasCollectionInconsistencias.getInconsistenciasCollection().remove(inconsistenciasCollectionInconsistencias);
                    oldIdproductoOfInconsistenciasCollectionInconsistencias = em.merge(oldIdproductoOfInconsistenciasCollectionInconsistencias);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdproducto());
            Comercio idcomercioOld = persistentProducto.getIdcomercio();
            Comercio idcomercioNew = producto.getIdcomercio();
            Collection<Inconsistencias> inconsistenciasCollectionOld = persistentProducto.getInconsistenciasCollection();
            Collection<Inconsistencias> inconsistenciasCollectionNew = producto.getInconsistenciasCollection();
            List<String> illegalOrphanMessages = null;
            for (Inconsistencias inconsistenciasCollectionOldInconsistencias : inconsistenciasCollectionOld) {
                if (!inconsistenciasCollectionNew.contains(inconsistenciasCollectionOldInconsistencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inconsistencias " + inconsistenciasCollectionOldInconsistencias + " since its idproducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idcomercioNew != null) {
                idcomercioNew = em.getReference(idcomercioNew.getClass(), idcomercioNew.getIdcomercio());
                producto.setIdcomercio(idcomercioNew);
            }
            Collection<Inconsistencias> attachedInconsistenciasCollectionNew = new ArrayList<Inconsistencias>();
            for (Inconsistencias inconsistenciasCollectionNewInconsistenciasToAttach : inconsistenciasCollectionNew) {
                inconsistenciasCollectionNewInconsistenciasToAttach = em.getReference(inconsistenciasCollectionNewInconsistenciasToAttach.getClass(), inconsistenciasCollectionNewInconsistenciasToAttach.getIdreporte());
                attachedInconsistenciasCollectionNew.add(inconsistenciasCollectionNewInconsistenciasToAttach);
            }
            inconsistenciasCollectionNew = attachedInconsistenciasCollectionNew;
            producto.setInconsistenciasCollection(inconsistenciasCollectionNew);
            producto = em.merge(producto);
            if (idcomercioOld != null && !idcomercioOld.equals(idcomercioNew)) {
                idcomercioOld.getProductoCollection().remove(producto);
                idcomercioOld = em.merge(idcomercioOld);
            }
            if (idcomercioNew != null && !idcomercioNew.equals(idcomercioOld)) {
                idcomercioNew.getProductoCollection().add(producto);
                idcomercioNew = em.merge(idcomercioNew);
            }
            for (Inconsistencias inconsistenciasCollectionNewInconsistencias : inconsistenciasCollectionNew) {
                if (!inconsistenciasCollectionOld.contains(inconsistenciasCollectionNewInconsistencias)) {
                    Producto oldIdproductoOfInconsistenciasCollectionNewInconsistencias = inconsistenciasCollectionNewInconsistencias.getIdproducto();
                    inconsistenciasCollectionNewInconsistencias.setIdproducto(producto);
                    inconsistenciasCollectionNewInconsistencias = em.merge(inconsistenciasCollectionNewInconsistencias);
                    if (oldIdproductoOfInconsistenciasCollectionNewInconsistencias != null && !oldIdproductoOfInconsistenciasCollectionNewInconsistencias.equals(producto)) {
                        oldIdproductoOfInconsistenciasCollectionNewInconsistencias.getInconsistenciasCollection().remove(inconsistenciasCollectionNewInconsistencias);
                        oldIdproductoOfInconsistenciasCollectionNewInconsistencias = em.merge(oldIdproductoOfInconsistenciasCollectionNewInconsistencias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdproducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdproducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Inconsistencias> inconsistenciasCollectionOrphanCheck = producto.getInconsistenciasCollection();
            for (Inconsistencias inconsistenciasCollectionOrphanCheckInconsistencias : inconsistenciasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Inconsistencias " + inconsistenciasCollectionOrphanCheckInconsistencias + " in its inconsistenciasCollection field has a non-nullable idproducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Comercio idcomercio = producto.getIdcomercio();
            if (idcomercio != null) {
                idcomercio.getProductoCollection().remove(producto);
                idcomercio = em.merge(idcomercio);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
