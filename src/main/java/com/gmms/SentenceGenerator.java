package com.gmms;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Diego Marchini
public class SentenceGenerator {

    private static String sentenceDesc;
    private static Map<String, List<String>> fillingWords;

    private SentenceGenerator() {
    }

    // metodi di supporto non presenti nel design class model (metodi di
    // SentenceGenerator)
    public static void generateSentenceDesc(TemplateController controller) {
        getTemplateDesc(controller);
        getWords();
        Random r = new Random();

        /*
        // String[] wordsCategories = (String[]) (fillingWords.keySet()).toArray();// array contenente le categorie di
                                                                                // parole
                                                                                // ([noun],[verb],[adjective],...)
        String[] wordsCategories = fillingWords.keySet().toArray(new String[fillingWords.keySet().size()]);

        // interi usati per scorrere lungo l'array sopra
        int categoryIndex = 0;
        int categoryTotalNumber = wordsCategories.length;
        // while annidato -> per ogni categoria di parola (cambiata ad ogni iterazione
        // del ciclo esterno) viene ricercata la parola indicante la
        // categoria(es."[noun]") nella descriprion e sostituita con un termine di
        // quella categoria scelto randomicamente dal dizionario(Map)
        while (categoryIndex < categoryTotalNumber) {
            String wordToBeReplaced = wordsCategories[categoryIndex];
            List<String> sameTypeWords = fillingWords.get(wordToBeReplaced);
            int dimension = sameTypeWords.size();
            while (dimension > 0) {// basandosi sul fatto che il WordPicker ha scelto il corretto numero di
                                   // nomi/parole
                // (gli # mancanti nel template)
                int index = r.nextInt(dimension);
                String wordToBeInserted = sameTypeWords.get(index);
                sentenceDesc.replaceFirst(wordToBeReplaced, wordToBeInserted);
                sameTypeWords.remove(index);
                dimension--;
            }
            categoryIndex++;
        }
        */

        // Itera direttamente su ogni coppia "categoria -> lista di parole" nella mappa.
        for (Map.Entry<String, List<String>> entry : fillingWords.entrySet()) {
        
            String wordToBeReplaced = entry.getKey(); // La categoria, es: "[[noun]]"
            
            // IMPORTANTE: Creo una copia della lista per poter rimuovere le parole usate
            // senza modificare la mappa originale "fillingWords".
            List<String> sameTypeWords = new ArrayList<>(entry.getValue());
            
            int dimension = sameTypeWords.size();

            while (dimension > 0) { // Basandosi sul fatto che il WordPicker ha scelto il corretto numero di nomi/parole
                
                // Scegli una parola casuale dalla lista della categoria corrente
                int index = r.nextInt(dimension);
                String wordToBeInserted = sameTypeWords.get(index);
                
                // Sostituisci la prima occorrenza della categoria con la parola scelta
                sentenceDesc.replaceFirst(wordToBeReplaced, wordToBeInserted);
                
                // Rimuovi la parola usata dalla lista temporanea per non sceglierla di nuovo
                sameTypeWords.remove(index);
                dimension--;
            }
        }

        createSentence();
    }

    // metodi di supporto non presenti nel design class model (chiamate ad altri
    // sottosistemi)
    @SuppressWarnings("unused")
    private static void getTemplateDesc(TemplateController controller) {
        sentenceDesc = controller.getTemplateDesc();
    }

    @SuppressWarnings("unused")
    private static void getWords() {
        fillingWords = WordPicker.getWords();
    }

    private static void createSentence() {
        SentenceProcessor.createSentence(sentenceDesc);
    }

}