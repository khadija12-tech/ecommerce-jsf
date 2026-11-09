package com.ecommerce.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import com.ecommerce.model.Internaute;
import com.ecommerce.util.JPAUtil;

@ManagedBean
@SessionScoped
public class InternauteBean implements Serializable {
    private Internaute internaute = new Internaute(); // formulaire d'inscription
    private Internaute utilisateur;                   // internaute connecté
    private String email;
    private String motDePasse;

    private void message(FacesMessage.Severity s, String texte) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(s, texte, null));
    }

    public String inscrire() {
        try {
            JPAUtil.inTransaction(em -> em.persist(internaute));
        } catch (RuntimeException e) {
            message(FacesMessage.SEVERITY_ERROR, "Inscription impossible (email déjà utilisé ?)");
            return null;
        }
        internaute = new Internaute();
        return "connexion?faces-redirect=true";
    }

    public String connecter() {
        EntityManager em = JPAUtil.getEM();
        try {
            List<Internaute> r = em.createQuery(
                    "SELECT i FROM Internaute i WHERE i.email = :e AND i.motDePasse = :m", Internaute.class)
                    .setParameter("e", email)
                    .setParameter("m", motDePasse)
                    .getResultList();
            if (r.isEmpty()) {
                message(FacesMessage.SEVERITY_ERROR, "Email ou mot de passe incorrect");
                return null;
            }
            utilisateur = r.get(0);
            motDePasse = null;
            return "produits?faces-redirect=true";
        } finally {
            em.close();
        }
    }

    public String deconnecter() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "produits?faces-redirect=true";
    }

    public boolean isConnecte() { return utilisateur != null; }

    public Internaute getInternaute() { return internaute; }
    public void setInternaute(Internaute internaute) { this.internaute = internaute; }
    public Internaute getUtilisateur() { return utilisateur; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}