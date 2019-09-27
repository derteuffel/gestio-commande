package com.derteuffel.services;

import com.derteuffel.entities.*;
import com.derteuffel.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ImpressionService {

    @Autowired
    ImpressionRepository impressionRepository;

    @Autowired
    private PapierRepository papierRepository;

    @Autowired
    private AutreRepository autreRepository;

    @Autowired
    private VetementRepository vetementRepository;

    public void save(Impression commande){
        impressionRepository.save(commande);
    }

    public void  savePapier(Papier papier){
        papierRepository.save(papier);
    }

    public void saveVetement(Vetement vetement){
        vetementRepository.save(vetement);
    }

    public  void saveAutre(Autre autre){
        autreRepository.save(autre);
    }

    public Papier getOnePapier(int impressionId){
        return papierRepository.getOne(impressionId);
    }

    public Vetement getOneVetement(int impressionId){
        return vetementRepository.getOne(impressionId);
    }

    public Autre getOneAutre(int impresionId){
        return autreRepository.getOne(impresionId);
    }

    public List<Papier> findAllPapiers(int commandeId){
        return papierRepository.findAllByCommande_CommandeId(commandeId);
    }

    public List<Vetement> findAllVetements(int commandeId){
        return vetementRepository.findAllByCommande_CommandeId(commandeId);
    }

    public List<Autre> findAllAutres(int commandeId){
        return autreRepository.findAllByCommande_CommandeId(commandeId);
    }

    public Impression getOne(int commandeId){
        return impressionRepository.getOne(commandeId);
    }

    public void valid(int commandeId){
        Impression commande= impressionRepository.getOne(commandeId);
        impressionRepository.save(commande);
    }


    public List<Impression> findll(){
        return impressionRepository.findAll(Sort.by(Sort.Direction.DESC,"commandeId"));
    }

    public void update(Impression commande){
        impressionRepository.save(commande);
    }

}
