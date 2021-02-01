package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Approbation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprobationRepository extends JpaRepository<Approbation, Long> {

    List<Approbation> findAllByCommande_CommandeId(Long id);
}
