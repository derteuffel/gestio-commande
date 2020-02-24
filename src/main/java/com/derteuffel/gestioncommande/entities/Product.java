package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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


     private String category;

     @OneToMany(mappedBy = "product")
     private List<AddedProduct> addedProducts;
     
     
     
     
}
