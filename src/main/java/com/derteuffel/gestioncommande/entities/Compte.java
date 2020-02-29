package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class Compte {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    private String email;
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "comptes_roles",
            joinColumns = @JoinColumn(
                    name = "compte_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToOne
    private User user;

    /*@OneToMany(mappedBy = "compte")
    private List<Commande> commandes;*/

    @OneToMany(mappedBy = "compte")
    private List<Visiteur> visiteurs;



}
