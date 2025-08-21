package com.gmms;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class SystemDictionaryTest {
    private List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));

    @BeforeEach
    void setUp(){
        try{
            SystemDictionary.getInstance().initializeDic();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Verifica la generazione di parole dal dizionario")
    void testWordsPickingBaseCase() {
        Map<String, List<String>> test = SystemDictionary.getInstance().pickDictionaryWords(new int[] { 3, 10, 10 });
       for(int i = 0; i < test.size(); i++){
            assertFalse(test.get(types.get(i)).isEmpty());
        }
    }

    @Test
    @DisplayName("Verifica che viene lanciato un errore")
    void testWordsPickingError() {

        OutOfBoundsExcpetion thrown = assertThrows(
                OutOfBoundsExcpetion.class,
                () -> SystemDictionary.getInstance().pickDictionaryWords(new int[] { 0, -1, -1 }),
                "Expected pickDictionaryWords() to throw, but it didn't");
        assertFalse(thrown.getMessage().isBlank());

        
        Map<String, List<String>> test = SystemDictionary.getInstance().pickDictionaryWords(new int[] { 0, 0, 0 });
        for(int i = 0; i < test.size(); i++){
            assertTrue(test.get(types.get(i)).isEmpty());
        }
    }

    @Test
    @DisplayName("Verifica cosa accade se vengono richiesti troppi elementi")
    void testPickingTooManyWords() {
      
        Map<String, List<String>> test = SystemDictionary.getInstance().pickDictionaryWords(new int[] { 1000, 1000, 1000 });
        for(int i = 0; i < test.size(); i++){
            assertFalse(test.get(types.get(i)).isEmpty());
        }
    }
}