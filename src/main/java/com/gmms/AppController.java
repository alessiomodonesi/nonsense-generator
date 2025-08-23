package com.gmms;

import java.util.Arrays;

// -- STATIC ---
public final class AppController {
    // alias statici ai singleton
    private static final IOController io = IOController.getInstance();
    private static final SentenceController sc = SentenceController.getInstance();
    private static final TemplateController tc = TemplateController.getInstance();
    private static final WordPicker wp = WordPicker.getInstance();

    // costruttore
    private AppController() {
    }

    public static void start() throws Exception {
        String input = new String();

        while (true) {
            boolean backToStart = false;

            // --- INPUT PHASE ---
            input = getValidSentence();
            sc.createSentence(input);
            sc.displayProcess(0);

            // --- ANALYSIS PHASE ---
            sc.analysisProcess();

            if (!sc.validationProcess()) {
                io.showValidationError();
                continue; // torna al ciclo while esterno
            }

            io.showSyntacticTree();

            // --- TEMPLATE GENERATION PHASE ---
            tc.generateTemplate();
            System.out.println("Template generato: " + tc.getTemplateDesc());
            System.out.println(
                    "\nParole necessarie: " + Arrays.toString(tc.getWordCount()));

            do {
                try {
                    // --- WORDS EXTRACTION PHASE ---
                    System.out.print("\nParole scelte: ");
                    wp.startWordsExtraction();

                    // --- SENTENCE GENERATION PHASE ---
                    sc.generateSentence();

                    // --- TOXICITY EVALUATION PHASE ---
                    if (!sc.toxicityProcess()) {
                        continue; // ricomincia il ciclo interno
                    }
                    break; // esce dal ciclo interno se tutto è ok
                } catch (RetryInputException e) {
                    // RESET AND RESTART
                    sc.resetSentenceState();
                    wp.resetNumOfRetries();
                    System.out.println(e.getMessage());
                    backToStart = true;
                    break; // esce dal ciclo interno, ma segna restart
                }
            } while (true);

            if (!backToStart)
                break; // esce dal ciclo principale
        }

        // --- DISPLAY SENTENCE PHASE ---
        sc.displayProcess(1);
    }

    // --- metodi di supporto ---

    private static String getValidSentence() {
        String input = new String();
        do {
            input = io.inputSentence();
            if (!Validator.verifySentence(input)) {
                io.showInputError();
            }
        } while (!Validator.verifySentence(input));
        return input;
    }
}
