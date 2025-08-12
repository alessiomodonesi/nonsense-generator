package com.gmms;
// Tommaso Silvestrin

/**
 * Rappresenta un modello (template) di frase, basato su una struttura data.
 * Contiene la descrizione del template e un array che definisce i tipi di parole necessarie.
 *
 * Design Model:
 * - Attributi: templateDesc (String), templateWords (int[])
 * - Metodi: create(randomStructure)
 */
public class Template {

    // La struttura della frase con segnaposto per la formattazione (es. "La %s %s.")
    public String templateDesc;

    // Un array di interi che rappresenta i tipi di parole da inserire.
    public int[] templateWords;

    /**
     * Costruttore che implementa il metodo 'create' del Design Model.
     * Riceve la struttura casuale e dovrebbe inizializzare gli attributi della classe.
     * @param randomStructure La stringa di base del template (es. "Il [NOUN] [VERB].")
     */
    public Template(String randomStructure) {
        // TODO: Implementare la logica per processare 'randomStructure'.
        // 1. Analizzare la stringa per popolare l'array 'templateWords'.
        // 2. Trasformare la stringa in 'templateDesc' (es. sostituendo "[NOUN]" con "%s").
        
        // Per ora, inizializzo i valori a null o vuoti.
        this.templateDesc = "";
        this.templateWords = new int[0];
    }
}