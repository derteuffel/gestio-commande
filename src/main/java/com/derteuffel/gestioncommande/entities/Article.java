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


    private Double price;

    private int quantity;

    private String type;

    private String Description;

    private Double totalPrice = 0.0;

    @ManyToOne
    private Commande commande;

    @ManyToOne
    private Category category;
}
