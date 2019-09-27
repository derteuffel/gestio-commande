package com.derteuffel.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "autre")
@PrimaryKeyJoinColumn(name = "impressionId")
public class Autre extends Impression{



}
