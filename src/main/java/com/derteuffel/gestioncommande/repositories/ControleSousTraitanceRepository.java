package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.ControleSousTraitance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControleSousTraitanceRepository extends JpaRepository<ControleSousTraitance, Long> {
}
