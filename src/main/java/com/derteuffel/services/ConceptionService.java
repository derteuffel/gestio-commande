package com.derteuffel.services;

import com.derteuffel.entities.Commande;
import com.derteuffel.entities.Conception;
import com.derteuffel.repositories.CommandeRepository;
import com.derteuffel.repositories.ConceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConceptionService {

    @Autowired
    ConceptionRepository conceptionRepository;

    public void save(Conception commande){
        commande.getValidations().add(true);
        commande.getAuthorizations().add(new Date());
        commande.setTotal_price(commande.getQuantite()*commande.getUnit_price());
        conceptionRepository.save(commande);
    }

    public Conception getOne(int commandeId){
        return conceptionRepository.getOne(commandeId);
    }

    public void valid(int commandeId){
        Conception commande= conceptionRepository.getOne(commandeId);
        commande.getValidations().add(true);
        commande.getAuthorizations().add(new Date());
        conceptionRepository.save(commande);
    }


    public List<Conception> findll(){
        return conceptionRepository.findAll(Sort.by(Sort.Direction.DESC,"commandeId"));
    }

    public void update(Conception commande){
        conceptionRepository.save(commande);
    }

    public List<Conception> findByUser(int id){
        return conceptionRepository.findByUsers_Id(id);
    }
}
