package com.derteuffel.gestioncommande.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "commande")
@Data
public class Commande  implements Serializable {

    @Id
    @GeneratedValue
    private Long commandeId;

    private String title;

    private Date createdDate = new Date();

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realeseDate;

    private int tauxJour;

    private Double amountUSD=0.0;
    private Double amountCDF=0.0;

    private Boolean status;
    private String caisseState;
    private String techniqueState;
    private String gerantState;
    private String states;
    private int nbreArticle;

    private String code;

    private int quantity=0;

    @ManyToOne
    private Client client;

    /*@OneToMany(mappedBy = "commande")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<Article> articles;*/

   /* @OneToMany(mappedBy = "commande")
    private List<Approbation> approbations;*/


    @ManyToOne
    @JsonIgnoreProperties("commandes")
    private Compte compte;

    public int getNbreArticle() {
        return nbreArticle;
    }

    public void setNbreArticle(int nbreArticle) {
        this.nbreArticle = nbreArticle;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getRealeseDate() {
        return realeseDate;
    }

    public void setRealeseDate(Date realeseDate) {
        this.realeseDate = realeseDate;
    }

    public int getTauxJour() {
        return tauxJour;
    }

    public void setTauxJour(int tauxJour) {
        this.tauxJour = tauxJour;
    }

    public Double getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(Double amountUSD) {
        this.amountUSD = amountUSD;
    }

    public Double getAmountCDF() {
        return amountCDF;
    }

    public void setAmountCDF(Double amountCDF) {
        this.amountCDF = amountCDF;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /*public Collection<Article> getArticles() {
        return articles;
    }

    public void setArticles(Collection<Article> articles) {
        this.articles = articles;
    }*/

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public String getCaisseState() {
        return caisseState;
    }

    public void setCaisseState(String caisseState) {
        this.caisseState = caisseState;
    }

    public String getTechniqueState() {
        return techniqueState;
    }

    public void setTechniqueState(String techniqueState) {
        this.techniqueState = techniqueState;
    }

    public String getGerantState() {
        return gerantState;
    }

    public void setGerantState(String gerantState) {
        this.gerantState = gerantState;
    }
}
