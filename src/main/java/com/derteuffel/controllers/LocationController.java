package com.derteuffel.controllers;

import com.derteuffel.entities.Commande;
import com.derteuffel.entities.Location;
import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.services.CommandeService;
import com.derteuffel.services.LocationService;
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
public class LocationController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/location/form")
    public String form(Model model, HttpServletRequest request, HttpSession session){
        session.setAttribute("lastUrl", request.getHeader("referer"));
        model.addAttribute("location", new Location());
        return "location/form";
    }

    @PostMapping("/location/save")
    public String save(Location commande, String price, HttpSession session,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        commande.getUsers().add(user);
        locationService.save(commande,price);
        user.getLocations().add(commande);
        userService.updateUser(user);

        model.addAttribute("location",commande);
        return "location/confirmation";
    }


    @GetMapping("/commande/location/{commandeId}")
    public String getOne(@PathVariable int commandeId, Model model){
        model.addAttribute("location", locationService.getOne(commandeId));
        return "location/detail";

    }

    @GetMapping("/location/validation/{commandeId}")
    public String validation(@PathVariable int commandeId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Location commande=locationService.getOne(commandeId);
        commande.getUsers().add(user);
        locationService.update(commande);
        return "redirect:/commande/location/"+commande.getCommandeId();
    }


    @GetMapping("/commande/locations")
    public String findAll(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Role role= roleService.findByRole("ROOT_MASTER");
        Role role1= roleService.findByRole("ROOT");
        Role role2= roleService.findByRole("GERANT");
        if (user.getRoles().contains(role) || user.getRoles().contains(role1) || user.getRoles().contains(role2)){
            model.addAttribute("locations",locationService.findll());
        }else {
            model.addAttribute("localtions", locationService.findByUser(user.getId()));
        }
        return "/location/locations";
    }
}
