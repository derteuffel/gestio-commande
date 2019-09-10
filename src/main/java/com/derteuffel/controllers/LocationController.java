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
import java.util.Arrays;
import java.util.HashSet;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("location", new Location());
        session.setAttribute("loggedName",user.getName()+" "+ user.getLastName());
        return "location/form";
    }

    public  String edit(@PathVariable int commandeId){
        return "";
    }

    @PostMapping("/location/save")
    public String save(Location commande, String price, HttpSession session,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println(user.getName());
        commande.setUnit_price(Double.parseDouble(price));
        System.out.println(commande.getUnit_price());
        locationService.save(commande);
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
        //user.getLocations().add(commande);
        userService.updateUser(user);
        locationService.valid(commandeId);
        System.out.println(commande.getAuthorizations().size());
        return "redirect:/commande/location/"+commande.getCommandeId();
    }


    @GetMapping("/location/locations")
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


    @GetMapping("/location/confirm/{commandeId}")
    public String confirm(@PathVariable int commandeId){

        return "redirect:/commande/location/"+commandeId;
    }
}
