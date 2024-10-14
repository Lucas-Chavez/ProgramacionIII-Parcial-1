package com.parcial.mutant_detection_api.service;

import com.parcial.mutant_detection_api.dto.StatsResponse;
import com.parcial.mutant_detection_api.repository.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private DnaRepository dnaRepository;

    public StatsResponse getStats() {
        // Obtener el conteo de mutantes y humanos desde la base de datos
        long countMutantDna = dnaRepository.countByIsMutant(true);
        long countHumanDna = dnaRepository.countByIsMutant(false);

        // Calcular el ratio evitando la división por cero
        double ratio = (countHumanDna == 0) ? 0 : (double) countMutantDna / countHumanDna;

        // Retornar la respuesta con las estadísticas
        return new StatsResponse(countMutantDna, countHumanDna, ratio);
    }
}
