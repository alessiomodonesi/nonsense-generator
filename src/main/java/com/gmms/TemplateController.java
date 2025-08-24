package com.gmms;

public final class TemplateController {
    private static final TemplateController instance = new TemplateController();
    private Template template; // contiene oggetto Template gestito dal controller
    private final SentenceStructures s = new SentenceStructures(
            "./src/main/resources/data/Structures.txt");

    // costruttore
    private TemplateController() {
    }

    // per inizializzare un singleton
    public static TemplateController getInstance() {
        return instance;
    }

    // chiama il generatore per il template
    public void generateTemplate() {
        template = TemplateGenerator.getInstance().generateTemplate(this.s);
    }

    // restituisce l'array di interi che rappresenta quanti nomi, verbi e aggettivi
    // sono richiesti nel template
    public int[] getWordCount() {
        return template.templateWords;
    }

    // restituisce la descrizione del template
    public String getTemplateDesc() {
        return template.templateDesc;
    }

    // metodo solo per WebController
    public void resetVar() {
        this.template = null;
    }
}