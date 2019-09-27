package com.derteuffel.repositories;

import com.derteuffel.entities.Papier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PapierRepository extends JpaRepository<Papier, Integer> {
    List<Papier> findAllByCommande_CommandeId(int commandeId);
}
