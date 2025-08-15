package com.gmms;

import java.io.IOException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        // init-time
        try{SystemDictionary.initializeDic();}
        catch(Exception e){
            System.out.println(e.getMessage());//si può mettere in iocontroller in caso
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
            SentenceProcessor.displayProcess();

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
        System.out.println(Arrays.toString(controller.getWordCount()));
        System.out.println(controller.getTemplateDesc());

        // internal-ssd WORDS EXTRACTION phase
        WordPicker.StartWordsExtraction(controller, 0);
        // internal-ssd SENTENCE GENERATION phase
        // internal-ssd TOXICITY EVALUATION phase
        // internal-ssd DISPLAY SENTENCE phase
    }
}