package com.derteuffel.gestioncommande.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "article")
public class Article implements Serializable {

    @Id
    @GeneratedValue
    private Long articleId;

    private String name;


    private Double price;

    private int quantity;
    private String monnaie;
    private Double totalUSD;
    private Double totalCDF;

    private String Description;

    private String dateJour;

    private String productCode;

    @Column(unique = true)
    @NotEmpty(message = "Cette colone doit toujours contenir une valeur")
    //@Size(max = 10, min = 8)
    private String codeArticle;

    @ManyToOne
    @JsonIgnoreProperties("articles")
    private Commande commande;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMonnaie() {
        return monnaie;
    }

    public void setMonnaie(String monnaie) {
        this.monnaie = monnaie;
    }

    public Double getTotalUSD() {
        return totalUSD;
    }

    public void setTotalUSD(Double totalUSD) {
        this.totalUSD = totalUSD;
    }

    public Double getTotalCDF() {
        return totalCDF;
    }

    public void setTotalCDF(Double totalCDF) {
        this.totalCDF = totalCDF;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDateJour() {
        return dateJour;
    }

    public void setDateJour(String dateJour) {
        this.dateJour = dateJour;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCodeArticle() {
        return codeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }
}
