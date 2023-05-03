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


    private Double buyedPrice;
    private Double sellPrice;

    private int quantity;
    private String monnaie;
    private Double totalBuyedUSD;
    private Double totalSellUSD;
    private Double totalBuyedCDF;
    private Double totalSellCDF;

    private String Description;

    private String dateJour;

    private String productCode;

    private Boolean activate;

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

    public Double getBuyedPrice() {
        return buyedPrice;
    }

    public void setBuyedPrice(Double buyedPrice) {
        this.buyedPrice = buyedPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
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

    public Double getTotalBuyedUSD() {
        return totalBuyedUSD;
    }

    public void setTotalBuyedUSD(Double totalBuyedUSD) {
        this.totalBuyedUSD = totalBuyedUSD;
    }

    public Double getTotalSellUSD() {
        return totalSellUSD;
    }

    public void setTotalSellUSD(Double totalSellUSD) {
        this.totalSellUSD = totalSellUSD;
    }

    public Double getTotalBuyedCDF() {
        return totalBuyedCDF;
    }

    public void setTotalBuyedCDF(Double totalBuyedCDF) {
        this.totalBuyedCDF = totalBuyedCDF;
    }

    public Double getTotalSellCDF() {
        return totalSellCDF;
    }

    public void setTotalSellCDF(Double totalSellCDF) {
        this.totalSellCDF = totalSellCDF;
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

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }
}
