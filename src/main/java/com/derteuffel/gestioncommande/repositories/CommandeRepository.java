package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Long> {

    Page<Commande> findAll(Pageable pageable);

}
