package com.ecommerce.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class LignePanier implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantite;
    @ManyToOne
    private Produit produit;
    @ManyToOne
    private Panier panier;

    public double getSousTotal() { return produit.getPrix() * quantite; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }
}