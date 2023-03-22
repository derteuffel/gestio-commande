package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.Constants;
import com.derteuffel.gestioncommande.entities.Materiel;
import com.derteuffel.gestioncommande.repositories.MaterielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterielService {

    @Autowired
    private MaterielRepository materielRepository;
    static String error = "Ooops, ue erreur est survenue";

    public List<Materiel> findAllByDepartement(String departement) {
        try {
            return materielRepository.findAllByDepartement(departement);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public List<Materiel> findAllByCategory(String category) {
        try {
            return materielRepository.findAllByCategory(category);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public List<Materiel> findAllByMarque(String marque) {
        try {
            return materielRepository.findAllByMarque(marque);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public List<Materiel> findAllByDepartementAndCategory(String departement, String category) {
        try {
            return materielRepository.findAllByDepartementAndCategory(departement, category);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public List<Materiel> findAllByDepartementAndMarque(String departement, String marque) {
        try {
            return materielRepository.findAllByDepartementAndMarque(departement, marque);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public List<Materiel> findAllByStatus(String status) {
        try {
            return materielRepository.findAllByStatus(status);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public List<Materiel> findAllByDepartementAndStatus(String departement, String status) {
        try {
            return materielRepository.findAllByDepartementAndStatus(departement, status);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public List<Materiel> findAll() {
        try {
            return materielRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public Materiel getOne(Long aLong) {
        try {
            return materielRepository.getOne(aLong);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public <S extends Materiel> S save(S entity) {
        try {
            return materielRepository.save(entity);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }

    }

    public void deleteById(Long aLong) {
        try {
            materielRepository.deleteById(aLong);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }
}
