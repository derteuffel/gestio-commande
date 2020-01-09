package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Ordinateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdinateurRepository extends JpaRepository<Ordinateur,Long> {
}
