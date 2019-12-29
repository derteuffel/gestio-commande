package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.UserService;
import com.derteuffel.gestioncommande.entities.User;
import com.derteuffel.gestioncommande.helpers.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PageModel pageModel;

    @Value("${file.upload-dir}")
    private  String fileStorage;


    @GetMapping("/users")
    public String all(Model model){
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        model.addAttribute("addUser", new User());
        model.addAttribute("lists",userService.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));

        System.out.println(fileStorage);
        return "user/all";
    }

    @GetMapping("/users/{contratActuel}")
    public String contracts(Model model, @PathVariable String contratActuel){
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();

        model.addAttribute("addUser", new User());
        model.addAttribute("lists",userService.findAllByContratActuel(contratActuel,PageRequest.of(pageModel.getPAGE(),pageModel.getSIZE())));
        return "user/all";
    }

    @PostMapping("/save")
    public String save(User user, @RequestParam("file") MultipartFile file ) throws IOException {
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
        }

        user.setCv(file.getOriginalFilename());
        System.out.println("This is storage place"+fileStorage + file.getOriginalFilename());

        System.out.println("This is poste actuel :"+user.getPosteActuel());
        System.out.println(user.getPostes());
        user.getPostes().add(user.getPosteActuel());
        userService.save(user);

        return "redirect:/user/users";
    }

    @GetMapping("/detail/{userId}")
    public String get(Model model, @PathVariable Long userId){
        User user = userService.getOne(userId);
        model.addAttribute("user",user);
        System.out.println(user.getPostes());
        return "user/detail";
    }
}
