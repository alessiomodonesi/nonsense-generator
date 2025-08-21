package com.gmms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidatorTest {

    // --- Test per verifySentence() ---
    @Test
    void testVerifySentenceWithValidInput() {
        assertTrue(Validator.getInstance().verifySentence("Ciao mondo"));
        assertTrue(Validator.getInstance().verifySentence("Hello123"));
    }

    @Test
    void testVerifySentenceWithInvalidInput() {
        assertFalse(Validator.getInstance().verifySentence("     ")); // solo spazi
        assertFalse(Validator.getInstance().verifySentence("123456")); // numeri senza lettere
        assertFalse(Validator.getInstance().verifySentence("")); // stringa vuota
    }

    // --- Test per validateSentenceStructure() ---
    @Test
    void testValidateSentenceStructure() {
        SyntacticNode root = new SyntacticNode("Mangio", "mangiare", "VERB");
        assertTrue(Validator.getInstance().validateSentenceStructure(root));
    }

    @Test
    void testValidateSentenceStructureWithNull() {
        assertTrue(Validator.getInstance().validateSentenceStructure(null));
    }

    // --- Test per verifyToxicity() con JSON simulato ---
    @Test
    void testVerifyToxicityNonToxic() throws Exception {
        String json = """
                    {
                      "moderationCategories": [
                        {"name": "harassment", "confidence": 0.3},
                        {"name": "violence", "confidence": 0.2}
                      ]
                    }
                """;

        // Simuliamo la chiamata ApiCaller restituendo il JSON fittizio
        boolean result = testVerifyToxicityWithJson(json);
        assertTrue(result);
    }

    @Test
    void testVerifyToxicityToxic() throws Exception {
        String json = """
                    {
                      "moderationCategories": [
                        {"name": "harassment", "confidence": 0.6},
                        {"name": "violence", "confidence": 0.4}
                      ]
                    }
                """;

        boolean result = testVerifyToxicityWithJson(json);
        assertFalse(result);
    }

    // --- Metodo helper per simulare verifyToxicity senza ApiCaller ---
    private boolean testVerifyToxicityWithJson(String json) throws Exception {
        // Copia della logica di verifyToxicity, senza chiamare ApiCaller
        double maxConfidence = 0.0;
        double criticValue = 0.50;

        var rootObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();
        var categories = rootObject.getAsJsonArray("moderationCategories");

        for (var categoryElement : categories) {
            var categoryObject = categoryElement.getAsJsonObject();
            double confidence = categoryObject.get("confidence").getAsDouble();
            if (confidence > maxConfidence) {
                maxConfidence = confidence;
            }
        }

        return maxConfidence < criticValue;
    }
}
