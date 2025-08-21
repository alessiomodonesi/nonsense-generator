package com.gmms;

import java.util.Map;
import java.util.List;
import java.util.Random;

// --- SINGLETON ---
public final class SentenceGenerator {
    private static final SentenceGenerator instance = new SentenceGenerator();
    private String sentenceDesc;
    private Map<String, List<String>> fillingWords;

    private SentenceGenerator() {
    }

    // per inizializzare un singleton
    public static SentenceGenerator getInstance() {
        return instance;
    }

    // metodi di supporto non presenti nel design class model (metodi di
    // SentenceGenerator)
    public void generateSentence() {
        this.getTemplateDesc();
        this.getWords();
        Random r = new Random();

        String[] wordsCategories = fillingWords.keySet().toArray(new String[fillingWords.keySet().size()]);

        // interi usati per scorrere lungo l'array sopra
        int categoryIndex = 0;
        int categoryTotalNumber = wordsCategories.length;

        // while annidato -> per ogni categoria di parola (cambiata ad ogni iterazione
        // del ciclo esterno) viene ricercata la parola indicante la
        // categoria (es. "[noun]") nella description e sostituita con un termine di
        // quella categoria scelto randomicamente dal dizionario(Map)
        while (categoryIndex < categoryTotalNumber) {
            String wordToBeReplaced = wordsCategories[categoryIndex];
            List<String> sameTypeWords = fillingWords.get(wordToBeReplaced);
            int dimension = sameTypeWords.size();

            // basandosi sul fatto che il WordPicker ha scelto il corretto numero di
            // nomi/parole
            while (dimension > 0) {
                // (gli # mancanti nel template)
                int index = r.nextInt(dimension);
                String wordToBeInserted = sameTypeWords.get(index);
                sentenceDesc = sentenceDesc.replaceFirst(wordToBeReplaced, wordToBeInserted);
                sentenceDesc = sentenceDesc.replaceAll("[\\[\\]]", ""); // RIMUOVE LE [] DALLA FRASE NON SENSE
                sameTypeWords.remove(index);
                dimension--;
            }
            categoryIndex++;
        }
        this.createSentence();
    }

    // metodi di supporto non presenti nel design class model (chiamate ad altri
    // sottosistemi)
    private void getTemplateDesc() {
        sentenceDesc = SentenceController.getInstance().getTemplateDesc();
    }

    private void getWords() {
        fillingWords = SentenceController.getInstance().getWords();
    }

    private void createSentence() {
        SentenceController.getInstance().createSentence(sentenceDesc);
    }

}