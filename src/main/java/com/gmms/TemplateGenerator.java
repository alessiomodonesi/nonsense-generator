package com.gmms;

// --- SINGLETON ---
public final class TemplateGenerator {
    private static final TemplateGenerator instance = new TemplateGenerator();
    private final SentenceStructures s = new SentenceStructures(
            "./src/main/java/com/gmms/resources/SentenceStructures.txt");

    private TemplateGenerator() {
    }

    // per inizializzare un singleton
    public static TemplateGenerator getInstance() {
        return instance;
    }

    // genera e restituisce un nuovo oggetto Template
    public Template generateTemplate() {
        // ottiene una struttura di frase casuale da sentenceStructures
        String randomStructure = s.getRandomStructure();

        // crea un nuovo oggetto Template
        return new Template(randomStructure);
    }
}
