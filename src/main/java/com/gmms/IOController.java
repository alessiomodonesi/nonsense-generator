package com.gmms;

import java.util.Scanner;

// --- SINGLETON ---
public final class IOController {
    private static final IOController instance = new IOController();
    private Scanner scanner = new Scanner(System.in);

    // costruttore
    private IOController() {
    }

    // per inizializzare un singleton
    public static IOController getInstance() {
        return instance;
    }

    // metodo solo per testing
    public void setScannerForTesting(Scanner newScanner) {
        scanner = newScanner;
    }

    // riceve una frase e avvia il processo di validazione chiamando il Validator
    public String inputSentence() {
        System.out.print("Inserisci una frase: ");
        String input = scanner.nextLine();
        return input;
    }

    // mostra errore per input non valido
    public void showInputError() {
        System.err.println("\nERRORE: L'input inserito non è valido\n");
    }

    public void displaySentence(String sentenceDesc, int flag) {
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
    public void showValidationError() {
        System.err.println("\nERRORE: La struttura della frase analizzata non è valida\n");
    }

    // mostra l'albero sintattico della frase
    public void showSyntacticTree() {
        String input = "";

        do {
            System.out.print("\nVuoi vedere il Syntactic Tree? y/n: ");
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.equals("y") && !input.equals("n"));

        if (input.equals("y")) {
            System.out.println("\nAlbero sintattico generato:");
            System.out.println(SentenceController.getInstance().getSyntacticTree());
        }
    }

    // mostra errore se la frase generata è tossica
    public void showToxicityError() {
        System.err.println("\nERRORE: La frase generata ha un livello di tossicità non accettabile");
    }

    public void showToxicityResults(String toxicityLabel, double toxicityLevel) {
        String roundedLevel = String.format("%.3f", toxicityLevel);
        System.out.println(
                "\nLivello di tossicità della frase generata: " + toxicityLabel + " = " + roundedLevel);
    }
}