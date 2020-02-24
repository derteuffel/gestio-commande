package com.derteuffel.gestioncommande.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "client")
public class Client implements Serializable {
    @Id
    @GeneratedValue
    private Long  clientId;

    private String name;


    private String email;

    private String phone;

    private String adresse;

    private Date createdDate = new Date();


   /* @OneToMany(mappedBy = "client")
    private List<Commande> commandes;*/
    
    
    
    
}
