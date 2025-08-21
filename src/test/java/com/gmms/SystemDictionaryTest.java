package com.gmms;

import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SystemDictionaryTest {

    @BeforeAll
    void testSetUpDic() {
        try {
            SystemDictionary.initializeDic();
        } catch (Exception e) {
            System.out.println("ERRORE: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Verifica la generazione di parole dal dizionario")
    void testWordsPickingBaseCase() {
        int[] wordsQt = new int[] { 3, 10, 10 };
        SystemDictionary.pickDictionaryWords(wordsQt);
        assertFalse(SystemDictionary.pickDictionaryWords(wordsQt).isEmpty());
        OutOfBoundsExcpetion thrown = assertThrows(
                OutOfBoundsExcpetion.class,
                () -> SystemDictionary.pickDictionaryWords(new int[] { 0, -1, -1 }),
                "Expected pickDictionaryWords() to throw, but it didn't");
        assertFalse(thrown.getMessage().isBlank());

        @SuppressWarnings("unused")
        Map<String, List<String>> test = SystemDictionary.pickDictionaryWords(new int[] { 0, 0, 0 });
    }
}