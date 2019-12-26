package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    private Long roleId;

    private String name;


    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
