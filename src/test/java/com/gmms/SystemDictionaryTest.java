package com.gmms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class SystemDictionaryTest {

    @BeforeAll
    static void setUp() {
        try {
            SystemDictionary.initializeDic();
        } catch (Exception e) {
            System.out.println("ERRORE: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Verifica la generazione di parole dal dizionario")
    static void example2() {
        int[] wordsQt = new int[] { 3, 10, 10 };
        SystemDictionary.pickDictionaryWords(wordsQt);
        wordsQt = new int[] { 0, -1, -1 };
        SystemDictionary.pickDictionaryWords(wordsQt);
    }

    @Test
    @DisplayName("Ottieni la quantità di parole del dizionario")
    static void example3() {
        System.out.println(SystemDictionary.getDictionaryWordsCount());
    }
}