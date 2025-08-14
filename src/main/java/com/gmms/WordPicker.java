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

    private WordPicker() {
    }

    public static void StartWordsExtraction(int flag_retry) {
        int[] word_types_templ = new int[3];

        // TemplateController.getWordCount()

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
        for (int i = 0; i < types.size(); i++) {
            picked.put(types.get(i), new ArrayList<String>());
            if ((qt[i] / 2) - flag_retry <= 0)
                continue;
            test = words.get(types.get(i));
            Collections.shuffle(test);
            picked.put(types.get(i), test.subList(0, i));
        }
        return picked;
    }

    public static Map<String, List<String>> getWords() {
        return generatedWords;
    }

    private static Map<String, List<String>> analyzeSyntacticTree(SyntacticNode tree) {
        return new HashMap<String, List<String>>();
    }
}