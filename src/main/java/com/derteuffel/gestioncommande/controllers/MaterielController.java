package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.AutreMaterielService;
import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.Services.ImprimanteService;
import com.derteuffel.gestioncommande.Services.OrdinateurService;
import com.derteuffel.gestioncommande.entities.AutreMateriel;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.Imprimante;
import com.derteuffel.gestioncommande.entities.Ordinateur;
import com.derteuffel.gestioncommande.helpers.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/materiel")
public class MaterielController {


    @Autowired
    private OrdinateurService ordinateurService;

    @Autowired
    private ImprimanteService imprimanteService;

    @Autowired
    private AutreMaterielService autreMaterielService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private PageModel pageModel;

//------- Ordinateur methods -----------//
    @GetMapping("/ordinateurs")
    public String findAll(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());

        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        model.addAttribute("compte", compte);
        model.addAttribute("ordinateur", new Ordinateur());
        model.addAttribute("title", "Ordinateurs");
        model.addAttribute("lists",ordinateurService.findAll());
        return "materiel/all";
    }

    @PostMapping("/ordinateur/save")
    public String save(Ordinateur s, RedirectAttributes redirectAttributes) {
         ordinateurService.save(s);
         redirectAttributes.addFlashAttribute("message","Vous venez d'ajouter un nouvel ordinateur dans l'ensemble de l'equipement");
         return "redirect:/materiel/ordinateurs";
    }


    @GetMapping("/ordinateur/delete/{materielId}")
    public String deleteById(@PathVariable Long materielId, RedirectAttributes redirectAttributes) {
        ordinateurService.deleteById(materielId);
        redirectAttributes.addFlashAttribute("deleteMessage","Vous avez supprimer avec succes votre element");
        return "redirect:/materiel/ordinateurs";
    }


    //------- Imprimante methods ------//


    @GetMapping("/imprimantes")
    public String findAllImprimantes(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        model.addAttribute("compte",compte);
        model.addAttribute("imprimante", new Imprimante());
        model.addAttribute("title", "Imprimantes");
        model.addAttribute("lists",imprimanteService.findAll());
        return "materiel/all";
    }

    @PostMapping("/imprimante/save")
    public String saveImprimante(Imprimante s, RedirectAttributes redirectAttributes) {
        imprimanteService.save(s);
        redirectAttributes.addFlashAttribute("message","Vous venez d'ajouter une nouvelle imprimante dans l'ensemble de l'equipement");
        return "redirect:/materiel/imprimantes";
    }


    @GetMapping("/imprimante/delete/{materielId}")
    public String deleteImprimante(@PathVariable Long materielId, RedirectAttributes redirectAttributes) {
        ordinateurService.deleteById(materielId);
        redirectAttributes.addFlashAttribute("deleteMessage","Vous avez supprimer avec succes votre element");
        return "redirect:/materiel/imprimantes";
    }

    //--------- Autres Materiels methods -------//

    @GetMapping("/studios")
    public String findAllOther(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        model.addAttribute("compte", compte);
        model.addAttribute("autre", new AutreMateriel());
        model.addAttribute("title", "Autres Materiels");
        model.addAttribute("lists",autreMaterielService.findAll());
        return "materiel/all";
    }

    @PostMapping("/studios/save")
    public String saveAutres(AutreMateriel s, RedirectAttributes redirectAttributes) {
        autreMaterielService.save(s);
        redirectAttributes.addFlashAttribute("message","Vous venez d'ajouter une nouvelle ce materiel dans l'ensemble de l'equipement");
        return "redirect:/materiel/studios";
    }


    @GetMapping("/studios/delete/{materielId}")
    public String deleteAutres(@PathVariable Long materielId, RedirectAttributes redirectAttributes) {
        ordinateurService.deleteById(materielId);
        redirectAttributes.addFlashAttribute("deleteMessage","Vous avez supprimer avec succes votre element");
        return "redirect:/materiel/studios";
    }
}
