package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "product")
public class Product  {

    @Id
    @GeneratedValue
    private Long productId;

    private String name;


    private int quantity;
     private Date addedDate = new Date();

     @ManyToOne
     private Category category;
}
