package com.derteuffel.gestioncommande.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "approbation")
public class Approbation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String comment;
    private String date;
    private String compte1;
    @ManyToOne
    @JsonIgnoreProperties("approbations")
    private Commande commande;

    @ManyToOne
    @JsonIgnoreProperties("approbations")
    private Compte compte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getCompte1() {
        return compte1;
    }

    public void setCompte1(String compte1) {
        this.compte1 = compte1;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
