package com.gmms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SentenceProcessorTest {

    @Test
    @DisplayName("Crea un oggetto Sentence correttamente")
    void testCreateSentence() {
        SentenceProcessor.createSentence("frase di test");
        assertNotNull(SentenceProcessor.getSentenceDesc());
        assertEquals("frase di test", SentenceProcessor.getSentenceDesc());
    }

    @Test
    @DisplayName("Cambia la Sentence puntata ad una nuova creazione")
    void testSwitchBetweenSentences() {
        SentenceProcessor.createSentence("input test");
        String input = SentenceProcessor.getSentenceDesc();
        SentenceProcessor.createSentence("nonsense test");
        assertTrue(!(input.equals(SentenceProcessor.getSentenceDesc())));
    }

    @Test
    @DisplayName("Punta alla corretta istanza di Sentence a seconda del tipo di rigenerazione richiesta")
    void testSentenceRegeneration() {
        SentenceProcessor.createSentence("input test");
        String input = SentenceProcessor.getSentenceDesc();
        SentenceProcessor.createSentence("nonsense test");
        SentenceProcessor.createSentence(" regeneration nonsense test");
        SentenceProcessor.resetSentenceState();
        assertEquals(input, SentenceProcessor.getSentenceDesc());
    }

}