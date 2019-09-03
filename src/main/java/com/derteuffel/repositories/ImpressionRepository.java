package com.derteuffel.repositories;

import com.derteuffel.entities.Conception;
import com.derteuffel.entities.Impression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpressionRepository extends JpaRepository<Impression, Integer> {
    List<Impression> findByUsers_Id(int id);
}
