package com.gustavo.labjava.repository;

import com.gustavo.labjava.model.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    @Query("SELECT c FROM Championship c WHERE c.year = ?1")
    Optional<Championship> findByYear(Integer year);

}
