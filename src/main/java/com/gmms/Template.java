package com.gmms;
// Tommaso Silvestrin

import java.util.Map;
import java.util.Random;
import java.util.List;

public class Template {

    // costanti per mappare i tipi di parola a interi
    public static final int NOUN_INDEX = 0;
    public static final int VERB_INDEX = 1;
    public static final int ADJECTIVE_INDEX = 2;

    private static final Map<Integer, List<int[]>> combinations = Map.of(
        1, List.of (
            new int [] {NOUN_INDEX},
            new int [] {VERB_INDEX},
            new int [] {ADJECTIVE_INDEX}
        ),
        2, List.of (
            new int [] {NOUN_INDEX, VERB_INDEX},
            new int [] {NOUN_INDEX, ADJECTIVE_INDEX},
            new int [] {ADJECTIVE_INDEX, NOUN_INDEX}
        ),
        3, List.of (
            new int [] {NOUN_INDEX, VERB_INDEX, ADJECTIVE_INDEX},
            new int [] {NOUN_INDEX, ADJECTIVE_INDEX, VERB_INDEX},
            new int [] {ADJECTIVE_INDEX, NOUN_INDEX, VERB_INDEX},
            new int [] {ADJECTIVE_INDEX, NOUN_INDEX, ADJECTIVE_INDEX}
        )
    );

    public String templateDesc; // descrizione del template nella forma con [NOUN], [VERB], [ADJECTIVE]
    public int[] templateWords; // array di interi che indica quanti nomi, verbi e aggettivi sono richiesti nel template

    // costruttore che riceve una struttura di frase casuale e crea un oggetto
    private Template(String randomStructure) {

        templateWords = new int[3];
        StringBuilder temporaryTemplate = new StringBuilder(); // template in formazione
        Random rand = new Random();

        String[] parts = randomStructure.split("%s", -1);

        for (int i = 0; i < parts.length - 1; i++) {
            
            temporaryTemplate.append(parts[i]);
            int numberOfWords = rand.nextInt(3) + 1; // genera un numero casuale tra 1 e 3 per determinare la dimensione della combinazione
            int [] alternatives = combinations.get(numberOfWords).get(rand.nextInt(combinations.get(numberOfWords).size()));

            for (int j = 0; j < alternatives.length; j++) {
                int wordIndex = alternatives[j]; // ottiene il tipo di parola dalla combinazione casuale
                templateWords[wordIndex]++; // incrementa il conteggio del tipo di parola
                
                switch (wordIndex) {
                    case NOUN_INDEX:
                        temporaryTemplate.append("[NOUN] ");
                        break;
                    case VERB_INDEX:
                        temporaryTemplate.append("[VERB] ");
                        break;
                    case ADJECTIVE_INDEX:
                        temporaryTemplate.append("[ADJECTIVE] ");
                        break;
                }
            }
        }

        temporaryTemplate.append(parts[parts.length - 1]);
        this.templateDesc = temporaryTemplate.toString();
    }

    public static Template create(String randomStructure) {
        return new Template(randomStructure);
    }
}