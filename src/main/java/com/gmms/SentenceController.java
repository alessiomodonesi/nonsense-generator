package com.gmms;

public final class SentenceController {
    @SuppressWarnings("unused")
    private static Sentence inputSentence;
    @SuppressWarnings("unused")
    private static Sentence nonsenseSentence;
    private static Sentence currentSentence; // variabile di supporto in attesa di adoperare il giusto pattern variabile
                                             // che uso al momento per differenziare tra le due istanze di sentence
                                             // memorizzate
    private static boolean state = true;

    // costruttore
    private SentenceController() {
    }

    public static void createSentence(String sentenceDesc) {
        currentSentence = new Sentence(sentenceDesc);
        if (state)
            inputSentence = currentSentence;
        else
            nonsenseSentence = currentSentence;

        state = false;
    }

    public static SyntacticNode getSyntacticTree() {
        return currentSentence.getSentenceTree();
    }

    // metodo solo per testing
    public static String getSentenceDesc() {
        return currentSentence.getSentenceDesc();
    }

    // metodi di supporto non presenti nel design class model (metodi di
    // SentenceController)

    public static void setSentenceTree(SyntacticNode syntacticTree) {
        // a seconda delle esigenze potrebbe anche solo trattarsi di inputSentence
        currentSentence.setSentenceTree(syntacticTree);
    }

    // resetta la sentence considerata in caso si debba far ripartire la generazione
    // dall'input utente
    public static void resetSentenceState() {
        state = true;
    }

    // chiamate ad altri sottosistemi

    public static void displayProcess(int flag) {
        IOController.displaySentence(currentSentence.getSentenceDesc(), flag);
    }

    public static void analysisProcess() throws Exception {
        Analyzer.analyzeSentence(currentSentence.getSentenceDesc());
    }

    public static boolean validationProcess() {
        return Validator.validateSentenceStructure(currentSentence.getSentenceTree());
    }

    public static boolean toxicityProcess() throws Exception {
        return Validator.verifyToxicity(currentSentence.getSentenceDesc());
    }
}