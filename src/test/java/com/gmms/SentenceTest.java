package com.gmms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

class SentenceTest {

    @Test
    @DisplayName("Verifica il funzionamento del costruttore e del getter per la sentence")
    void testConstructorAndGetSentenceDesc() {
        String expectedDesc = "Questa è una frase di test";
        Sentence sentence = new Sentence(expectedDesc);

        assertEquals(expectedDesc, sentence.getSentenceDesc(),
                "Il metodo getSentenceDesc() deve restituire la descrizione passata al costruttore");
    }

    @Test
    @DisplayName("Verifica setter e getter del SyntacticTree")
    void testSetAndGetSentenceTree() {
        Sentence sentence = new Sentence("Frase con albero sintattico");

        // Creiamo un mock/fake per SyntacticNode
        SyntacticNode syntacticNode = new SyntacticNode("studia", "studiare", "ROOT");
        sentence.setSentenceTree(syntacticNode);

        assertNotNull(sentence.getSentenceTree(),
                "L'albero sintattico non dovrebbe essere null dopo setSentenceTree()");
        assertEquals(syntacticNode, sentence.getSentenceTree(),
                "Il metodo getSentenceTree() deve restituire l'oggetto impostato con setSentenceTree()");
    }

    @Test
    @DisplayName("Verifica la nullità iniziale del SyntacticTree")
    void testSentenceTreeInitiallyNull() {
        Sentence sentence = new Sentence("Frase senza albero");
        assertNull(sentence.getSentenceTree(),
                "Inizialmente l'albero sintattico dovrebbe essere null");
    }
}
