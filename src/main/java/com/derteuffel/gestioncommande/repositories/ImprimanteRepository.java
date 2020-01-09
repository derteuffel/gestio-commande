package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Imprimante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImprimanteRepository extends JpaRepository<Imprimante,Long> {
}
