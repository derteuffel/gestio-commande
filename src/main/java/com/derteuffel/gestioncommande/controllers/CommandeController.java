package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ClientService;
import com.derteuffel.gestioncommande.Services.CommandeService;
import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.entities.*;
import com.derteuffel.gestioncommande.repositories.ApprobationRepository;
import com.derteuffel.gestioncommande.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;

@Controller
@RequestMapping("/commande")
public class CommandeController {


    @Autowired
    private CommandeService commandeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ApprobationRepository approbationRepository;


    //------------ Save Command method Start -----------------//
    @PostMapping("/save")
    public String save(Commande commande, String name, String email, String phone, String adresse, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        Compte compte = compteRepository.findByLogin(principal.getName());
        Client clientSearch = clientService.findByPhone(phone);
        System.out.println("je suis :"+phone);
        System.out.println(clientSearch);
        Client client = new Client();


        if (clientSearch != null){
            commande.setClient(clientSearch);
        }else {

            client.setAdresse(adresse);
            client.setEmail(email);
            client.setPhone(phone);
            client.setName(name);
            clientService.save(client);
            commande.setClient(client);
        }

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy");
        DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Approbation approbation = new Approbation();
        approbation.setComment("Initializing order");
        approbation.setDate(format1.format(date));
        commande.setCode("N°"+commandeService.findAll().size()+1+"/"+format.format(date));
        commande.setStates(ECommande.EN_ATTENTE.toString());
        commande.setCompte(compte);
        commandeService.save(commande);
        approbation.setCommande(commande);
        approbationRepository.save(approbation);
        return "redirect:/";

    }

    //------------ Find all Command method Start -----------------//
    @GetMapping("/commandes")
    public String commands(){
        return "redirect:/";
    }

    //------------ Update form for a Command ------------------//

    @PostMapping("/update/{commandeId}")
    public String update( @PathVariable Long commandeId, String title, int tauxJour){

        Commande commande= commandeService.getOne(commandeId);
        commande.setTauxJour(tauxJour);
        commande.setTitle(title);

        commandeService.update(commande);
        return "redirect:/";
    }

    //----------- Delete Item for commande ------------------//

    @GetMapping("/delete/{commandeId}")
    public String delete(@PathVariable Long commandeId){
        commandeService.deleteById(commandeId);
        return "redirect:/";
    }
}
