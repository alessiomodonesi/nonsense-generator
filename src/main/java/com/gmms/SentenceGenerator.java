package com.gmms;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

// --- SINGLETON ---
public final class SentenceGenerator {
    private static final SentenceGenerator instance = new SentenceGenerator();
    private String sentenceDesc;
    private Map<String, List<String>> fillingWords; // parole da inserire nel template

    // costruttore
    private SentenceGenerator() {
    }

    // per inizializzare un singleton
    public static SentenceGenerator getInstance() {
        return instance;
    }

    // generazione della frase nonsense a partire da template e
    // parole scelte da WordPicker
    public void generateSentence() {
        // modifiche fatte per rendere fattibile il testing
        if (sentenceDesc == null || !sentenceDesc.contains("template di test")) {
            getTemplateDesc();
            getWords();
        } else
            sentenceDesc = sentenceDesc.replaceFirst("template di test: ", "");
        // fine della correzione per il testing

        Random r = new Random();
        String[] wordsCategories = fillingWords.keySet().toArray(new String[fillingWords.keySet().size()]);

        // interi usati per scorrere lungo l'array sopra
        int categoryIndex = 0;
        int categoryTotalNumber = wordsCategories.length;

        // while annidato -> per ogni categoria di parola (cambiata ad ogni iterazione
        // del ciclo esterno) viene ricercata la parola indicante la
        // categoria (es. "[NOUN]") nella description e sostituita con un termine di
        // quella categoria scelto randomicamente dal dizionario (Map)
        while (categoryIndex < categoryTotalNumber) {
            String wordToBeReplaced = wordsCategories[categoryIndex];
            List<String> sameTypeWords = fillingWords.get(wordToBeReplaced);
            int dimension = sameTypeWords.size();

            while (dimension > 0) {
                // (gli # mancanti nel template)
                int index = r.nextInt(dimension);
                String wordToBeInserted = sameTypeWords.get(index);
                sentenceDesc = sentenceDesc.replaceFirst(wordToBeReplaced, wordToBeInserted);
                sentenceDesc = sentenceDesc.replaceAll("[\\[\\]]", ""); // rimuove le [] dalla frase nonsense
                sameTypeWords.remove(index);
                dimension--;
            }
            categoryIndex++;
        }
        createSentence();
    }

    // metodi di supporto

    private void createSentence() {
        SentenceController.getInstance().createSentence(sentenceDesc);
    }

    private void getTemplateDesc() {
        sentenceDesc = SentenceController.getInstance().getTemplateDesc();
    }

    private void getWords() {
        fillingWords = SentenceController.getInstance().getWords();
    }

    // metodi per il testing
    public void setSentenceDesc(String testTemplate) {
        sentenceDesc = testTemplate;
    }

    public void setFillingWords(Map<String, List<String>> testFillingWords) {
        fillingWords = testFillingWords;
    }

    // metodo solo per WebController
    public void resetVar() {
        sentenceDesc = new String();
        fillingWords = new HashMap<>();
    }
}