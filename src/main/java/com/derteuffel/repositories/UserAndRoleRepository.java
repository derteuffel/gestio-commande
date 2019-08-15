package com.derteuffel.repositories;

import com.derteuffel.entities.UserAndRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAndRoleRepository extends JpaRepository<UserAndRole, Integer> {

    UserAndRole findByUserId(int userId);
}
