package com.gmms;
// Tommaso Silvestrin

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Rappresenta un modello (template) di frase.
 * Un template è composto da una stringa formattabile (templateDesc) e da un array
 * che specifica il tipo di parola richiesta per ogni segnaposto (templateWords).
 *
 * Esempio di struttura in input: "The [NOUN] [VERB] the [ADJECTIVE] [NOUN]."
 * -> templateDesc: "The %s %s the %s %s."
 * -> templateWords: [NOUN, VERB, ADJECTIVE, NOUN]
 */
public class Template {

    /**
     * Enumerazione per rappresentare i tipi di parole che possono essere inseriti nel template.
     * Migliora la leggibilità e la sicurezza del codice rispetto all'uso di interi.
     */
    public enum WordType {
        NOUN, VERB, ADJECTIVE, UNKNOWN // Per gestire eventuali segnaposto non riconosciuti
    }

    private final String templateDesc;
    private final WordType[] templateWords;

    /**
     * Costruttore della classe Template.
     * Processa una stringa di struttura per inizializzare gli attributi della classe.
     *
     * @param randomStructure La stringa contenente la struttura del template,
     * con segnaposto come [NOUN], [VERB], etc.
     */
    public Template(String randomStructure) {
        List<WordType> wordTypesList = new ArrayList<>();
        
        // Pattern per trovare tutti i segnaposto nel formato [TIPO]
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(randomStructure);

        // Itera su tutti i segnaposto trovati
        while (matcher.find()) {
            // Estrae il tipo di parola (es. "NOUN") dal segnaposto (es. "[NOUN]")
            String type = matcher.group(1).toUpperCase();
            try {
                wordTypesList.add(WordType.valueOf(type));
            } catch (IllegalArgumentException e) {
                // Se il tipo non è riconosciuto, lo aggiunge come UNKNOWN
                wordTypesList.add(WordType.UNKNOWN);
                System.err.println("Attenzione: tipo di parola non riconosciuto '" + type + "'");
            }
        }

        // Converte la lista in un array
        this.templateWords = wordTypesList.toArray(new WordType[0]);

        // Sostituisce tutti i segnaposto con "%s" per creare una stringa formattabile
        this.templateDesc = randomStructure.replaceAll("\\[(.*?)\\]", "%s");
    }

    /**
     * Restituisce la descrizione del template formattabile.
     * @return Una stringa con segnaposto "%s" pronta per essere usata con String.format().
     */
    public String getTemplateDesc() {
        return templateDesc;
    }

    /**
     * Restituisce l'array dei tipi di parole richiesti dal template.
     * @return Un array di WordType che specifica l'ordine e il tipo delle parole necessarie.
     */
    public WordType[] getTemplateWords() {
        return templateWords;
    }

    /**
     * Restituisce il numero di parole necessarie per riempire il template.
     * Corrisponde alla lunghezza dell'array templateWords.
     * @return il conteggio delle parole.
     */
    public int getWordCount() {
        return templateWords.length;
    }
}