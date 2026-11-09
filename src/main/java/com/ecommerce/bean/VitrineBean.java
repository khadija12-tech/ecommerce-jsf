package com.ecommerce.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import com.ecommerce.model.Produit;
import com.ecommerce.model.Vitrine;
import com.ecommerce.util.JPAUtil;

@ManagedBean
@SessionScoped
public class VitrineBean implements Serializable {
    private Produit produit = new Produit();
    private List<Produit> produits;
    private Vitrine vitrine;

    // Charge (ou crée) la vitrine au premier accès
    public Vitrine getVitrine() {
        if (vitrine == null) {
            EntityManager em = JPAUtil.getEM();
            try {
                List<Vitrine> vs = em.createQuery("SELECT v FROM Vitrine v ORDER BY v.id", Vitrine.class)
                        .setMaxResults(1).getResultList();
                if (vs.isEmpty()) {
                    Vitrine v = new Vitrine();
                    v.setNom("Ma boutique");
                    v.setDescription("Vitrine principale");
                    em.getTransaction().begin();
                    em.persist(v);
                    em.getTransaction().commit();
                    vitrine = v;
                } else {
                    vitrine = vs.get(0);
                }
            } finally {
                em.close();
            }
        }
        return vitrine;
    }

    public List<Produit> getProduits() {
        if (produits == null) {
            charger();
        }
        return produits;
    }

    public void charger() {
        EntityManager em = JPAUtil.getEM();
        try {
            produits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        } finally {
            em.close();
        }
    }

    public String ajouter() {
        final Long vitrineId = getVitrine().getId();
        JPAUtil.inTransaction(em -> {
            produit.setVitrine(em.getReference(Vitrine.class, vitrineId));
            em.persist(produit);
        });
        produit = new Produit();
        charger();
        return "produits?faces-redirect=true";
    }

    public void supprimer(Produit p) {
        JPAUtil.inTransaction(em -> em.remove(em.find(Produit.class, p.getId())));
        charger();
    }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public void setProduits(List<Produit> produits) { this.produits = produits; }
}