package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ArticleService;
import com.derteuffel.gestioncommande.Services.CategoryService;
import com.derteuffel.gestioncommande.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    public List<Article> findAll() {
        return articleService.findAll();
    }

    public <S extends Article> List<S> saveAll(List<S> iterable, Long commandeId, List<Long> categoryIds) {
        return articleService.saveAll(iterable, commandeId, categoryIds);
    }


    @GetMapping("/edit/form/{articleId}")
    public String editForm(Model model, @PathVariable Long articleId){
        Article article = articleService.getOne(articleId);
        model.addAttribute("commandeId",article.getCommande().getCommandeId());
        model.addAttribute("article",article);
        model.addAttribute("categories", categoryService.findAll());
        return "article/edit";
    }

    @GetMapping("/get/{articleId}")
    public String getOne(@PathVariable Long articleId, Model model) {
        Article article = articleService.getOne(articleId);

        model.addAttribute("article",article);
        return "article/detail";
    }

    public List<Article> findAllByCommande_CommandeId(Long commandeId) {
        return articleService.findAllByCommande_CommandeId(commandeId);
    }

    public List<Article> findAllByCategory_CategoryId(Long categoryId) {
        return articleService.findAllByCategory_CategoryId(categoryId);
    }

    @PostMapping("/save/{commandId}")
    public String save(Article s, @PathVariable Long commandId, String montant,  String categoryId) {
        s.setPrice(Double.parseDouble(montant));
        s.setTotalPrice(s.getPrice()*s.getQuantity());
        articleService.save(s, commandId, Long.parseLong(categoryId));

         return "redirect:/";
    }

    @PostMapping("/update")
    public String update(Article s, Long commandId, String montant,  String categoryId) {
        s.setPrice(Double.parseDouble(montant));
        s.setTotalPrice(s.getPrice()*s.getQuantity());
        articleService.save(s, commandId, Long.parseLong(categoryId));

        return "redirect:/article/get/"+ s.getArticleId();
    }

    public long count() {
        return articleService.count();
    }

    public void deleteById(Long articleId) {
        articleService.deleteById(articleId);
    }
}
