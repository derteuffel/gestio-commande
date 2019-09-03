package com.derteuffel.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@Table(name = "location")
@PrimaryKeyJoinColumn(name = "commandeId")
public class Location extends Commande{
    private String deviceName;
    private String duration;
    @ManyToMany
    private Set<User> users;
}
