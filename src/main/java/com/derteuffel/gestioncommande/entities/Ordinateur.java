package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ordinateur")
@PrimaryKeyJoinColumn(name = "materielId")
public class Ordinateur extends Materiel{

    private  String disqueDur;
    private String ram;
    private String nombreCoeur;
    private String password;
}
