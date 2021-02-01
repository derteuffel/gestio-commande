package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ordinateur")
@Data
@PrimaryKeyJoinColumn(name = "materielId")
public class Ordinateur extends Materiel{

    private  String disqueDur;
    private String ram;
    private String nombreCoeur;
    private String password;

    public String getDisqueDur() {
        return disqueDur;
    }

    public void setDisqueDur(String disqueDur) {
        this.disqueDur = disqueDur;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getNombreCoeur() {
        return nombreCoeur;
    }

    public void setNombreCoeur(String nombreCoeur) {
        this.nombreCoeur = nombreCoeur;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
