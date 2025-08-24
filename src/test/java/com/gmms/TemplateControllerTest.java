package com.gmms;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TemplateControllerTest {
        private static final Path STRUCTURES_PATH = Path
                        .of("./src/main/resources/data/Structures.txt");

        @BeforeEach
        void resetSingletonState() throws Exception {
                // azzera il campo 'template' del singleton per isolare i test
                TemplateController controller = TemplateController.getInstance();
                Field f = TemplateController.class.getDeclaredField("template");
                f.setAccessible(true);
                f.set(controller, null);
        }

        @Test
        @DisplayName("Singleton: getInstance() restituisce sempre la stessa istanza")
        void singletonReturnsSameInstance() {
                TemplateController c1 = TemplateController.getInstance();
                TemplateController c2 = TemplateController.getInstance();
                assertSame(c1, c2, "TemplateController deve essere un singleton.");
        }

        @Test
        @DisplayName("Getter prima di generateTemplate(): NullPointerException attesa")
        void gettersBeforeGenerateThrowNpe() {
                TemplateController controller = TemplateController.getInstance();

                assertThrows(NullPointerException.class, controller::getWordCount,
                                "Senza generateTemplate() ci si aspetta una NullPointerException.");
                assertThrows(NullPointerException.class, controller::getTemplateDesc,
                                "Senza generateTemplate() ci si aspetta una NullPointerException.");
        }

        @Test
        @DisplayName("Dopo generateTemplate() i getter restituiscono valori validi")
        void generateTemplateThenGettersWork() {
                assumeTrue(Files.exists(STRUCTURES_PATH),
                                "Risorsa SentenceStructures.txt mancante: test saltato.");

                TemplateController controller = TemplateController.getInstance();
                controller.generateTemplate();

                String desc = controller.getTemplateDesc();
                int[] counts = controller.getWordCount();

                assertNotNull(desc, "La descrizione del template non dovrebbe essere null dopo la generazione.");
                assertFalse(desc.isBlank(), "La descrizione del template non dovrebbe essere vuota.");
                assertNotNull(counts, "L'array dei conteggi non dovrebbe essere null.");
                assertTrue(counts.length >= 0, "L'array può essere vuoto ma non null.");
        }

        @Test
        @DisplayName("generateTemplate() è ri-invocabile senza eccezioni")
        void generateTemplateIsIdempotentEnough() {
                assumeTrue(Files.exists(STRUCTURES_PATH),
                                "Risorsa SentenceStructures.txt mancante: test saltato.");

                TemplateController controller = TemplateController.getInstance();

                assertDoesNotThrow(controller::generateTemplate);
                assertDoesNotThrow(controller::generateTemplate);

                assertNotNull(controller.getTemplateDesc());
                assertNotNull(controller.getWordCount());
        }
}
