package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Mouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long> {

    List<Mouvement> findAllByCaisse_Id(Long id);
    List<Mouvement> findAllByCreatedDateAndCaisse_Id(String createdDate, Long id);
    List<Mouvement> findAllByTypeAndCaisse_Id(String type, Long id);
}
