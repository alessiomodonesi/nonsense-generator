package com.gmms;

import java.util.List;
import java.util.Map;
import java.util.Random;

// --- SINGLETON ---
public final class TemplateGenerator {
    private static final TemplateGenerator instance = new TemplateGenerator();

    // costanti per mappare i tipi di parola a interi
    private static final int NOUN_INDEX = 0;
    private static final int VERB_INDEX = 1;
    private static final int ADJECTIVE_INDEX = 2;

    // mappa di possibili combinazioni seguendo la sintassi italiana
    private static final Map<Integer, List<int[]>> combinations = Map.of(
            1, List.of(
                    new int[] { NOUN_INDEX },
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

    // costruttore
    private TemplateGenerator() {
    }

    // per inizializzare un singleton
    public static TemplateGenerator getInstance() {
        return instance;
    }

    // genera e restituisce un nuovo oggetto Template
    public Template generateTemplate(SentenceStructures s) {
        // ottiene una struttura di frase casuale da sentenceStructures
        String randomStructure = s.getRandomStructure();
        int[] templateWords = new int[3];
        StringBuilder temporaryTemplate = new StringBuilder(); // template in formazione
        Random rand = new Random();
        String[] parts = randomStructure.split("%s", -1);

        for (int i = 0; i < parts.length - 1; i++) {
            temporaryTemplate.append(parts[i]);
            // genera un numero casuale tra 1 e 3 per determinare
            // la dimensione della combinazione
            int numberOfWords = rand.nextInt(3) + 1;
            List<int[]> combinationsForNumber = combinations.get(numberOfWords);
            int[] alternatives = combinationsForNumber.get(rand.nextInt(combinationsForNumber.size()));

            for (int j = 0; j < alternatives.length; j++) {
                // aggiungi uno spazio se non è il primo elemento
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
        String templateDesc = temporaryTemplate.toString();

        // crea un nuovo oggetto Template
        return new Template(templateDesc, templateWords);
    }
}
