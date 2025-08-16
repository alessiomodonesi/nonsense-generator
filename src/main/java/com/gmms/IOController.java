package com.gmms;

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
        System.err.println("\nERRORE: L'input inserito non è valido\n");
    }

    public static void displaySentence(String sentenceDesc, int flag) {
        switch (flag) {
            case 0:
                System.out.println("\nFrase da analizzare: \"" + sentenceDesc + "\"");
                break;
            case 1:
                System.out.println("\nFrase non-sense: \"" + sentenceDesc + "\"");
                break;
            default:
                break;
        }

    }

    // mostra errore relativo alla validazione della struttura della frase
    public static void showValidationError() {
        System.err.println("\nERRORE: La struttura della frase analizzata non è valida\n");
    }

    // mostra errore relativo alla lingua utilizzata
    public static void showLanguageError() {
        System.err.println("\nERRORE: La lingua utilizzata non è supportata\n");
    }

    // mostra l'albero sintattico della frase
    public static void showSyntacticTree() {
        String input = "";

        do {
            System.out.print("\nVuoi vedere il Syntactic Tree? y/n: ");
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.equals("y") && !input.equals("n"));

        if (input.equals("y")) {
            System.out.println("\nAlbero sintattico generato:");
            System.out.println(SentenceProcessor.getSyntacticTree());
        }
    }

    // mostra errore se la frase generata è tossica
    public static void showToxicityError() {
        System.err.println("\nERRORE: La frase generata ha un livello di tossicità non accettabile\n");
    }

    public static void showToxicityResults(double toxicityLevel) {
        System.out.println("\nLivello di tossicità della frase generata: " + toxicityLevel);
    }
}