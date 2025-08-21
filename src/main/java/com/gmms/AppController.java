package com.gmms;

import java.util.Arrays;

public class AppController {
    private static AppController instance = null;

    private AppController() {
    }

    // per inizializzare un singleton
    public static synchronized AppController getInstance() {
        if (instance == null)
            instance = new AppController();
        return instance;
    }

    public void start() throws Exception {
        String input;

        while (true) {
            boolean backToStart = false;

            // --- INPUT PHASE ---
            input = getValidSentence();
            SentenceProcessor.createSentence(input);
            SentenceProcessor.displayProcess(0);

            // --- ANALYSIS PHASE ---
            SentenceProcessor.analysisProcess();

            if (!SentenceProcessor.validationProcess()) {
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
                    WordPicker.startWordsExtraction(controller);

                    // --- SENTENCE GENERATION PHASE ---
                    SentenceGenerator.generateSentenceDesc(controller);

                    // --- TOXICITY EVALUATION PHASE ---
                    if (!SentenceProcessor.toxicityProcess()) {
                        continue; // ricomincia il ciclo interno
                    }
                    break; // esce dal ciclo interno se tutto è ok
                } catch (RetryInputException e) {
                    // RESET AND RESTART
                    SentenceProcessor.resetSentenceState();
                    WordPicker.resetNumOfRetries();
                    System.out.println(e.getMessage());
                    backToStart = true;
                    break; // esce dal ciclo interno, ma segna restart
                }
            } while (true);

            if (!backToStart)
                break; // esce dal ciclo principale
        }

        // --- DISPLAY SENTENCE PHASE ---
        SentenceProcessor.displayProcess(1);
    }

    // --- Metodi di supporto ---

    private String getValidSentence() {
        String input;
        do {
            input = IOController.inputSentence();
            if (!Validator.verifySentence(input)) {
                IOController.showInputError();
            }
        } while (!Validator.verifySentence(input));
        return input;
    }

    private TemplateController createTemplateController() {
        TemplateGenerator generator = new TemplateGenerator();
        TemplateController controller = new TemplateController(generator);

        System.out.println("Template generato: " + controller.getTemplateDesc());
        System.out.println("\nParole necessarie: " + Arrays.toString(controller.getWordCount()));

        return controller;
    }
}
