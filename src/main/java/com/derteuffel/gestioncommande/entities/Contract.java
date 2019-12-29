package com.derteuffel.gestioncommande.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "contract")
public class Contract implements Serializable {

    @Id
    @GeneratedValue
    private Long contractId;

    private String type;
    private String salaire;
    private String description;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date debutContrat;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finContrat;
    private String dureeContrat;
    private ArrayList<String> documents;

    @ManyToOne
    private User user;
}
