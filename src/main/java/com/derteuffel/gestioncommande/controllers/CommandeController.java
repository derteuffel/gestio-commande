package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ClientService;
import com.derteuffel.gestioncommande.Services.CommandeService;
import com.derteuffel.gestioncommande.entities.Client;
import com.derteuffel.gestioncommande.entities.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


    //------------ Save Command method Start -----------------//
    @PostMapping("/save")
    public String save(Commande commande, String name, String email, String phone, String adresse){

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
        commande.setCode("N°"+commandeService.findAll().size()+1+"/"+format.format(date));
        commandeService.save(commande);
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
