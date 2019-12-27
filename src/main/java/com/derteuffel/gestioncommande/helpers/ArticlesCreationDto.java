package com.derteuffel.gestioncommande.helpers;


import com.derteuffel.gestioncommande.entities.Article;

import java.util.List;

public class ArticlesCreationDto {

    List<Article> articles;

    public void addArticles(Article article){
        this.articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
