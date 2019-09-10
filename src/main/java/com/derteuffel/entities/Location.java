package com.derteuffel.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "location")
@PrimaryKeyJoinColumn(name = "commandeId")
public class Location extends Commande{
    private String deviceName;
    private String duration;
    @ManyToMany
    private Set<User> users;
}
