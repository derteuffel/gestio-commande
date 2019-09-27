package com.derteuffel.entities;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commmande_id")
    private int commandeId;

    @NotEmpty(message = "*pleace provide client name")
    private String demandeur;
    private ArrayList<Boolean> validations= new ArrayList<Boolean>();
    @NotEmpty(message = "*pleace provide the product designation")
    private String designation;
    private int quantite;
    private Double amount;
    private Date requestDate= new Date();
    private Date sellingDate;
    private ArrayList<Date> authorizations= new ArrayList<>();
    private Boolean etatCommande;
    private Boolean commandeAprobation;

    @ManyToMany
     private List<User> users;

    @OneToMany(mappedBy = "commande")
    private List<Conception> conceptions;

    @OneToMany(mappedBy = "commande")
    private List<Impression> impressions;

    @OneToMany(mappedBy = "commande")
    private List<Location> locations;

    @ManyToOne
    private Client client;




}
