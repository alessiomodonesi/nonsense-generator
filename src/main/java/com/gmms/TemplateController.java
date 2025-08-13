package com.gmms;
// Tommaso Silvestrin

public class TemplateController {

    public Template template; // contiene oggetto Template gestito dal controller

    // costruttore che, appena viene creato un controller, usa il generator per creare un nuovo Template
    public TemplateController(TemplateGenerator generator) {
        this.template = generator.generateTemplate();
    }

    // restituisce l'array di interi che rappresenta i tipi di parola richiesti dal template
    public int[] getWordCount() {
        return this.template.templateWords;
    }

    // restituisce la descrizione del template, con i segnaposto %s
    public String getTemplateDesc() {
        return this.template.templateDesc;
    }
}