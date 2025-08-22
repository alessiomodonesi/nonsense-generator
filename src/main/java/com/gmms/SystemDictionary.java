package com.gmms;

import java.util.*;
import java.io.File;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class OutOfBoundsException extends RuntimeException {
    public OutOfBoundsException(String m) {
        super(m);
    }
}

class DictionaryNotInitialized extends RuntimeException{
    public DictionaryNotInitialized(String m) {
        super(m);
    }
}

// -- SINGLETON ---
public final class SystemDictionary {
    private static class Nouns {
        List<String> nouns = null;

        public Nouns(List<String> words, StringBuilder sb) {
            nouns = new ArrayList<>(words);
            Collections.shuffle(nouns);
            if (nouns.size() > 0)
                sb.append("Nome generato: " + nouns.get(0));
        }

        public List<String> getNouns(int count) {
            if (count > nouns.size())
                count = nouns.size();
            Collections.shuffle(nouns);
            List<String> pickedWords = nouns.subList(0, count);
            return pickedWords;
        }
    }

    private static class Verbs {
        List<String> verbs = null;

        public Verbs(List<String> words, StringBuilder sb) {
            verbs = new ArrayList<>(words);
            Collections.shuffle(verbs);
            if (verbs.size() > 0)
                sb.append("Verbo generato: " + verbs.get(0));
        }

        public List<String> getVerbs(int count) {
            if (count > verbs.size())
                count = verbs.size();
            Collections.shuffle(verbs);
            List<String> pickedWords = verbs.subList(0, count);
            return pickedWords;
        }
    }

    private static class Adjectives {

        // contiene la lista di aggettivi
        List<String> adjectives = null;

        /*
         * salva gli aggettivi nella lista e , se words non era vuoto, scrive un
         * elemento
         * randomico della lista nello stringbuilder
         */
        public Adjectives(List<String> words, StringBuilder sb) {
            adjectives = new ArrayList<>(words);
            Collections.shuffle(adjectives);
            if (adjectives.size() > 0)
                sb.append("Aggettivo generato: " + adjectives.get(0));
        }

        // ritorna una lista con la quantità richiesta di aggettivi
        // se chiede più aggettivi di quelli presenti nella lista, ritorna la lista
        // intera
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

    // Costruttore privato per non permettere di creare oggetti di tipo
    // SystemDictionary
    private SystemDictionary() {
    }

    // per inizializzare un singleton
    public static SystemDictionary getInstance() {
        return instance;
    }

    // Si occupa di prendere tre liste e unirle in un unico dizionario di java
    private Map<String, List<String>> createDictionary(List<String> noun, List<String> verbs,
            List<String> adjectives) {
        Map<String, List<String>> test = new HashMap<>();
        test.put("NOUN", noun);
        test.put("VERB", verbs);
        test.put("ADJECTIVE", adjectives);
        return test;

    }

    // legge le parole di un dizionario interno
    // le salva in una mappa e poi inizializza gli oggetti
    private void setupWordDic() throws Exception {
        List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));
        StringBuilder sb = new StringBuilder();
        String path = "./src/main/java/com/gmms/resources/dictionary.json";
        JsonObject json = null;
        String text = "";
        try {
            Scanner sc = new Scanner(new File(path));
            sc.useDelimiter("\\Z");
            text = sc.next();
            sc.close();
            json = JsonParser.parseString(text).getAsJsonObject();

            Map<String, List<String>> wordsJson = new HashMap<String, List<String>>();
            JsonArray arr = null;
            for (int i = 0; i < types.size(); i++) {
                try {
                    arr = json.getAsJsonArray(types.get(i));
                } catch (Exception e) {
                    throw new OutOfBoundsException("Non esiste il campo " + types.get(i) + " nel json");
                }
                List<String> tmp = new ArrayList<String>();
                for (int j = 0; j < arr.size(); j++) {
                    tmp.add(arr.get(j).getAsString());
                }
                wordsJson.put(types.get(i), tmp);
            }
            nouns = new Nouns(wordsJson.get("NOUN"), sb);
            verbs = new Verbs(wordsJson.get("VERB"), sb);
            adjectives = new Adjectives(wordsJson.get("ADJECTIVE"), sb);
        } catch (OutOfBoundsException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public void initializeDic() throws Exception {
        try {
            setupWordDic();
        } catch (Exception e) {
            throw e;
        }
    }

    // seleziona un sottoinsieme di parole dal dizionario
    public Map<String, List<String>> pickDictionaryWords(int[] words) {
        if(nouns == null || verbs == null || adjectives == null){
            throw new DictionaryNotInitialized("ERRORE: Il dizionario non e' stato inizializzato");
        }
        for (int tmp : words) {
            if (tmp < 0)
                throw new OutOfBoundsException("ERRORE: Gli indici inseriti non sono validi");
        }
        return createDictionary(nouns.getNouns(words[0]), verbs.getVerbs(words[1]), adjectives.getAdjectives(words[2]));
    }
}