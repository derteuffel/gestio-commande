package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.AddedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AddedProductRepository extends JpaRepository<AddedProduct, Long> {

    List<AddedProduct> findAllByProduct(Long productId);
    List<AddedProduct> findAllByActionType(String actionType);
    List<AddedProduct> findAllByActionTypeAndAddedDate(String actionType, Date addedDate);
}
