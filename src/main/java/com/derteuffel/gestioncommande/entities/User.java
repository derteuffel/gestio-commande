package com.derteuffel.gestioncommande.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue
    private Long userId;
    private String name;

    private String email;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;

    private String sexe;

    private String avatar;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEngagement;

    private ArrayList<String> postes = new ArrayList<>();
    private String cv;
    private String contratActuel;
    private String posteActuel;
    private String profession;
    private Boolean active;

    @OneToMany(mappedBy = "user")
    private List<Contract> contracts;

    @OneToOne(mappedBy = "user")
    private Compte compte;


    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private  List<Commande> commandes;

    public void addPost(String  poste){
        this.postes.add(poste);
    }

    @OneToMany(mappedBy = "user")
    private List<Materiel> materiels;
    
    
    
    
}
