package com.gmms;

import java.util.Map;
import java.util.List;

// --- SINGLETON ---
public final class SentenceController {
    private static final SentenceController instance = new SentenceController();
    private Sentence inputSentence; // frase in input dall'utente
    private Sentence nonsenseSentence; // frase in output generata
    private Sentence currentSentence; // frase in utilizzo

    // costruttore
    private SentenceController() {
    }

    // per inizializzare un singleton
    public static SentenceController getInstance() {
        return instance;
    }

    // crea una nuova Sentence
    public void createSentence(String sentenceDesc) {
        Sentence s = new Sentence(sentenceDesc);

        if (inputSentence == null) {
            // prima chiamata: l'input "buono"
            inputSentence = s;
            currentSentence = inputSentence;
            return;
        }

        // chiamate successive: "nonsense/rigenerate"
        nonsenseSentence = s;
        currentSentence = nonsenseSentence;
    }

    // chiama il Generator per generare la frase nonsense
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

    // metodi di supporto

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

    // metodo solo per WebController
    public void resetVar() {
        inputSentence = null;
        nonsenseSentence = null;
        currentSentence = null;
    }

    // chiamate ad altri sottosistemi

    // chiama l'IOController per mostrare la frase in utilizzo
    public void displayProcess(int flag) {
        IOController.getInstance().displaySentence(currentSentence.getSentenceDesc(), flag);
    }

    // chiama l'Analyzer per il processo di analisi
    public void analysisProcess() throws Exception {
        Analyzer.analyzeSentence(currentSentence.getSentenceDesc());
    }

    // chiama il Validator per la validazione del SentenceTree
    public boolean validationProcess() {
        return Validator.getInstance().validateSentenceStructure(currentSentence.getSentenceTree());
    }

    // chiama il Validator per il processo di verifica della tossicità
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