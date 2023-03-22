package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.ControleImpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControleImpressionRepository extends JpaRepository<ControleImpression, Long> {
}
