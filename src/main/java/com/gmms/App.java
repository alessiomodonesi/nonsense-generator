package com.gmms;

public class App {
    public static void main(String[] args) throws Exception {
        // init-time
        //SystemDictionary.initializeDic();
        // SentenceStructures s = new SentenceStructures();

        // internal-ssd INPUT phase
        String input = IOController.inputSentence();
        boolean validationRestart = false;

        while (!validationRestart) {
            while (!Validator.verifySentence(input)) {
                IOController.showInputError();
                input = IOController.inputSentence();
            }

            SentenceProcessor.createSentence(input);
            SentenceProcessor.displayProcess();

            // internal-ssd ANALYSIS phase
            SentenceProcessor.analysisProcess();
            validationRestart = SentenceProcessor.validationProcess();
        }

        IOController.showSyntacticTree();
        //WordPicker.StartWordsExtraction(0); per testarlo dovete far inizializzare il dizionario sennò da errore perchè gli elementi sono a null
        // internal-ssd TEMPLATE GENERATION phase
        // internal-ssd WORDS EXTRACTION phase
        // internal-ssd SENTENCE GENERATION phase
        // internal-ssd TOXICITY EVALUATION phase
        // internal-ssd DISPLAY SENTENCE phase

    }
}