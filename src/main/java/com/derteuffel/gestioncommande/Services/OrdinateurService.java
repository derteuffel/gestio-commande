package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Ordinateur;
import com.derteuffel.gestioncommande.repositories.OrdinateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdinateurService {

    @Autowired
    private OrdinateurRepository ordinateurRepository;


    public List<Ordinateur> findAll() {
        return ordinateurRepository.findAll();
    }

    public <S extends Ordinateur> S save(S s) {
        return ordinateurRepository.save(s);
    }

    public Ordinateur getOne(Long materielId) {
        return ordinateurRepository.getOne(materielId);
    }

    public boolean existsById(Long materielId) {
        return ordinateurRepository.existsById(materielId);
    }

    public long count() {
        return ordinateurRepository.count();
    }

    public void deleteById(Long materielId) {
        ordinateurRepository.deleteById(materielId);
    }
}
