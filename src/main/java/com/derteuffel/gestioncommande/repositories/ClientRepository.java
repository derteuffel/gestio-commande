package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    Client findByPhone(String phone);
}
