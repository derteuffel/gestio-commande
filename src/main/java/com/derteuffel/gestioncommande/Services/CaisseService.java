package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Caisse;
import com.derteuffel.gestioncommande.entities.EMois;
import com.derteuffel.gestioncommande.repositories.CaisseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CaisseService {

    @Autowired
    private CaisseRepository caisseRepository;


    public Caisse save(Caisse caisse) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        SimpleDateFormat sdfAnnee = new SimpleDateFormat("yyyy");
        String date = sdf.format(new Date());
        caisse.setAnnee(sdfAnnee.format(new Date()));
        System.out.println(date);
        if (date.equals("01")) {
            caisse.setMois(EMois.JANVIER.toString());
        } else if (date.equals("02")) {
            caisse.setMois(EMois.FEVRIER.toString());
        } else if (date.equals("03")) {
            caisse.setMois(EMois.MARS.toString());
        } else if (date.equals("04")) {
            caisse.setMois(EMois.AVRIL.toString());
        } else if (date.equals("05")) {
            caisse.setMois(EMois.MAI.toString());
        }else if (date.equals("06")) {
            caisse.setMois(EMois.JUIN.toString());
        }else if (date.equals("07")) {
            caisse.setMois(EMois.JUILLET.toString());
        }else if (date.equals("08")) {
            caisse.setMois(EMois.AOUT.toString());
        }else if (date.equals("09")) {
            caisse.setMois(EMois.SEPTEMBRE.toString());
        }else if (date.equals("10")) {
            caisse.setMois(EMois.OCTOBRE.toString());
        }else if (date.equals("11")) {
            caisse.setMois(EMois.NOVEMBRE.toString());
        }else {
                caisse.setMois(EMois.DECEMBRE.toString());
        }
        return caisseRepository.save(caisse);
    }

    public Caisse update(Caisse caisse){
        return caisseRepository.save(caisse);
    }

    public List<Caisse> findAll(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        System.out.println(sdf.format(new Date()));
        return caisseRepository.findAll();
    }

    public void delete(Long id){
        caisseRepository.deleteById(id);
    }

    public Caisse getOne(Long id){
        return caisseRepository.getOne(id);
    }
}
