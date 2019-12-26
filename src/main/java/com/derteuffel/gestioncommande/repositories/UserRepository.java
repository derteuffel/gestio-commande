package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
