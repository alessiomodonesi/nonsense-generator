package com.gmms;

import java.util.Arrays;

// -- STATIC ---
public final class AppController {
    // costruttore
    private AppController() {
    }

    public static void start() throws Exception {
        String input;

        while (true) {
            boolean backToStart = false;

            // --- INPUT PHASE ---
            input = getValidSentence();
            SentenceController.getInstance().createSentence(input);
            SentenceController.getInstance().displayProcess(0);

            // --- ANALYSIS PHASE ---
            SentenceController.getInstance().analysisProcess();

            if (!SentenceController.getInstance().validationProcess()) {
                IOController.getInstance().showValidationError();
                continue; // torna al ciclo while esterno
            }

            IOController.getInstance().showSyntacticTree();

            // --- TEMPLATE GENERATION PHASE ---
            TemplateController controller = createTemplateController();

            do {
                try {
                    // --- WORDS EXTRACTION PHASE ---
                    System.out.print("\nParole scelte: ");
                    WordPicker.getInstance().startWordsExtraction(controller);

                    // --- SENTENCE GENERATION PHASE ---
                    SentenceGenerator.getInstance().generateSentenceDesc(controller);

                    // --- TOXICITY EVALUATION PHASE ---
                    if (!SentenceController.getInstance().toxicityProcess()) {
                        continue; // ricomincia il ciclo interno
                    }
                    break; // esce dal ciclo interno se tutto è ok
                } catch (RetryInputException e) {
                    // RESET AND RESTART
                    SentenceController.getInstance().resetSentenceState();
                    WordPicker.getInstance().resetNumOfRetries();
                    System.out.println(e.getMessage());
                    backToStart = true;
                    break; // esce dal ciclo interno, ma segna restart
                }
            } while (true);

            if (!backToStart)
                break; // esce dal ciclo principale
        }

        // --- DISPLAY SENTENCE PHASE ---
        SentenceController.getInstance().displayProcess(1);
    }

    // --- Metodi di supporto ---

    private static String getValidSentence() {
        String input;
        do {
            input = IOController.getInstance().inputSentence();
            if (!Validator.verifySentence(input)) {
                IOController.getInstance().showInputError();
            }
        } while (!Validator.verifySentence(input));
        return input;
    }

    private static TemplateController createTemplateController() {
        TemplateController controller = new TemplateController();

        System.out.println("Template generato: " + controller.getTemplateDesc());
        System.out.println("\nParole necessarie: " + Arrays.toString(controller.getWordCount()));

        return controller;
    }
}
