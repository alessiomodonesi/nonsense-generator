package com.gmms;
// Tommaso Silvestrin

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {

    // costanti per mappare i tipi di parola a interi
    public static final int NOUN_TYPE = 0;
    public static final int VERB_TYPE = 1;
    public static final int ADJECTIVE_TYPE = 2;
    public static final int UNKNOWN_TYPE = -1;

    public String templateDesc; // descrizione del template con %s nei posti in cui andranno nomi, verbi, aggettivi
    public int[] templateWords; // array di interi che rappresenta i tipi di parola richiesti dal template in ogni posizione %s

    // costruttore che riceve una struttura di frase casuale e crea un oggetto Template
    public Template(String randomStructure) {
        List<Integer> wordTypesList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(randomStructure);

        while (matcher.find()) {
            String type = matcher.group(1).toUpperCase();
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
        }

        // converte la lista di interi in un array di int
        this.templateWords = new int[wordTypesList.size()];
        for (int i = 0; i < wordTypesList.size(); i++) {
            this.templateWords[i] = wordTypesList.get(i);
        }

        this.templateDesc = randomStructure.replaceAll("\\[(.*?)\\]", "%s");
    }
}