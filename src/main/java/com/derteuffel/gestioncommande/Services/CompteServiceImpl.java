package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.entities.Role;
import com.derteuffel.gestioncommande.entities.User;
import com.derteuffel.gestioncommande.helpers.CompteRegistrationDto;
import com.derteuffel.gestioncommande.repositories.CompteRepository;
import com.derteuffel.gestioncommande.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CompteServiceImpl implements CompteService{

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Override
    public Compte findByLogin(String login) {
        return compteRepository.findByLogin(login);
    }

    @Override
    public Compte save(CompteRegistrationDto compteRegistrationDto, String s) {
        Compte compte = new Compte();
        User user = new User();
        user.setName(compteRegistrationDto.getLogin());
        user.setEmail(compteRegistrationDto.getEmail());
        user.setAvatar(s);
        user.setContratActuel("GUEST");
        user.setPosteActuel("GUEST");
        user.setPostes(new ArrayList<>(Arrays.asList(user.getPosteActuel())));
        compte.setLogin(compteRegistrationDto.getLogin());
        compte.setPassword(passwordEncoder.encode(compteRegistrationDto.getPassword()));
        compte.setEmail(compteRegistrationDto.getEmail());
        compte.setAvatar(s);
        userService.save(user);
        Role role = new Role();
        if (compteRepository.findAll().size()<=2){
            role.setName("ROLE_ROOT");
        }else {
            role.setName("ROLE_USER");
        }
        Role existRole =  roleRepository.findByName(role.getName());
        if (existRole != null){
            compte.setRoles(Arrays.asList(existRole));
        }else {
            System.out.println(role.getName());
            roleRepository.save(role);
            compte.setRoles(Arrays.asList(role));
        }
        compte.setUser(user);
        compteRepository.save(compte);
        return compte;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        System.out.println(login);
        Compte compte = compteRepository.findByLogin(login);
        if (compte == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(compte.getLogin(),
                compte.getPassword(),
                mapRolesToAuthorities(compte.getRoles()));
    }

    private Collection <? extends GrantedAuthority > mapRolesToAuthorities(Collection < Role > roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
