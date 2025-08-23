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

    // per fare in modo che venga resettato lo stato di wordpicker
    // e per non avere delle parole generate salvate
    @AfterEach
    void reset() {
        WordPicker.getInstance().resetNumOfRetries();
    }

    @Test
    @DisplayName("Verifica che vengano scelte parole della frase in input durante la generazione delle parole")
    void testStartWordsExtraction() {
        String name = "penna";
        WordPicker.getInstance().startWordsExtraction(); // fa generare le parole in base alla frase di test
        Map<String, List<String>> map = WordPicker.getInstance().getWords(); // ritorna le parole generate
        Set<String> keys = map.keySet();
        for (int i = 0; i < map.size(); i++) {
            String tmp = keys.iterator().next();
            if (tmp == "NOUN")
                assertTrue(map.get(tmp).contains(name)); // controlla che ci sia la parola penna nell'insieme dei noun
        }
    }

    @Test
    @DisplayName("Verifica che tra le parole generate ce ne deve sempre essere almeno una della frase in input dell'utente")
    void testRetryInputException() {
        RetryInputException retryexcept = assertThrows(RetryInputException.class, () -> {
            while (true) {
                // viene ripetuta la generazione delle parola fino a quando
                // tutte le parole della frase in input non sono presenti
                // tra le parole estratte
                WordPicker.getInstance().startWordsExtraction();
            }
        }, "RetryInputException non e' stato lanciato");
        assertTrue(retryexcept.getMessage().contains("ERRORE: nessuna parola dell'user selezionata"));
    }

    @Test
    @DisplayName("Verifica che vengano restituite le parole dopo che sono state scelte")
    void testGenerationOfRandomWords() {
        WordPicker.getInstance().startWordsExtraction();
        Map<String, List<String>> map = WordPicker.getInstance().getWords();
        Set<String> keys = map.keySet();
        for (int i = 0; i < map.size(); i++) {
            assertFalse(map.get(keys.iterator().next()).isEmpty());
        }
    }

    @Test
    @DisplayName("Verifica se viene lanciato un errore nel caso in cui non sono state generate parole in precedenza")
    void testStartWordsExtractionError() {
        NoGeneratedWordsException ng = assertThrows(NoGeneratedWordsException.class,
                () -> WordPicker.getInstance().getWords(),
                "NoGeneratedWordsException non e' stato lanciato");
        assertTrue(ng.getMessage().contains("ERRORE: non sono state generate parole in precedenza"));
    }
}
