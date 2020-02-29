package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "visiteur")
public class Visiteur implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private ArrayList<String> dates = new ArrayList<>();
    private String name;
    private String fonction;
    private String motif;
    private String adressePhysique;
    private String email;
    private String telephone;

    @ManyToOne
    private Compte compte;
}
