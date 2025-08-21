package com.gmms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SentenceControllerTest {

    @Test
    @DisplayName("Crea un oggetto Sentence correttamente")
    void testCreateSentence() {
        SentenceController.getInstance().createSentence("frase di test");
        assertNotNull(SentenceController.getInstance().getSentenceDesc());
        assertEquals("frase di test", SentenceController.getInstance().getSentenceDesc());
    }

    @Test
    @DisplayName("Cambia la Sentence puntata ad una nuova creazione")
    void testSwitchBetweenSentences() {
        SentenceController.getInstance().createSentence("input test");
        String input = SentenceController.getInstance().getSentenceDesc();
        SentenceController.getInstance().createSentence("nonsense test");
        assertTrue(!(input.equals(SentenceController.getInstance().getSentenceDesc())));
    }

    @Test
    @DisplayName("Punta alla corretta istanza di Sentence a seconda del tipo di rigenerazione richiesta")
    void testSentenceRegeneration() {
        SentenceController.getInstance().createSentence("input test");
        String input = SentenceController.getInstance().getSentenceDesc();
        SentenceController.getInstance().createSentence("nonsense test");
        SentenceController.getInstance().createSentence(" regeneration nonsense test");
        SentenceController.getInstance().resetSentenceState();
        assertEquals(input, SentenceController.getInstance().getSentenceDesc());
    }

}