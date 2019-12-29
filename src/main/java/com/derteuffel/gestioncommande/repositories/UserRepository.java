package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);
    Page<User> findAllByContratActuel(String contratActuel, Pageable pageable);
}
