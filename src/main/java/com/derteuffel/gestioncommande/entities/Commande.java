package com.derteuffel.gestioncommande.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "commande")
public class Commande  {

    @Id
    @GeneratedValue
    private Long commandeId;

    private String title;

    private Date createdDate = new Date();

    private Double amount=0.0;

    private Boolean status;

    private int code=0;

    private int quantity=0;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "commande")
    private List<Article> articles;


    @ManyToOne
    private User user;


}
