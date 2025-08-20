package com.gmms;

public class App {
    public static void main(String[] args) {
        try {
            SystemDictionary.initializeDic();
            new ApplicationController().start();
        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione: " + e.getMessage());
        }
    }
}
