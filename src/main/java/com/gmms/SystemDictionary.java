package com.gmms;

import java.util.*;
import java.io.File;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// custom exception
class OutOfBoundsException extends RuntimeException {
    public OutOfBoundsException(String m) {
        super(m);
    }
}

// custom exception
class DictionaryNotInitialized extends RuntimeException {
    public DictionaryNotInitialized(String m) {
        super(m);
    }
}

// -- SINGLETON ---
public final class SystemDictionary {
    // inner-class
    private static class Nouns {
        List<String> nouns = null; // contiene la lista di nomi

        // costruttore
        // salva i nomi nella lista e, se words non era vuoto, scrive
        // un elemento randomico della lista nello stringbuilder
        public Nouns(List<String> words, StringBuilder sb) {
            nouns = new ArrayList<>(words);
            Collections.shuffle(nouns);
            if (nouns.size() > 0)
                sb.append("Nome generato: " + nouns.get(0));
        }

        // ritorna una lista con la quantità richiesta di nomi
        // se vengono chiesti più nomi di quelli presenti nella lista,
        // ritorna la lista intera
        public List<String> getNouns(int count) {
            if (count > nouns.size())
                count = nouns.size();
            Collections.shuffle(nouns);
            List<String> pickedWords = nouns.subList(0, count);
            return pickedWords;
        }
    }

    // inner-class
    private static class Verbs {
        List<String> verbs = null; // contiene la lista di verbi

        // costruttore
        // salva i verbi nella lista e, se words non era vuoto, scrive
        // un elemento randomico della lista nello stringbuilder
        public Verbs(List<String> words, StringBuilder sb) {
            verbs = new ArrayList<>(words);
            Collections.shuffle(verbs);
            if (verbs.size() > 0)
                sb.append("Verbo generato: " + verbs.get(0));
        }

        // ritorna una lista con la quantità richiesta di verbi
        // se vengono chiesti più verbi di quelli presenti nella lista,
        // ritorna la lista intera
        public List<String> getVerbs(int count) {
            if (count > verbs.size())
                count = verbs.size();
            Collections.shuffle(verbs);
            List<String> pickedWords = verbs.subList(0, count);
            return pickedWords;
        }
    }

    // inner-class
    private static class Adjectives {
        List<String> adjectives = null; // contiene la lista di aggettivi

        // costruttore
        // salva gli aggettivi nella lista e, se words non era vuoto, scrive
        // un elemento randomico della lista nello stringbuilder
        public Adjectives(List<String> words, StringBuilder sb) {
            adjectives = new ArrayList<>(words);
            Collections.shuffle(adjectives);
            if (adjectives.size() > 0)
                sb.append("Aggettivo generato: " + adjectives.get(0));
        }

        // ritorna una lista con la quantità richiesta di aggettivi
        // se vengono chiesti più aggettivi di quelli presenti nella lista,
        // ritorna la lista intera
        public List<String> getAdjectives(int count) {
            if (count > adjectives.size())
                count = adjectives.size();
            Collections.shuffle(adjectives);
            List<String> pickedWords = adjectives.subList(0, count);
            return pickedWords;
        }
    }

    private static Nouns nouns = null;
    private static Verbs verbs = null;
    private static Adjectives adjectives = null;
    private static final SystemDictionary instance = new SystemDictionary();

    // costruttore
    private SystemDictionary() {
    }

    // per inizializzare un singleton
    public static SystemDictionary getInstance() {
        return instance;
    }

    // si occupa di prendere tre liste e unirle in un unico dizionario di java
    private Map<String, List<String>> createDictionary(List<String> noun, List<String> verbs,
            List<String> adjectives) {
        Map<String, List<String>> dictionary = new HashMap<>();
        dictionary.put("NOUN", noun);
        dictionary.put("VERB", verbs);
        dictionary.put("ADJECTIVE", adjectives);
        return dictionary;
    }

    // legge le parole del dizionario di sistema,
    // le salva in una mappa e poi inizializza gli oggetti
    private void setupWordDic(String path) throws Exception {
        List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));
        StringBuilder sb = new StringBuilder();
        JsonObject json = null;
        String text = "";

        try {
            Scanner sc = new Scanner(new File(path));
            sc.useDelimiter("\\Z");
            text = sc.next();
            sc.close();
            json = JsonParser.parseString(text).getAsJsonObject();

            Map<String, List<String>> wordsJson = new HashMap<>();
            JsonArray arr = null;

            for (int i = 0; i < types.size(); i++) {
                arr = json.getAsJsonArray(types.get(i));
                if (arr == null)
                    throw new OutOfBoundsException("Non esiste il campo " + types.get(i) + " nel json");

                List<String> tmp = new ArrayList<String>();

                for (int j = 0; j < arr.size(); j++)
                    tmp.add(arr.get(j).getAsString());

                wordsJson.put(types.get(i), tmp);
            }

            nouns = new Nouns(wordsJson.get("NOUN"), sb);
            verbs = new Verbs(wordsJson.get("VERB"), sb);
            adjectives = new Adjectives(wordsJson.get("ADJECTIVE"), sb);
        } catch (Exception e) {
            throw e;
        }
    }

    // inizializza il dizionario di sistema ad init-time
    public void initializeDic(String path) throws Exception {
        try {
            setupWordDic(path);
        } catch (Exception e) {
            throw e;
        }
    }

    // seleziona un sottoinsieme di parole dal dizionario
    public Map<String, List<String>> pickDictionaryWords(int[] words) {
        if (nouns == null || verbs == null || adjectives == null) {
            throw new DictionaryNotInitialized("ERRORE: Il dizionario non è stato inizializzato");
        }

        for (int tmp : words) {
            if (tmp < 0)
                throw new OutOfBoundsException("ERRORE: Gli indici inseriti non sono validi");
        }

        return createDictionary(nouns.getNouns(words[0]), verbs.getVerbs(words[1]), adjectives.getAdjectives(words[2]));
    }

    // metodo solo per testing
    public void reset() {
        nouns = null;
        verbs = null;
        adjectives = null;
    }
}