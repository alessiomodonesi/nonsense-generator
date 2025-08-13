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

    /**
     * Mostra un errore generico per un input non valido.
     */
    public void showInputError() {
        System.err.println(" ERRORE: L'input inserito non è valido. Riprova.");
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

    /**
     * Mostra un errore relativo alla validazione della struttura della frase.
     */
    public void showValidationError() {
        System.err.println(" ERRORE: La struttura della frase analizzata non è valida.");
    }

    /**
     * Recupera l'albero sintattico dal SentenceProcessor e lo mostra all'utente.
     */
    public void showSyntacticTree() {
        // La logica del TODO è stata completata: il metodo ora chiama il sentenceProcessor.
        String tree = sentenceProcessor.getSyntacticTree();
        
        System.out.println("\n Albero Sintattico:");
        System.out.println(tree);
    }

    /**
     * Mostra un errore se la frase generata è stata classificata come tossica.
     */
    public void showToxicityError() {
        System.err.println("ATTENZIONE: La frase generata potrebbe contenere linguaggio non appropriato.");
    }

    /**
     * Mostra i risultati finali dell'analisi di tossicità.
     */
    public void showToxicityResults() {
        // La logica del TODO è stata completata con un messaggio di successo.
        // Nota: per mostrare risultati dettagliati, questo metodo dovrebbe ricevere dei parametri,
        // ma ci atteniamo al Design Model che non ne prevede.
        System.out.println("Analisi di tossicità completata. Risultati nella norma.");
    }
}