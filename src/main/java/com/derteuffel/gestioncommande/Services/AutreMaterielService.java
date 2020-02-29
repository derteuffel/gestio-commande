package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.AutreMateriel;
import com.derteuffel.gestioncommande.repositories.AutreMaterielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutreMaterielService {

    @Autowired
    private AutreMaterielRepository autreMaterielRepository;

    public AutreMateriel getOne(Long materielId) {
        return autreMaterielRepository.getOne(materielId);
    }

    public List<AutreMateriel> findAll() {
        return autreMaterielRepository.findAll();
    }

    public <S extends AutreMateriel> S save(S s) {
        return autreMaterielRepository.save(s);
    }

    public long count() {
        return autreMaterielRepository.count();
    }

    public void deleteById(Long materielId) {
        autreMaterielRepository.deleteById(materielId);
    }
}
