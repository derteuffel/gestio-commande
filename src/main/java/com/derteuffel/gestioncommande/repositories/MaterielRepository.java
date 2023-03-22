package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterielRepository extends JpaRepository<Materiel,Long> {

    List<Materiel> findAllByDepartement(String departement);
    List<Materiel> findAllByCategory(String category);
    List<Materiel> findAllByMarque(String marque);
    List<Materiel> findAllByDepartementAndCategory(String departement, String category);
    List<Materiel> findAllByDepartementAndMarque(String departement, String marque);
    List<Materiel> findAllByStatus(String status);
    List<Materiel> findAllByDepartementAndStatus(String departement, String status);
}
