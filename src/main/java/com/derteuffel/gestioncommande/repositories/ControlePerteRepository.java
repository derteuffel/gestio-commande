package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.ControlePerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlePerteRepository extends JpaRepository<ControlePerte, Long> {
}
