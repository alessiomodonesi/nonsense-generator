package com.gmms;

public class TemplateController {

    private static Template template; // contiene oggetto Template gestito dal controller

    // costruttore che, appena viene creato un controller, usa il generator per
    // creare un nuovo Template
    public TemplateController(TemplateGenerator generator) {
        template = generator.generateTemplate();
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
}