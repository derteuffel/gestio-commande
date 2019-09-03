package com.derteuffel.controllers;

import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.services.RoleService;
import com.derteuffel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user/role/save")
    public String addRole( int id, int roleId, UserAndRole form){
        User user =userService.findOne(id);
        Role role= roleService.findOne(roleId);
        System.out.println(user.getRoles());
        user.getRoles().clear();
        System.out.println(user.getRoles());
        user.getRoles().add(role);
        System.out.println(user.getRoles());
        userService.updateUser(user);
        return "redirect:/user/user/"+user.getId();
    }


    @GetMapping("/user/user/{id}")
    public String getOne(@PathVariable int id, Model model){
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user",userService.findOne(id));
        model.addAttribute("form",new UserAndRole());
        return "user/user";
    }

    @GetMapping("/user/users")
    public String findAll(Model model){
        model.addAttribute("users",userService.all());
        return "user/users";
    }
}
