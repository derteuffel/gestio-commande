package com.derteuffel.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "request_api")
public class RequestApi {

    @Id
    @GeneratedValue
    private int requestApiId;
}
