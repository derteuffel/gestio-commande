package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.helpers.CompteRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class LoginController {

    @Autowired
    private CompteService compteService;
    @Value("${file.upload-dir}")
    private  String fileStorage;


    @ModelAttribute("compte")
    public CompteRegistrationDto compteRegistrationDto(){
        return new CompteRegistrationDto();
    }

    @GetMapping("/registration")
    public String registrationForm(Model model){
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("compte") @Valid CompteRegistrationDto compteDto,
                                      BindingResult result, RedirectAttributes redirectAttributes, Model model, @RequestParam("file") MultipartFile file) {

        Compte existing = compteService.findByLogin(compteDto.getLogin());
        if (existing != null) {
            result.rejectValue("login", null, "There is already an account registered with that login");
            model.addAttribute("error","There is already an account registered with that login");
        }

        if (result.hasErrors()) {
            return "registration";
        }

        if (!(file.isEmpty())){
            try{
                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileStorage + file.getOriginalFilename());
                Files.write(path, bytes);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        compteService.save(compteDto, "/downloadFile/"+file.getOriginalFilename());
        redirectAttributes.addFlashAttribute("success","You've been registered successfully, try to login to your account");
        return "redirect:/login";
    }
}