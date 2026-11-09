package com.ecommerce.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Produit implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private double prix;
    private int stock;
    @ManyToOne
    private Vitrine vitrine;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Vitrine getVitrine() { return vitrine; }
    public void setVitrine(Vitrine vitrine) { this.vitrine = vitrine; }
}