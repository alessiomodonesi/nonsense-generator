package com.gmms;
// Tommaso Silvestrin

import java.util.Map;
import java.util.Random;
import java.util.List;

public class Template {
    // costanti per mappare i tipi di parola a interi
    private static final int NOUN_INDEX = 0;
    private static final int VERB_INDEX = 1;
    private static final int ADJECTIVE_INDEX = 2;

    private static final Map<Integer, List<int[]>> combinations = Map.of(
            1, List.of(
                    new int[] { NOUN_INDEX },
                    new int[] { VERB_INDEX },
                    new int[] { ADJECTIVE_INDEX }),
            2, List.of(
                    new int[] { NOUN_INDEX, VERB_INDEX },
                    new int[] { NOUN_INDEX, ADJECTIVE_INDEX },
                    new int[] { ADJECTIVE_INDEX, NOUN_INDEX }),
            3, List.of(
                    new int[] { NOUN_INDEX, VERB_INDEX, ADJECTIVE_INDEX },
                    new int[] { NOUN_INDEX, ADJECTIVE_INDEX, VERB_INDEX },
                    new int[] { ADJECTIVE_INDEX, NOUN_INDEX, VERB_INDEX },
                    new int[] { ADJECTIVE_INDEX, NOUN_INDEX, ADJECTIVE_INDEX }));

    public String templateDesc; // descrizione del template nella forma con [NOUN], [VERB], [ADJECTIVE]
    public int[] templateWords; // array di interi che indica quanti nomi, verbi e aggettivi sono richiesti nel
                                // template

    // costruttore che riceve una struttura di frase casuale e crea un oggetto
    private Template(String randomStructure) {

        templateWords = new int[3];
        StringBuilder temporaryTemplate = new StringBuilder(); // template in formazione
        Random rand = new Random();

        String[] parts = randomStructure.split("%s", -1);

        for (int i = 0; i < parts.length - 1; i++) {

            temporaryTemplate.append(parts[i]);
            int numberOfWords = rand.nextInt(3) + 1; // genera un numero casuale tra 1 e 3 per determinare la dimensione
                                                     // della combinazione
            List<int[]> combinationsForNumber = combinations.get(numberOfWords);
            int[] alternatives = combinationsForNumber.get(rand.nextInt(combinationsForNumber.size()));

            for (int j = 0; j < alternatives.length; j++) {

                // Aggiungi uno spazio se non è il primo elemento
                if (j > 0)
                    temporaryTemplate.append(" ");

                int wordIndex = alternatives[j]; // ottiene il tipo di parola
                templateWords[wordIndex]++; // incrementa il conteggio

                switch (wordIndex) {
                    case NOUN_INDEX:
                        temporaryTemplate.append("[NOUN]");
                        break;
                    case VERB_INDEX:
                        temporaryTemplate.append("[VERB]");
                        break;
                    case ADJECTIVE_INDEX:
                        temporaryTemplate.append("[ADJECTIVE]");
                        break;
                }
            }
        }

        temporaryTemplate.append(parts[parts.length - 1]);
        templateDesc = temporaryTemplate.toString();
    }

    public static Template create(String randomStructure) {
        return new Template(randomStructure);
    }
}