package com.derteuffel.entities;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import javax.persistence.*;
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

    private String demandeur;
    private ArrayList<Boolean> validations= new ArrayList<Boolean>();
    private String designation;
    private int quantite;
    private Double unit_price;
    private Double total_price;
    private Date requestDate= new Date();
    private Date sellingDate;
    private ArrayList<Date> authorizations= new ArrayList<>();

    @ManyToMany
    private Set<User> users;


}
