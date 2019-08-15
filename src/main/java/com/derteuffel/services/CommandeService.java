package com.derteuffel.services;

import com.derteuffel.entities.Commande;
import com.derteuffel.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    CommandeRepository commandeRepository;

    public void save(Commande commande, String price){
        commande.setUnit_price(Double.parseDouble(price));
        commande.getValidations().add(true);
        commande.getAuthorizations().add(new Date());
        commande.setTotal_price(commande.getQuantite()*Double.parseDouble(price));
        commandeRepository.save(commande);
    }

    public Commande getOne(int commandeId){
        return commandeRepository.getOne(commandeId);
    }

    public void valid(int commandeId){
        Commande commande= commandeRepository.getOne(commandeId);
        commande.getValidations().add(true);
        commande.getAuthorizations().add(new Date());
        commandeRepository.save(commande);
    }

    public List<Commande> findll(){
        return commandeRepository.findAll(Sort.by(Sort.Direction.DESC,"commandeId"));
    }

    public void update(Commande commande){
        commandeRepository.save(commande);
    }

    public List<Commande> findByUser(int id){
        return commandeRepository.findByUsers_Id(id);
    }
}
