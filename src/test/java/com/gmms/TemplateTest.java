package com.gmms;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TemplateTest {

    @Test
    @DisplayName("Il costruttore assegna correttamente i campi")
    void constructorAssignsFields() {
        String desc = "[NOUN] [VERB] [ADJECTIVE]";
        int[] words = new int[] { 2, 1, 1 };

        Template t = new Template(desc, words);

        assertEquals(desc, t.templateDesc);
        assertSame(words, t.templateWords, "L'array deve essere lo stesso riferimento passato (nessuna copia).");
        assertArrayEquals(new int[] { 2, 1, 1 }, t.templateWords);
    }

    @Test
    @DisplayName("Modificare l'array originale modifica anche quello interno (aliasing previsto)")
    void externalArrayMutationReflectsInside() {
        int[] words = new int[] { 1, 0, 3 };
        Template t = new Template("desc", words);

        // mutazione esterna
        words[2] = 4;

        assertArrayEquals(new int[] { 1, 0, 4 }, t.templateWords,
                "Poiché l'array non viene copiato, la modifica esterna si riflette nel campo pubblico.");
    }

    @Test
    @DisplayName("Supporta descrizione null e array null")
    void allowsNullInputs() {
        Template t1 = new Template(null, null);
        assertNull(t1.templateDesc);
        assertNull(t1.templateWords);
    }

    @Test
    @DisplayName("Supporta array vuoto")
    void allowsEmptyArray() {
        Template t = new Template("only desc", new int[0]);
        assertNotNull(t.templateWords);
        assertEquals(0, t.templateWords.length);
    }
}
