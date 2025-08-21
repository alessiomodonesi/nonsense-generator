package com.gmms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

class SentenceStructuresTest {
    private File tempFile;

    @BeforeAll
    void setUp() throws IOException {
        tempFile = File.createTempFile("SentenceStructures", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("prima struttura\n");
            writer.write("seconda struttura\n");
            writer.write("terza struttura\n");
        }
    }

    @Test
    @DisplayName("Costruisci un oggetto SentenceStructures")
    void testConstructor() {
        SentenceStructures tempStructures = new SentenceStructures(tempFile.getAbsolutePath());
        assertNotNull(tempStructures);
        assertEquals(3, tempStructures.getDimension());
    }

    @Test
    @DisplayName("Rileva correttamente il numero delle strutture")
    void testStructuresCount() {
        SentenceStructures tempStructures = new SentenceStructures(tempFile.getAbsolutePath());
        assertEquals(3, tempStructures.getDimension());
    }

    @Test
    @DisplayName("Verifica che getRandomStructure() restituisca una stringa")
    void testGetRandomStructureNotEmpty() {
        SentenceStructures tempStructures = new SentenceStructures(tempFile.getAbsolutePath());
        String tempRandomStructure = tempStructures.getRandomStructure();
        assertNotNull(tempRandomStructure);
    }

    @Test
    @DisplayName("Verifica che la stringa restituita da getRandomStructure() sia valida")
    void testGetRandomStructureIsValid() {
        SentenceStructures tempStructures = new SentenceStructures(tempFile.getAbsolutePath());
        String tempRandomStructure = tempStructures.getRandomStructure();
        assertTrue(tempRandomStructure.equals("prima struttura") ||
                tempRandomStructure.equals("seconda struttura")
                || tempRandomStructure.equals("terza struttura"));
    }

    @AfterAll
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }
}
