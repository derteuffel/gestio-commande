package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue
    private Long userId;
    private String name;


    private String login;

    private String email;

    private String password;

    private String avatar;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private  List<Commande> commandes;
}
