package com.derteuffel.services;

import com.derteuffel.entities.Commande;
import com.derteuffel.entities.Impression;
import com.derteuffel.repositories.CommandeRepository;
import com.derteuffel.repositories.ImpressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ImpressionService {

    @Autowired
    ImpressionRepository impressionRepository;

    public void save(Impression commande){
        commande.getValidations().add(true);
        commande.getAuthorizations().add(new Date());
        commande.setTotal_price(commande.getQuantite()*commande.getUnit_price());
        impressionRepository.save(commande);
    }

    public Impression getOne(int commandeId){
        return impressionRepository.getOne(commandeId);
    }

    public void valid(int commandeId){
        Impression commande= impressionRepository.getOne(commandeId);
        commande.getValidations().add(true);
        commande.getAuthorizations().add(new Date());
        impressionRepository.save(commande);
    }


    public List<Impression> findll(){
        return impressionRepository.findAll(Sort.by(Sort.Direction.DESC,"commandeId"));
    }

    public void update(Impression commande){
        impressionRepository.save(commande);
    }

    public List<Impression> findByUser(int id){
        return impressionRepository.findByUsers_Id(id);
    }
}
