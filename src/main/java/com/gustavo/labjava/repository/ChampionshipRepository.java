package com.gustavo.labjava.repository;

import com.gustavo.labjava.model.Championship;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
  @Query("SELECT c FROM Championship c WHERE c.year = :year")
  Optional<Championship> findByYear(Integer year);

}
