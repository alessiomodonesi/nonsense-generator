package com.gmms;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

// -- STATIC ---
public class ApiCaller {
  // costruttore
  private ApiCaller() {
  }

  public static String getSyntaxAnalysis(String sentenceDesc) throws Exception {
    String url = "https://language.googleapis.com/v1/documents:analyzeSyntax?key=" + getApiKey();
    String jsonPayload = String.format("""
        {
          "document": {
            "type": "PLAIN_TEXT",
            "content": "%s"
          },
          "encodingType": "UTF8"
        }
        """, sentenceDesc);

    String syntaxAnalysis = makeCall(url, jsonPayload);
    return syntaxAnalysis;
  }

  public static String getToxicityAnalysis(String sentenceDesc) throws Exception {
    String url = "https://language.googleapis.com/v1/documents:moderateText?key=" + getApiKey();
    String jsonPayload = String.format("""
        {
          "document": {
            "type": "PLAIN_TEXT",
            "content": "%s"
          }
        }
        """, sentenceDesc);

    String toxicityAnalysis = makeCall(url, jsonPayload);
    return toxicityAnalysis;
  }

  private static String getApiKey() {
    String apiKey = "";
    try {
      // Specifica il percorso del file
      Path filePath = Path.of(".api_key");

      // Leggi il contenuto del file in una stringa
      apiKey = Files.readString(filePath);

      // Rimuovi eventuali spazi bianchi o interruzioni di riga
      apiKey = apiKey.trim();

      return apiKey;

    } catch (IOException e) {
      // Gestisci l'errore se il file non viene trovato
      System.err.println("Errore durante la lettura del file della chiave API: " + e.getMessage());
      return e.toString(); // Termina il programma se la chiave non può essere letta
    }
  }

  private static String makeCall(String url, String payload) throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Content-Type", "application/json") // Imposta l'header richiesto per il JSON
        .POST(HttpRequest.BodyPublishers.ofString(
            payload))
        .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    // System.out.println(response.body());
    return response.body();
  }
}