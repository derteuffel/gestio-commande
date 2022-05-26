package com.derteuffel.gestioncommande.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "added_product")
public class AddedProduct {

    @Id
    @GeneratedValue
    private Long addedProductId;

    private int quantity;
    private String description;
    private Date addedDate = new Date();

    @ManyToOne
    @JsonIgnoreProperties("addedProducts")
    private Product product;

    public Long getAddedProductId() {
        return addedProductId;
    }

    public void setAddedProductId(Long addedProductId) {
        this.addedProductId = addedProductId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
