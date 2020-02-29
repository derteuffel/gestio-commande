package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Approbation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprobationRepository extends JpaRepository<Approbation, Long> {
}
