package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository  extends JpaRepository<Article,Long> {

    List<Article> findAllByCommande_CommandeIdOrderByArticleIdDesc(Long commandeId);

    List<Article> findAllByProductCode(String productCode);

}
