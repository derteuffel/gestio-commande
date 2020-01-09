package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table(name = "materiel")
@Inheritance(strategy = InheritanceType.JOINED)
public class Materiel implements Serializable {

    @Id
    @GeneratedValue
    private Long materielId;

    private String nom;
    private String marque;
    private String description;

    @ManyToOne
    private User user;
}
