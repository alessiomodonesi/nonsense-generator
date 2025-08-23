package com.gmms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

class SentenceGeneratorTest {

    @Test
    @DisplayName("Crea una frase nonsense correttamente")
    void testGenerateSentenceDesc() {

        String testTemplate = "template di test : Il [NOUN] [VERB] e il [ADJECTIVE] [NOUN] [VERB] in un [NOUN] [ADJECTIVE] ";
        Map<String, List<String>> testFillingWords = new HashMap<>();
        List<String> tmp;

        tmp = new ArrayList<>(List.of("leone", "aeroplano", "scatola"));
        testFillingWords.put("NOUN", tmp);

        tmp = new ArrayList<>(List.of("rotola", "piange"));
        testFillingWords.put("VERB", tmp);

        tmp = new ArrayList<>(List.of("pachidermico", "idrodinamico"));
        testFillingWords.put("ADJECTIVE", tmp);

        SentenceGenerator.getInstance().setSentenceDesc(testTemplate);
        SentenceGenerator.getInstance().setFillingWords(testFillingWords);

        SentenceGenerator.getInstance().generateSentence();
        String nonsense = SentenceController.getInstance().getSentenceDesc();

        assertNotNull(nonsense); // o assertThrows(?)

        boolean correctReplacement = true;
        if (nonsense.contains("NOUN") || nonsense.contains("VERB") || nonsense.contains("ADJECTIVE"))
            correctReplacement = false;

        System.out.println(nonsense);
        assertTrue(correctReplacement, "Rimpiazzo di parole fallimentare");
    }

}