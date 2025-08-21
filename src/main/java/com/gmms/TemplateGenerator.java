package com.gmms;

public class TemplateGenerator {

    private final SentenceStructures s = new SentenceStructures(
            "./src/main/java/com/gmms/resources/SentenceStructures.txt");

    // genera e restituisce un nuovo oggetto Template
    public Template generateTemplate() {
        // ottiene una struttura di frase casuale da sentenceStructures
        String randomStructure = s.getRandomStructure();

        // crea un nuovo oggetto Template
        return Template.create(randomStructure);
    }
}
