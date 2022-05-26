package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.Visiteur;
import com.derteuffel.gestioncommande.repositories.CompteRepository;
import com.derteuffel.gestioncommande.repositories.VisiteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/receptions")
public class ReceptionController {

    @Autowired
    private VisiteurRepository visiteurRepository;

    @Autowired
    private CompteService compteService;

    @GetMapping("/lists")
    public String findAll(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        model.addAttribute("compte",compte);
        model.addAttribute("visiteur", new Visiteur());
        model.addAttribute("lists", visiteurRepository.findAll(Sort.by(Sort.Direction.DESC,"id")));
        return "reception/lists";
    }

    @PostMapping("/save")
    public String save(@Valid Visiteur visiteur, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        Optional<Visiteur> optionalVisiteur = visiteurRepository.findByNameOrTelephone(visiteur.getName(),visiteur.getTelephone());
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        if (optionalVisiteur.isPresent()){
            optionalVisiteur.get().getDates().add(dateFormat.format(date));
            visiteurRepository.save(optionalVisiteur.get());
        }else {
            visiteur.setDates(new ArrayList<>(Arrays.asList(dateFormat.format(date))));
            visiteur.setCompte(compte);
            visiteurRepository.save(visiteur);
        }
        redirectAttributes.addFlashAttribute("success", "You added new visitor successfully");
        return "redirect:/receptions/lists";

    }

    @GetMapping("/get/{id}")
    public String findOne(Model model, @PathVariable Long id, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        Optional<Visiteur> optionalVisiteur = visiteurRepository.findById(id);
        model.addAttribute("compte",compte);

        if (optionalVisiteur.isPresent()){
            model.addAttribute("visitor",optionalVisiteur.get());
        }else {
            model.addAttribute("error","There are no visitor with Id : "+id);
        }

        return "reception/detail";
    }

    @GetMapping("/form/{id}")
    public String updateForm(Model model, @PathVariable Long id, HttpServletRequest request){

        Optional<Visiteur> optionalVisiteur = visiteurRepository.findById(id);
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());

        request.getSession().setAttribute("dates",optionalVisiteur.get().getDates());
        if (optionalVisiteur.isPresent()){
            model.addAttribute("visitor", optionalVisiteur.get());
        }else {
            model.addAttribute("error","There are no visitor with Id : "+id);
            return "redirect:/receptions";
        }
        model.addAttribute("compte",compte);
        return "reception/update";
    }

    @PostMapping("/update")
    public String update(@Valid Visiteur visiteur,RedirectAttributes redirectAttributes, HttpServletRequest request){
        visiteur.setDates((ArrayList<String>)request.getSession().getAttribute("dates"));
        visiteurRepository.save(visiteur);
        redirectAttributes.addFlashAttribute("success", "Your changes has been saved successfully..");
        return "redirect:/receptions/get/"+visiteur.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        visiteurRepository.delete(visiteurRepository.getOne(id));
        return "redirect:/receptions/lists";
    }
}
