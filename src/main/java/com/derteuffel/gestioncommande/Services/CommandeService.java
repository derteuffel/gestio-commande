package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Commande;
import com.derteuffel.gestioncommande.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;


    public List<Commande> findAll() {
        try {
            return commandeRepository.findAll(Sort.by(Sort.Direction.DESC, "commandeId"));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Commande getOne(Long commandeId) {
        try {
            return commandeRepository.getOne(commandeId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public <S extends Commande> S save(S s) {
        try {
            return commandeRepository.save(s);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public long count() {
        return commandeRepository.count();
    }

    public void deleteById(Long commandeId) {
        try {
            commandeRepository.deleteById(commandeId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Commande commande){
        try {
            commandeRepository.save(commande);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
