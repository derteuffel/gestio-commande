package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.Services.ControleSoutraitanceService;
import com.derteuffel.gestioncommande.Services.MaterielService;
import com.derteuffel.gestioncommande.Services.ProductService;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.ControleImpression;
import com.derteuffel.gestioncommande.entities.ControleSousTraitance;
import com.derteuffel.gestioncommande.helpers.PageModel;
import com.derteuffel.gestioncommande.repositories.ControleImpressionRepository;
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
import java.util.List;

@Controller
@RequestMapping("/controle/sousTraitance")
public class ControleSousTraitanceController {


    @Autowired
    private ControleSoutraitanceService controleSoutraitanceService;
    @Autowired
    private CompteService compteService;
    @Autowired
    private PageModel pageModel;
    @Autowired
    private ProductService productService;

    @Autowired
    private MaterielService materielService;


    @GetMapping("/lists")
    public String findAll(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        model.addAttribute("compte",compte);
        List<ControleSousTraitance> lists = controleSoutraitanceService.findAll();
        model.addAttribute("lists", lists);
        model.addAttribute("controleSousTraitance", new ControleSousTraitance());
        model.addAttribute("produits", productService.findAll());
        model.addAttribute("materiels", materielService.findAll());
        model.addAttribute("comptes", compteService.findAll());
        return "controle/sousTraitance/lists";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(Model model, HttpServletRequest request, @PathVariable Long id) {

        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        model.addAttribute("compte",compte);
        model.addAttribute("controleSousTraitance", controleSoutraitanceService.getOne(id));
        model.addAttribute("produits", productService.findAll());
        model.addAttribute("materiels", materielService.findAll());
        model.addAttribute("comptes", compteService.findAll());
        return "controle/sousTraitance/edit";
    }



    @PostMapping("/save")
    public String save(ControleSousTraitance controleSousTraitance, RedirectAttributes redirectAttributes) {
        try {
            controleSoutraitanceService.save(controleSousTraitance);
            redirectAttributes.addFlashAttribute("success", "Enregistrement réussis");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Ooops, une erreur est survenue lors de l'excecution de votre operation");
        }
        return "redirect:/controle/sousTraitance/lists";
    }

    @PostMapping("/update")
    public String update(ControleSousTraitance controleSousTraitance, RedirectAttributes redirectAttributes) {
        try {
            controleSoutraitanceService.save(controleSousTraitance);
            redirectAttributes.addFlashAttribute("success", "Modification réussis");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Ooops, une erreur est survenue lors de l'excecution de votre operation");
        }
        return "redirect:/controle/sousTraitance/lists";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try{
            controleSoutraitanceService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Suppression réussis");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Ooops, une erreur est survenue lors de l'excecution de votre operation");
        }

        return "redirect:/controle/sousTraitance/lists";
    }
}
