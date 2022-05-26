package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ArticleService;
import com.derteuffel.gestioncommande.Services.CategoryService;
import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.entities.Article;
import com.derteuffel.gestioncommande.entities.Caisse;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.Mouvement;
import com.derteuffel.gestioncommande.helpers.PageModel;
import com.derteuffel.gestioncommande.repositories.CaisseRepository;
import com.derteuffel.gestioncommande.repositories.MouvementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private PageModel pageModel;

    @GetMapping("/articles")
    public String findAll(Model model, HttpServletRequest request) {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte",compte);
        //model.addAttribute("lists",articleService.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));
        model.addAttribute("lists",articleService.findAll());
        return "article/articles";
    }

    public <S extends Article> List<S> saveAll(List<S> iterable, Long commandeId) {
        return articleService.saveAll(iterable, commandeId);
    }

/*
    @GetMapping("/edit/form/{articleId}")
    public String editForm(Model model, @PathVariable Long articleId){
        Article article = articleService.getOne(articleId);
        model.addAttribute("commandeId",article.getCommande().getCommandeId());
        model.addAttribute("article",article);
        model.addAttribute("categories", categoryService.findAll());
        return "article/edit";
    }*/

    @GetMapping("/get/{articleId}")
    public String getOne(@PathVariable Long articleId, Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        Article article = articleService.getOne(articleId);

        model.addAttribute("compte",compte);
        model.addAttribute("article",article);
        return "article/detail";
    }

    public List<Article> findAllByCommande_CommandeId(Long commandeId) {
        return articleService.findAllByCommande_CommandeId(commandeId);
    }



    @PostMapping("/save/{commandId}")
    public String save(Article s, @PathVariable Long commandId) {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        s.setDateJour(dateFormat.format(date));
        s.setType(s.getType().toString());
        s.setCategory(s.getCategory().toString());
        //s.setPrice(Double.parseDouble(montant));
        articleService.save(s, commandId);
         return "redirect:/";
    }

    @PostMapping("/update/{articleId}")
    public String update(Article s, @PathVariable Long articleId) {
        s.setType(s.getType().toString());
        s.setCategory(s.getCategory().toString());
        Article article = articleService.getOne(articleId);
        article.setDateJour(s.getDateJour());
        article.setCategory(s.getCategory());
        article.setType(s.getType());
        article.setName(s.getName());
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        article.setDateJour(dateFormat.format(date));
        article.setMonnaie(s.getMonnaie());
        article.setDescription(s.getDescription());
        article.setPrice(s.getPrice());

        articleService.save(article, article.getCommande().getCommandeId());

        return "redirect:/article/get/"+ s.getArticleId();
    }

    public long count() {
        return articleService.count();
    }

    public void deleteById(Long articleId) {
        articleService.deleteById(articleId);
    }
}
