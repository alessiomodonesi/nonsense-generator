package com.gmms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;;

public class TemplateTest {
    private File tempFile;

    void setUp(String s) throws IOException {
        tempFile = File.createTempFile("testStructures", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(s);
        }
    }

    @Test
    @DisplayName("Crea un template quando la struttura è corretta")
    void testCorrectStructureTemplate() throws IOException {
        setUp("Il %s corre e %s bello");
        SentenceStructures s = new SentenceStructures(
                "./src/main/java/com/gmms/resources/testStructures.txt");
        Template template = TemplateGenerator.getInstance().generateTemplate(s);

        assertNotNull(template); // verifica che il template non sia null
        assertNotEquals(s, template.templateDesc); // verifica che la descrizione del template sia stata modificata
        assertTrue(template.templateDesc.startsWith("Il "));
        assertTrue(template.templateDesc.endsWith(" bello"));
        assertTrue(Arrays.stream(template.templateWords).sum() > 0); // verifica che ci siano parole da sostituire
    }

    @Test
    @DisplayName("Restituisce la stringa uguale se non ci sono segnaposti")
    void testWithoutPlaceholderTemplate() throws IOException {
        setUp("L'aereo vola alto nel cielo");
        SentenceStructures s = new SentenceStructures(
                "./src/main/java/com/gmms/resources/testStructures.txt");
        Template template = TemplateGenerator.getInstance().generateTemplate(s);

        assertNotNull(template);
        assertEquals(s, template.templateDesc); // verifica che la descrizione del template sia uguale alla stringa
                                                // originale
        assertArrayEquals(new int[] { 0, 0, 0 }, template.templateWords); // verifica che non ci siano parole da
                                                                          // sostituire
    }

    @Test
    @DisplayName("Da una stringa vuota, restituisce un template vuoto")
    void testEmptyTemplate() throws IOException {
        setUp("");
        SentenceStructures s = new SentenceStructures(
                "./src/main/java/com/gmms/resources/testStructures.txt");
        Template template = TemplateGenerator.getInstance().generateTemplate(s);

        assertNotNull(template);
        assertEquals("", template.templateDesc); // verifica che la descrizione del template sia vuota
        assertArrayEquals(new int[] { 0, 0, 0 }, template.templateWords); // verifica che non ci siano parole da
                                                                          // sostituire
    }

    @AfterAll
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }
}