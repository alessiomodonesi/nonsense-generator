package com.gmms;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ApiCallerTest {

    private static Method makeCallMethod;

    @BeforeAll
    static void reflectPrivateMakeCall() throws NoSuchMethodException {
        // Accediamo a makeCall(String url, String payload) via reflection
        makeCallMethod = ApiCaller.class.getDeclaredMethod("makeCall", String.class, String.class);
        makeCallMethod.setAccessible(true);
    }

    @Test
    void testMakeCall_UsesHttpPostAndReturnsBody() throws Exception {
        // 1) Avvia un piccolo server HTTP locale che restituisce un JSON fisso
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        int port = server.getAddress().getPort();
        String expectedResponse = "{\"ok\":true,\"echo\":\"hello\"}";

        server.createContext("/test", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Verifica che sia una POST e che il content-type sia JSON (best-effort)
                String method = exchange.getRequestMethod();
                assertEquals("POST", method, "La richiesta deve essere POST");

                String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
                assertNotNull(contentType);
                assertTrue(contentType.toLowerCase().contains("application/json"),
                        "Content-Type application/json atteso");

                // Leggi il payload (opzionale: potresti assertare il contenuto)
                byte[] requestBody = exchange.getRequestBody().readAllBytes();
                String body = new String(requestBody, StandardCharsets.UTF_8);
                assertTrue(body.contains("payload-di-test"), "Il payload deve contenere la stringa attesa");

                // Rispondi con il JSON atteso
                byte[] responseBytes = expectedResponse.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, responseBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            }
        });
        server.start();

        try {
            String url = "http://localhost:" + port + "/test";
            String payload = "{\"payload\":\"payload-di-test\"}";

            // 2) Chiama makeCall via reflection
            Object raw = invokeMakeCall(url, payload);
            String actual = (String) raw;

            // 3) Asserzioni sul body
            assertEquals(expectedResponse, actual, "La risposta del server locale deve essere ritornata invariata");
        } finally {
            server.stop(0);
        }
    }

    @Test
    void testGetSyntaxAnalysis_LiveCallIfApiKeyPresent() throws Exception {
        // Esegui il test solo se è presente una chiave API non vuota in .api_key
        Path apiKeyPath = Path.of(".api_key");
        boolean apiKeyPresent = Files.exists(apiKeyPath) && !Files.readString(apiKeyPath).trim().isEmpty();
        assumeTrue(apiKeyPresent, "Nessuna API key valida in .api_key: salto il test live di getSyntaxAnalysis");

        // Chiamata “live” molto semplice (potrebbe consumare quota!)
        String sentence = "Ciao mondo!";
        String response = ApiCaller.getSyntaxAnalysis(sentence);

        // Verifiche deboli (il formato preciso potrebbe cambiare): ci aspettiamo JSON
        // non vuoto
        assertNotNull(response);
        assertFalse(response.isBlank(), "La risposta non deve essere vuota");
        // Se vuoi, controlla qualche chiave tipica dell’API (senza essere troppo
        // rigido)
        assertTrue(
                response.contains("tokens") || response.contains("documentSentiment") || response.contains("sentences"),
                "La risposta dovrebbe contenere campi tipici delle API di Language");
    }

    @Test
    void testGetToxicityAnalysis_LiveCallIfApiKeyPresent() throws Exception {
        // Esegui il test solo se è presente una chiave API non vuota in .api_key
        Path apiKeyPath = Path.of(".api_key");
        boolean apiKeyPresent = Files.exists(apiKeyPath) && !Files.readString(apiKeyPath).trim().isEmpty();
        assumeTrue(apiKeyPresent, "Nessuna API key valida in .api_key: salto il test live di getToxicityAnalysis");

        String sentence = "Sei terribile!";
        String response = ApiCaller.getToxicityAnalysis(sentence);

        assertNotNull(response);
        assertFalse(response.isBlank(), "La risposta non deve essere vuota");
        // Controllo blando su parole chiave che spesso compaiono nella moderazione
        assertTrue(response.contains("moderationCategories") || response.contains("language"),
                "La risposta dovrebbe contenere campi tipici della moderazione testo");
    }

    // ---------------------
    // Helpers
    // ---------------------

    private static Object invokeMakeCall(String url, String payload)
            throws InvocationTargetException, IllegalAccessException {
        return makeCallMethod.invoke(null, url, payload);
        // null = metodo statico
    }
}
