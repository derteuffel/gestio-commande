package com.derteuffel.repositories;

import com.derteuffel.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findByNomClient(String nomClient);
    Client findByEmailClient(String emailClient);

    @Query("select c from Client  as c where c.nomClient=:x order by clientId desc ")
    List<Client> chercherClientExistant(@Param("x") String nomClient);

    List<Client> findAllByNomClient(String nomClient);
}
