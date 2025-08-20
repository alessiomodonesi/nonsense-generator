package com.gmms;

public class TemplateGenerator {

    private final SentenceStructures sentenceStructures = new SentenceStructures();

    // genera e restituisce un nuovo oggetto Template
    public Template generateTemplate() {
        // ottiene una struttura di frase casuale da sentenceStructures
        String randomStructure = sentenceStructures.getRandomStructure();

        // crea un nuovo oggetto Template
        return Template.create(randomStructure);
    }
}
