package com.derteuffel.controllers;

import com.derteuffel.entities.Commande;
import com.derteuffel.entities.Impression;
import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.services.CommandeService;
import com.derteuffel.services.ImpressionService;
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
public class ImpressionController {

    @Autowired
    private ImpressionService impressionService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/impression/form")
    public String form(Model model, HttpServletRequest request, HttpSession session){
        session.setAttribute("lastUrl", request.getHeader("referer"));
        model.addAttribute("impression", new Impression());
        return "impression/form";
    }

    @PostMapping("/impression/save")
    public String save(Impression commande, String price, HttpSession session,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        commande.setUnit_price(Double.parseDouble(price));
        impressionService.save(commande);
        user.getImpressions().add(commande);
        userService.updateUser(user);

        model.addAttribute("commande",commande);
        return "impression/confirmation";
    }


    @GetMapping("/commande/impression/{commandeId}")
    public String getOne(@PathVariable int commandeId, Model model){
        model.addAttribute("impression", impressionService.getOne(commandeId));
        return "impression/detail";

    }

    @GetMapping("/impression/validation/{commandeId}")
    public String validation(@PathVariable int commandeId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Impression commande=impressionService.getOne(commandeId);
        user.getImpressions().add(commande);
        userService.updateUser(user);
        impressionService.update(commande);
        return "redirect:/commande/impression/"+commande.getCommandeId();
    }


    @GetMapping("/impression/impressions")
    public String findAll(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Role role= roleService.findByRole("ROOT_MASTER");
        Role role1= roleService.findByRole("ROOT");
        Role role2= roleService.findByRole("GERANT");
        if (user.getRoles().contains(role) || user.getRoles().contains(role1) || user.getRoles().contains(role2)){
            model.addAttribute("impressions",impressionService.findll());
        }else {
            model.addAttribute("impressions", impressionService.findByUser(user.getId()));
        }
        return "/impression/impressions";
    }


    @GetMapping("/impressio/confirm/{commandeId}")
    public String confirm(@PathVariable int commandeId){

        return "redirect:/commande/impression/"+commandeId;
    }
}
