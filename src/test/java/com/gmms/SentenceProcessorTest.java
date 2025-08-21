package com.gmms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SentenceControllerTest {

    @Test
    @DisplayName("Crea un oggetto Sentence correttamente")
    void testCreateSentence() {
        SentenceController.createSentence("frase di test");
        assertNotNull(SentenceController.getSentenceDesc());
        assertEquals("frase di test", SentenceController.getSentenceDesc());
    }

    @Test
    @DisplayName("Cambia la Sentence puntata ad una nuova creazione")
    void testSwitchBetweenSentences() {
        SentenceController.createSentence("input test");
        String input = SentenceController.getSentenceDesc();
        SentenceController.createSentence("nonsense test");
        assertTrue(!(input.equals(SentenceController.getSentenceDesc())));
    }

    @Test
    @DisplayName("Punta alla corretta istanza di Sentence a seconda del tipo di rigenerazione richiesta")
    void testSentenceRegeneration() {
        SentenceController.createSentence("input test");
        String input = SentenceController.getSentenceDesc();
        SentenceController.createSentence("nonsense test");
        SentenceController.createSentence(" regeneration nonsense test");
        SentenceController.resetSentenceState();
        assertEquals(input, SentenceController.getSentenceDesc());
    }

}