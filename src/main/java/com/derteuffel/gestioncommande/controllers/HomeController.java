package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CommandeService;
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
     CommandeRepository commandeRepository;

    @Autowired
    private PageModel pageModel;


    @GetMapping(value = {"/" , "/accueil"})
    public String index(Model model){


       pageModel.setSIZE(5);
       pageModel.initPageAndSize();


        Page<Commande> commandes = commandeService.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
        model.addAttribute("commande",new Commande());
        System.out.println(commandes);
        model.addAttribute("lists",commandes);
        //model.addAttribute("commandes",commandeService.findAll());
        return "index";
    }
}
