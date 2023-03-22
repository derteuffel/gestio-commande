package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.Services.ControleImpressionService;
import com.derteuffel.gestioncommande.Services.MaterielService;
import com.derteuffel.gestioncommande.Services.ProductService;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.ControleImpression;
import com.derteuffel.gestioncommande.helpers.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/controle")
public class ControleImpressionController {

    @Autowired
    private ControleImpressionService controleImpressionService;
    @Autowired
    private CompteService compteService;
    @Autowired
    private PageModel pageModel;
    @Autowired
    private ProductService productService;

    @Autowired
    private MaterielService materielService;



    @GetMapping("/impressions/lists")
    public String findAll(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        model.addAttribute("compte",compte);
        List<ControleImpression> lists = controleImpressionService.findAll();
        model.addAttribute("lists", lists);
        model.addAttribute("controleImpression", new ControleImpression());
        model.addAttribute("produits", productService.findAll());
        model.addAttribute("materiels", materielService.findAll());
        model.addAttribute("comptes", compteService.findAll());
         return "controle/impressions/lists";
    }

    @GetMapping("/impressions/edit/{id}")
    public String updateForm(Model model, HttpServletRequest request, @PathVariable Long id) {

        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        model.addAttribute("compte",compte);
        model.addAttribute("controleImpression", controleImpressionService.getOne(id));
        model.addAttribute("produits", productService.findAll());
        model.addAttribute("materiels", materielService.findAll());
        model.addAttribute("comptes", compteService.findAll());
        return "controle/impressions/edit";
    }

    public ControleImpression getOne(Long aLong) {
        return controleImpressionService.getOne(aLong);
    }

    @PostMapping("/impressions/save")
    public String save(ControleImpression controleImpression, RedirectAttributes redirectAttributes) {
        try {
            controleImpressionService.save(controleImpression);
            redirectAttributes.addFlashAttribute("success", "Enregistrement réussis");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Ooops, une erreur est survenue lors de l'excecution de votre operation");
        }
         return "redirect:/controle/impressions/lists";
    }

    @PostMapping("/impressions/update")
    public String update(ControleImpression controleImpression, RedirectAttributes redirectAttributes) {
        try {
            controleImpressionService.save(controleImpression);
            redirectAttributes.addFlashAttribute("success", "Modification réussis");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Ooops, une erreur est survenue lors de l'excecution de votre operation");
        }
        return "redirect:/controle/impressions/lists";
    }

    @GetMapping("/impressions/delete/{id}")
    public String deleteById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try{
            controleImpressionService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Suppression réussis");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Ooops, une erreur est survenue lors de l'excecution de votre operation");
        }

        return "redirect:/controle/impressions/lists";
    }
}
