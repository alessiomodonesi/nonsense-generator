package com.gmms;

public final class SentenceProcessor {
    @SuppressWarnings("unused")
    private static Sentence inputSentence;
    @SuppressWarnings("unused")
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
        // a seconda delle esigenze potrebbe anche solo trattarsi di inputSentence
        tempSentence.setSentenceTree(syntacticTree);
    }

    // resetta la sentence considerata in caso si debba far ripartire la generazione
    // dall'input utente
    public static void reset() {
        state = true;
    }

    // chiamate ad altri sottosistemi

    public static void displayProcess(int flag) {
        IOController.displaySentence(tempSentence.getSentenceDesc(), flag);
    }

    public static void analysisProcess() throws Exception {
        Analyzer.analyzeSentence(tempSentence.getSentenceDesc());
    }

    public static boolean validationProcess() {
        return Validator.validateSentenceStructure(tempSentence.getSentenceTree());
    }

    public static boolean toxicityProcess() throws Exception {
        return Validator.verifyToxicity(tempSentence.getSentenceDesc());
    }
}