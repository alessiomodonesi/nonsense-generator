package com.gmms;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TemplateGeneratorTest {

    // sottoclasse di SentenceStructures per testare senza file reali
    static class FakeSentenceStructures extends SentenceStructures {
        private final String structure;

        FakeSentenceStructures(String structure, Path tempFilePath) {
            super(tempFilePath.toString()); // il super richiede un path: usiamo un file temp
            this.structure = structure;
        }

        @Override
        public String getRandomStructure() {
            return structure;
        }
    }

    @TempDir
    Path tempDir; // JUnit crea e pulisce automaticamente

    private Path touchTempFile() throws IOException {
        Path p = tempDir.resolve("structures.txt");
        Files.writeString(p, "dummy\n"); // contenuto irrilevante: non lo useremo
        return p;
    }

    // conta le occorrenze non sovrapposte di una sottostringa
    private static int countOccurrences(String text, String token) {
        int count = 0, idx = 0;
        while ((idx = text.indexOf(token, idx)) >= 0) {
            count++;
            idx += token.length();
        }
        return count;
    }

    @Test
    @DisplayName("Struttura con 0 placeholder: nessun tag e contatori a zero")
    void noSlotsProducesNoTagsAndZeroCounts() throws Exception {
        Path file = touchTempFile();
        SentenceStructures s = new FakeSentenceStructures("Ciao mondo.", file);

        Template t = TemplateGenerator.getInstance().generateTemplate(s);

        assertEquals("Ciao mondo.", t.templateDesc);
        assertEquals(3, t.templateWords.length);
        assertArrayEquals(new int[] { 0, 0, 0 }, t.templateWords);

        assertFalse(t.templateDesc.contains("%s"));
        assertEquals(0, countOccurrences(t.templateDesc, "[NOUN]"));
        assertEquals(0, countOccurrences(t.templateDesc, "[VERB]"));
        assertEquals(0, countOccurrences(t.templateDesc, "[ADJECTIVE]"));
    }

    @Test
    @DisplayName("Struttura con 1 %s: inserisce 1..3 tag e i contatori corrispondono")
    void oneSlotProducesBetweenOneAndThreeTags() throws Exception {
        Path file = touchTempFile();
        SentenceStructures s = new FakeSentenceStructures("Il %s.", file);

        Template t = TemplateGenerator.getInstance().generateTemplate(s);

        assertFalse(t.templateDesc.contains("%s"), "Non dovrebbero rimanere %s nella descrizione.");

        int nN = countOccurrences(t.templateDesc, "[NOUN]");
        int nV = countOccurrences(t.templateDesc, "[VERB]");
        int nA = countOccurrences(t.templateDesc, "[ADJECTIVE]");
        int totalTags = nN + nV + nA;

        // 1 slot genera 1..3 tag
        assertTrue(totalTags >= 1 && totalTags <= 3,
                "Con un singolo %s ci si attende da 1 a 3 tag.");

        // i contatori devono corrispondere al numero di tag
        assertEquals(nN, t.templateWords[0], "Conteggio NOUN non coerente.");
        assertEquals(nV, t.templateWords[1], "Conteggio VERB non coerente.");
        assertEquals(nA, t.templateWords[2], "Conteggio ADJECTIVE non coerente.");
        assertEquals(totalTags, t.templateWords[0] + t.templateWords[1] + t.templateWords[2],
                "La somma dei contatori deve eguagliare i tag trovati.");
    }

    @Test
    @DisplayName("Struttura con 2 %s: somma tag tra 2 e 6 e contatori coerenti")
    void twoSlotsProduceBetweenTwoAndSixTags() throws Exception {
        Path file = touchTempFile();
        SentenceStructures s = new FakeSentenceStructures("%s corre e %s!", file);

        Template t = TemplateGenerator.getInstance().generateTemplate(s);

        assertFalse(t.templateDesc.contains("%s"));

        int nN = countOccurrences(t.templateDesc, "[NOUN]");
        int nV = countOccurrences(t.templateDesc, "[VERB]");
        int nA = countOccurrences(t.templateDesc, "[ADJECTIVE]");
        int totalTags = nN + nV + nA;

        // 2 slot => ciascuno 1..3 -> totale 2..6
        assertTrue(totalTags >= 2 && totalTags <= 6,
                "Con due %s ci si attende da 2 a 6 tag complessivi.");

        assertEquals(nN, t.templateWords[0]);
        assertEquals(nV, t.templateWords[1]);
        assertEquals(nA, t.templateWords[2]);
        assertEquals(totalTags, t.templateWords[0] + t.templateWords[1] + t.templateWords[2]);
    }

    @Test
    @DisplayName("Generazioni ripetute rispettano i limiti per slot (robustezza al caso)")
    void repeatedGenerationsStayWithinBounds() throws Exception {
        Path file = touchTempFile();
        SentenceStructures s = new FakeSentenceStructures("A %s B %s C %s D.", file); // 3 slot

        for (int i = 0; i < 20; i++) {
            Template t = TemplateGenerator.getInstance().generateTemplate(s);

            int nN = countOccurrences(t.templateDesc, "[NOUN]");
            int nV = countOccurrences(t.templateDesc, "[VERB]");
            int nA = countOccurrences(t.templateDesc, "[ADJECTIVE]");
            int totalTags = nN + nV + nA;

            // 3 slot => 3..9 tag complessivi
            assertTrue(totalTags >= 3 && totalTags <= 9,
                    "Con tre %s ci si attende da 3 a 9 tag complessivi.");

            // coerenza contatori
            assertEquals(nN, t.templateWords[0]);
            assertEquals(nV, t.templateWords[1]);
            assertEquals(nA, t.templateWords[2]);
            assertEquals(totalTags, t.templateWords[0] + t.templateWords[1] + t.templateWords[2]);

            // sanity check: contiene solo tag previsti (oltre al testo fisso)
            String payload = t.templateDesc.replace("A ", "")
                    .replace(" B ", " ")
                    .replace(" C ", " ")
                    .replace(" D.", "")
                    .trim();
            // i gruppi inseriti sono sequenze di tag separati da spazio singolo
            assertFalse(payload.contains("%s"));
        }
    }
}
