package com.parcial.mutant_detection_api.service;

import com.parcial.mutant_detection_api.entities.DnaEntity;
import com.parcial.mutant_detection_api.dto.DnaRequest;
import com.parcial.mutant_detection_api.repository.DnaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;






public class DnaServiceTest {

    @Mock
    private DnaRepository dnaRepository;  // Mock del repositorio

    @InjectMocks
    private DnaService dnaService;  // Inyectar el mock en el servicio

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializar los mocks antes de cada test
    }

    @Test
    public void testEmptyArray() {
        DnaRequest dnaRequest = new DnaRequest(new String[]{});
        // Excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dnaService.isMutant(dnaRequest);
        });
        assertEquals("El array debe tener al menos 4 filas", exception.getMessage());
    }

    @Test
    public void testArrayNxM() {
        String[] nonSquareDna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AG"
        };
        DnaRequest dnaRequest = new DnaRequest(nonSquareDna);
        // Excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dnaService.isMutant(dnaRequest);
        });
        assertEquals("El array debe ser NxN", exception.getMessage());
    }

    @Test
    public void testArrayWithNumbers() {
        String[] invalidDna = {
                "AT1C",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        DnaRequest dnaRequest = new DnaRequest(invalidDna);
        // Excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dnaService.isMutant(dnaRequest);
        });
        assertEquals("El array contiene caracteres no permitidos.", exception.getMessage());
    }

    @Test
    public void testNullArray() {
        DnaRequest dnaRequest = new DnaRequest(null);
        // Excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dnaService.isMutant(dnaRequest);
        });
        assertEquals("El array no debe ser null", exception.getMessage());
    }

    @Test
    public void testArrayNxNOfNulls() {
        String[] nullDna = {
                null, null, null, null
        };
        DnaRequest dnaRequest = new DnaRequest(nullDna);
        // Excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dnaService.isMutant(dnaRequest);
        });
        assertEquals("El array contiene valores null o filas vacías", exception.getMessage());
    }

    @Test
    public void testArrayNxNWithInvalidCharacters() {
        String[] invalidDna = {
                "AXTG",
                "TGAC",
                "ATCG",
                "GAXT"
        };
        DnaRequest dnaRequest = new DnaRequest(invalidDna);
        // Excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dnaService.isMutant(dnaRequest);
        });
        assertEquals("El array contiene caracteres no permitidos.", exception.getMessage());
    }

    @Test
    public void testMutantDna1() {
        String[] mutantDna = {"AAAA", "CCCC", "TCAG", "GGTC"}; // Caso de ADN mutante
        DnaRequest dnaRequest = new DnaRequest(mutantDna);

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());

        boolean result = dnaService.isMutant(dnaRequest);
        assertTrue(result);
        verify(dnaRepository, times(1)).save(any(DnaEntity.class));
    }

    @Test
    public void testNonMutantDna1() {
        String[] nonMutantDna = {"AAAT", "AACC", "AAAC", "CGGG"}; // Caso de ADN no mutante
        DnaRequest dnaRequest = new DnaRequest(nonMutantDna);

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());

        boolean result = dnaService.isMutant(dnaRequest);
        assertFalse(result);
        verify(dnaRepository, times(1)).save(any(DnaEntity.class));
    }

    @Test
    public void testMutantDna2() {
        String[] mutantDna = {"TGAC", "AGAC", "TGAC", "GGTC"}; // Caso de ADN mutante
        DnaRequest dnaRequest = new DnaRequest(mutantDna);

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());

        boolean result = dnaService.isMutant(dnaRequest);
        assertTrue(result);
        verify(dnaRepository, times(1)).save(any(DnaEntity.class));
    }

    @Test
    public void testNonMutantDna2() {
        String[] nonMutantDna = {"TGAC", "ATCC", "TAAG", "GGTC"}; // Caso de ADN no mutante
        DnaRequest dnaRequest = new DnaRequest(nonMutantDna);

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());

        boolean result = dnaService.isMutant(dnaRequest);
        assertFalse(result);
        verify(dnaRepository, times(1)).save(any(DnaEntity.class));
    }

    @Test
    public void testMutantDna3() {
        String[] mutantDna = {"AAAA", "AAAA", "AAAA", "AAAA"}; // Caso de ADN mutante
        DnaRequest dnaRequest = new DnaRequest(mutantDna);

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());

        boolean result = dnaService.isMutant(dnaRequest);
        assertTrue(result);
        verify(dnaRepository, times(1)).save(any(DnaEntity.class));
    }

    @Test
    public void testMutantDna4() {
        String[] mutantDna = {
                "TCGGGTGAT",
                "TGATCCTTT",
                "TACGAGTGA",
                "AAATGTACG",
                "ACGAGTGCT",
                "AGACACATG",
                "GAATTCCAA",
                "ACTACGACC",
                "TGAGTATCC"
        }; // Caso de ADN mutante
        DnaRequest dnaRequest = new DnaRequest(mutantDna);

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());

        boolean result = dnaService.isMutant(dnaRequest);
        assertTrue(result);
        verify(dnaRepository, times(1)).save(any(DnaEntity.class));
    }

    @Test
    public void testMutantDna5() {
        String[] mutantDna = {
                "TTTTTTTTT",
                "TTTTTTTTT",
                "TTTTTTTTT",
                "TTTTTTTTT",
                "CCGACCAGT",
                "GGCACTCCA",
                "AGGACACTA",
                "CAAAGGCAT",
                "GCAGTCCCC"
        }; // Caso de ADN mutante
        DnaRequest dnaRequest = new DnaRequest(mutantDna);

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());

        boolean result = dnaService.isMutant(dnaRequest);
        assertTrue(result);
        verify(dnaRepository, times(1)).save(any(DnaEntity.class));
    }
}
