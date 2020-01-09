package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ContractService;
import com.derteuffel.gestioncommande.Services.UserService;
import com.derteuffel.gestioncommande.entities.Contract;
import com.derteuffel.gestioncommande.entities.User;
import com.derteuffel.gestioncommande.helpers.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Controller
@RequestMapping("/contract")
public class ContractController {
    @Value("${file.upload-dir}")
    private  String fileStorage;

    @Autowired
    private ContractService contractService;

    @Autowired
    private UserService userService;

    @Autowired
    private PageModel pageModel;


    @GetMapping("/contracts/{userId}")
    public String findByUser(Model model, @PathVariable Long userId){
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        User user = userService.getOne(userId);
        model.addAttribute("user",user);
        model.addAttribute("addContract", new Contract());
        model.addAttribute("lists", contractService.findAllByUser_UserId(userId,PageRequest.of(pageModel.getPAGE(),pageModel.getSIZE())));
        return "contract/user";
    }


    @PostMapping("/save/{userId}")
    public String save(Contract contract, RedirectAttributes redirectAttributes, @PathVariable Long userId, @RequestParam("files") MultipartFile[] files) throws IOException {
        User user = userService.getOne(userId);
        contract.setUser(user);
        ArrayList<String> filesPaths = new ArrayList<>();
        for (MultipartFile multipartFile : files){

            if (!(multipartFile.isEmpty())) {
                try{
                    byte[] bytes = multipartFile.getBytes();
                    Path path = Paths.get(fileStorage + multipartFile.getOriginalFilename());
                    Files.write(path, bytes);
                    filesPaths.add("/downloadFile/"+multipartFile.getOriginalFilename());
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        }

        System.out.println(filesPaths);
        contract.setDocuments(filesPaths);

        contractService.save(contract);

        redirectAttributes.addFlashAttribute("message","vous avez enregistrer avec succes un contrat pour "+user.getName());

        return "redirect:/contract/contracts/"+ user.getUserId();
    }
}
