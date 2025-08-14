package com.gmms;

import java.util.ArrayList;
import java.util.List;

// Rappresenta un singolo nodo nell'albero sintattico.
// Ogni nodo contiene le informazioni del token e una lista dei suoi figli.
public class SyntacticNode {
    private final String text; // parola contenuta nel nodo
    private final String lemma; // primitiva della parola
    private final String partOfSpeech; // ruolo della parola nella frase
    private String dependencyLabel; // relazione con il padre
    private final List<SyntacticNode> node; // singolo nodo dell'albero

    // Costruttore
    public SyntacticNode(String text, String lemma, String partOfSpeech) {
        this.text = text;
        this.lemma = lemma;
        this.partOfSpeech = partOfSpeech;
        this.node = new ArrayList<>();
    }

    // Metodo per aggiungere un nodo figlio
    public void addChild(SyntacticNode child, String depLabel) {
        child.setDependencyLabel(depLabel);
        this.node.add(child);
    }

    // Getter e Setter
    public String getText() {
        return text;
    }

    public String getLemma() {
        return lemma;
    }

    public List<SyntacticNode> getnode() {
        return node;
    }

    public void setDependencyLabel(String dependencyLabel) {
        this.dependencyLabel = dependencyLabel;
    }

    public String getDependencyLabel() {
        return dependencyLabel;
    }

    // Metodo per stampare l'albero in modo leggibile a partire da questo nodo.
    public String printTree(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent);
        sb.append(text).append(" [").append(partOfSpeech).append(" | ").append(dependencyLabel).append("]");
        sb.append("\n");
        for (SyntacticNode child : node)
            sb.append(child.printTree(indent + "  "));
        return sb.toString();
    }

    @Override
    public String toString() {
        return printTree("");
    }
}