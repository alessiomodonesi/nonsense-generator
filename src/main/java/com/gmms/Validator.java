package com.gmms;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// -- SINGLETON ---
public final class Validator {
    private static final Validator instance = new Validator();
    private String maxName = new String(); // campo con valore massimo risultante dall'analisi della tossicità
    private double maxConfidence = 0.0; // valore del campo sopra citato
    private double criticValue = 0.60; // valore massimo accettato per la tossicità

    // costruttore
    private Validator() {
    }

    // per inizializzare un singleton
    public static Validator getInstance() {
        return instance;
    }

    // metodo che verifica l'input iniziale dell'utente
    public boolean verifySentence(String input) {
        // controlla che l'input non sia nullo
        if (input.trim().isEmpty())
            return false;

        // ".*[a-zA-Z].*" è una regular expression che cerca almeno una lettera
        // (maiuscola o minuscola) in qualsiasi punto della stringa
        return input.matches(".*[a-zA-Z].*");
    }

    // metodo che valida la struttura sintattica della frase in input
    // implementato di facciata, la logica del controllo è da individuare
    public boolean validateSentenceStructure(SyntacticNode syntacticTree) {
        boolean checkSentenceStructure = true;
        if (!checkSentenceStructure) {
            IOController.getInstance().showValidationError();
            return false;
        }
        return true;
    }

    // metodo che chiama l'ApiCaller per la verifica della tossicità
    public boolean verifyToxicity(String sentenceDesc) throws Exception {
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
            IOController.getInstance().showToxicityError();
            // System.out.println(maxName + " = " + maxConfidence);
            return false;
        } else {
            IOController.getInstance().showToxicityResults(maxName, maxConfidence);
            return true;
        }
    }

    // metodi solo per WebController

    public String getToxicityDetails() {
        String roundedLevel = String.format("%.3f", maxConfidence);
        return maxName + " = " + roundedLevel;
    }

    public void resetVar() {
        maxName = new String();
        maxConfidence = 0.0;
    }
}