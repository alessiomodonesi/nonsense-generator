package com.gmms;

import java.util.Map;
import java.util.List;

// --- SINGLETON ---
public final class SentenceController {
    private static final SentenceController instance = new SentenceController();
    private Sentence inputSentence;
    private Sentence nonsenseSentence;
    private Sentence currentSentence;

    // costruttore
    private SentenceController() {
    }

    // per inizializzare un singleton
    public static SentenceController getInstance() {
        return instance;
    }

    // crea una nuova Sentence
    public void createSentence(String desc) {
        Sentence s = new Sentence(desc);

        if (inputSentence == null) {
            // prima chiamata: è l'input "buono"
            inputSentence = s;
            currentSentence = inputSentence;
            return;
        }

        // chiamate successive: considerale "nonsense/rigenerate"
        nonsenseSentence = s;
        currentSentence = nonsenseSentence;
    }

    public void generateSentence() {
        SentenceGenerator.getInstance().generateSentence();
    }

    public SyntacticNode getSyntacticTree() {
        return currentSentence.getSentenceTree();
    }

    public Map<String, List<String>> getWords() {
        return WordPicker.getInstance().getWords();
    }

    public String getTemplateDesc() {
        return TemplateController.getInstance().getTemplateDesc();
    }

    // metodi di supporto non presenti nel design class model

    public void setSentenceTree(SyntacticNode syntacticTree) {
        // a seconda delle esigenze potrebbe anche solo trattarsi di inputSentence
        currentSentence.setSentenceTree(syntacticTree);
    }

    // torna a puntare alla frase di input
    public void resetSentenceState() {
        if (inputSentence != null) {
            currentSentence = inputSentence;
        }
    }

    // chiamate ad altri sottosistemi

    public void displayProcess(int flag) {
        IOController.getInstance().displaySentence(currentSentence.getSentenceDesc(), flag);
    }

    public void analysisProcess() throws Exception {
        Analyzer.analyzeSentence(currentSentence.getSentenceDesc());
    }

    public boolean validationProcess() {
        return Validator.getInstance().validateSentenceStructure(currentSentence.getSentenceTree());
    }

    public boolean toxicityProcess() throws Exception {
        return Validator.getInstance().verifyToxicity(currentSentence.getSentenceDesc());
    }

    // metodi solo per testing

    public String getSentenceDesc() {
        return currentSentence.getSentenceDesc();
    }

    public void hardResetForTests() {
        inputSentence = null;
        nonsenseSentence = null;
        currentSentence = null;
    }
}