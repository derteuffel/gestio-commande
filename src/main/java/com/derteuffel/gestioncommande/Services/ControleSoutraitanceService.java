package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.ControleSousTraitance;
import com.derteuffel.gestioncommande.repositories.ControleSousTraitanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControleSoutraitanceService {

    @Autowired
    private ControleSousTraitanceRepository controleSousTraitanceRepository;

    public List<ControleSousTraitance> findAll() {
        return controleSousTraitanceRepository.findAll();
    }

    public ControleSousTraitance getOne(Long aLong) {
        return controleSousTraitanceRepository.getOne(aLong);
    }

    public <S extends ControleSousTraitance> S save(S entity) {
        return controleSousTraitanceRepository.save(entity);
    }

    public void deleteById(Long aLong) {
        controleSousTraitanceRepository.deleteById(aLong);
    }
}
