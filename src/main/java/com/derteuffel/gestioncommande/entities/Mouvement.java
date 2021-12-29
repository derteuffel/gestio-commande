package com.derteuffel.gestioncommande.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "mouvement")
public class Mouvement implements Serializable {

    @Id
    @GeneratedValue
    private Long id;


    private String createdDate;
    private String libelle;
    private String type;
    private Double montantDollard;
    private Double montantFranc;
    private Double soldeFinDollard;
    private Double soldeFinFranc;
    private String numMouvement;

    @ManyToOne
    private Caisse caisse;

    public Mouvement() {
    }

    public Mouvement(String createdDate, String libelle, String type, Double montantDollard,
                     Double montantFranc, Double soldeFinDollard, Double soldeFinFranc, String numMouvement) {
        this.createdDate = createdDate;
        this.libelle = libelle;
        this.type = type;
        this.montantDollard = montantDollard;
        this.montantFranc = montantFranc;
        this.soldeFinDollard = soldeFinDollard;
        this.soldeFinFranc = soldeFinFranc;
        this.numMouvement = numMouvement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMontantDollard() {
        return montantDollard;
    }

    public void setMontantDollard(Double montantDollard) {
        this.montantDollard = montantDollard;
    }

    public Double getMontantFranc() {
        return montantFranc;
    }

    public void setMontantFranc(Double montantFranc) {
        this.montantFranc = montantFranc;
    }

    public Double getSoldeFinDollard() {
        return soldeFinDollard;
    }

    public void setSoldeFinDollard(Double soldeFinDollard) {
        this.soldeFinDollard = soldeFinDollard;
    }

    public Double getSoldeFinFranc() {
        return soldeFinFranc;
    }

    public void setSoldeFinFranc(Double soldeFinFranc) {
        this.soldeFinFranc = soldeFinFranc;
    }

    public String getNumMouvement() {
        return numMouvement;
    }

    public void setNumMouvement(String numMouvement) {
        this.numMouvement = numMouvement;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }
}
