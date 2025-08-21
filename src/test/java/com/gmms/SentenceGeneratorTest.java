package com.gmms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

class SentenceGeneratorTest {

    @Test
    @DisplayName("Crea una frase nonsense correttamente")
    void testGenerateSentenceDesc() {
        TemplateGenerator testGenerator = new TemplateGenerator();
        TemplateController testController = new TemplateController(testGenerator);

        SentenceGenerator.generateSentenceDesc(testController);
        String nonsense = SentenceProcessor.getSentenceDesc();
        assertNotNull(nonsense); // o assertThrows(?)

        boolean correctReplacement = true;
        if (nonsense.contains("NOUN") || nonsense.contains("VERB") || nonsense.contains("ADJECTIVE"))
            correctReplacement = false;

        assertTrue(correctReplacement, "Rimpiazzo di parole fallimentare");
    }
}