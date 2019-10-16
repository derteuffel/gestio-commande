package com.derteuffel.entities;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idProduct;

    @Column(name= "nomProduit", nullable = false)
    private String nomProduit;
    @Column(name= "dateFabrication" , nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateFabrication;
    @Column(name= "datePeremption", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date datePeremption;
    @Column(name= "qty", nullable = false)
    private Long qty;
    @Column(name= "prix_achat")
    private Long prix_achat;
    @Column(name= "prix_vente" , nullable = false )
    private Long prix_vente;
    @Column(name= "prix_unitaire" , nullable = false)
    private Long prix_unitaire;
    @Column(name= "perissable" , nullable = false)
    private String perissable;
    @Column(name= "TVA" , nullable = false)
    private Long TVA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idCat")
    private Category category;

}
