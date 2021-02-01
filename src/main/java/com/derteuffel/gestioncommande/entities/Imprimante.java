package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "imprimante")
@Data
@PrimaryKeyJoinColumn(name = "materielId")
public class Imprimante extends Materiel {

    private int nbreBac;
    private String typeImprimante;
    private String numeroImprimante;

    public int getNbreBac() {
        return nbreBac;
    }

    public void setNbreBac(int nbreBac) {
        this.nbreBac = nbreBac;
    }

    public String getTypeImprimante() {
        return typeImprimante;
    }

    public void setTypeImprimante(String typeImprimante) {
        this.typeImprimante = typeImprimante;
    }

    public String getNumeroImprimante() {
        return numeroImprimante;
    }

    public void setNumeroImprimante(String numeroImprimante) {
        this.numeroImprimante = numeroImprimante;
    }
}
