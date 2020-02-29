package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisiteurRepository extends JpaRepository<Visiteur,Long> {


    Optional<Visiteur> findByNameOrTelephone(String name, String telephone);
}
