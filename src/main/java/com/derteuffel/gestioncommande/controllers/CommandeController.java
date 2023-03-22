package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ArticleService;
import com.derteuffel.gestioncommande.Services.CommandeService;
import com.derteuffel.gestioncommande.Services.ProductService;
import com.derteuffel.gestioncommande.entities.*;
import com.derteuffel.gestioncommande.repositories.ApprobationRepository;
import com.derteuffel.gestioncommande.repositories.CompteRepository;
import com.derteuffel.gestioncommande.repositories.MouvementRepository;
import com.derteuffel.gestioncommande.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/commande")
public class CommandeController {


    @Autowired
    private CommandeService commandeService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ApprobationRepository approbationRepository;


    //------------ Save Command method Start -----------------//
    @PostMapping("/save")
    public String save(Commande commande, String name, String email, String phone, String adresse, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        Compte compte = compteRepository.findByLogin(principal.getName());
        System.out.println("je suis :"+phone);

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy");
        DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Approbation approbation = new Approbation();
        approbation.setComment("Initializing order");
        approbation.setDate(format1.format(date));
        commande.setCode("N°"+commandeService.findAll().size()+1+"/"+format.format(date));
        commande.setStates(ECommande.EN_ATTENTE.toString());
        commande.setCompte(compte);
        commande.setNbreArticle(0);
        commande.setGerantState(false);
        commande.setCaisseState(false);
        commande.setTechniqueState(false);
        commandeService.save(commande);
        approbation.setCommande(commande);
        approbationRepository.save(approbation);
        return "redirect:/commande/details/"+commande.getCommandeId();

    }

    //------------ Find all Command method Start -----------------//
    @GetMapping("/commandes")
    public String commands(){
        return "redirect:/";
    }

    //------------ Update form for a Command ------------------//

    @PostMapping("/update/{commandeId}")
    public String update( @PathVariable Long commandeId, String title, int tauxJour,
                          @DateTimeFormat(pattern = "yyyy-MM-dd") Date realeseDate, RedirectAttributes redirectAttributes){

        Commande commande= commandeService.getOne(commandeId);
        commande.setTauxJour(tauxJour);
        commande.setTitle(title);
        commande.setRealeseDate(realeseDate);

        commandeService.update(commande);
        redirectAttributes.addFlashAttribute("success","Commande modifier avec succes");
        return "redirect:/";
    }

    //----------- Delete Item for commande ------------------//

    @GetMapping("/delete/{commandeId}")
    public String delete(@PathVariable Long commandeId){
        commandeService.deleteById(commandeId);
        return "redirect:/";
    }

    //----------- Details Item for commande ------------------//

    @GetMapping("/details/{commandeId}")
    public String details(@PathVariable Long commandeId,HttpServletRequest request, Model model){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteRepository.findByLogin(principal.getName());
        Commande commande = commandeService.getOne(commandeId);
        List<Product> produits = productService.findAll();
        List<Compte> comptes = new ArrayList<>();
        List<Article> articles = articleService.findAllByCommande_CommandeId(commande.getCommandeId());
        List<Approbation> approbations = approbationRepository.findAllByCommande_CommandeId(commande.getCommandeId());
        for (Approbation approbation: approbations){
            if(approbation != null) {
                if (approbation.getCompte() != null) {
                    comptes.add(approbation.getCompte());
                    System.out.println(approbation.getCompte().getEmail());
                }
            }
        }
        System.out.println(comptes.size());
        if (comptes.contains(compte)){
            System.out.println(true);
            model.addAttribute("isApproved", true);
        }else {
            System.out.println(false);
            model.addAttribute("isApproved",false);
        }
        model.addAttribute("lists",articles);
        model.addAttribute("compte",compte);
        model.addAttribute("produits",produits);
        model.addAttribute("approbations", approbations);
        model.addAttribute("approbation",new Approbation());
        model.addAttribute("article",new Article());
        model.addAttribute("commande",commande);
        return "commande/detail";
    }


    @PostMapping("/approbation/{id}")
    public String setState(@PathVariable Long id, HttpServletRequest request, Approbation approbation,
                           RedirectAttributes redirectAttributes, String valeur){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteRepository.findByLogin(principal.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Commande commande = commandeService.getOne(id);
        Role gerant = roleRepository.findByName(ERole.ROLE_GERANT.toString());
        Role caisse = roleRepository.findByName(ERole.ROLE_CAISSE.toString());
        if (compte.getRoles().contains(gerant)){
            if (valeur.toLowerCase().equals("ACCORDER".toLowerCase())) {
                commande.setGerantState(true);
            }else {
               commande.setGerantState(false);
            }
        }else if (compte.getRoles().contains(caisse)){
            if (commande.getGerantState() == false){
                redirectAttributes.addFlashAttribute("message", "Le gerant doit avoir approuve, vueillez confirmer avec votre gerant");
            }else {
                if (valeur.toLowerCase().equals("ACCORDER".toLowerCase()))
                commande.setCaisseState(true);
            }
        }else {
            if (commande.getGerantState() == false){
                redirectAttributes.addFlashAttribute("message", "Le gerant doit avoir approuve, vueillez confirmer avec votre gerant");
            }else {
                commande.setTechniqueState(true);
            }
        }

        commandeService.save(commande);
        approbation.setCommande(commande);
        approbation.setCompte(compte);
        approbation.setDate(sdf.format(new Date()));
        approbation.setCompte1(compte.getLogin()+", Email : "+ compte.getEmail());
        approbationRepository.save(approbation);
        return "redirect:/commande/details/"+commande.getCommandeId();



    }


}
