package com.gmms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class SentenceStructures {

    private File structures;
    private int dimension;

    public SentenceStructures(String path) {
        dimension = 0;
        this.structures = new File(path);
        try {
            Scanner sc = new Scanner(structures);
            while (sc.hasNextLine()) {
                sc.nextLine(); // leggi la riga e sposta il "cursore" in avanti
                dimension++; // ora puoi incrementare il contatore
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("\nFile SentenceStructures non trovato: " + e);
            System.exit(1);
        }
    }

    public String getRandomStructure() {
        String randomStructure = "";
        Random r = new Random();
        int selectedStructure = r.nextInt(dimension);
        try {
            Scanner sc = new Scanner(structures);
            while (selectedStructure > 0) {
                sc.hasNextLine();
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
}