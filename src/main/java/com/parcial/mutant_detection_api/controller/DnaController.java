package com.parcial.mutant_detection_api.controller;

import com.parcial.mutant_detection_api.dto.DnaRequest;
import com.parcial.mutant_detection_api.dto.DnaResponse;
import com.parcial.mutant_detection_api.service.DnaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mutant")
public class DnaController {

    @Autowired
    private DnaService dnaService;

    @PostMapping
    public ResponseEntity<DnaResponse> isMutant(@RequestBody @Valid DnaRequest dnaRequest) {
        try {
            boolean isMutant = dnaService.isMutant(dnaRequest);

            if (isMutant) {
                return ResponseEntity.ok(new DnaResponse(true)); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DnaResponse(false)); // 403 Forbidden
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DnaResponse(false)); // 400 Bad Request
        }
    }

}
