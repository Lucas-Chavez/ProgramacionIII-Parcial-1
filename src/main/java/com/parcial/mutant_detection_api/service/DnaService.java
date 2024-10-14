package com.parcial.mutant_detection_api.service;

import com.parcial.mutant_detection_api.dto.DnaRequest;
import com.parcial.mutant_detection_api.entities.DnaEntity;
import com.parcial.mutant_detection_api.repository.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DnaService {

    @Autowired
    private DnaRepository dnaRepository;

    public boolean isMutant(DnaRequest dnaRequest) {
        String[] dnaSequence = dnaRequest.getDna();

        // Verificar si el ADN es nulo o vacío
        if (dnaSequence == null) {
            throw new IllegalArgumentException("El array no debe ser null");
        }

        // Convierte la matriz de ADN en un String
        String dnaString = DnaEntity.convertArrayToString(dnaSequence);

        // Verificar si el ADN ya fue registrado
        Optional<DnaEntity> existingDna = dnaRepository.findByDna(dnaString);
        if (existingDna.isPresent()) {
            return existingDna.get().isMutant(); // Devuelve el resultado del ADN ya registrado
        }

        // Lógica para determinar si es mutante
        boolean isMutant = checkIfMutant(dnaSequence);

        // Persistir el resultado en la base de datos
        DnaEntity dnaEntity = new DnaEntity();
        dnaEntity.setDna(dnaString);  // Almacenar como String
        dnaEntity.setMutant(isMutant);
        dnaRepository.save(dnaEntity);

        return isMutant;
    }



    private boolean checkIfMutant(String[] dna) {
        // Verifica si el ADN es válido
        if (!isValidDna(dna)) {
            return false;
        }

        int sequenceCount = 0;
        int n = dna.length;

        // Busca secuencias en filas
        for (int i = 0; i < n; i++) {
            sequenceCount += checkHorizontal(dna[i]);
            if (sequenceCount > 1) return true; // Si hay más de una secuencia, es mutante
        }

        // Busca secuencias en columnas
        for (int j = 0; j < n; j++) {
            sequenceCount += checkVertical(dna, j);
            if (sequenceCount > 1) return true; // Si hay más de una secuencia, es mutante
        }

        // Busca secuencias en diagonales principales
        sequenceCount += checkMainDiagonals(dna);
        if (sequenceCount > 1) return true; // Si hay más de una secuencia, es mutante

        // Busca secuencias en diagonales inversas
        sequenceCount += checkReverseDiagonals(dna);
        return sequenceCount > 1; // Devuelve verdadero si hay más de una secuencia
    }

    // Verifica si la matriz de ADN es válida
    private boolean isValidDna(String[] dna) {

        // Comprueba si el ADN tiene una longitud mínima
        if (dna.length < 4) {
            throw new IllegalArgumentException("El array debe tener al menos 4 filas");
        }

        int n = dna.length;
        // Verifica cada fila del ADN
        for (String row : dna) {
            if (row == null || row.isEmpty()) {
                throw new IllegalArgumentException("El array contiene valores null o filas vacías");
            }
            if (row.length() != n) {
                throw new IllegalArgumentException("El array debe ser NxN");
            }
            // Comprueba si cada base es válida
            for (char base : row.toCharArray()) {
                if (!isValidBase(base)) {
                    throw new IllegalArgumentException("El array contiene caracteres no permitidos.");
                }
            }
        }
        return true; // El ADN es válido
    }

    // Comprueba si una base de ADN es válida
    private boolean isValidBase(char base) {
        return base == 'A' || base == 'C' || base == 'G' || base == 'T';
    }


    // Busca secuencias horizontales en una fila
    private int checkHorizontal(String row) {
        int count = 0;
        int i = 0;
        while (i <= row.length() - 4) {
            if (isValidSequence(row.charAt(i), row.charAt(i + 1), row.charAt(i + 2), row.charAt(i + 3))) {
                count++;
                i += 4;
            } else {
                i++;
            }
            if (count > 1) return count;
        }
        return count;
    }

    // Busca secuencias verticales en una columna
    private int checkVertical(String[] dna, int col) {
        int count = 0;
        int i = 0;
        int n = dna.length;
        while (i <= n - 4) {
            if (isValidSequence(dna[i].charAt(col), dna[i + 1].charAt(col), dna[i + 2].charAt(col), dna[i + 3].charAt(col))) {
                count++;
                i += 4;
            } else {
                i++;
            }
            if (count > 1) return count;
        }
        return count;
    }

    // Busca secuencias en diagonales principales
    private int checkMainDiagonals(String[] dna) {
        int n = dna.length;
        int count = 0;

        // Busca en diagonales empezando desde la primera fila
        for (int startCol = 0; startCol <= n - 4; startCol++) {
            count += checkSingleMainDiagonal(dna, 0, startCol);
            if (count > 1) return count;
        }

        // Busca en diagonales comenzando desde la primera columna
        for (int startRow = 1; startRow <= n - 4; startRow++) {
            count += checkSingleMainDiagonal(dna, startRow, 0);
            if (count > 1) return count;
        }

        return count;
    }

    // Busca secuencias en una diagonal principal específica
    private int checkSingleMainDiagonal(String[] dna, int startRow, int startCol) {
        int n = dna.length;
        int count = 0;
        int row = startRow;
        int col = startCol;

        while (row + 3 < n && col + 3 < n) {
            if (isValidSequence(
                    dna[row].charAt(col),
                    dna[row + 1].charAt(col + 1),
                    dna[row + 2].charAt(col + 2),
                    dna[row + 3].charAt(col + 3)
            )) {
                count++;
                row += 4;
                col += 4;
            } else {
                row++;
                col++;
            }
            if (count > 1) return count;
        }
        return count;
    }

    // Busca secuencias en diagonales inversas
    private int checkReverseDiagonals(String[] dna) {
        int n = dna.length;
        int count = 0;

        // Busca en diagonales inversas que comienzan desde la primera fila
        for (int startCol = n - 1; startCol >= 3; startCol--) {
            count += checkSingleReverseDiagonal(dna, 0, startCol);
            if (count > 1) return count;
        }

        // Busca en diagonales inversas que comienzan desde la última columna
        for (int startRow = 1; startRow <= n - 4; startRow++) {
            count += checkSingleReverseDiagonal(dna, startRow, n - 1);
            if (count > 1) return count;
        }

        return count;
    }

    // Busca secuencias en una diagonal inversa específica
    private int checkSingleReverseDiagonal(String[] dna, int startRow, int startCol) {
        int n = dna.length;
        int count = 0;
        int row = startRow;
        int col = startCol;

        while (row + 3 < n && col - 3 >= 0) {
            if (isValidSequence(
                    dna[row].charAt(col),
                    dna[row + 1].charAt(col - 1),
                    dna[row + 2].charAt(col - 2),
                    dna[row + 3].charAt(col - 3)
            )) {
                count++;
                row += 4;
                col -= 4;
            } else {
                row++;
                col--;
            }
            if (count > 1) return count;
        }
        return count;
    }

    // Verifica si cuatro bases consecutivas forman una secuencia válida
    private boolean isValidSequence(char c1, char c2, char c3, char c4) {
        return c1 == c2 && c1 == c3 && c1 == c4 && (c1 == 'A' || c1 == 'C' || c1 == 'G' || c1 == 'T');
    }
}


