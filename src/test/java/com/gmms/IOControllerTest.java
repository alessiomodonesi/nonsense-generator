package com.gmms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class IOControllerTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err; 
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams () {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams () {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("inputSentence deve leggere la frase data in input dall'utente")
    void IOControllerTest1 () {
        String s = "Frase di input di test";
        System.setIn(new ByteArrayInputStream(s.getBytes()));

        String result = IOController.inputSentence();
        assertEquals(s, result);
    }    

}