package com.gmms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

// Diego Marchini
public class SentenceStructures {

    private File structures;
    private int dimension;

    public SentenceStructures() {
        dimension = 0;
        this.structures = new File("data/SentenceStructures.txt");
        try {
            Scanner sc = new Scanner(structures);
            while (sc.hasNextLine())
                dimension++;
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File SentenceStructures non trovato: " + e);
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
            System.out.println("File SentenceStructures non trovato: " + e);
            System.exit(1);
        }
        return randomStructure;
    }
}