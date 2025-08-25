package com.gmms;

public final class Sentence {
    private String sentenceDesc; // testo della frase
    private SyntacticNode syntacticTree; // albero sintattico della frase

    // costruttore
    public Sentence(String sentenceDesc) {
        this.sentenceDesc = sentenceDesc;
    }

    public void setSentenceTree(SyntacticNode syntacticTree) {
        this.syntacticTree = syntacticTree;
    }

    public String getSentenceDesc() {
        return this.sentenceDesc;
    }

    public SyntacticNode getSentenceTree() {
        return this.syntacticTree;
    }
}
