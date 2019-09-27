package com.derteuffel.repositories;

import com.derteuffel.entities.Conception;
import com.derteuffel.entities.Impression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpressionRepository extends JpaRepository<Impression, Integer> {

    @Query("select i from Impression as i join i.commande ic where ic.commandeId=:id order by i.impressionId desc")
    List<Impression> findByCommande(@Param("id") int commandeId);
}
