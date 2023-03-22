package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ArticleService;
import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.entities.Article;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.Product;
import com.derteuffel.gestioncommande.helpers.PageModel;
import com.derteuffel.gestioncommande.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private CompteService compteService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PageModel pageModel;

    @GetMapping("/articles/commande/{id}")
    public String findAll(Model model, HttpServletRequest request, @PathVariable Long id) {

        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());

        model.addAttribute("compte",compte);
        model.addAttribute("lists",articleService.findAllByCommande_CommandeId(id));
        return "article/articles";
    }

    @GetMapping("/articles/product/{productCode}")
    public String findAllByProductCode(Model model, HttpServletRequest request, @PathVariable String productCode) {

        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte",compte);
        model.addAttribute("lists",articleService.findAllByProductCode(productCode));
        return "article/articles";
    }


    @GetMapping("/get/{articleId}")
    public String getOne(@PathVariable Long articleId, Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        Article article = articleService.getOne(articleId);

        model.addAttribute("compte",compte);
        model.addAttribute("article",article);
        return "article/detail";
    }


    @PostMapping("/save/{commandId}")
    public String save(Article s, @PathVariable Long commandId, RedirectAttributes model) {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        s.setDateJour(dateFormat.format(date));

        if (s.getName().split("_")[1] != null){
            String codeProduit = s.getName().split("_")[1];
            Product product = productRepository.findByProductCode(codeProduit);
            if (product != null) {
                if (product.getQuantity() <= 0 || s.getQuantity() > product.getQuantity()) {
                    model.addFlashAttribute("message", "Desole,  veuilez approvisionner le stock dans le magasin");
                    return "redirect:/commande/details/" + commandId;
                }
            }
        }
        articleService.save(s, commandId);
         return "redirect:/commande/details/"+commandId;
    }

    @PostMapping("/update/{articleId}")
    public String update(Article s, @PathVariable Long articleId) {


        articleService.update(s, articleId);

        return "redirect:/article/get/"+ s.getArticleId();
    }

    public void deleteById(Long articleId) {
        articleService.deleteById(articleId);
    }
}
