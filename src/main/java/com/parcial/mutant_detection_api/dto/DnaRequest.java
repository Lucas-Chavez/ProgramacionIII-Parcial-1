package com.parcial.mutant_detection_api.dto;

//import com.parcial.mutant_detection_api.validators.ValidDna;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnaRequest {

    private String[] dna; // Representa la matriz de ADN
}
