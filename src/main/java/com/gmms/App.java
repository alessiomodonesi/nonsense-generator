package com.gmms;

import java.io.IOException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        // init-time
        try {
            SystemDictionary.initializeDic();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // si può inserire in IOController
        }
        SentenceStructures s = new SentenceStructures();

        // internal-ssd INPUT phase
        String input = IOController.inputSentence();
        boolean validationRestart = false;
        boolean languageRestart = false;

        while (!validationRestart) {
            while (!Validator.verifySentence(input) || languageRestart == true) {
                if (languageRestart == false)
                    IOController.showInputError();

                languageRestart = false;
                input = IOController.inputSentence();
            }

            SentenceProcessor.createSentence(input);
            SentenceProcessor.displayProcess(0);

            // internal-ssd ANALYSIS phase
            try {
                SentenceProcessor.analysisProcess();
                validationRestart = SentenceProcessor.validationProcess();
            } catch (IOException e) {
                IOController.showLanguageError();
                languageRestart = true;
            }
        }

        IOController.showSyntacticTree();

        // internal-ssd TEMPLATE GENERATION phase
        TemplateGenerator generator = new TemplateGenerator(s);
        TemplateController controller = new TemplateController(generator);

        System.out.println("Template generato: " + controller.getTemplateDesc());
        System.out.println("Parole necessarie: " + Arrays.toString(controller.getWordCount()));

        // internal-ssd WORDS EXTRACTION phase

        // PROPOSTA MODIFICA
        // WordPicker.StartWordsExtraction(controller, 0);

        System.out.print("Parole scelte: ");
        WordPicker.StartWordsExtraction(controller, 0);
        System.out.println();
        // FINE PROPOSTA MODIFICA

        // internal-ssd SENTENCE GENERATION phase
        SentenceGenerator.generateSentenceDesc(controller);
        SentenceProcessor.displayProcess(1);

        // internal-ssd TOXICITY EVALUATION phase
        // SentenceProcessor.toxicityProcess();

        // internal-ssd DISPLAY SENTENCE phase
        // SentenceProcessor.displayProcess(1);
    }
}