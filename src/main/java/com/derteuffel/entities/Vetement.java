package com.derteuffel.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "vetement")
@PrimaryKeyJoinColumn(name = "impressionId")
public class Vetement extends Impression {


    private String couleur;
}
