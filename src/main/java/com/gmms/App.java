package com.gmms;

public class App {
    public static void main(String[] args) {
        try {
            SystemDictionary.getInstance().initializeDic("./src/main/resources/data/Dictionary.json"); // crea ed inizializza il dizionario di sistema
            AppController.start(); // lancia il controller contenente la logica del programma
        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione: " + e.getMessage());
        }
    }
}
