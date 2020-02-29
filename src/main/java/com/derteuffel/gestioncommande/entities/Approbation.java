package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "approbation")
public class Approbation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String comment;
    private String date;
    @ManyToOne
    private Commande commande;
}
