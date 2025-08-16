package com.gmms;

// Mattia Gallinaro
import java.util.List;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.util.Collections;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import static java.util.Map.entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class SystemDictionary {
    private static class Nouns {
        List<String> nouns = null;

        public Nouns(List<String> words, StringBuilder sb) {
            nouns = new ArrayList<>(words);
            Collections.shuffle(nouns);
            if (nouns.size() > 0)
                sb.append("Nome generato : " + nouns.get(0));
        }

        public List<String> getNouns(int count) {
            if (count > nouns.size())
                count = nouns.size();
            Collections.shuffle(nouns);
            List<String> pickedWords = nouns.subList(0, count);
            return pickedWords;
        }

        public int getNounsCount() {
            return nouns.size();
        }
    }

    private static class Adjectives {
        List<String> adjectives = null;

        public Adjectives(List<String> words, StringBuilder sb) {
            adjectives = new ArrayList<>(words);
            Collections.shuffle(adjectives);
            if (adjectives.size() > 0)
                sb.append("Aggettivo generato : " + adjectives.get(0));
        }

        public List<String> getAdjectives(int count) {
            if (count > adjectives.size())
                count = adjectives.size();
            Collections.shuffle(adjectives);
            List<String> pickedWords = adjectives.subList(0, count);
            return pickedWords;
        }

        public int getAdjectivesCount() {
            return adjectives.size();
        }
    }

    private static class Verbs {
        List<String> verbs = null;

        public Verbs(List<String> words, StringBuilder sb) {
            verbs = new ArrayList<>(words);
            Collections.shuffle(verbs);
            if (verbs.size() > 0)
                sb.append("Verbo generato : " + verbs.get(0));
        }

        public List<String> getVerbs(int count) {
            if (count > verbs.size())
                count = verbs.size();
            Collections.shuffle(verbs);
            List<String> pickedWords = verbs.subList(0, count);
            return pickedWords;
        }

        public int getVerbsCount() {
            return verbs.size();
        }
    }

    private static Nouns nouns;
    private static Verbs verbs;
    private static Adjectives adjectives;

    private SystemDictionary() {
    }

    // Si occupa di prendere i 3 arry e unirli in un unico dizionario di java
    private static Dictionary<String, List<String>> createDictionary(List<String> noun, List<String> verbs,
            List<String> adjectives) {
        Dictionary<String, List<String>> test = new Hashtable<>();
        test.put("NOUN", noun);
        test.put("VERB", verbs);
        test.put("ADJECTIVE", adjectives);
        return test;

    }

    private static void setupWordDic() throws Exception {
        List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));
        StringBuilder sb = new StringBuilder();
        String path = "./src/main/java/com/gmms/data/dictionary.json";
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
                    throw new Exception("Non esiste il campo" + types.get(i) + " nel json");
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
        } catch (Exception e) {
            throw e;
        }
    }

    public static void initializeDic() throws Exception {
        try {
            setupWordDic();
        } catch (Exception e) {
            throw e;
        }
    }

    public static Dictionary<String, List<String>> pickDictionaryWords(int[] words) {
        return createDictionary(nouns.getNouns(words[0]), verbs.getVerbs(words[1]), adjectives.getAdjectives(words[2]));
    }

    public static Map<String, Integer> getDictionaryWordsCount() {
        return new HashMap<String, Integer>(Map.ofEntries(entry("NOUN", nouns.getNounsCount()),
                entry("VERB", verbs.getVerbsCount()), entry("ADJECTIVE", adjectives.getAdjectivesCount())));
    }
}