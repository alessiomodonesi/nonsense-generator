package com.gmms;
// Alessio Modonesi

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class Validator {
    // Costruttore
    private Validator() {
    }

    public static boolean verifySentence(String input) {
        if (input.isEmpty())
            return false;
        return true;
    }

    public static boolean validateSentenceStructure(SyntacticNode syntacticTree) {
        boolean checkSentenceStructure = true;
        if (!checkSentenceStructure) {
            IOController.showValidationError();
            return false;
        }
        return true;
    }

    public static boolean verifyToxicity(String sentenceDesc) throws Exception {
        // Invocazione della funzione getToxicityAnalysis dalla classe ApiCaller
        String toxicityAnalysis = ApiCaller.getToxicityAnalysis(sentenceDesc);
        // System.out.println(toxicityAnalysis);

        // 1. Analizza la stringa JSON e ottieni l'oggetto radice
        JsonObject rootObject = JsonParser.parseString(toxicityAnalysis).getAsJsonObject();

        // 2. Estrai l'array di 'moderationCategories'
        JsonArray categories = rootObject.getAsJsonArray("moderationCategories");

        // 3. Inizializza le variabili per il calcolo
        double sum = 0.0;
        int count = categories.size();

        // 4. Itera su ogni elemento dell'array
        for (JsonElement categoryElement : categories) {
            JsonObject categoryObject = categoryElement.getAsJsonObject();

            // Estrai il valore di 'confidence' e sommalo al totale
            double confidence = categoryObject.get("confidence").getAsDouble();
            sum += confidence;
        }

        // 5. Calcola la media (con un controllo per evitare la divisione per zero)
        double toxicityLevel = 0.0;
        double criticValue = 0.09;
        if (count > 0)
            toxicityLevel = sum / count;

        if (toxicityLevel >= criticValue) {
            IOController.showToxicityError();
            // System.out.println(toxicityLevel);
            return false;
        } else {
            IOController.showToxicityResults(toxicityLevel);
            return true;
        }
    }
}