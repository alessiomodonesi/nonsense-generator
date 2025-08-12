package com.gmms;

// Diego Marchini
public class Sentence {

    private String sentenceDesc;
    private SyntacticTree syntacticTree;

    // costruttore
    public Sentence(String sentenceDesc) {
        this.sentenceDesc = sentenceDesc;
    }

    public void setSentenceTree(SyntacticTree syntacticTree) {
        this.syntacticTree = syntacticTree;
    }

    public String getSentenceDesc() {
        return this.sentenceDesc;
    }

    public SyntacticTree getSentenceTree() {
        return this.syntacticTree;
    }

}
