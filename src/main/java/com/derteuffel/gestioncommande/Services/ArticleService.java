package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Article;
import com.derteuffel.gestioncommande.entities.Category;
import com.derteuffel.gestioncommande.entities.Commande;
import com.derteuffel.gestioncommande.repositories.ArticleRepository;
import com.derteuffel.gestioncommande.repositories.CategoryRepository;
import com.derteuffel.gestioncommande.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public <S extends Article> List<S> saveAll(List<S> iterable, Long commandeId, List<Long> categoryIds) {

        Commande commande = commandeRepository.getOne(commandeId);
        for(int i=0; i<=iterable.size();i++){
            iterable.get(i).setCommande(commande);
            Category category = categoryRepository.getOne(categoryIds.get(i));
            iterable.get(i).setCategory(category);
        }
        return articleRepository.saveAll(iterable);
    }

    public Article getOne(Long articleId) {
        return articleRepository.getOne(articleId);
    }

    public List<Article> findAllByCommande_CommandeId(Long commandeId) {
        return articleRepository.findAllByCommande_CommandeId(commandeId);
    }

    public List<Article> findAllByCategory_CategoryId(Long categoryId) {
        return articleRepository.findAllByCategory_CategoryId(categoryId);
    }

    public <S extends Article> S save(S s, Long commandId, Long categoryId) {

        Commande commande = commandeRepository.getOne(commandId);
        s.setCommande(commande);

        Category category = categoryRepository.getOne(categoryId);
        s.setCategory(category);

        return articleRepository.save(s);
    }

    public long count() {
        return articleRepository.count();
    }

    public void deleteById(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
