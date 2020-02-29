package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CategoryService;
import com.derteuffel.gestioncommande.Services.CommandeService;
import com.derteuffel.gestioncommande.entities.*;
import com.derteuffel.gestioncommande.helpers.PageModel;
import com.derteuffel.gestioncommande.repositories.CommandeRepository;
import com.derteuffel.gestioncommande.repositories.CompteRepository;
import com.derteuffel.gestioncommande.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CommandeService commandeService;



    @Autowired
     private CommandeRepository commandeRepository;

    @Autowired
    private PageModel pageModel;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping(value = {"/" , "/accueil"})
    public String index(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        Compte compte =  compteRepository.findByLogin(principal.getName());
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        request.getSession().setAttribute("currentDate", dateFormat.format(currentDate));
        request.getSession().setAttribute("compte", compte);
        request.getSession().setAttribute("lastUrl", request.getHeader("Referer"));


        List<Commande> commandes = commandeRepository.findAll();
        model.addAttribute("commande",new Commande());
        model.addAttribute("article", new Article());
        System.out.println(commandes);
        Role role = roleRepository.findByName("ROLE_DRH");
        Role role1 = roleRepository.findByName("ROLE_INFO");
        Role role2 = roleRepository.findByName("ROLE_USER");
        if (compte.getRoles().size() ==1 && (compte.getRoles().contains(role)
                || compte.getRoles().contains(role1))){
            commandes.addAll(commandeRepository.findAllByCompte_Id(compte.getId()));
        }
        if (compte.getRoles().size() == 1 && compte.getRoles().contains(role2)){

            System.out.println("je suis la ");
            return "redirect:/receptions/lists";
        }else {
            System.out.println("je suis ici");
            System.out.println(compte.getRoles());
            model.addAttribute("compte", compte);
            model.addAttribute("lists", commandes);
            //model.addAttribute("commandes",commandeService.findAll());
            return "index";
        }
    }

    @GetMapping("/backside")
    public String back(HttpServletRequest request){
        System.out.println((String) request.getSession().getAttribute("lastUrl"));
        return "redirect:"+(String) request.getSession().getAttribute("lastUrl");
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }
}
