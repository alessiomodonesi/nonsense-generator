package com.gmms;

// Mattia Gallinaro
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;

public class SystemDictionary {
    Nouns nouns;
    Verbs verbs; 
    Adjectives adjectives;
    private SystemDictionary instance = null;

    private SystemDictionary(){
        nouns =  new Nouns();
        verbs = new Verbs();
        adjectives = new Adjectives();
    }

    /*ok per ora , dopo la testo
     * Si occupa di prendere i 3 arry e unirli in un unico dizionario di java
    */
    private Dictionary<String, String[]> createDictionary(String[] words, String[] verbs, String[] adjectives){
        Dictionary<String, String[]> test =  new Hashtable<>();
        test.put("words", words);
        test.put("verbs", verbs);
        test.put("adjectives", adjectives);
        return test;
    }

    /*Per far si che esista una sola istanza di SystemDictionary*/
    public SystemDictionary getInstance(){
        if(instance == null)
            instance = new SystemDictionary();

        return instance;
    }



    public Dictionary<String, String[]> pickDictionaryWords(int[] dicWords){
        
        return createDictionary(null, null, null);
    }
}