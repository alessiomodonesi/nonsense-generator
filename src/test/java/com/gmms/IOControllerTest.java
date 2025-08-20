package com.gmms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class IOControllerTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err; 
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams () {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams () {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("inputSentence deve leggere la frase data in input dall'utente")
    void testCorrectInputSentence () {
        String input = "Frase di input di test";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        String result = IOController.inputSentence();
        assertEquals(input, result);
    }

    @Test
    @DisplayName("showInputError deve mostrare un messaggio di errore per input non valido")
    void testShowInputError () {
        IOController.showInputError();
        assertTrue(errContent.toString().contains("ERRORE: L'input inserito non è valido"));   
    }

    @Test
    @DisplayName("displaySentence con flag 0 deve mostrare la frase da analizzare")
    void testDisplaySentence1 (int flag, String expectedOutput) {
        String sentence = "Frase input di test";
        IOController.displaySentence(sentence, 0);
        String expected = "\nFrase da analizzare: \"" + sentence + "\"";
        assertEquals(expected, outContent.toString().trim());
    }

    @Test
    @DisplayName("displaySentence con flag 1 deve mostrare la frase non-sense")
    void testDisplaySentence2 (int flag, String expectedOutput) {
        String sentence = "Frase non-sense di test";
        IOController.displaySentence(sentence, 1);
        String expected = "\nFrase non-sense: \"" + sentence + "\"";
        assertEquals(expected, outContent.toString().trim());
    }

    @Test
    @DisplayName("showValidationError deve mostrare un messaggio di errore per la validazione della struttura della frase")
    void testShowValidationError () {
        IOController.showValidationError();
        String expectedOutput = "\nERRORE: La struttura della frase analizzata non è valida\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @DisplayName("showSyntacticTree deve stampare l'albero se l'utente digita 'y'")
    void testShowSyntacticTree1 () {
        System.setIn(new ByteArrayInputStream("y\n".getBytes()));
        IOController.showSyntacticTree();
        assertTrue(outContent.toString().contains("Albero sintattico generato:"));
    }

    @Test
    @DisplayName("showSyntacticTree non deve stampare l'albero se l'utente digita 'n'")
    void testShowSyntacticTree2 () {
        System.setIn(new ByteArrayInputStream("yìn\n".getBytes()));
        IOController.showSyntacticTree();
        assertFalse(outContent.toString().contains("Albero sintattico generato:"));
    }

    @Test
    @DisplayName("showToxicityError deve stampare un messaggio di errore se la frase generata è tossica")
    void testShowToxicityError () {
        IOController.showToxicityError();
        assertTrue(errContent.toString().contains("ERRORE: La frase generata ha un livello di tossicità non accettabile"));
    }

    @Test
    @DisplayName("showToxicityResults deve mostrare i risultati della tossicità (label e valore)")
    void testShowToxicityResults () {
        IOController.showToxicityResults("Profanity", 0.23);
        String expectedOutput = "\nLivello di tossicità della frase generata: Profanity = 0.230";
        assertTrue(outContent.toString().contains(expectedOutput));
    }

}