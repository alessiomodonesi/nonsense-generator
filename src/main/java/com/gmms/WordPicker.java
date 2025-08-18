package com.gmms;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

//errore Unchecked 
class RetryInputException extends RuntimeException{
    public RetryInputException(String message){
        super(message);
    }
}

public final class WordPicker {
    private static List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));
    private static Map<String, List<String>> generatedWords;
    private static Map<String, List<String>> wordsSentence;
    private static List<String> tmp;
    private static Map<String, Integer> wordsOfDictionary = null;
    private static Integer numOfRetries = -1;

    private WordPicker() {
    }

    public static void startWordsExtraction(TemplateController controller) {
        numOfRetries++;
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
                wordsSentence, templateWords);

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

    private static Map<String, List<String>> pickSentenceWords(Map<String, List<String>> words, int[] qt) {
        /*
         * qt = [ x, y , z]
         * x = numero di sostantivi
         * y = numero di verbi
         * z = numero di aggettivi
         */

        int emptyWordsMap = 0;
        Map<String, List<String>> picked = new HashMap<String, List<String>>();
        List<String> tmp = null;
        int count = 0;
        for (int i = 0; i < types.size(); i++) {
            picked.put(types.get(i), new ArrayList<String>());
            count = (int)Math.round(((double)qt[i] * 0.75)) - numOfRetries;
            if (count <= 0){
                emptyWordsMap++;
                continue;
            }
            tmp = words.get(types.get(i));
            if (count > tmp.size())
                count = tmp.size();
            Collections.shuffle(tmp);
            picked.put(types.get(i), tmp.subList(0, count));
        }
        if(emptyWordsMap == 3)throw new RetryInputException(" 0 parole scelte dell'utente");
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
        loopOnNodes(tree);
        return wordsSentence;
    }

    private static void loopOnNodes(SyntacticNode node) {
        if (types.contains(node.getPartOfSpeech())) {
            tmp = wordsSentence.get(node.getPartOfSpeech());
            tmp.add(node.getText());
            wordsSentence.put(node.getPartOfSpeech(), tmp);
        }
        for (SyntacticNode sn : node.getnode()) {
            loopOnNodes(sn);
        }
    }

    public static void resetNumOfRetries() {
        numOfRetries = -1;
    }
}