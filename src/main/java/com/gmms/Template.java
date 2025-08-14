package com.gmms;
// Tommaso Silvestrin

import java.util.Random;

public class Template {

    // costanti per mappare i tipi di parola a interi
    public static final int NOUN_INDEX = 0;
    public static final int VERB_INDEX = 1;
    public static final int ADJECTIVE_INDEX = 2;

    public String templateDesc; // descrizione del template nella forma con [NOUN], [VERB], [ADJECTIVE]
    public int[] templateWords; // array di interi che indica quanti nomi, verbi e aggettivi sono richiesti nel
                                // template

    // costruttore che riceve una struttura di frase casuale e crea un oggetto
    public Template(String randomStructure) {

        this.templateWords = new int[3];
        StringBuilder temporaryTemplate = new StringBuilder(); // template in formazione
        Random randomGenerator = new Random(); // usato per sostituire %s randomicamente con i tipi di parola

        String[] parts = randomStructure.split("%s", -1); // divide la struttura in parti usando %s come separatore

        for (int i = 0; i < parts.length - 1; i++) {
            temporaryTemplate.append(parts[i]);

            int randomType = randomGenerator.nextInt(3);

            this.templateWords[randomType]++; // aumenta il numero di parole del tipo randomico generato

            switch (randomType) {
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

        temporaryTemplate.append(parts[parts.length - 1]);
        this.templateDesc = temporaryTemplate.toString();
    }
}