package com.gmms;

import java.util.Map;
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
    public static void generateSentenceDesc() {
        // this.getTemplateDesc();
        // this.getWords();
        Random r = new Random();
        String[] wordsCategories = (String[]) (fillingWords.keySet()).toArray();// array contenente le categorie di
                                                                                // parole
                                                                                // ([noun],[verb],[adjective],...)
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

    public static void createSentence() {
        SentenceProcessor.createSentence(sentenceDesc);
    }

}