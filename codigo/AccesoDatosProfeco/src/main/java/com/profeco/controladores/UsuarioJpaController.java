/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profeco.controladores;

import com.profeco.controladores.exceptions.IllegalOrphanException;
import com.profeco.controladores.exceptions.NonexistentEntityException;
import com.profeco.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.profeco.entidades.Calificacion;
import java.util.ArrayList;
import java.util.Collection;
import com.profeco.entidades.Inconsistencias;
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
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.profeco_AccesoDatosProfeco_jar_1.0-SNAPSHOTPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getCalificacionCollection() == null) {
            usuario.setCalificacionCollection(new ArrayList<Calificacion>());
        }
        if (usuario.getInconsistenciasCollection() == null) {
            usuario.setInconsistenciasCollection(new ArrayList<Inconsistencias>());
        }
        if (usuario.getComercioCollection() == null) {
            usuario.setComercioCollection(new ArrayList<Comercio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Calificacion> attachedCalificacionCollection = new ArrayList<Calificacion>();
            for (Calificacion calificacionCollectionCalificacionToAttach : usuario.getCalificacionCollection()) {
                calificacionCollectionCalificacionToAttach = em.getReference(calificacionCollectionCalificacionToAttach.getClass(), calificacionCollectionCalificacionToAttach.getIdCalificacion());
                attachedCalificacionCollection.add(calificacionCollectionCalificacionToAttach);
            }
            usuario.setCalificacionCollection(attachedCalificacionCollection);
            Collection<Inconsistencias> attachedInconsistenciasCollection = new ArrayList<Inconsistencias>();
            for (Inconsistencias inconsistenciasCollectionInconsistenciasToAttach : usuario.getInconsistenciasCollection()) {
                inconsistenciasCollectionInconsistenciasToAttach = em.getReference(inconsistenciasCollectionInconsistenciasToAttach.getClass(), inconsistenciasCollectionInconsistenciasToAttach.getIdreporte());
                attachedInconsistenciasCollection.add(inconsistenciasCollectionInconsistenciasToAttach);
            }
            usuario.setInconsistenciasCollection(attachedInconsistenciasCollection);
            Collection<Comercio> attachedComercioCollection = new ArrayList<Comercio>();
            for (Comercio comercioCollectionComercioToAttach : usuario.getComercioCollection()) {
                comercioCollectionComercioToAttach = em.getReference(comercioCollectionComercioToAttach.getClass(), comercioCollectionComercioToAttach.getIdcomercio());
                attachedComercioCollection.add(comercioCollectionComercioToAttach);
            }
            usuario.setComercioCollection(attachedComercioCollection);
            em.persist(usuario);
            for (Calificacion calificacionCollectionCalificacion : usuario.getCalificacionCollection()) {
                Usuario oldIdUsuarioOfCalificacionCollectionCalificacion = calificacionCollectionCalificacion.getIdUsuario();
                calificacionCollectionCalificacion.setIdUsuario(usuario);
                calificacionCollectionCalificacion = em.merge(calificacionCollectionCalificacion);
                if (oldIdUsuarioOfCalificacionCollectionCalificacion != null) {
                    oldIdUsuarioOfCalificacionCollectionCalificacion.getCalificacionCollection().remove(calificacionCollectionCalificacion);
                    oldIdUsuarioOfCalificacionCollectionCalificacion = em.merge(oldIdUsuarioOfCalificacionCollectionCalificacion);
                }
            }
            for (Inconsistencias inconsistenciasCollectionInconsistencias : usuario.getInconsistenciasCollection()) {
                Usuario oldIdusuarioOfInconsistenciasCollectionInconsistencias = inconsistenciasCollectionInconsistencias.getIdusuario();
                inconsistenciasCollectionInconsistencias.setIdusuario(usuario);
                inconsistenciasCollectionInconsistencias = em.merge(inconsistenciasCollectionInconsistencias);
                if (oldIdusuarioOfInconsistenciasCollectionInconsistencias != null) {
                    oldIdusuarioOfInconsistenciasCollectionInconsistencias.getInconsistenciasCollection().remove(inconsistenciasCollectionInconsistencias);
                    oldIdusuarioOfInconsistenciasCollectionInconsistencias = em.merge(oldIdusuarioOfInconsistenciasCollectionInconsistencias);
                }
            }
            for (Comercio comercioCollectionComercio : usuario.getComercioCollection()) {
                Usuario oldIdusuarioOfComercioCollectionComercio = comercioCollectionComercio.getIdusuario();
                comercioCollectionComercio.setIdusuario(usuario);
                comercioCollectionComercio = em.merge(comercioCollectionComercio);
                if (oldIdusuarioOfComercioCollectionComercio != null) {
                    oldIdusuarioOfComercioCollectionComercio.getComercioCollection().remove(comercioCollectionComercio);
                    oldIdusuarioOfComercioCollectionComercio = em.merge(oldIdusuarioOfComercioCollectionComercio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getIdusuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdusuario());
            Collection<Calificacion> calificacionCollectionOld = persistentUsuario.getCalificacionCollection();
            Collection<Calificacion> calificacionCollectionNew = usuario.getCalificacionCollection();
            Collection<Inconsistencias> inconsistenciasCollectionOld = persistentUsuario.getInconsistenciasCollection();
            Collection<Inconsistencias> inconsistenciasCollectionNew = usuario.getInconsistenciasCollection();
            Collection<Comercio> comercioCollectionOld = persistentUsuario.getComercioCollection();
            Collection<Comercio> comercioCollectionNew = usuario.getComercioCollection();
            List<String> illegalOrphanMessages = null;
            for (Calificacion calificacionCollectionOldCalificacion : calificacionCollectionOld) {
                if (!calificacionCollectionNew.contains(calificacionCollectionOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionCollectionOldCalificacion + " since its idUsuario field is not nullable.");
                }
            }
            for (Inconsistencias inconsistenciasCollectionOldInconsistencias : inconsistenciasCollectionOld) {
                if (!inconsistenciasCollectionNew.contains(inconsistenciasCollectionOldInconsistencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inconsistencias " + inconsistenciasCollectionOldInconsistencias + " since its idusuario field is not nullable.");
                }
            }
            for (Comercio comercioCollectionOldComercio : comercioCollectionOld) {
                if (!comercioCollectionNew.contains(comercioCollectionOldComercio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comercio " + comercioCollectionOldComercio + " since its idusuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Calificacion> attachedCalificacionCollectionNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionCollectionNewCalificacionToAttach : calificacionCollectionNew) {
                calificacionCollectionNewCalificacionToAttach = em.getReference(calificacionCollectionNewCalificacionToAttach.getClass(), calificacionCollectionNewCalificacionToAttach.getIdCalificacion());
                attachedCalificacionCollectionNew.add(calificacionCollectionNewCalificacionToAttach);
            }
            calificacionCollectionNew = attachedCalificacionCollectionNew;
            usuario.setCalificacionCollection(calificacionCollectionNew);
            Collection<Inconsistencias> attachedInconsistenciasCollectionNew = new ArrayList<Inconsistencias>();
            for (Inconsistencias inconsistenciasCollectionNewInconsistenciasToAttach : inconsistenciasCollectionNew) {
                inconsistenciasCollectionNewInconsistenciasToAttach = em.getReference(inconsistenciasCollectionNewInconsistenciasToAttach.getClass(), inconsistenciasCollectionNewInconsistenciasToAttach.getIdreporte());
                attachedInconsistenciasCollectionNew.add(inconsistenciasCollectionNewInconsistenciasToAttach);
            }
            inconsistenciasCollectionNew = attachedInconsistenciasCollectionNew;
            usuario.setInconsistenciasCollection(inconsistenciasCollectionNew);
            Collection<Comercio> attachedComercioCollectionNew = new ArrayList<Comercio>();
            for (Comercio comercioCollectionNewComercioToAttach : comercioCollectionNew) {
                comercioCollectionNewComercioToAttach = em.getReference(comercioCollectionNewComercioToAttach.getClass(), comercioCollectionNewComercioToAttach.getIdcomercio());
                attachedComercioCollectionNew.add(comercioCollectionNewComercioToAttach);
            }
            comercioCollectionNew = attachedComercioCollectionNew;
            usuario.setComercioCollection(comercioCollectionNew);
            usuario = em.merge(usuario);
            for (Calificacion calificacionCollectionNewCalificacion : calificacionCollectionNew) {
                if (!calificacionCollectionOld.contains(calificacionCollectionNewCalificacion)) {
                    Usuario oldIdUsuarioOfCalificacionCollectionNewCalificacion = calificacionCollectionNewCalificacion.getIdUsuario();
                    calificacionCollectionNewCalificacion.setIdUsuario(usuario);
                    calificacionCollectionNewCalificacion = em.merge(calificacionCollectionNewCalificacion);
                    if (oldIdUsuarioOfCalificacionCollectionNewCalificacion != null && !oldIdUsuarioOfCalificacionCollectionNewCalificacion.equals(usuario)) {
                        oldIdUsuarioOfCalificacionCollectionNewCalificacion.getCalificacionCollection().remove(calificacionCollectionNewCalificacion);
                        oldIdUsuarioOfCalificacionCollectionNewCalificacion = em.merge(oldIdUsuarioOfCalificacionCollectionNewCalificacion);
                    }
                }
            }
            for (Inconsistencias inconsistenciasCollectionNewInconsistencias : inconsistenciasCollectionNew) {
                if (!inconsistenciasCollectionOld.contains(inconsistenciasCollectionNewInconsistencias)) {
                    Usuario oldIdusuarioOfInconsistenciasCollectionNewInconsistencias = inconsistenciasCollectionNewInconsistencias.getIdusuario();
                    inconsistenciasCollectionNewInconsistencias.setIdusuario(usuario);
                    inconsistenciasCollectionNewInconsistencias = em.merge(inconsistenciasCollectionNewInconsistencias);
                    if (oldIdusuarioOfInconsistenciasCollectionNewInconsistencias != null && !oldIdusuarioOfInconsistenciasCollectionNewInconsistencias.equals(usuario)) {
                        oldIdusuarioOfInconsistenciasCollectionNewInconsistencias.getInconsistenciasCollection().remove(inconsistenciasCollectionNewInconsistencias);
                        oldIdusuarioOfInconsistenciasCollectionNewInconsistencias = em.merge(oldIdusuarioOfInconsistenciasCollectionNewInconsistencias);
                    }
                }
            }
            for (Comercio comercioCollectionNewComercio : comercioCollectionNew) {
                if (!comercioCollectionOld.contains(comercioCollectionNewComercio)) {
                    Usuario oldIdusuarioOfComercioCollectionNewComercio = comercioCollectionNewComercio.getIdusuario();
                    comercioCollectionNewComercio.setIdusuario(usuario);
                    comercioCollectionNewComercio = em.merge(comercioCollectionNewComercio);
                    if (oldIdusuarioOfComercioCollectionNewComercio != null && !oldIdusuarioOfComercioCollectionNewComercio.equals(usuario)) {
                        oldIdusuarioOfComercioCollectionNewComercio.getComercioCollection().remove(comercioCollectionNewComercio);
                        oldIdusuarioOfComercioCollectionNewComercio = em.merge(oldIdusuarioOfComercioCollectionNewComercio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdusuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Calificacion> calificacionCollectionOrphanCheck = usuario.getCalificacionCollection();
            for (Calificacion calificacionCollectionOrphanCheckCalificacion : calificacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Calificacion " + calificacionCollectionOrphanCheckCalificacion + " in its calificacionCollection field has a non-nullable idUsuario field.");
            }
            Collection<Inconsistencias> inconsistenciasCollectionOrphanCheck = usuario.getInconsistenciasCollection();
            for (Inconsistencias inconsistenciasCollectionOrphanCheckInconsistencias : inconsistenciasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Inconsistencias " + inconsistenciasCollectionOrphanCheckInconsistencias + " in its inconsistenciasCollection field has a non-nullable idusuario field.");
            }
            Collection<Comercio> comercioCollectionOrphanCheck = usuario.getComercioCollection();
            for (Comercio comercioCollectionOrphanCheckComercio : comercioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Comercio " + comercioCollectionOrphanCheckComercio + " in its comercioCollection field has a non-nullable idusuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
