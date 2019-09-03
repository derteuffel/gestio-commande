package com.derteuffel.repositories;

import com.derteuffel.entities.Conception;
import com.derteuffel.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findByUsers_Id(int id);
}
