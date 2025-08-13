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

    // riceve una frase e avvia il processo di validazione chiamando il Validator
    
    public void inputSentence(String input) {
        validator.verifySentence(input);
    }

    // mostra errore per input non valido
    public void showInputError() {
        System.err.println("ERRORE: L'input inserito non è valido");
    }

    
    public void displaySentence(String sentenceDesc) {

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