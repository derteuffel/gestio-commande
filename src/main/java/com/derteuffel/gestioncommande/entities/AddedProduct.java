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

    private String addedDate;

    private String actionType;

    private Double unitCost = 0.0;

    private Double totalCost = 0.0;

    private String devise;

    private int tauxDuJour;

    private Boolean valide;

    @ManyToOne
    @JsonIgnoreProperties("addedProducts")
    private Product product;

    @OneToOne
    @JsonIgnoreProperties("addedProduct")
    private Article article;

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

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public int getTauxDuJour() {
        return tauxDuJour;
    }

    public void setTauxDuJour(int tauxDuJour) {
        this.tauxDuJour = tauxDuJour;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
