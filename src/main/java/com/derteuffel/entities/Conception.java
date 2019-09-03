package com.derteuffel.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@Table(name = "conception")
@PrimaryKeyJoinColumn(name = "commandeId")
public class Conception extends  Commande{

    private String type;
    @ManyToMany
    private Set<User> users;
}
