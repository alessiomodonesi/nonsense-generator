package com.gmms;
// Alessio Modonesi

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Validator {
    public static void verifyToxicity(String sentenceDesc) throws Exception {
        // Invocazione della funzione getToxicityAnalysis dalla classe ApiCaller
        String toxicityAnalysis = ApiCaller.getToxicityAnalysis(sentenceDesc);
        double toxicityLevel = 0.0; // media dei valori restituiti
        System.out.println(toxicityAnalysis);

        // Analizza la stringa JSON per ottenere l'oggetto principale
        JsonObject rootObject = JsonParser.parseString(toxicityAnalysis).getAsJsonObject();

        // Estrai l'array "moderationCategories"
        JsonArray moderationCategories = rootObject.getAsJsonArray("moderationCategories");

        // Itera sull'array per trovare l'oggetto con il nome "Toxic"
        if (moderationCategories != null) {
            for (int i = 0; i < moderationCategories.size(); i++) {
                JsonObject category = moderationCategories.get(i).getAsJsonObject();

                // Controlla se il nome è "Toxic"
                if ("Toxic".equals(category.get("name").getAsString())) {
                    toxicityLevel = category.get("confidence").getAsDouble();
                    break;
                }
            }
        }

        if (toxicityLevel >= 0.50) {
            System.out.println("error: toxicity level is too high");
            // IOController.showToxicityError();
        } else {
            System.out.println("toxicity level is: " + toxicityLevel);
            // IOController.showToxicityResults(toxicityLevel);
        }
    }
}