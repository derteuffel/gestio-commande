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
    private String monnaie;
    private Double totalUSD;
    private Double totalCDF;


    private String type;
    private String category;

    private String Description;

    private String dateJour;

    @ManyToOne
    private Commande commande;

    
    
    
}
