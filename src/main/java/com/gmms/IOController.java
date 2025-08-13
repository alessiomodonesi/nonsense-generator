package com.gmms;
// Tommaso Silvestrin

public class IOController {

    // Dipendenze necessarie per l'IOController per comunicare con il resto del sistema.
    private final Validator validator;
    private final SentenceProcessor sentenceProcessor;

    public IOController(Validator validator, SentenceProcessor sentenceProcessor) {
        this.validator = validator; // validator da usare per verificare l'input
        this.sentenceProcessor = sentenceProcessor;
    }

    /**
     * Riceve una frase e avvia il processo di validazione chiamando il Validator.
     * Questo metodo corrisponde esattamente a 'inputSentence' del Design Model.
     * @param input La stringa della frase da processare.
     */
    public void inputSentence(String input) {
        // Come da diagramma SSD, l'IOController riceve l'input e lo passa al Validator.
        validator.verifySentence(input);
    }

    // mostra errore per input non valido
    public void showInputError() {
        System.err.println("ERRORE: L'input inserito non è valido");
    }

    /**
     * Mostra la frase finale generata dal sistema.
     * @param sentenceDesc La stringa della frase da mostrare.
     */
    public void displaySentence(String sentenceDesc) {
        System.out.println("\n✨ Frase 'Nonsense' Generata:");
        System.out.println("------------------------------------");
        System.out.println(sentenceDesc);
        System.out.println("------------------------------------");
    }

    // mostra errore relativo alla validazione della struttura della frase
    public void showValidationError() {
        System.err.println("ERRORE: La struttura della frase analizzata non è valida");
    }

    // mostra l'albero sintattico della frase
    public void showSyntacticTree() {
        
    }

    // mostra errore se la frase generata è tossica
    public void showToxicityError() {
        System.err.println("ERRORE: La frase generata ha un livello di tossicità non accettabile");
    }

    public void showToxicityResults() {
        // DA FARE
    }
}