package com.derteuffel.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue
    private  int clientId;
     private String nomClient;
     private String phoneClient;
     private String emailClient;
     private  String adresseClient;
     private String codeClient;
     private Date createdDate= new Date();


     @OneToMany(mappedBy = "client")
     private List<Commande> commandes;
}
