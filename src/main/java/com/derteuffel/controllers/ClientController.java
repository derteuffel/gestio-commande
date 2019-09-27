package com.derteuffel.controllers;


import com.derteuffel.entities.Client;
import com.derteuffel.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/client/form")
    public String form(Model model){
        model.addAttribute("client",new Client());
        return "client/form";
    }

    @PostMapping("/client/save")
    public String save(Client client){
        Client client1=clientService.findByEmail(client.getEmailClient());

        if (client1 != null) {
            return "redirect:/client/client/"+client1.getClientId();
        }else {
            client.setCodeClient("#SSA/CLI/"+(clientService.findAll().size()+1));

            clientService.save(client);
            return "redirect:/client/client/"+client.getClientId();
        }
    }


    @GetMapping("/client/client/{clientId}")
    public String findOne(@PathVariable int clientId, Model model){

        model.addAttribute("client",clientService.getOne(clientId));
        return "client/client";
    }


    @GetMapping("/search/client")
    public String search(@RequestParam(name = "searched", defaultValue = "") String item){
        List<Client> clients=clientService.findClient("%"+item+"%");

        System.out.println(clients);
        return "client/clients";
    }


    @GetMapping("/client/clients")
    public String findAll(Model model){
        List<Client> clients=clientService.findAll();
        model.addAttribute("clients",clients);
        return "client/clients";
    }
}
