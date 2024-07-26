package com.fourat.authapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import  com.fourat.authapi.models.User ;

public interface UserRepository extends JpaRepository<User,Integer> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}