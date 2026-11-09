package com.ecommerce.util;

import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("ecommercePU");

    public static EntityManager getEM() {
        return EMF.createEntityManager();
    }

    // Transaction JPA: begin / commit, rollback on error
    public static void inTransaction(Consumer<EntityManager> work) {
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            work.accept(em);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}