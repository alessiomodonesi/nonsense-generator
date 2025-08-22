package com.gmms;

import java.util.Map;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class WordPickerTest {
    private static SystemDictionary sd = SystemDictionary.getInstance();
    private static SentenceController sc = SentenceController.getInstance();
    private static TemplateController tc = TemplateController.getInstance();

    @BeforeAll
    static void setUp() {
        String inputSentence = "La penna cade";
        try {
            sd.initializeDic();
            sc.createSentence(inputSentence);
            sc.analysisProcess();
            tc.generateTemplate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    void reset(){
        WordPicker.getInstance().resetNumOfRetries();
    }

    @Test
    @DisplayName("Verifica che vengano scelte parole della frase in input durante la generazione delle parole")
    void testStartWordsExtraction() {
        String name = "penna";
        WordPicker.getInstance().startWordsExtraction();
        Map<String , List<String>> map = WordPicker.getInstance().getWords();
        Set<String> keys = map.keySet();
        for(int i = 0; i < map.size(); i++){
            if(keys.iterator().next()== "NOUN")
                assertTrue(map.get(keys.iterator().next()).contains(name));
        }
    }

    
    @Test
    @DisplayName("Verifica il lancio dell'errore in caso di non estrazione")
    void testStartWordsExtractionError() {
        NoGeneratedWordsException ng = assertThrows(NoGeneratedWordsException.class,
                () -> WordPicker.getInstance().getWords(), 
                "NoGeneratedWordsException non e' stato lanciato");
        assertTrue(ng.getMessage().contains("ERRORE: non sono state generate parole in precedenza"));
    }

    @Test
    @DisplayName("Verifica la generazione di parole")
    void testGenerationOfRandomWords() {
        WordPicker.getInstance().startWordsExtraction();
        Map<String , List<String>> map = WordPicker.getInstance().getWords();
        Set<String> keys = map.keySet();
        for(int i = 0; i < map.size(); i++){
            assertFalse(map.get(keys.iterator().next()).isEmpty());
        }
    }

    @Test
    @DisplayName("Verifica che tra le parole generate ce ne deve sempre essere almeno una della frase in input dell'utente")
    void testRetryInputException(){
        RetryInputException retryexcept = assertThrows(RetryInputException.class, () ->{
            while(true){
                WordPicker.getInstance().startWordsExtraction();
            }
        }, "RetryInputException non e' stato lanciato");
        assertTrue(retryexcept.getMessage().contains("ERRORE: nessuna parola dell'user selezionata"));
    }
}
