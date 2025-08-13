package com.gmms;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        // questa parte va nel IOController
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci una frase:");
        String input = scanner.nextLine();
        scanner.close();

        // chiamate di SentenceProcessor
        String syntacticTree = Analyzer.analyzeSentence(input);
        System.out.println(syntacticTree);
        Validator.verifyToxicity(input);
    }
}
