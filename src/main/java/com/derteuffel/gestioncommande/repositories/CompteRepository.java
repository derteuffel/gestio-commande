package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<Compte,Long> {

    Compte findByLogin(String login);
}
