package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Caisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaisseRepository extends JpaRepository<Caisse, Long> {

    Caisse findByStatus(Boolean status);
}
