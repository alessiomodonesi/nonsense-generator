package com.gmms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

class SentenceGeneratorTest {

    @Test
    @DisplayName("Crea una frase nonsense correttamente")
    void testGenerateSentenceDesc() {
        SentenceGenerator.getInstance().generateSentence();
        String nonsense = SentenceController.getInstance().getSentenceDesc();
        assertNotNull(nonsense); // o assertThrows(?)

        boolean correctReplacement = true;
        if (nonsense.contains("NOUN") || nonsense.contains("VERB") || nonsense.contains("ADJECTIVE"))
            correctReplacement = false;

        assertTrue(correctReplacement, "Rimpiazzo di parole fallimentare");
    }
}