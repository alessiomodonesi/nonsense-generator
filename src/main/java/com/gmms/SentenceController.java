package com.gmms;

import java.util.Map;
import java.util.List;

// --- SINGLETON ---
public final class SentenceController {
    private static final SentenceController instance = new SentenceController();

    @SuppressWarnings("unused")
    private Sentence inputSentence;

    @SuppressWarnings("unused")
    private Sentence nonsenseSentence;

    private Sentence currentSentence;
    private boolean state = true;

    // costruttore
    private SentenceController() {
    }

    // per inizializzare un singleton
    public static SentenceController getInstance() {
        return instance;
    }

    public void createSentence(String sentenceDesc) {
        currentSentence = new Sentence(sentenceDesc);
        if (state)
            inputSentence = currentSentence;
        else
            nonsenseSentence = currentSentence;

        state = false;
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

    // metodo solo per testing
    public String generateTemplate() {
        return currentSentence.getSentenceDesc();
    }

    public String getTemplateDesc() {
        return TemplateController.getInstance().getTemplateDesc();
    }

    // metodi di supporto non presenti nel design class model (metodi di
    // SentenceController)

    public void setSentenceTree(SyntacticNode syntacticTree) {
        // a seconda delle esigenze potrebbe anche solo trattarsi di inputSentence
        currentSentence.setSentenceTree(syntacticTree);
    }

    // resetta la sentence considerata in caso si debba far ripartire la generazione
    // dall'input utente
    public void resetSentenceState() {
        state = true;
    }

    // chiamate ad altri sottosistemi

    public void displayProcess(int flag) {
        IOController.getInstance().displaySentence(currentSentence.getSentenceDesc(), flag);
    }

    public void analysisProcess() throws Exception {
        Analyzer.analyzeSentence(currentSentence.getSentenceDesc());
    }

    public boolean validationProcess() {
        return Validator.validateSentenceStructure(currentSentence.getSentenceTree());
    }

    public boolean toxicityProcess() throws Exception {
        return Validator.verifyToxicity(currentSentence.getSentenceDesc());
    }
}