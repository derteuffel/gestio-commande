package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.ControlePerte;
import com.derteuffel.gestioncommande.repositories.ControlePerteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControlePerteService {

    @Autowired
    private ControlePerteRepository controlePerteRepository;

    public List<ControlePerte> findAll() {
        return controlePerteRepository.findAll();
    }

    public ControlePerte getOne(Long aLong) {
        return controlePerteRepository.getOne(aLong);
    }

    public <S extends ControlePerte> S save(S entity) {
        return controlePerteRepository.save(entity);
    }

    public void deleteById(Long aLong) {
        controlePerteRepository.deleteById(aLong);
    }
}
