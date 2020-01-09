package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Ordinateur;
import com.derteuffel.gestioncommande.repositories.OrdinateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrdinateurService {

    @Autowired
    private OrdinateurRepository ordinateurRepository;


    public Page<Ordinateur> findAll(Pageable pageable) {
        return ordinateurRepository.findAll(pageable);
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
