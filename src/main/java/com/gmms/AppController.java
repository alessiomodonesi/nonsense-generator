package com.gmms;

import java.util.Arrays;

// -- SINGLETON ---
public class AppController {
    private static final AppController instance = new AppController();

    // costruttore
    private AppController() {
    }

    // per inizializzare un singleton
    public static AppController getInstance() {
        return instance;
    }

    public void start() throws Exception {
        String input;

        while (true) {
            boolean backToStart = false;

            // --- INPUT PHASE ---
            input = getValidSentence();
            SentenceController.createSentence(input);
            SentenceController.displayProcess(0);

            // --- ANALYSIS PHASE ---
            SentenceController.analysisProcess();

            if (!SentenceController.validationProcess()) {
                IOController.showValidationError();
                continue; // torna al ciclo while esterno
            }

            IOController.showSyntacticTree();

            // --- TEMPLATE GENERATION PHASE ---
            TemplateController controller = createTemplateController();

            do {
                try {
                    // --- WORDS EXTRACTION PHASE ---
                    System.out.print("\nParole scelte: ");
                    WordPicker.getInstance().startWordsExtraction(controller);

                    // --- SENTENCE GENERATION PHASE ---
                    SentenceGenerator.generateSentenceDesc(controller);

                    // --- TOXICITY EVALUATION PHASE ---
                    if (!SentenceController.toxicityProcess()) {
                        continue; // ricomincia il ciclo interno
                    }
                    break; // esce dal ciclo interno se tutto è ok
                } catch (RetryInputException e) {
                    // RESET AND RESTART
                    SentenceController.resetSentenceState();
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
        SentenceController.displayProcess(1);
    }

    // --- Metodi di supporto ---

    private String getValidSentence() {
        String input;
        do {
            input = IOController.inputSentence();
            if (!Validator.getInstance().verifySentence(input)) {
                IOController.showInputError();
            }
        } while (!Validator.getInstance().verifySentence(input));
        return input;
    }

    private TemplateController createTemplateController() {
        TemplateController controller = new TemplateController();

        System.out.println("Template generato: " + controller.getTemplateDesc());
        System.out.println("\nParole necessarie: " + Arrays.toString(controller.getWordCount()));

        return controller;
    }
}
