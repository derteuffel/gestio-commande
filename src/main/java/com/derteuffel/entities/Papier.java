package com.derteuffel.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "papier")
@PrimaryKeyJoinColumn(name = "impressionId")
public class Papier  extends Impression{

    private String format;

    private String quality;


}
