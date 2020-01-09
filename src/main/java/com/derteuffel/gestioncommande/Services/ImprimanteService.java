package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.Imprimante;
import com.derteuffel.gestioncommande.repositories.ImprimanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ImprimanteService {


    @Autowired
    private ImprimanteRepository imprimanteRepository;


    public Imprimante getOne(Long materielId) {
        return imprimanteRepository.getOne(materielId);
    }

    public Page<Imprimante> findAll(Pageable pageable) {
        return imprimanteRepository.findAll(pageable);
    }

    public <S extends Imprimante> S save(S s) {
        return imprimanteRepository.save(s);
    }

    public boolean existsById(Long materielId) {
        return imprimanteRepository.existsById(materielId);
    }

    public long count() {
        return imprimanteRepository.count();
    }

    public void deleteById(Long materielId) {
        imprimanteRepository.deleteById(materielId);
    }
}
