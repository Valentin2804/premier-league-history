package com.example.plhistory.repositories;
import com.example.plhistory.entities.User.ConformationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConformationTokenRepository extends JpaRepository<ConformationToken, Long> {

    ConformationToken findByToken(String token);
}
