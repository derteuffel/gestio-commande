package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.AutreMateriel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutreMaterielRepository extends JpaRepository<AutreMateriel, Long> {
}
