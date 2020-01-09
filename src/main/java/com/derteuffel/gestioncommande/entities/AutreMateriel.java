package com.derteuffel.gestioncommande.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "autre_materiel")
@PrimaryKeyJoinColumn(name = "materielId")
public class AutreMateriel extends Materiel {

    private String utilite;
}
