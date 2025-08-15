package com.gmms;
// Tommaso Silvestrin

import java.util.Scanner;

public class IOController {
    private static final Scanner scanner = new Scanner(System.in);

    private IOController(Validator validator, SentenceProcessor sentenceProcessor) {
    }

    // riceve una frase e avvia il processo di validazione chiamando il Validator
    public static String inputSentence() {
        System.out.print("Inserisci una frase: ");
        String input = scanner.nextLine();
        return input;
    }

    // mostra errore per input non valido
    public static void showInputError() {
        System.err.println("ERRORE: L'input inserito non è valido");
    }

    public static void displaySentence(String sentenceDesc) {
        System.out.println("Frase da analizzare: " + sentenceDesc);
    }

    // mostra errore relativo alla validazione della struttura della frase
    public static void showValidationError() {
        System.err.println("ERRORE: La struttura della frase analizzata non è valida");
    }

    // mostra errore relativo alla lingua utilizzata
    public static void showLanguageError() {
        System.err.println("ERRORE: La lingua utilizzata non è supportata");
    }

    // mostra l'albero sintattico della frase
    public static void showSyntacticTree() {
        String input = "";

        do {
            System.out.print("Vuoi vedere il Syntactic Tree? y/n: ");
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.equals("y") && !input.equals("n"));

        if (input.equals("y")) {
            System.out.println("Albero sintattico generato:");
            System.out.println(SentenceProcessor.getSyntacticTree());
        }
    }

    // mostra errore se la frase generata è tossica
    public static void showToxicityError() {
        System.err.println("ERRORE: La frase generata ha un livello di tossicità non accettabile");
    }

    public static void showToxicityResults(double toxicityLevel) {
        System.out.println("Livello di tossicità della frase generata: " + toxicityLevel);
    }
}