package com.derteuffel.gestioncommande.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "materiel")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Materiel implements Serializable {

    @Id
    @GeneratedValue
    private Long materielId;

    private String nom;
    private String marque;
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("materiels")
    private User user;

    public Long getMaterielId() {
        return materielId;
    }

    public void setMaterielId(Long materielId) {
        this.materielId = materielId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
