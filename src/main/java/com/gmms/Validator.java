package com.gmms;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// -- SINGLETON ---
public final class Validator {
    private static final Validator instance = new Validator();

    // costruttore
    private Validator() {
    }

    // per inizializzare un singleton
    public static Validator getInstance() {
        return instance;
    }

    public boolean verifySentence(String input) {
        // Prima controlliamo che l'input non sia nullo.
        if (input.trim().isEmpty())
            return false;

        // ".*[a-zA-Z].*" è una regular expression che cerca almeno una lettera
        // (maiuscola o minuscola) in qualsiasi punto della stringa.
        return input.matches(".*[a-zA-Z].*");
    }

    public boolean validateSentenceStructure(SyntacticNode syntacticTree) {
        boolean checkSentenceStructure = true;
        if (!checkSentenceStructure) {
            IOController.showValidationError();
            return false;
        }
        return true;
    }

    public boolean verifyToxicity(String sentenceDesc) throws Exception {
        String maxName = new String();
        double maxConfidence = 0.0;
        double criticValue = 0.50;

        // invocazione della funzione getToxicityAnalysis dalla classe ApiCaller
        String toxicityAnalysis = ApiCaller.getToxicityAnalysis(sentenceDesc);
        // System.out.println(toxicityAnalysis);

        // analizza la stringa JSON e ottieni l'oggetto radice
        JsonObject rootObject = JsonParser.parseString(toxicityAnalysis).getAsJsonObject();

        // estrai l'array di 'moderationCategories'
        JsonArray categories = rootObject.getAsJsonArray("moderationCategories");

        // itera su ogni elemento dell'array
        for (JsonElement categoryElement : categories) {
            JsonObject categoryObject = categoryElement.getAsJsonObject();

            // estrai il valore di 'confidence' più alto
            String name = categoryObject.get("name").getAsString();
            double confidence = categoryObject.get("confidence").getAsDouble();
            if (confidence > maxConfidence) {
                maxName = name;
                maxConfidence = confidence;
            }
        }

        if (maxConfidence >= criticValue) {
            IOController.showToxicityError();
            // System.out.println(maxName + " = " + maxConfidence);
            return false;
        } else {
            IOController.showToxicityResults(maxName, maxConfidence);
            return true;
        }
    }
}