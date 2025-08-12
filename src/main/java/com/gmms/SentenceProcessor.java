package com.gmms;

// Diego Marchini
public class SentenceProcessor {

    private Sentence inputSentence;
    private Sentence nonsenseSentence;
    private Sentence tempSentence;// variabile di supporto in attesa di adoperare il giusto pattern
    private boolean state;// variabile che uso al momento per differenziare tra le due istanze di sentence
                          // memorizzate
    // costruttore

    public SentenceProcessor() {
        this.state = false;
    }

    public void createSentence(String sentenceDesc) {
        if (state) {
            nonsenseSentence = new Sentence(sentenceDesc);
            tempSentence = nonsenseSentence;
        }

        else {
            inputSentence = new Sentence(sentenceDesc);
            tempSentence = inputSentence;
        }

        state = true;
    }

    public SyntacticTree getSyntacticTree() {
        return tempSentence.getSentenceTree();
    }
    // metodi di supporto non presenti nel design class model (metodi di
    // SentenceProcessor)

    public void setSentenceTree(SyntacticTree syntacticTree) {
        tempSentence.setSentenceTree(syntacticTree); // a seconda delle esigenze potrebbe anche solo trattarsi di
                                                     // inputSentence
    }

    // metodi di supporto non presenti nel design class model (chiamate ad altri
    // sottosistemi)

    public void displaySentence() {
        IOController.displaySentence(tempSentence.getSentenceDesc());
    }

    public void analyzeSentence() {
        Analyzer.analyzeSentence(tempSentence.getSentenceDesc());
    }

    public void validateSentenceStructure() {
        Validator.validateSentenceStructure(tempSentence.getSentenceTree());
    }

    public void verifyToxicity() {
        Validator.verifyToxicity(tempSentence.getSentenceDesc());
    }
}