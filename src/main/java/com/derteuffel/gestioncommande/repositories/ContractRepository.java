package com.derteuffel.gestioncommande.repositories;

import com.derteuffel.gestioncommande.entities.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long> {

    Page<Contract> findAllByUser_UserId(Long userId, Pageable pageable);
}
