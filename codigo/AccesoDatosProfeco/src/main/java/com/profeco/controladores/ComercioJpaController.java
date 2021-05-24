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
import com.profeco.entidades.Usuario;
import com.profeco.entidades.Calificacion;
import com.profeco.entidades.Comercio;
import java.util.ArrayList;
import java.util.Collection;
import com.profeco.entidades.Inconsistencias;
import com.profeco.entidades.Sancion;
import com.profeco.entidades.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lenovo
 */
public class ComercioJpaController implements Serializable {

    public ComercioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.profeco_AccesoDatosProfeco_jar_1.0-SNAPSHOTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comercio comercio) {
        if (comercio.getCalificacionCollection() == null) {
            comercio.setCalificacionCollection(new ArrayList<Calificacion>());
        }
        if (comercio.getInconsistenciasCollection() == null) {
            comercio.setInconsistenciasCollection(new ArrayList<Inconsistencias>());
        }
        if (comercio.getSancionCollection() == null) {
            comercio.setSancionCollection(new ArrayList<Sancion>());
        }
        if (comercio.getProductoCollection() == null) {
            comercio.setProductoCollection(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idusuario = comercio.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
                comercio.setIdusuario(idusuario);
            }
            Collection<Calificacion> attachedCalificacionCollection = new ArrayList<Calificacion>();
            for (Calificacion calificacionCollectionCalificacionToAttach : comercio.getCalificacionCollection()) {
                calificacionCollectionCalificacionToAttach = em.getReference(calificacionCollectionCalificacionToAttach.getClass(), calificacionCollectionCalificacionToAttach.getIdCalificacion());
                attachedCalificacionCollection.add(calificacionCollectionCalificacionToAttach);
            }
            comercio.setCalificacionCollection(attachedCalificacionCollection);
            Collection<Inconsistencias> attachedInconsistenciasCollection = new ArrayList<Inconsistencias>();
            for (Inconsistencias inconsistenciasCollectionInconsistenciasToAttach : comercio.getInconsistenciasCollection()) {
                inconsistenciasCollectionInconsistenciasToAttach = em.getReference(inconsistenciasCollectionInconsistenciasToAttach.getClass(), inconsistenciasCollectionInconsistenciasToAttach.getIdreporte());
                attachedInconsistenciasCollection.add(inconsistenciasCollectionInconsistenciasToAttach);
            }
            comercio.setInconsistenciasCollection(attachedInconsistenciasCollection);
            Collection<Sancion> attachedSancionCollection = new ArrayList<Sancion>();
            for (Sancion sancionCollectionSancionToAttach : comercio.getSancionCollection()) {
                sancionCollectionSancionToAttach = em.getReference(sancionCollectionSancionToAttach.getClass(), sancionCollectionSancionToAttach.getIdsancion());
                attachedSancionCollection.add(sancionCollectionSancionToAttach);
            }
            comercio.setSancionCollection(attachedSancionCollection);
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : comercio.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getIdproducto());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            comercio.setProductoCollection(attachedProductoCollection);
            em.persist(comercio);
            if (idusuario != null) {
                idusuario.getComercioCollection().add(comercio);
                idusuario = em.merge(idusuario);
            }
            for (Calificacion calificacionCollectionCalificacion : comercio.getCalificacionCollection()) {
                Comercio oldIdComercioOfCalificacionCollectionCalificacion = calificacionCollectionCalificacion.getIdComercio();
                calificacionCollectionCalificacion.setIdComercio(comercio);
                calificacionCollectionCalificacion = em.merge(calificacionCollectionCalificacion);
                if (oldIdComercioOfCalificacionCollectionCalificacion != null) {
                    oldIdComercioOfCalificacionCollectionCalificacion.getCalificacionCollection().remove(calificacionCollectionCalificacion);
                    oldIdComercioOfCalificacionCollectionCalificacion = em.merge(oldIdComercioOfCalificacionCollectionCalificacion);
                }
            }
            for (Inconsistencias inconsistenciasCollectionInconsistencias : comercio.getInconsistenciasCollection()) {
                Comercio oldIdcomercioOfInconsistenciasCollectionInconsistencias = inconsistenciasCollectionInconsistencias.getIdcomercio();
                inconsistenciasCollectionInconsistencias.setIdcomercio(comercio);
                inconsistenciasCollectionInconsistencias = em.merge(inconsistenciasCollectionInconsistencias);
                if (oldIdcomercioOfInconsistenciasCollectionInconsistencias != null) {
                    oldIdcomercioOfInconsistenciasCollectionInconsistencias.getInconsistenciasCollection().remove(inconsistenciasCollectionInconsistencias);
                    oldIdcomercioOfInconsistenciasCollectionInconsistencias = em.merge(oldIdcomercioOfInconsistenciasCollectionInconsistencias);
                }
            }
            for (Sancion sancionCollectionSancion : comercio.getSancionCollection()) {
                Comercio oldIdcomercioOfSancionCollectionSancion = sancionCollectionSancion.getIdcomercio();
                sancionCollectionSancion.setIdcomercio(comercio);
                sancionCollectionSancion = em.merge(sancionCollectionSancion);
                if (oldIdcomercioOfSancionCollectionSancion != null) {
                    oldIdcomercioOfSancionCollectionSancion.getSancionCollection().remove(sancionCollectionSancion);
                    oldIdcomercioOfSancionCollectionSancion = em.merge(oldIdcomercioOfSancionCollectionSancion);
                }
            }
            for (Producto productoCollectionProducto : comercio.getProductoCollection()) {
                Comercio oldIdcomercioOfProductoCollectionProducto = productoCollectionProducto.getIdcomercio();
                productoCollectionProducto.setIdcomercio(comercio);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldIdcomercioOfProductoCollectionProducto != null) {
                    oldIdcomercioOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldIdcomercioOfProductoCollectionProducto = em.merge(oldIdcomercioOfProductoCollectionProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comercio comercio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comercio persistentComercio = em.find(Comercio.class, comercio.getIdcomercio());
            Usuario idusuarioOld = persistentComercio.getIdusuario();
            Usuario idusuarioNew = comercio.getIdusuario();
            Collection<Calificacion> calificacionCollectionOld = persistentComercio.getCalificacionCollection();
            Collection<Calificacion> calificacionCollectionNew = comercio.getCalificacionCollection();
            Collection<Inconsistencias> inconsistenciasCollectionOld = persistentComercio.getInconsistenciasCollection();
            Collection<Inconsistencias> inconsistenciasCollectionNew = comercio.getInconsistenciasCollection();
            Collection<Sancion> sancionCollectionOld = persistentComercio.getSancionCollection();
            Collection<Sancion> sancionCollectionNew = comercio.getSancionCollection();
            Collection<Producto> productoCollectionOld = persistentComercio.getProductoCollection();
            Collection<Producto> productoCollectionNew = comercio.getProductoCollection();
            List<String> illegalOrphanMessages = null;
            for (Calificacion calificacionCollectionOldCalificacion : calificacionCollectionOld) {
                if (!calificacionCollectionNew.contains(calificacionCollectionOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionCollectionOldCalificacion + " since its idComercio field is not nullable.");
                }
            }
            for (Inconsistencias inconsistenciasCollectionOldInconsistencias : inconsistenciasCollectionOld) {
                if (!inconsistenciasCollectionNew.contains(inconsistenciasCollectionOldInconsistencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inconsistencias " + inconsistenciasCollectionOldInconsistencias + " since its idcomercio field is not nullable.");
                }
            }
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoCollectionOldProducto + " since its idcomercio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                comercio.setIdusuario(idusuarioNew);
            }
            Collection<Calificacion> attachedCalificacionCollectionNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionCollectionNewCalificacionToAttach : calificacionCollectionNew) {
                calificacionCollectionNewCalificacionToAttach = em.getReference(calificacionCollectionNewCalificacionToAttach.getClass(), calificacionCollectionNewCalificacionToAttach.getIdCalificacion());
                attachedCalificacionCollectionNew.add(calificacionCollectionNewCalificacionToAttach);
            }
            calificacionCollectionNew = attachedCalificacionCollectionNew;
            comercio.setCalificacionCollection(calificacionCollectionNew);
            Collection<Inconsistencias> attachedInconsistenciasCollectionNew = new ArrayList<Inconsistencias>();
            for (Inconsistencias inconsistenciasCollectionNewInconsistenciasToAttach : inconsistenciasCollectionNew) {
                inconsistenciasCollectionNewInconsistenciasToAttach = em.getReference(inconsistenciasCollectionNewInconsistenciasToAttach.getClass(), inconsistenciasCollectionNewInconsistenciasToAttach.getIdreporte());
                attachedInconsistenciasCollectionNew.add(inconsistenciasCollectionNewInconsistenciasToAttach);
            }
            inconsistenciasCollectionNew = attachedInconsistenciasCollectionNew;
            comercio.setInconsistenciasCollection(inconsistenciasCollectionNew);
            Collection<Sancion> attachedSancionCollectionNew = new ArrayList<Sancion>();
            for (Sancion sancionCollectionNewSancionToAttach : sancionCollectionNew) {
                sancionCollectionNewSancionToAttach = em.getReference(sancionCollectionNewSancionToAttach.getClass(), sancionCollectionNewSancionToAttach.getIdsancion());
                attachedSancionCollectionNew.add(sancionCollectionNewSancionToAttach);
            }
            sancionCollectionNew = attachedSancionCollectionNew;
            comercio.setSancionCollection(sancionCollectionNew);
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getIdproducto());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            comercio.setProductoCollection(productoCollectionNew);
            comercio = em.merge(comercio);
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getComercioCollection().remove(comercio);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getComercioCollection().add(comercio);
                idusuarioNew = em.merge(idusuarioNew);
            }
            for (Calificacion calificacionCollectionNewCalificacion : calificacionCollectionNew) {
                if (!calificacionCollectionOld.contains(calificacionCollectionNewCalificacion)) {
                    Comercio oldIdComercioOfCalificacionCollectionNewCalificacion = calificacionCollectionNewCalificacion.getIdComercio();
                    calificacionCollectionNewCalificacion.setIdComercio(comercio);
                    calificacionCollectionNewCalificacion = em.merge(calificacionCollectionNewCalificacion);
                    if (oldIdComercioOfCalificacionCollectionNewCalificacion != null && !oldIdComercioOfCalificacionCollectionNewCalificacion.equals(comercio)) {
                        oldIdComercioOfCalificacionCollectionNewCalificacion.getCalificacionCollection().remove(calificacionCollectionNewCalificacion);
                        oldIdComercioOfCalificacionCollectionNewCalificacion = em.merge(oldIdComercioOfCalificacionCollectionNewCalificacion);
                    }
                }
            }
            for (Inconsistencias inconsistenciasCollectionNewInconsistencias : inconsistenciasCollectionNew) {
                if (!inconsistenciasCollectionOld.contains(inconsistenciasCollectionNewInconsistencias)) {
                    Comercio oldIdcomercioOfInconsistenciasCollectionNewInconsistencias = inconsistenciasCollectionNewInconsistencias.getIdcomercio();
                    inconsistenciasCollectionNewInconsistencias.setIdcomercio(comercio);
                    inconsistenciasCollectionNewInconsistencias = em.merge(inconsistenciasCollectionNewInconsistencias);
                    if (oldIdcomercioOfInconsistenciasCollectionNewInconsistencias != null && !oldIdcomercioOfInconsistenciasCollectionNewInconsistencias.equals(comercio)) {
                        oldIdcomercioOfInconsistenciasCollectionNewInconsistencias.getInconsistenciasCollection().remove(inconsistenciasCollectionNewInconsistencias);
                        oldIdcomercioOfInconsistenciasCollectionNewInconsistencias = em.merge(oldIdcomercioOfInconsistenciasCollectionNewInconsistencias);
                    }
                }
            }
            for (Sancion sancionCollectionOldSancion : sancionCollectionOld) {
                if (!sancionCollectionNew.contains(sancionCollectionOldSancion)) {
                    sancionCollectionOldSancion.setIdcomercio(null);
                    sancionCollectionOldSancion = em.merge(sancionCollectionOldSancion);
                }
            }
            for (Sancion sancionCollectionNewSancion : sancionCollectionNew) {
                if (!sancionCollectionOld.contains(sancionCollectionNewSancion)) {
                    Comercio oldIdcomercioOfSancionCollectionNewSancion = sancionCollectionNewSancion.getIdcomercio();
                    sancionCollectionNewSancion.setIdcomercio(comercio);
                    sancionCollectionNewSancion = em.merge(sancionCollectionNewSancion);
                    if (oldIdcomercioOfSancionCollectionNewSancion != null && !oldIdcomercioOfSancionCollectionNewSancion.equals(comercio)) {
                        oldIdcomercioOfSancionCollectionNewSancion.getSancionCollection().remove(sancionCollectionNewSancion);
                        oldIdcomercioOfSancionCollectionNewSancion = em.merge(oldIdcomercioOfSancionCollectionNewSancion);
                    }
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    Comercio oldIdcomercioOfProductoCollectionNewProducto = productoCollectionNewProducto.getIdcomercio();
                    productoCollectionNewProducto.setIdcomercio(comercio);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldIdcomercioOfProductoCollectionNewProducto != null && !oldIdcomercioOfProductoCollectionNewProducto.equals(comercio)) {
                        oldIdcomercioOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldIdcomercioOfProductoCollectionNewProducto = em.merge(oldIdcomercioOfProductoCollectionNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comercio.getIdcomercio();
                if (findComercio(id) == null) {
                    throw new NonexistentEntityException("The comercio with id " + id + " no longer exists.");
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
            Comercio comercio;
            try {
                comercio = em.getReference(Comercio.class, id);
                comercio.getIdcomercio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comercio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Calificacion> calificacionCollectionOrphanCheck = comercio.getCalificacionCollection();
            for (Calificacion calificacionCollectionOrphanCheckCalificacion : calificacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comercio (" + comercio + ") cannot be destroyed since the Calificacion " + calificacionCollectionOrphanCheckCalificacion + " in its calificacionCollection field has a non-nullable idComercio field.");
            }
            Collection<Inconsistencias> inconsistenciasCollectionOrphanCheck = comercio.getInconsistenciasCollection();
            for (Inconsistencias inconsistenciasCollectionOrphanCheckInconsistencias : inconsistenciasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comercio (" + comercio + ") cannot be destroyed since the Inconsistencias " + inconsistenciasCollectionOrphanCheckInconsistencias + " in its inconsistenciasCollection field has a non-nullable idcomercio field.");
            }
            Collection<Producto> productoCollectionOrphanCheck = comercio.getProductoCollection();
            for (Producto productoCollectionOrphanCheckProducto : productoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comercio (" + comercio + ") cannot be destroyed since the Producto " + productoCollectionOrphanCheckProducto + " in its productoCollection field has a non-nullable idcomercio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario idusuario = comercio.getIdusuario();
            if (idusuario != null) {
                idusuario.getComercioCollection().remove(comercio);
                idusuario = em.merge(idusuario);
            }
            Collection<Sancion> sancionCollection = comercio.getSancionCollection();
            for (Sancion sancionCollectionSancion : sancionCollection) {
                sancionCollectionSancion.setIdcomercio(null);
                sancionCollectionSancion = em.merge(sancionCollectionSancion);
            }
            em.remove(comercio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comercio> findComercioEntities() {
        return findComercioEntities(true, -1, -1);
    }

    public List<Comercio> findComercioEntities(int maxResults, int firstResult) {
        return findComercioEntities(false, maxResults, firstResult);
    }

    private List<Comercio> findComercioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comercio.class));
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

    public Comercio findComercio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comercio.class, id);
        } finally {
            em.close();
        }
    }

    public int getComercioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comercio> rt = cq.from(Comercio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
