package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "added_product")
public class AddedProduct {

    @Id
    @GeneratedValue
    private Long addedProductId;

    private int quantity;
    private String description;
    private Date addedDate = new Date();

    @ManyToOne
    private Product product;
    
    
    
    
}
