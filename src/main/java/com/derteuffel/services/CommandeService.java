package com.derteuffel.services;

import com.derteuffel.entities.Client;
import com.derteuffel.entities.Commande;
import com.derteuffel.entities.Impression;
import com.derteuffel.repositories.ClientRepository;
import com.derteuffel.repositories.CommandeRepository;
import com.derteuffel.repositories.ImpressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ImpressionRepository impressionRepository;

    public void save(Commande commande, int clientId){
        commande.setClient(clientRepository.getOne(clientId));
        commande.setEtatCommande(false);
        commande.getValidations().add(true);
        commande.getAuthorizations().add(new Date());
        commande.setAmount(0.0);
        commande.setQuantite(0);
        commande.setEtatCommande(null);
        commande.setDemandeur(clientRepository.getOne(clientId).getNomClient());
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

    public List<Impression> findByCommande(int commandeId){
        return impressionRepository.findByCommande(commandeId);
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
