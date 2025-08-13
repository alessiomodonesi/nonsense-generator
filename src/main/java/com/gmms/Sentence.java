package com.gmms;

// Diego Marchini
import java.util.List;
import java.util.Map;

public class Sentence {

    private String sentenceDesc;
    private Map<String, List<String>> syntacticTree;

    // costruttore
    public Sentence(String sentenceDesc) {
        this.sentenceDesc = sentenceDesc;
    }

    public void setSentenceTree(Map<String, List<String>> syntacticTree) {
        this.syntacticTree = syntacticTree;
    }

    public String getSentenceDesc() {
        return this.sentenceDesc;
    }

    public Map<String, List<String>> getSentenceTree() {
        return this.syntacticTree;
    }
}
