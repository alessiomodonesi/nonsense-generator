package com.gmms;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SyntacticNodeTest {

    @Test
    @DisplayName("addChild: imposta la dependencyLabel e aggiunge il figlio all'elenco")
    void addChild_setsDependencyAndAddsChild() {
        SyntacticNode root = new SyntacticNode("Mangia", "mangiare", "VERB");
        root.setDependencyLabel("ROOT");

        SyntacticNode child = new SyntacticNode("Mario", "mario", "NOUN");
        root.addChild(child, "nsubj");

        assertEquals(1, root.getnode().size(), "Il root deve avere un figlio");
        assertSame(child, root.getnode().get(0), "Il figlio deve essere lo stesso oggetto");
        assertEquals("nsubj", child.getDependencyLabel(), "La dependencyLabel del figlio deve essere impostata");
    }

    @Test
    @DisplayName("printTree/toString: formato e indentazione corretti")
    void printTree_formatsCorrectly() {
        // root
        SyntacticNode root = new SyntacticNode("Mangia", "mangiare", "VERB");
        root.setDependencyLabel("ROOT");

        // figli: Mario (nsubj) e pizza (obj) con determinante 'una' (det)
        SyntacticNode mario = new SyntacticNode("Mario", "mario", "NOUN");
        SyntacticNode pizza = new SyntacticNode("pizza", "pizza", "NOUN");
        SyntacticNode una = new SyntacticNode("una", "uno", "DET");

        root.addChild(mario, "nsubj");
        root.addChild(pizza, "obj");
        pizza.addChild(una, "det");

        String expected = ""
                + "Mangia [VERB | ROOT]\n"
                + "  Mario [NOUN | nsubj]\n"
                + "  pizza [NOUN | obj]\n"
                + "    una [DET | det]\n";

        assertEquals(expected, root.toString(), "L'albero stampato deve combaciare (indentazione 2 spazi per livello)");
    }

    @Test
    @DisplayName("Getters: text, lemma, partOfSpeech e lista figli")
    void getters_returnValues() {
        SyntacticNode node = new SyntacticNode("corre", "correre", "VERB");
        assertEquals("corre", node.getText());
        assertEquals("correre", node.getLemma());
        assertEquals("VERB", node.getPartOfSpeech());
        assertNotNull(node.getnode());
        assertTrue(node.getnode().isEmpty(), "All'inizio non ci sono figli");
    }

    @Test
    @DisplayName("Ordine figli: preserva l'ordine di inserimento")
    void children_orderIsPreserved() {
        SyntacticNode root = new SyntacticNode("vede", "vedere", "VERB");
        root.setDependencyLabel("ROOT");

        SyntacticNode a = new SyntacticNode("Marco", "Marco", "PROPN");
        SyntacticNode b = new SyntacticNode("lo", "egli", "PRON");
        SyntacticNode c = new SyntacticNode("oggi", "oggi", "ADV");

        root.addChild(a, "nsubj");
        root.addChild(b, "obj");
        root.addChild(c, "advmod");

        assertEquals("Marco", root.getnode().get(0).getText());
        assertEquals("lo", root.getnode().get(1).getText());
        assertEquals("oggi", root.getnode().get(2).getText());
    }

    @Test
    @DisplayName("printTree: se la dependencyLabel è null viene stampato 'null'")
    void printTree_handlesNullDependency() {
        SyntacticNode node = new SyntacticNode("Ciao", "ciao", "INTJ");
        // non settiamo la label
        String expected = "Ciao [INTJ | null]\n";
        assertEquals(expected, node.toString());
    }
}
