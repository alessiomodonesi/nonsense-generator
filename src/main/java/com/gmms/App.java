package com.gmms;

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
        String input;

        while (true) {
            // internal-ssd INPUT phase
            input = IOController.inputSentence();
            while (!Validator.verifySentence(input)) {
                IOController.showInputError();
                input = IOController.inputSentence();
            }

            SentenceProcessor.createSentence(input);
            SentenceProcessor.displayProcess(0);

            // internal-ssd ANALYSIS phase
            SentenceProcessor.analysisProcess();

            // il validationProcess ora determina se continuare o ricominciare
            if (!SentenceProcessor.validationProcess()) {
                IOController.showValidationError();
                continue;
            }

            IOController.showSyntacticTree();

            // internal-ssd TEMPLATE GENERATION phase
            TemplateGenerator generator = new TemplateGenerator(s);
            TemplateController controller = new TemplateController(generator);
            System.out.println("Template generato: " + controller.getTemplateDesc());
            System.out.println("\nParole necessarie: " + Arrays.toString(controller.getWordCount()));

            do {
                // internal-ssd WORDS EXTRACTION phase
                System.out.print("\nParole scelte: ");
                try{
                    WordPicker.startWordsExtraction(controller);
                }catch(Exception e){
                    System.out.println("Messaggio di errore : "  +  e);
                }

                // internal-ssd SENTENCE GENERATION phase
                SentenceGenerator.generateSentenceDesc(controller);

                // internal-ssd TOXICITY EVALUATION phase
                // se la frase è tossica, ricomincia il ciclo principale
                if (!SentenceProcessor.toxicityProcess())
                    continue;
                break;
            } while (true);

            // se arrivi qui, tutto è andato a buon fine, esci dal ciclo
            break;
        }

        // internal-ssd DISPLAY SENTENCE phase
        SentenceProcessor.displayProcess(1);
    }
}