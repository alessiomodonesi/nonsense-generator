package com.gmms;
// Tommaso Silvestrin

public class TemplateGenerator {

    private final SentenceStructures sentenceStructures;

    public TemplateGenerator(SentenceStructures sentenceStructures) {
        this.sentenceStructures = sentenceStructures;
    }

    // genera e restituisce un nuovo oggetto Template
    public Template generateTemplate() {

        // ottiene una struttura di frase casuale da sentenceStructures
        String randomStructure = sentenceStructures.getRandomStructure();
        
        // crea un nuovo oggetto Template
        Template newTemplate = Template.create(randomStructure);
        
        return newTemplate;
    }
}