package com.parcial.mutant_detection_api.repository;

import com.parcial.mutant_detection_api.entities.DnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaRepository extends JpaRepository<DnaEntity, Long> {
    // Método para contar la cantidad de ADN que es mutante
    long countByIsMutant(boolean isMutant);
    Optional<DnaEntity> findByDna(String dna);
}
