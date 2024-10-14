package com.parcial.mutant_detection_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dna", uniqueConstraints = @UniqueConstraint(columnNames = "dna"))
public class DnaEntity extends Base {

    @Column(nullable = false, columnDefinition = "TEXT") // Cambiar a TEXT para permitir texto largo
    private String dna; // ADN almacenado como String

    @Column(nullable = false)
    private boolean isMutant;

    // Método auxiliar para convertir String[] en String
    public static String convertArrayToString(String[] dnaArray) {
        return String.join(",", dnaArray); // Convierte el arreglo en una cadena separada por comas
    }
}
