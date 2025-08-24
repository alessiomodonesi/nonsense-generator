package com.gmms;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApp {
    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }

    @PostConstruct
    public void initDictionary() {
        try {
            SystemDictionary.getInstance().initializeDic("./src/main/resources/data/Dictionary.json"); // crea ed inizializza il dizionario di sistema
        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione: " + e.getMessage());
        }
    }
}
