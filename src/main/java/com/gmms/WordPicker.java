package com.gmms;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public final class WordPicker {

    private static List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));
    private static Map<String, List<String>> generatedWords;
    private static Map<String, List<String>> wordsSentence;
    private static List<String> tmp;
    private static Map<String, Integer> wordsOfDictionary = null;

    private WordPicker() {
    }

    public static void StartWordsExtraction(TemplateController controller, int flagRetry) {
        int[] templateWords = controller.getWordCount();
        generatedWords = new HashMap<String, List<String>>();

        if (wordsOfDictionary == null)
            wordsOfDictionary = SystemDictionary.getDictionaryWordsCount();

        SyntacticNode wordsSent = SentenceProcessor.getSyntacticTree();
        Map<String, List<String>> wordsSentence = analyzeSyntacticTree(wordsSent);
        /*
         * controllo per verificare che sia possibile di genereare il numero di
         * parole richiesto
         * for(int i = 0; i < types.size(); i++){
         * if(wordsOfDictionary.get(types.get(i)) +
         * (wordsSentence.get(types.get(i)).size()/2 - flagRetry) < templateWords[i]){
         * throw new Exception("Il template richiede troppi elementi");
         * }
         * }
         */
        Map<String, List<String>> pickedWordsSen = pickSentenceWords(
                wordsSentence, templateWords, flagRetry);

        for (int i = 0; i < templateWords.length; i++) {
            templateWords[i] -= pickedWordsSen.get(types.get(i)).size();
        }

        Dictionary<String, List<String>> pickedDictWords = SystemDictionary.pickDictionaryWords(templateWords);

        for (int i = 0; i < types.size(); i++) {
            List<String> tmp = pickedWordsSen.get(types.get(i));
            tmp.addAll(pickedDictWords.get(types.get(i)));
            generatedWords.put(types.get(i), tmp);
        }

        System.out.println(generatedWords);
    }

    private static Map<String, List<String>> pickSentenceWords(Map<String, List<String>> words, int[] qt,
            int flagRetry) {
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
            count = (qt[i] / 2) - flagRetry;
            if (count <= 0)
                continue;
            test = words.get(types.get(i));
            if ((qt[i] / 2) - flagRetry > test.size())
                count = test.size();
            Collections.shuffle(test);
            picked.put(types.get(i), test.subList(0, count));
        }
        return picked;
    }

    public static Map<String, List<String>> getWords() {
        return generatedWords;
    }

    private static Map<String, List<String>> analyzeSyntacticTree(SyntacticNode tree) {
        wordsSentence = new HashMap<String, List<String>>();
        wordsSentence.put("NOUN", new ArrayList<String>());
        wordsSentence.put("VERB", new ArrayList<String>());
        wordsSentence.put("ADJECTIVE", new ArrayList<String>());
        LoopOnNodes(tree);
        return wordsSentence;
    }

    private static void LoopOnNodes(SyntacticNode node) {
        if (types.contains(node.getPartOfSpeech())) {
            tmp = wordsSentence.get(node.getPartOfSpeech());
            tmp.add(node.getText());
            wordsSentence.put(node.getPartOfSpeech(), tmp);
        }
        for (SyntacticNode sn : node.getnode()) {
            LoopOnNodes(sn);
        }
    }
}