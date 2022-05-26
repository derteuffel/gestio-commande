package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "caisse")
public class Caisse {

    @Id
    @GeneratedValue
    private Long id;

    private String mois;
    private String annee;
    private Double soldeDebutMoisDollard;
    private Double soldeDebutMoisFranc;
    private Double soldeFinMoisDollard;
    private Double soldeFinMoisFranc;
    private Double mouvementMensuelDollard;
    private Double mouvementMensuelFranc;
    private Boolean status;

    public Caisse() {
    }

    public Caisse(String mois, String annee, Double soldeDebutMoisDollard, Double soldeDebutMoisFranc, Double soldeFinMoisDollard,
                  Double soldeFinMoisFranc, Double mouvementMensuelDollard, Double mouvementMensuelFranc, Boolean status) {
        this.mois = mois;
        this.annee = annee;
        this.soldeDebutMoisDollard = soldeDebutMoisDollard;
        this.soldeDebutMoisFranc = soldeDebutMoisFranc;
        this.soldeFinMoisDollard = soldeFinMoisDollard;
        this.soldeFinMoisFranc = soldeFinMoisFranc;
        this.mouvementMensuelDollard = mouvementMensuelDollard;
        this.mouvementMensuelFranc = mouvementMensuelFranc;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public Double getSoldeDebutMoisDollard() {
        return soldeDebutMoisDollard;
    }

    public void setSoldeDebutMoisDollard(Double soldeDebutMoisDollard) {
        this.soldeDebutMoisDollard = soldeDebutMoisDollard;
    }

    public Double getSoldeDebutMoisFranc() {
        return soldeDebutMoisFranc;
    }

    public void setSoldeDebutMoisFranc(Double soldeDebutMoisFranc) {
        this.soldeDebutMoisFranc = soldeDebutMoisFranc;
    }

    public Double getSoldeFinMoisDollard() {
        return soldeFinMoisDollard;
    }

    public void setSoldeFinMoisDollard(Double soldeFinMoisDollard) {
        this.soldeFinMoisDollard = soldeFinMoisDollard;
    }

    public Double getSoldeFinMoisFranc() {
        return soldeFinMoisFranc;
    }

    public void setSoldeFinMoisFranc(Double soldeFinMoisFranc) {
        this.soldeFinMoisFranc = soldeFinMoisFranc;
    }

    public Double getMouvementMensuelDollard() {
        return mouvementMensuelDollard;
    }

    public void setMouvementMensuelDollard(Double mouvementMensuelDollard) {
        this.mouvementMensuelDollard = mouvementMensuelDollard;
    }

    public Double getMouvementMensuelFranc() {
        return mouvementMensuelFranc;
    }

    public void setMouvementMensuelFranc(Double mouvementMensuelFranc) {
        this.mouvementMensuelFranc = mouvementMensuelFranc;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }
}
