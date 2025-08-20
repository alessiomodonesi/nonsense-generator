package com.gmms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class TemplateTest {

    @Test
    @DisplayName("Crea un template quando la struttura è corretta")
    void testCorrectStructureTemplate() {
        String s = "Il %s corre e %s bello";
        Template template = Template.create(s);

        assertNotNull(template); // verifica che il template non sia null
        assertNotEquals(s, template.templateDesc); // verifica che la descrizione del template sia stata modificata
        assertTrue(template.templateDesc.startsWith("Il "));
        assertTrue(template.templateDesc.endsWith(" bello"));
        assertTrue(Arrays.stream(template.templateWords).sum() > 0); // verifica che ci siano parole da sostituire
    }

    @Test
    @DisplayName("Restituisce la stringa uguale se non ci sono segnaposti")
    void testWithoutPlaceholderTemplate() {
        String s = "L'aereo vola alto nel cielo";
        Template template = Template.create(s);
        assertNotNull(template);
        assertEquals(s, template.templateDesc); // verifica che la descrizione del template sia uguale alla stringa
                                                // originale
        assertArrayEquals(new int[] { 0, 0, 0 }, template.templateWords); // verifica che non ci siano parole da
                                                                          // sostituire
    }

    @Test
    @DisplayName("Da una stringa vuota, restituisce un template vuoto")
    void testEmptyTemplate() {
        String s = "";
        Template template = Template.create(s);
        assertNotNull(template);
        assertEquals("", template.templateDesc); // verifica che la descrizione del template sia vuota
        assertArrayEquals(new int[] { 0, 0, 0 }, template.templateWords); // verifica che non ci siano parole da
                                                                          // sostituire
    }

}