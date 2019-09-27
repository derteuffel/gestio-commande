package com.derteuffel.controllers;

import com.derteuffel.entities.Client;
import com.derteuffel.entities.Commande;
import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.repositories.CommandeRepository;
import com.derteuffel.services.ClientService;
import com.derteuffel.services.CommandeService;
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

@Controller
public class CommandeController {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private ClientService clientService;
    @GetMapping("/commande/form/{clientId}")
    public String form(Model model, HttpServletRequest request, HttpSession session, @PathVariable int clientId){
        session.setAttribute("lastUrl", request.getHeader("referer"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client= clientService.getOne(clientId);
        model.addAttribute("client",client);
        User user = userService.findUserByEmail(auth.getName());
        session.setAttribute("loggedName", user.getName() + " " + user.getLastName());
        model.addAttribute("commande", new Commande());
        return "commande/form";
    }

    @PostMapping("/commande/save")
    public String save(Commande commande, int clientId, HttpSession session,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        commandeService.save(commande, clientId);
        user.getCommandes().add(commande);
        userService.updateUser(user);

        return "redirect:/commande/commande/"+commande.getCommandeId();
    }


    @GetMapping("/commande/commande/{commandeId}")
    public String getOne(@PathVariable int commandeId, Model model){
        model.addAttribute("commande", commandeService.getOne(commandeId));
        return "commande/detail";

    }

    @GetMapping("/commande/validation/{commandeId}")
    public String validation(@PathVariable int commandeId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Commande commande=commandeService.getOne(commandeId);
        commande.getUsers().add(user);
        commandeService.update(commande);
        return "redirect:/commande/commande/"+commande.getCommandeId();
    }


    @GetMapping("/commande/elements/impressions/{commandeId}")
    public String findByCommande(@PathVariable int commandeId, Model model){
        model.addAttribute("impressions", commandeService.findByCommande(commandeId));
        return "impression/papier/papiers";
    }


    @GetMapping("/commande/commandes")
    public String findAll(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Role role= roleService.findByRole("ROOT_MASTER");
        Role role1= roleService.findByRole("ROOT");
        Role role2= roleService.findByRole("GERANT");
        if (user.getRoles().contains(role) || user.getRoles().contains(role1) || user.getRoles().contains(role2)){
            model.addAttribute("commandes",commandeService.findll());
        }else {
            model.addAttribute("commandes", commandeService.findByUser(user.getId()));
        }
        return "/commande/commandes";
    }
}
