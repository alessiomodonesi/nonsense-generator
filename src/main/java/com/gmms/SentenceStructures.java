package com.gmms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class SentenceStructures {
    private File structures; // file contenente le possibili strutture della frase nonsense
    private int dimension; // dimensione del file sopra citato

    // costruttore
    public SentenceStructures(String path) {
        this.dimension = 0;
        this.structures = new File(path);
        try {
            Scanner sc = new Scanner(structures);
            // scorre lungo il file e misura la dimensione
            while (sc.hasNextLine()) {
                sc.nextLine();
                this.dimension++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("\nFile SentenceStructures non trovato: " + e);
            System.exit(1);
        }
    }

    // metodo che restituisce una struttura selezionata in modo randomico dal file
    public String getRandomStructure() {
        String randomStructure = "";
        Random r = new Random();
        int selectedStructure = r.nextInt(dimension);

        try {
            Scanner sc = new Scanner(structures);
            while (selectedStructure > 0) {
                sc.nextLine();
                selectedStructure--;
            }
            randomStructure = sc.nextLine();
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("\nFile SentenceStructures non trovato: " + e);
            System.exit(1);
        }
        return randomStructure;
    }

    // metodo per il testing
    public int getDimension() {
        return this.dimension;
    }
}