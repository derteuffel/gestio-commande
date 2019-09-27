package com.derteuffel.repositories;

import com.derteuffel.entities.Vetement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetementRepository extends JpaRepository<Vetement,Integer> {

    List<Vetement> findAllByCommande_CommandeId(int commandeId);

}
