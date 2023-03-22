package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Compte;
import com.derteuffel.gestioncommande.helpers.CompteRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CompteService extends UserDetailsService {

        Compte findByLogin(String login);
        Compte save(CompteRegistrationDto compteRegistrationDto, String s);

        List<Compte> findAll();
}
