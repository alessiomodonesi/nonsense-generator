package com.gmms;

// Mattia Gallinaro
import java.util.List;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class SystemDictionary {
    private static Nouns nouns;
    private static Verbs verbs;
    private static Adjectives adjectives;

    private SystemDictionary() {
    }

    /*
     * ok per ora , dopo la testo
     * Si occupa di prendere i 3 arry e unirli in un unico dizionario di java
     */
    private static Dictionary<String, List<String>> createDictionary(List<String> noun, List<String> verbs,
            List<String> adjectives) {
        Dictionary<String, List<String>> test = new Hashtable<>();
        test.put("NOUN", noun);
        test.put("VERB", verbs);
        test.put("ADJ", adjectives);
        return test;

    }

    private static void setupWordDic() {
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
            JsonArray arr = json.getAsJsonArray("NOUN");
            List<String> test = new ArrayList<String>();
            for (int i = 0; i < arr.size(); i++) {
                test.add(arr.get(i).getAsString());
            }
            nouns = new Nouns(test, sb);
            arr = json.getAsJsonArray("VERB");
            test = new ArrayList<String>();
            for (int i = 0; i < arr.size(); i++) {
                test.add(arr.get(i).getAsString());
            }
            verbs = new Verbs(test, sb);
            arr = json.getAsJsonArray("ADJ");
            test = new ArrayList<String>();
            for (int i = 0; i < arr.size(); i++) {
                test.add(arr.get(i).getAsString());
            }
            adjectives = new Adjectives(test, sb);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void initializeDic() {
        setupWordDic();
    }

    public static Dictionary<String, List<String>> pickDictionaryWords(int[] words) {
        return createDictionary(nouns.getNouns(words[0]), verbs.getVerbs(words[1]), adjectives.getAdjectives(words[2]));
    }
}