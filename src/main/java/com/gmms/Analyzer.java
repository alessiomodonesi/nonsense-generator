package com.gmms;
// Alessio Modonesi

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyzer {
    public static Map<String, List<String>> analyzeSentence(String sentenceDesc) throws Exception {
        // 1. Inizializza la mappa per i risultati
        Map<String, List<String>> syntacticTree = new HashMap<>();

        // 2. Invocazione della funzione getSyntaxAnalysis dalla classe ApiCaller
        String jsonData = ApiCaller.getSyntaxAnalysis(sentenceDesc);

        // 3. Analizza la stringa JSON in una struttura ad albero di JsonElement
        JsonObject rootObject = JsonParser.parseString(jsonData).getAsJsonObject();

        // 4. Estrai l'array di 'tokens'
        JsonArray tokens = rootObject.getAsJsonArray("tokens");

        // 5. Itera su ogni elemento dell'array usando un for-each
        for (JsonElement tokenElement : tokens) {
            // Converte l'elemento corrente in un JsonObject
            JsonObject tokenObject = tokenElement.getAsJsonObject();

            // Estrai la parola (content) e il tag
            String word = tokenObject.getAsJsonObject("text").get("content").getAsString();
            String tag = tokenObject.getAsJsonObject("partOfSpeech").get("tag").getAsString();

            // 6. Aggiungi la parola alla lista corretta nella mappa
            // La logica è identica a prima, poiché è una funzionalità standard di Java
            syntacticTree.computeIfAbsent(tag, k -> new ArrayList<>()).add(word);
        }

        // Funzione di stampa da inserire in IOController
        Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
        String prettyJsonResult = gson.toJson(syntacticTree);
        System.out.println(prettyJsonResult);

        return syntacticTree;
    }
}