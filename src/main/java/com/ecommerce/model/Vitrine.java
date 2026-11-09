package com.ecommerce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Vitrine implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    @OneToMany(mappedBy = "vitrine", cascade = CascadeType.ALL)
    private List<Produit> produits = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Produit> getProduits() { return produits; }
    public void setProduits(List<Produit> produits) { this.produits = produits; }
}