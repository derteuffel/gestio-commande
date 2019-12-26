package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "article")
public class Article  {

    @Id
    @GeneratedValue
    private Long articleId;

    private String name;


    private double price;

    private int quantity;

    @ManyToOne
    private Commande commande;

    @ManyToOne
    private Category category;
}
