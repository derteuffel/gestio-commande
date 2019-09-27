package com.derteuffel.repositories;

import com.derteuffel.entities.Autre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutreRepository extends JpaRepository<Autre,Integer> {

    List<Autre> findAllByCommande_CommandeId(int commandeId);

}
