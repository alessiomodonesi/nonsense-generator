package com.gmms;

import java.net.ServerSocket;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApp {
    public static void main(String[] args) {
        int port = findAvailablePort(8080, 8999); // range di porte selezionabili
        SpringApplication app = new SpringApplication(WebApp.class);
        app.setDefaultProperties(java.util.Map.of("server.port", port));
        app.run(args);
    }

    @PostConstruct
    public static void initDictionary() { // crea ed inizializza il dizionario di sistema
        try {
            SystemDictionary.getInstance().initializeDic("./src/main/resources/data/Dictionary.json");
        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione: " + e.getMessage());
        }
    }

    // funzione per selezionare dinamicamente la porta su cui esporre il servizio
    private static int findAvailablePort(int start, int end) {
        for (int port = start; port <= end; port++) {
            try (ServerSocket socket = new ServerSocket(port)) {
                return port; // trovato libero
            } catch (Exception ignored) {
            }
        }
        return 0; // 0 = porta casuale
    }
}