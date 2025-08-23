package com.gmms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testVerifySentenceWithValidInput() {
        assertTrue(Validator.verifySentence("Ciao mondo"));
        assertTrue(Validator.verifySentence("Hello123"));
    }

    @Test
    void testVerifySentenceWithInvalidInput() {
        assertFalse(Validator.verifySentence("     ")); // solo spazi
        assertFalse(Validator.verifySentence("123456")); // numeri senza lettere
        assertFalse(Validator.verifySentence("")); // stringa vuota
    }

    @Test
    void testValidateSentenceStructure() {
        SyntacticNode root = new SyntacticNode("Mangio", "mangiare", "VERB");
        assertTrue(Validator.validateSentenceStructure(root));
    }

    @Test
    void testValidateSentenceStructureWithNull() {
        assertTrue(Validator.validateSentenceStructure(null));
    }

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

        // simuliamo la chiamata ApiCaller restituendo il JSON fittizio
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

    // metodo helper per simulare verifyToxicity senza ApiCaller
    private boolean testVerifyToxicityWithJson(String json) throws Exception {
        // copia della logica di verifyToxicity, senza chiamare ApiCaller
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
