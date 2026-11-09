package com.ecommerce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Panier implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date();
    @ManyToOne
    private Internaute internaute;
    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LignePanier> lignes = new ArrayList<>();

    public double getTotal() {
        double t = 0;
        for (LignePanier l : lignes) t += l.getSousTotal();
        return t;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    public Internaute getInternaute() { return internaute; }
    public void setInternaute(Internaute internaute) { this.internaute = internaute; }
    public List<LignePanier> getLignes() { return lignes; }
    public void setLignes(List<LignePanier> lignes) { this.lignes = lignes; }
}