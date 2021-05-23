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
import com.profeco.entidades.Comercio;
import com.profeco.entidades.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lenovo
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
        if (usuario.getComercioCollection() == null) {
            usuario.setComercioCollection(new ArrayList<Comercio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Comercio> attachedComercioCollection = new ArrayList<Comercio>();
            for (Comercio comercioCollectionComercioToAttach : usuario.getComercioCollection()) {
                comercioCollectionComercioToAttach = em.getReference(comercioCollectionComercioToAttach.getClass(), comercioCollectionComercioToAttach.getIdcomercio());
                attachedComercioCollection.add(comercioCollectionComercioToAttach);
            }
            usuario.setComercioCollection(attachedComercioCollection);
            em.persist(usuario);
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
            Collection<Comercio> comercioCollectionOld = persistentUsuario.getComercioCollection();
            Collection<Comercio> comercioCollectionNew = usuario.getComercioCollection();
            List<String> illegalOrphanMessages = null;
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
            Collection<Comercio> attachedComercioCollectionNew = new ArrayList<Comercio>();
            for (Comercio comercioCollectionNewComercioToAttach : comercioCollectionNew) {
                comercioCollectionNewComercioToAttach = em.getReference(comercioCollectionNewComercioToAttach.getClass(), comercioCollectionNewComercioToAttach.getIdcomercio());
                attachedComercioCollectionNew.add(comercioCollectionNewComercioToAttach);
            }
            comercioCollectionNew = attachedComercioCollectionNew;
            usuario.setComercioCollection(comercioCollectionNew);
            usuario = em.merge(usuario);
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
