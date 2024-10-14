package com.parcial.mutant_detection_api.controller;

import com.parcial.mutant_detection_api.dto.StatsResponse;
import com.parcial.mutant_detection_api.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping
    public StatsResponse getStats() {
        // Llamar al servicio para obtener las estadísticas
        return statsService.getStats();
    }
}

