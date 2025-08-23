package com.gmms;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class SystemDictionaryTest {
    private List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));


    private void setUp(){
try{
                SystemDictionary.getInstance().initializeDic();
            }catch(Exception e){

        }
}
    
    @Test
    @DisplayName("Verifica che venga lanciato l'errore se viene richiesto di generare parole senza aver inizializzato il dizionario")
    static void testErrorPickDictWords() {
        DictionaryNotInitialized dictErr = assertThrows(DictionaryNotInitialized.class, 
            () -> SystemDictionary.getInstance().pickDictionaryWords(new int[1]),
            "DictionaryNotInitialized non e' stato lanciato");
        assertTrue(dictErr.getMessage().contains("ERRORE: Il dizionario non e' stato inizializzato"));
    }


    @Test
    @DisplayName("Verifica la generazione di parole dal dizionario")
    void testWordsPickingBaseCase() {
        setUp();
        Map<String, List<String>> test = SystemDictionary.getInstance().pickDictionaryWords(new int[] { 3, 10, 10 });
        for (int i = 0; i < test.size(); i++) {
            assertFalse(test.get(types.get(i)).isEmpty());
        }
    }

    @Test
    @DisplayName("Verifica che viene lanciato un errore")
    void testWordsPickingError() {
        setUp();
        OutOfBoundsException thrown = assertThrows(
                OutOfBoundsException.class,
                () -> SystemDictionary.getInstance().pickDictionaryWords(new int[] { 0, -1, -1 }),
                "Expected pickDictionaryWords() to throw, but it didn't");
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