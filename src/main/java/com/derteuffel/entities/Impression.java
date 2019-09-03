package com.derteuffel.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@Table(name = "impression")
@PrimaryKeyJoinColumn(name = "commandeId")
public class Impression  extends Commande{

    private String supportType;
    private String supportQuality;
    @ManyToMany
    private Set<User> users;
}
