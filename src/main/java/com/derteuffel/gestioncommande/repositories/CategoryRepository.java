package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
