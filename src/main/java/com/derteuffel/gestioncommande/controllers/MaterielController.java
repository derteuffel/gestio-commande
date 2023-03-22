package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.Services.MaterielService;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.Materiel;
import com.derteuffel.gestioncommande.helpers.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/materiel")
public class MaterielController {




    @Autowired
    private CompteService compteService;

    @Autowired
    private MaterielService materielService;

    final static List<String> departements = new ArrayList<>(Arrays.asList("departement 1","departement 2","departement 3"));
    final static List<String> categories = new ArrayList<>(Arrays.asList("category 1","category 2","category 3"));

//------- Ordinateur methods -----------//
    @GetMapping("/materiels")
    public String findAll(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte", compte);
        model.addAttribute("materiel", new Materiel());
        model.addAttribute("departements", departements);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Materiels");
        model.addAttribute("lists",materielService.findAll());
        return "materiel/all";
    }

    //------- Find By departement methods -----------//
    @GetMapping("/materiels/departement")
    public String findAllByDepartement(Model model, HttpServletRequest request, @RequestParam("departement") String departement) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte", compte);
        model.addAttribute("materiel", new Materiel());
        model.addAttribute("departements", departements);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Materiels");
        model.addAttribute("lists",materielService.findAllByDepartement(departement));
        return "materiel/all";
    }

    //------- Find By category methods -----------//
    @GetMapping("/materiels/category")
    public String findAllByCategory(Model model, HttpServletRequest request, @RequestParam("category") String category) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte", compte);
        model.addAttribute("materiel", new Materiel());
        model.addAttribute("departements", departements);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Materiels");
        model.addAttribute("lists",materielService.findAllByCategory(category));
        return "materiel/all";
    }

    //------- Find By marque methods -----------//
    @GetMapping("/materiels/marque")
    public String findAllByMarque(Model model, HttpServletRequest request, @RequestParam("marque") String marque) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte", compte);
        model.addAttribute("materiel", new Materiel());
        model.addAttribute("departements", departements);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Materiels");
        model.addAttribute("lists",materielService.findAllByMarque("%"+marque+"%"));
        return "materiel/all";
    }

    //------- Find By departement and category methods -----------//
    @GetMapping("/materiels/departement/category")
    public String findAllByDepartementAndCategory(Model model, HttpServletRequest request, @RequestParam("departement") String departement, @RequestParam("category") String category) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte", compte);
        model.addAttribute("materiel", new Materiel());
        model.addAttribute("departements", departements);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Materiels");
        model.addAttribute("lists",materielService.findAllByDepartementAndCategory("%"+departement+"%","%"+category+"%"));
        return "materiel/all";
    }

    //------- Find By departement and marque methods -----------//
    @GetMapping("/materiels/departement/marque")
    public String findAllByDepartementAndMarque(Model model, HttpServletRequest request, @RequestParam("departement") String departement, @RequestParam("marque") String marque) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte", compte);
        model.addAttribute("materiel", new Materiel());
        model.addAttribute("departements", departements);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Materiels");
        model.addAttribute("lists",materielService.findAllByDepartementAndCategory("%"+departement+"%","%"+marque+"%"));
        return "materiel/all";
    }

    //------- Find By departement and marque methods -----------//
    @GetMapping("/materiels/departement/status")
    public String findAllByDepartementAndStatus(Model model, HttpServletRequest request, @RequestParam("departement") String departement, @RequestParam("status") String status) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte", compte);
        model.addAttribute("materiel", new Materiel());
        model.addAttribute("departements", departements);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Materiels");
        model.addAttribute("lists",materielService.findAllByDepartementAndStatus("%"+departement+"%","%"+status+"%"));
        return "materiel/all";
    }

    //------- Find By status methods -----------//
    @GetMapping("/materiels/status")
    public String findAllByStatus(Model model, HttpServletRequest request, @RequestParam("status") String status) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());

        model.addAttribute("compte", compte);
        model.addAttribute("materiel", new Materiel());
        model.addAttribute("departements", departements);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Materiels");
        model.addAttribute("lists",materielService.findAllByStatus("%"+status+"%"));
        return "materiel/all";
    }

    //------- Find By status methods -----------//
    @GetMapping("/detail/{id}")
    public String findOne(Model model, HttpServletRequest request, @PathVariable Long id) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());


        model.addAttribute("compte", compte);
        model.addAttribute("departements", departements);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Materiels");
        model.addAttribute("materiel",materielService.getOne(id));
        return "materiel/detail";
    }



    @PostMapping("/save")
    public String save(Materiel s, RedirectAttributes redirectAttributes) {

        materielService.save(s);
         redirectAttributes.addFlashAttribute("message","Vous venez d'ajouter un nouvel ordinateur dans l'ensemble de l'equipement");
         return "redirect:/materiel/materiels";
    }




    @GetMapping("/delete/{materielId}")
    public String deleteById(@PathVariable Long materielId, RedirectAttributes redirectAttributes) {
        materielService.deleteById(materielId);
        redirectAttributes.addFlashAttribute("deleteMessage","Vous avez supprimer avec succes votre element");
        return "redirect:/materiel/materiels";
    }

}
