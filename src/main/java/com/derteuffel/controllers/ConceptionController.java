package com.derteuffel.controllers;

import com.derteuffel.entities.Commande;
import com.derteuffel.entities.Conception;
import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.services.CommandeService;
import com.derteuffel.services.ConceptionService;
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
public class ConceptionController {

    @Autowired
    private ConceptionService conceptionService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/conception/form")
    public String form(Model model, HttpServletRequest request, HttpSession session){
        session.setAttribute("lastUrl", request.getHeader("referer"));
        model.addAttribute("conception", new Conception());
        return "conception/form";
    }

    @PostMapping("/conception/save")
    public String save(Conception commande, String price, HttpSession session,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        commande.setUnit_price(Double.parseDouble(price));
        conceptionService.save(commande);
        user.getConceptions().add(commande);
        userService.updateUser(user);

        model.addAttribute("commande",commande);
        return "conception/confirmation";
    }


    @GetMapping("/commande/conception/{commandeId}")
    public String getOne(@PathVariable int commandeId, Model model){
        model.addAttribute("conception", conceptionService.getOne(commandeId));
        return "conception/detail";

    }

    @GetMapping("/conception/validation/{commandeId}")
    public String validation(@PathVariable int commandeId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Conception commande=conceptionService.getOne(commandeId);
        user.getConceptions().add(commande);
        userService.updateUser(user);
        conceptionService.update(commande);
        return "redirect:/commande/conception/"+commande.getCommandeId();
    }


    @GetMapping("/conception/conceptions")
    public String findAll(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Role role= roleService.findByRole("ROOT_MASTER");
        Role role1= roleService.findByRole("ROOT");
        Role role2= roleService.findByRole("GERANT");
        if (user.getRoles().contains(role) || user.getRoles().contains(role1) || user.getRoles().contains(role2)){
            model.addAttribute("conceptions",conceptionService.findll());
        }else {
            model.addAttribute("conceptions", conceptionService.findByUser(user.getId()));
        }
        return "/conception/conceptions";
    }

    @GetMapping("/conception/confirm/{commandeId}")
    public String confirm(@PathVariable int commandeId){

        return "redirect:/commande/conception/"+commandeId;
    }
}
