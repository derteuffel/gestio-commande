package com.derteuffel.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "impression")
@Inheritance(strategy = InheritanceType.JOINED)
public class Impression {

    @Id
    @GeneratedValue
    private int impressionId;

    private String supportType;

    private String supportQuality;
    private int quantite;
    private Double unit_price;
    private Double total_price;

    private Date createdDate= new Date();

    @ManyToOne
    private Commande commande;
}
