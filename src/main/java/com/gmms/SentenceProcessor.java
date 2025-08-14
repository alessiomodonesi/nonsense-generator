package com.gmms;
// Diego Marchini

public final class SentenceProcessor {

    private static Sentence inputSentence;
    private static Sentence nonsenseSentence;
    private static Sentence tempSentence; // variabile di supporto in attesa di adoperare il giusto pattern variabile
                                          // che uso al momento per differenziare tra le due istanze di sentence
                                          // memorizzate
    private static boolean state = true;

    // costruttore
    private SentenceProcessor() {
    }

    public static void createSentence(String sentenceDesc) {
        tempSentence = new Sentence(sentenceDesc);
        if (state) {
            inputSentence = tempSentence;
        } else {
            nonsenseSentence = tempSentence;
        }

        state = false;
    }

    public static SyntacticNode getSyntacticTree() {
        return tempSentence.getSentenceTree();
    }

    // metodi di supporto non presenti nel design class model (metodi di
    // SentenceProcessor)

    public static void setSentenceTree(SyntacticNode syntacticTree) {
        tempSentence.setSentenceTree(syntacticTree); // a seconda delle esigenze
        // potrebbe anche solo trattarsi di
        // inputSentence
    }

    // metodi di supporto non presenti nel design class model (chiamate ad altri
    // sottosistemi)

    public static void displayProcess() {
        IOController.displaySentence(tempSentence.getSentenceDesc());
    }

    public static void analysisProcess() throws Exception {
        Analyzer.analyzeSentence(tempSentence.getSentenceDesc());
    }

    public static boolean validationProcess() {
        return Validator.validateSentenceStructure(tempSentence.getSentenceTree());
    }

    public static void toxicityProcess() throws Exception {
        Validator.verifyToxicity(tempSentence.getSentenceDesc());
    }
}