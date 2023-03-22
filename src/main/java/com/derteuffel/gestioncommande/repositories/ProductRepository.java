package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByCategory(String category);
    Product findByProductCode(String code);

}
