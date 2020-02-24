package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Commande;
import com.derteuffel.gestioncommande.entities.User;
import com.derteuffel.gestioncommande.repositories.CommandeRepository;
import com.derteuffel.gestioncommande.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Commande> findAll() {
        return commandeRepository.findAll(Sort.by(Sort.Direction.DESC,"commandeId"));
    }

    public Commande getOne(Long commandeId) {
        return commandeRepository.getOne(commandeId);
    }

    public <S extends Commande> S save(S s) {
        return commandeRepository.save(s);
    }

    public long count() {
        return commandeRepository.count();
    }

    public void deleteById(Long commandeId) {
        commandeRepository.deleteById(commandeId);
    }

    public void update(Commande commande){
        commandeRepository.save(commande);
    }

    public Page<Commande> findAll(Pageable pageable) {
        return commandeRepository.findAll(pageable);
    }
}
