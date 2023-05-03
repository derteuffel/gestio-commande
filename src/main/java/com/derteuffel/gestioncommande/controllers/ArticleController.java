package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ArticleService;
import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.entities.*;
import com.derteuffel.gestioncommande.helpers.PageModel;
import com.derteuffel.gestioncommande.repositories.AddedProductRepository;
import com.derteuffel.gestioncommande.repositories.CommandeRepository;
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
    @Autowired
    private AddedProductRepository addedProductRepository;
    @Autowired
    private CommandeRepository commandeRepository;

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
    public String save(Article s, @PathVariable Long commandId,String price, RedirectAttributes model) {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        s.setDateJour(dateFormat.format(date));

        if (price.split("_")[1] != null){
            AddedProduct addedProduct = addedProductRepository.getOne(Long.parseLong(price.split("_")[1]));
            Product product = addedProduct.getProduct();
            if (product != null) {
                if (product.getQuantity() <= 0 || s.getQuantity() > product.getQuantity()) {
                    model.addFlashAttribute("message", "Desole,  veuilez approvisionner le stock dans le magasin");
                    return "redirect:/commande/details/" + commandId;
                }
            }
        }
        articleService.save(s, commandId,price);
         return "redirect:/commande/details/"+commandId;
    }

    @GetMapping("/edit/form/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpServletRequest request ){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        Article article = articleService.getOne(id);
        List<AddedProduct> elements = addedProductRepository.findAllByActionType("AJOUT");

        model.addAttribute("compte",compte);
        model.addAttribute("lists",elements);
        model.addAttribute("article",article);
        return "article/edit";
    }
    @PostMapping("/update/{articleId}")
    public String update(Article s, @PathVariable Long articleId) {

        articleService.update(s, articleId);
        Article article = articleService.getOne(articleId);

        return "redirect:/commande/details/"+article.getCommande().getCommandeId();
    }

    public void deleteById(Long articleId) {
        articleService.deleteById(articleId);
    }
}
