package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.ControleImpression;
import com.derteuffel.gestioncommande.repositories.ControleImpressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControleImpressionService {

    @Autowired
    private ControleImpressionRepository controleImpressionRepository;

    public List<ControleImpression> findAll() {
        return controleImpressionRepository.findAll();
    }

    public ControleImpression getOne(Long aLong) {
        return controleImpressionRepository.getOne(aLong);
    }

    public <S extends ControleImpression> S save(S entity) {
        return controleImpressionRepository.save(entity);
    }

    public void deleteById(Long aLong) {
        controleImpressionRepository.deleteById(aLong);
    }
}
