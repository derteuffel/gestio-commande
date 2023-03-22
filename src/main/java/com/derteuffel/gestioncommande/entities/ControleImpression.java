package com.derteuffel.gestioncommande.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table
@Entity
public class ControleImpression implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String nomMachine;
    private String nomArticle;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateJour;

    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;
    private int quantite;
    private String numeroBonCommande;
    private String staffResponsable;

    public ControleImpression() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMachine() {
        return nomMachine;
    }

    public void setNomMachine(String nomMachine) {
        this.nomMachine = nomMachine;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public Date getDateJour() {
        return dateJour;
    }

    public void setDateJour(Date dateJour) {
        this.dateJour = dateJour;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getNumeroBonCommande() {
        return numeroBonCommande;
    }

    public void setNumeroBonCommande(String numeroBonCommande) {
        this.numeroBonCommande = numeroBonCommande;
    }

    public String getStaffResponsable() {
        return staffResponsable;
    }

    public void setStaffResponsable(String staffResponsable) {
        this.staffResponsable = staffResponsable;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
