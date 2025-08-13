package com.gmms;
// Tommaso Silvestrin

import java.util.ArrayList;
import java.util.List;

public class Template {

    // costanti per mappare i tipi di parola a interi
    public static final int NOUN_TYPE = 0;
    public static final int VERB_TYPE = 1;
    public static final int ADJECTIVE_TYPE = 2;
    public static final int UNKNOWN_TYPE = -1;

    public String templateDesc; // descrizione del template con %s nei posti in cui andranno nomi, verbi,
                                // aggettivi
    public int[] templateWords; // array di interi che rappresenta i tipi di parola richiesti dal template in
                                // ogni posizione %s
    // templateWords = [2, 1, 1] 2 nomi, 1 verbo, 1 aggettivo

    // costruttore che riceve una struttura di frase casuale e crea un oggetto
    // Template
    public Template(String randomStructure) {
        List<Integer> wordTypesList = new ArrayList<>();
        StringBuilder templateBuilder = new StringBuilder();

        int currentIndex = 0; // attuale posizione nella stringa
        int lastIndex = 0; // fine dell'ultimo segnaposto trovato

        while ((currentIndex = randomStructure.indexOf('[', currentIndex)) != -1) {

            // aggiunge la parte di stringa tra il segnaposto precedente e attuale
            templateBuilder.append(randomStructure, lastIndex, currentIndex);

            // cerca la [ di chiusura del segnaposto
            int endIndex = randomStructure.indexOf(']', currentIndex);

            // se non c'è ] la stringa non è valida
            if (endIndex == -1) {
                break;
            }

            // estrae il tipo di parola tra le parentesi quadre
            String type = randomStructure.substring(currentIndex + 1, endIndex).toUpperCase();

            switch (type) {
                case "NOUN":
                    wordTypesList.add(NOUN_TYPE);
                    break;
                case "VERB":
                    wordTypesList.add(VERB_TYPE);
                    break;
                case "ADJECTIVE":
                    wordTypesList.add(ADJECTIVE_TYPE);
                    break;
                default:
                    wordTypesList.add(UNKNOWN_TYPE);
                    break;
            }
            // aggiunge il segnaposto %s al template
            templateBuilder.append("%s");

            // aggiornamento indici
            currentIndex = endIndex + 1;
            lastIndex = currentIndex;
        }

        // aggiunge la parte finale della stringa dopo l'ultimo segnaposto
        templateBuilder.append(randomStructure.substring(lastIndex));

        // imposta la descrizione del template
        this.templateDesc = templateBuilder.toString();

        // converte la lista nell'array di interi
        this.templateWords = new int[wordTypesList.size()];
        for (int i = 0; i < wordTypesList.size(); i++) {
            this.templateWords[i] = wordTypesList.get(i);
        }
    }
}