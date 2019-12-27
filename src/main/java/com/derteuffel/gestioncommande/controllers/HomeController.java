package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CategoryService;
import com.derteuffel.gestioncommande.Services.CommandeService;
import com.derteuffel.gestioncommande.entities.Article;
import com.derteuffel.gestioncommande.entities.Commande;
import com.derteuffel.gestioncommande.helpers.PageModel;
import com.derteuffel.gestioncommande.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
     private CommandeRepository commandeRepository;

    @Autowired
    private PageModel pageModel;


    @GetMapping(value = {"/" , "/accueil"})
    public String index(Model model, HttpServletRequest request){

        request.getSession().setAttribute("lastUrl", request.getHeader("Referer"));

       pageModel.setSIZE(5);
       pageModel.initPageAndSize();


        Page<Commande> commandes = commandeService.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
        model.addAttribute("commande",new Commande());
        model.addAttribute("article", new Article());
        System.out.println(commandes);
        model.addAttribute("lists",commandes);
        model.addAttribute("categories",categoryService.findAll());
        //model.addAttribute("commandes",commandeService.findAll());
        return "index";
    }

    @GetMapping("/backside")
    public String back(HttpServletRequest request){
        System.out.println((String) request.getSession().getAttribute("lastUrl"));
        return "redirect:"+(String) request.getSession().getAttribute("lastUrl");
    }
}
