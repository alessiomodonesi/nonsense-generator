package com.gmms;

import java.io.FileWriter;
import java.io.File;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class SystemDictionaryTest {
    private List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));
    // Percorso reale del file usato dalla tua classe

    @Test
    @DisplayName("Verifica eccezione se manca un campo nel JSON (VERB)")
    void testJsonMissingField() throws Exception {
        // Crea un file temporaneo
        File tempJsonFile = File.createTempFile("test_dict", ".json");
        tempJsonFile.deleteOnExit(); // Lo rimuove automaticamente al termine del test

        // Scrivi un JSON malformato dato che mancano i verbi
        String malformedJson = """
                    {
                        "NOUN": ["cane", "gatto"],
                        "ADJECTIVE": ["grande", "veloce"]
                    }
                """;

        try (FileWriter fw = new FileWriter(tempJsonFile)) {
            fw.write(malformedJson);
        }

        // Verifica che l'eccezione venga lanciata
        assertThrows(OutOfBoundsException.class, () -> {
            SystemDictionary.getInstance().initializeDic(tempJsonFile.getAbsolutePath());
        });
    }

    private File createTempDictionaryFile(String jsonContent) throws Exception {
        File tempFile = File.createTempFile("dict_test", ".json");
        tempFile.deleteOnExit();
        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write(jsonContent);
        }
        return tempFile;
    }

    private void setUp() {
        String json = """
                    {
                        "NOUN": ["cane", "gatto", "albero"],
                        "VERB": ["correre", "saltare", "nuotare"],
                        "ADJECTIVE": ["grande", "veloce", "rosso"]
                    }
                """;
        SystemDictionary.getInstance().reset();
        try {
            SystemDictionary.getInstance().initializeDic(createTempDictionaryFile(json).getAbsolutePath()); // crea ed inizializza il dizionario di sistema
        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Verifica che venga lanciato l'errore se viene richiesto di generare parole senza aver inizializzato il dizionario")
    void testErrorPickDictWords() {
        DictionaryNotInitialized dictErr = assertThrows(DictionaryNotInitialized.class,
                () -> SystemDictionary.getInstance().pickDictionaryWords(new int[1]),
                "DictionaryNotInitialized non è stato lanciato");
        assertTrue(dictErr.getMessage().contains("ERRORE: Il dizionario non è stato inizializzato"));
    }

    @Test
    @DisplayName("Verifica la generazione di parole dal dizionario")
    void testWordsPickingBaseCase() {
        setUp();
        Map<String, List<String>> test = SystemDictionary.getInstance().pickDictionaryWords(new int[] { 2,2,2 });
        for (int i = 0; i < test.size(); i++) {
            assertTrue(test.get(types.get(i)).size() == 2);
        }
    }

    @Test
    @DisplayName("Verifica che viene lanciato un errore")
    void testWordsPickingError() {
        setUp();
        OutOfBoundsException thrown = assertThrows(
                OutOfBoundsException.class,
                () -> SystemDictionary.getInstance().pickDictionaryWords(new int[] { 0, -1, -1 }),
                "pickDictionaryWords non è stato lanciato");
        assertTrue(thrown.getMessage().contains("ERRORE: Gli indici inseriti non sono validi"));

    }

    @Test
    @DisplayName("Verifica cosa accade se vengono richiesti troppi elementi")
    void testPickingTooManyWords() {
        setUp();
        Map<String, List<String>> test = SystemDictionary.getInstance()
                .pickDictionaryWords(new int[] { 1000, 1000, 1000 });
        for (int i = 0; i < test.size(); i++) {
            assertFalse(test.get(types.get(i)).isEmpty());
        }
    }
}