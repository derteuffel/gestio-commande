package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "imprimante")
@PrimaryKeyJoinColumn(name = "materielId")
public class Imprimante extends Materiel {

    private int nbreBac;
    private String typeImprimante;
    private String numeroImprimante;
}
