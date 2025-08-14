package com.gmms;
// Tommaso Silvestrin

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOController {

    private final Validator validator;
    private final SentenceProcessor sentenceProcessor;

    public IOController(Validator validator, SentenceProcessor sentenceProcessor) {
        this.validator = validator; // validator da usare per verificare l'input
        this.sentenceProcessor = sentenceProcessor;
    }

    // riceve una frase e avvia il processo di validazione chiamando il Validator
    public void inputSentence(String input) {
        validator.verifySentence(input);
    }

    // mostra errore per input non valido
    public void showInputError() {
        System.err.println("ERRORE: L'input inserito non è valido");
    }

    public void displaySentence(String sentenceDesc) {
        System.out.println("Frase:");
        System.out.println(sentenceDesc);
    }

    // mostra errore relativo alla validazione della struttura della frase
    public void showValidationError() {
        System.err.println("ERRORE: La struttura della frase analizzata non è valida");
    }

    // mostra l'albero sintattico della frase
    public void showSyntacticTree() {
        Object syntacticTree = sentenceProcessor.getSyntacticTree();
        Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
        String prettyJsonResult = gson.toJson(syntacticTree);
        System.out.println(prettyJsonResult);
    }

    // mostra errore se la frase generata è tossica
    public static void showToxicityError() {
        System.err.println("ERRORE: La frase generata ha un livello di tossicità non accettabile");
    }

    public static void showToxicityResults(double toxicityLevel) {
        System.out.println("Livello di tossicità della frase generata: " + toxicityLevel);
    }
}