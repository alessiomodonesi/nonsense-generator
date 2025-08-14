package com.gmms;
// Diego Marchini

public class Sentence {

    private String sentenceDesc;
    private SyntacticNode syntacticTree;

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
