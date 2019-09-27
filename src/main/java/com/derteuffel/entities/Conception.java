package com.derteuffel.entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "conception")
public class Conception {

    @Id
    @GeneratedValue
    private int conceptionId;

    @NotEmpty(message = "*chose type of conception")
    private String type;

    @NotEmpty(message = "*quantity  does be under 1 or equal")
    private int quantite;
    @NotEmpty(message = "*this field can't be empty")
    private Double unit_price;
    private Double total_price;

    private Date createdDate = new Date();


    @ManyToOne
    private Commande commande;
}
