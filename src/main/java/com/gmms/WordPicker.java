package com.gmms;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// Mattia Gallinaro
public final class WordPicker {

    private static List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJ"));
    private static Map<String, List<String>> generatedWords;
    private static Map<String, List<String>> words_sentence;
    private static List<String> tmp;

    private WordPicker() {
    }

    public static void StartWordsExtraction(int flag_retry) {
        //int[] word_types_templ = TemplateController.getWordCount();
        int[] word_types_templ = new int[]{2,3,4};

        generatedWords= new HashMap<String , List<String>>();

        SyntacticNode words_sent = SentenceProcessor.getSyntacticTree();
        Map<String, List<String>> picked_words_sen = pickSentenceWords(
                analyzeSyntacticTree(words_sent), word_types_templ, flag_retry);

        for (int i = 0; i < word_types_templ.length; i++) {
            word_types_templ[i] -= picked_words_sen.get(types.get(i)).size();
        }

        Dictionary<String, List<String>> picked_dict_words = SystemDictionary.pickDictionaryWords(word_types_templ);

        for (int i = 0; i < types.size(); i++) {
            List<String> tmp = picked_words_sen.get(types.get(i));
            tmp.addAll(picked_dict_words.get(types.get(i)));
            generatedWords.put(types.get(i), tmp);
        }
    }

    private static Map<String, List<String>> pickSentenceWords(Map<String, List<String>> words, int[] qt,
            int flag_retry) {
        /*
         * qt = [ x, y , z]
         * x = numero di sostantivi
         * y = numero di verbi
         * z = numero di aggettivi
         */
        Map<String, List<String>> picked = new HashMap<String, List<String>>();
        List<String> test = null;
        int count = 0;
        for (int i = 0; i < types.size(); i++) {
            picked.put(types.get(i), new ArrayList<String>());
            count = (qt[i] / 2) - flag_retry;
            if (count <= 0)
                continue; 
            test = words.get(types.get(i));
            if((qt[i]/2) - flag_retry > test.size())count = test.size();
            Collections.shuffle(test);
            picked.put(types.get(i), test.subList(0, count));
        }
        return picked;
    }

    public static Map<String, List<String>> getWords() {
        return generatedWords;
    }

    private static Map<String, List<String>> analyzeSyntacticTree(SyntacticNode tree) {
        words_sentence = new HashMap<String, List<String>>();
        words_sentence.put("NOUN", new ArrayList<String>());
        words_sentence.put("VERB", new ArrayList<String>());
        words_sentence.put("ADJ", new ArrayList<String>());
        LoopOnNodes(tree);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < words_sentence.get(types.get(i)).size();j++){
                System.out.println(words_sentence.get(types.get(i)).get(j));
            }
        }
        return words_sentence;
    }
    private static void LoopOnNodes(SyntacticNode node){
        if(types.contains(node.getPartOfSpeech())){
           tmp = words_sentence.get(node.getPartOfSpeech());
           tmp.add(node.getText());
           words_sentence.put(node.getPartOfSpeech(), tmp);
        }
        for(SyntacticNode sn : node.getnode()){
            LoopOnNodes(sn);
        }
    }
}