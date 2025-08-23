package com.gmms;

public final class Template {
    public String templateDesc; // descrizione del template nella forma con [NOUN], [VERB], [ADJECTIVE]
    public int[] templateWords; // array di interi che indica quanti nomi, verbi e aggettivi sono richiesti

    // costruttore
    public Template(String templateDesc, int[] templateWords) {
        this.templateDesc = templateDesc;
        this.templateWords = templateWords;
    }
}