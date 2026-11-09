package com.ecommerce.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import com.ecommerce.model.Internaute;
import com.ecommerce.model.LignePanier;
import com.ecommerce.model.Panier;
import com.ecommerce.model.Produit;
import com.ecommerce.util.JPAUtil;

@ManagedBean
@SessionScoped
public class PanierBean implements Serializable {
    private Panier panier = new Panier();

    @ManagedProperty(value = "#{internauteBean}")
    private InternauteBean internauteBean;

    public void ajouterProduit(Produit p) {
        for (LignePanier l : panier.getLignes()) {
            if (l.getProduit().getId().equals(p.getId())) {
                l.setQuantite(l.getQuantite() + 1);
                return;
            }
        }
        LignePanier l = new LignePanier();
        l.setProduit(p);
        l.setQuantite(1);
        l.setPanier(panier);
        panier.getLignes().add(l);
    }

    public void retirer(LignePanier l) {
        panier.getLignes().remove(l);
    }

    public String valider() {
        if (!internauteBean.isConnecte()) {
            return "connexion?faces-redirect=true";
        }
        if (panier.getLignes().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Le panier est vide", null));
            return null;
        }
        JPAUtil.inTransaction(em -> {
            panier.setInternaute(em.getReference(Internaute.class, internauteBean.getUtilisateur().getId()));
            for (LignePanier l : panier.getLignes()) {
                l.setProduit(em.getReference(Produit.class, l.getProduit().getId()));
            }
            em.persist(panier);
        });
        panier = new Panier();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Commande enregistrée. Merci !", null));
        return "panier";
    }

    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }
    public InternauteBean getInternauteBean() { return internauteBean; }
    public void setInternauteBean(InternauteBean internauteBean) { this.internauteBean = internauteBean; }
}