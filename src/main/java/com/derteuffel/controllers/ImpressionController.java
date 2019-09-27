package com.derteuffel.controllers;

import com.derteuffel.entities.*;
import com.derteuffel.services.CommandeService;
import com.derteuffel.services.ImpressionService;
import com.derteuffel.services.RoleService;
import com.derteuffel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ImpressionController {

    @Autowired
    private ImpressionService impressionService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private CommandeService commandeService;

    // Add autres papier methods
    @GetMapping("/impression/papier/form/{commandeId}")
    public String form(Model model, HttpServletRequest request, HttpSession session, @PathVariable int commandeId){
        session.setAttribute("lastUrl", request.getHeader("referer"));
        Commande commande= commandeService.getOne(commandeId);
        model.addAttribute("commande",commande);
        model.addAttribute("papier", new Papier());
        return "impression/papier/form";
    }

    @PostMapping("/impression/papier/save")
    public String save(Papier commande, String price, int commandeId,Model model){
        commande.setUnit_price(Double.parseDouble(price));
        commande.setTotal_price(commande.getUnit_price()*commande.getQuantite());
        Commande commande1=commandeService.getOne(commandeId);
        commande.setCommande(commande1);
        impressionService.savePapier(commande);
        return "redirect:/impression/papier/papier/"+commande.getImpressionId();
    }

    @GetMapping("/impression/papier/papier/{impressionId}")
    public String getOnePapier(@PathVariable int impressionId, Model model){
        Papier papier=impressionService.getOnePapier(impressionId);
        model.addAttribute("papier",papier);
        return "impression/papier/papier";
    }


    @GetMapping("/impression/papier/papiers/{commandeId}")
    public String getAllPapiers(@PathVariable int commandeId, Model model){
        List<Papier> papiers=impressionService.findAllPapiers(commandeId);
        Commande commande=commandeService.getOne(commandeId);
        model.addAttribute("commande",commande);
        model.addAttribute("papiers",papiers);
        return "impression/papier/papiers";
    }

    //Add clothes printables methods
    @GetMapping("/impression/vetement/form/{commandeId}")
    public String vetementform(Model model, HttpServletRequest request, HttpSession session, @PathVariable int commandeId){
        session.setAttribute("lastUrl", request.getHeader("referer"));
        Commande commande= commandeService.getOne(commandeId);
        model.addAttribute("commande",commande);
        model.addAttribute("vetement", new Vetement());
        return "impression/vetement/form";
    }

    @PostMapping("/impression/vetement/save")
    public String save(Vetement commande, String price, int commandeId,Model model){
        commande.setUnit_price(Double.parseDouble(price));
        commande.setTotal_price(commande.getUnit_price()*commande.getQuantite());
        Commande commande1=commandeService.getOne(commandeId);
        commande.setCommande(commande1);
        impressionService.saveVetement(commande);
        return "redirect:/impression/vetement/vetement/"+commande.getImpressionId();
    }

    @GetMapping("/impression/vetement/vetement/{impressionId}")
    public String getOneVetement(@PathVariable int impressionId, Model model){
        Vetement vetement=impressionService.getOneVetement(impressionId);
        model.addAttribute("vetement",vetement);
        return "impression/vetement/vetement";
    }


    @GetMapping("/impression/vetement/vetements/{commandeId}")
    public String getAllVetements(@PathVariable int commandeId, Model model){
        List<Vetement> vetements=impressionService.findAllVetements(commandeId);
        model.addAttribute("vetements",vetements);
        return "impression/vetement/vetements";
    }

    // Add autres elements methods
    @GetMapping("/impression/autre/form/{commandeId}")
    public String autreform(Model model, HttpServletRequest request, HttpSession session, @PathVariable int commandeId){
        session.setAttribute("lastUrl", request.getHeader("referer"));
        Commande commande= commandeService.getOne(commandeId);
        model.addAttribute("commande",commande);
        model.addAttribute("autre", new Autre());
        return "impression/autre/form";
    }



    @PostMapping("/impression/autre/save")
    public String save(Autre commande, String price, int commandeId,Model model){
        commande.setUnit_price(Double.parseDouble(price));
        commande.setTotal_price(commande.getUnit_price()*commande.getQuantite());
        Commande commande1=commandeService.getOne(commandeId);
        commande.setCommande(commande1);
        impressionService.saveAutre(commande);
        return "redirect:/impression/autre/autre/"+commande.getImpressionId();
    }

    @GetMapping("/impression/autre/autre/{impressionId}")
    public String getOneAutre(@PathVariable int impressionId, Model model){
       Autre autre=impressionService.getOneAutre(impressionId);
        model.addAttribute("autre",autre);
        return "impression/autre/autre";
    }


    @GetMapping("/impression/autre/autres/{commandeId}")
    public String getAllAutres(@PathVariable int commandeId, Model model){
        List<Autre> autres=impressionService.findAllAutres(commandeId);
        model.addAttribute("autres",autres);
        return "impression/autre/autres";
    }


    @GetMapping("/commande/impression/{commandeId}")
    public String getOne(@PathVariable int commandeId, Model model){
        model.addAttribute("impression", impressionService.getOne(commandeId));
        return "impression/detail";

    }

    @GetMapping("/impression/validation/{commandeId}")
    public String validation(@PathVariable int commandeId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Impression commande=impressionService.getOne(commandeId);
        userService.updateUser(user);
        impressionService.update(commande);
        return "redirect:/commande/impression/"+commande.getImpressionId();
    }


    @GetMapping("/impression/impressions")
    public String findAll(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Role role= roleService.findByRole("ROOT_MASTER");
        Role role1= roleService.findByRole("ROOT");
        Role role2= roleService.findByRole("GERANT");
            model.addAttribute("impressions",impressionService.findll());

        return "/impression/impressions";
    }


    @GetMapping("/impressio/confirm/{commandeId}")
    public String confirm(@PathVariable int commandeId){

        return "redirect:/commande/impression/"+commandeId;
    }
}
