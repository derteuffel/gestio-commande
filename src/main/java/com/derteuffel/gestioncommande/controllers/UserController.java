package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CompteService;
import com.derteuffel.gestioncommande.Services.ContractService;
import com.derteuffel.gestioncommande.Services.UserService;
import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.Contract;
import com.derteuffel.gestioncommande.entities.User;
import com.derteuffel.gestioncommande.helpers.PageModel;
import com.derteuffel.gestioncommande.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContractService contractService;

    @Autowired
    private CompteService compteService;

    @Value("${file.upload-dir}")
    private  String fileStorage;


    @GetMapping("/users")
    public String all(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        model.addAttribute("compte", compte);
        model.addAttribute("addUser", new User());
        model.addAttribute("lists",userService.findAll());

        System.out.println(fileStorage);
        return "user/all";
    }

    @GetMapping("/users/{contratActuel}")
    public String contracts(Model model, @PathVariable String contratActuel, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        model.addAttribute("compte", compte);
        model.addAttribute("contrat",contratActuel);
        model.addAttribute("addUser", new User());
        model.addAttribute("lists",userService.findAllByContratActuel(contratActuel));
        return "user/all";
    }

    @PostMapping("/save")
    public String save(User user, @RequestParam("file") MultipartFile file, @RequestParam("file1") MultipartFile file1 , RedirectAttributes redirectAttributes) throws IOException {
        /*if (!(file.isEmpty())){
            System.out.println("Here is"+System.getProperty(fileStorage)+file.getOriginalFilename());
            file.transferTo(new File(fileStorage + file.getOriginalFilename()));
        }*/

        if (!(file.isEmpty())){
            try{
                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileStorage + file.getOriginalFilename());
                Files.write(path, bytes);
            }catch (IOException e){
                e.printStackTrace();
            }
            user.setAvatar("/downloadFile/"+file.getOriginalFilename());
        }else {
            user.setAvatar("/images/default.jpeg");
        }

        if (!(file1.isEmpty())){
            try{
                // Get the file and save it somewhere
                byte[] bytes = file1.getBytes();
                Path path = Paths.get(fileStorage + file1.getOriginalFilename());
                Files.write(path, bytes);
            }catch (IOException e){
                e.printStackTrace();
            }
            user.setCv("/downloadFile/"+file1.getOriginalFilename());
        }else {
            user.setCv("Aucun Curriculum Vitae n'a ete charger");
        }


        user.setContratActuel(user.getPosteActuel().toString().toLowerCase());
        System.out.println("This is storage place"+fileStorage + file.getOriginalFilename());

        System.out.println("This is poste actuel :"+user.getPosteActuel());
        System.out.println(user.getPostes());
        user.getPostes().add(user.getPosteActuel());
        userService.save(user);

        Contract contract = new Contract();
        contract.setDebutContrat(user.getDateEngagement());
        contract.setType(user.getContratActuel());
        contract.setUser(user);
        redirectAttributes.addFlashAttribute("message","Vous avez initialiser un nouveau contrat pour ce nouvel agent- "+user.getName()+" Bien vouloir completer les informations liees a son contrat ");

        contractService.save(contract);
        return "redirect:/user/users";
    }

    @PostMapping("/update")
    public String update(User user, @RequestParam("file") MultipartFile file, @RequestParam("file1") MultipartFile file1 , RedirectAttributes redirectAttributes) throws IOException {
        /*if (!(file.isEmpty())){
            System.out.println("Here is"+System.getProperty(fileStorage)+file.getOriginalFilename());
            file.transferTo(new File(fileStorage + file.getOriginalFilename()));
        }*/

        if (!(file.isEmpty())){
            try{
                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileStorage + file.getOriginalFilename());
                Files.write(path, bytes);
            }catch (IOException e){
                e.printStackTrace();
            }
            user.setAvatar("/downloadFile/"+file.getOriginalFilename());
        }else {
            user.setAvatar(user.getAvatar());
        }
        if (!(file1.isEmpty())){
            try{
                // Get the file and save it somewhere
                byte[] bytes = file1.getBytes();
                Path path = Paths.get(fileStorage + file1.getOriginalFilename());
                Files.write(path, bytes);
            }catch (IOException e){
                e.printStackTrace();
            }
            user.setCv("/downloadFile/"+file1.getOriginalFilename());
        }else {
            user.setCv(user.getCv());
        }


        user.setContratActuel(user.getPosteActuel().toString().toLowerCase());
        System.out.println("This is storage place"+fileStorage + file.getOriginalFilename());

        System.out.println("This is poste actuel :"+user.getPosteActuel());
        System.out.println(user.getPostes());
        user.getPostes().add(user.getPosteActuel());
        userService.save(user);

        Contract contract = new Contract();
        contract.setDebutContrat(user.getDateEngagement());
        contract.setType(user.getContratActuel());
        contract.setUser(user);
        redirectAttributes.addFlashAttribute("message","Vous avez initialiser un nouveau contrat pour ce nouvel agent- "+user.getName()+" Bien vouloir completer les informations liees a son contrat ");

        contractService.save(contract);
        return "redirect:/user/detail/"+user.getUserId();
    }

    @GetMapping("/update/{userId}")
    public String updateForm(@PathVariable Long userId, Model model, RedirectAttributes redirectAttributes){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            model.addAttribute("user",optionalUser.get());
            return "user/update";
        }else {
            redirectAttributes.addFlashAttribute("message", "There are no user with id:"+userId);
            return "redirect:/";
        }
    }

    @GetMapping("/detail/{userId}")
    public String get(Model model, @PathVariable Long userId, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByLogin(principal.getName());
        model.addAttribute("compte", compte);
        User user = userService.getOne(userId);
        model.addAttribute("user",user);
        System.out.println(user.getPostes());
        return "user/detail";
    }
}
