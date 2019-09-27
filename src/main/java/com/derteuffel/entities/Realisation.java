package com.derteuffel.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "realisation")
public class Realisation {

    @Id
    @GeneratedValue
    private int realisationId;

}
