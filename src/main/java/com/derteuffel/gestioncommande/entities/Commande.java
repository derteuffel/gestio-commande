package com.derteuffel.gestioncommande.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "commande")
public class Commande  implements Serializable {

    @Id
    @GeneratedValue
    private Long commandeId;

    private String title;

    private Date createdDate = new Date();

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realeseDate;

    private int tauxJour;

    private Double amountUSD=0.0;
    private Double amountCDF=0.0;

    private Boolean status;

    private String code;

    private int quantity=0;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "commande")
    private List<Article> articles;


    @ManyToOne
    private User user;
    
    

}
