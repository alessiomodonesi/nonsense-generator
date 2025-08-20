package com.gmms;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// errore Unchecked
class RetryInputException extends RuntimeException {
    public RetryInputException(String message) {
        super(message);
    }
}

class NoGeneratedWordsException extends RuntimeException {
    public NoGeneratedWordsException(String message) {
        super(message);
    }
}

public final class WordPicker {
    private static List<String> types = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));
    private static Map<String, List<String>> generatedWords;
    private static Map<String, List<String>> wordsSentence;
    private static SyntacticNode wordsInput;
    private static List<String> tmpForNodes;
    private static Map<String, List<String>> pickedWordsSen;
    private static Integer numOfRetries = -1;
    private static int[] templateWords;
    private static int[] count = new int[3];

    private WordPicker() {
    }

    public static void startWordsExtraction(TemplateController controller) {
        if (templateWords == null)
            templateWords = controller.getWordCount();

        generatedWords = new HashMap<String, List<String>>();
        numOfRetries++;

        if (numOfRetries == 0) {
            wordsInput = SentenceProcessor.getSyntacticTree();
            pickedWordsSen = new HashMap<String, List<String>>();
        }

        analyzeSyntacticTree(wordsInput);
        pickSentenceWords();

        int[] dictionaryWords = templateWords.clone();
        for (int i = 0; i < dictionaryWords.length; i++) {
            dictionaryWords[i] -= pickedWordsSen.get(types.get(i)).size();
        }

        Dictionary<String, List<String>> pickedDictWords = SystemDictionary.pickDictionaryWords(dictionaryWords);

        for (int i = 0; i < types.size(); i++) {
            List<String> tmp = pickedWordsSen.get(types.get(i));
            tmp.addAll(pickedDictWords.get(types.get(i)));
            generatedWords.put(types.get(i), tmp);
        }

        System.out.println(generatedWords);
    }

    private static void pickSentenceWords() {
        /*
         * qt = [ x, y, z]
         * x = numero di sostantivi
         * y = numero di verbi
         * z = numero di aggettivi
         */

        int emptyWordsMap = 0;
        List<String> tmp = new ArrayList<String>();

        if (numOfRetries != 0) {
            for (int i = 0; i < types.size(); i++) {
                tmp = wordsSentence.get(types.get(i));

                count[i] -= 1;

                if (count[i] <= 0) {
                    emptyWordsMap++;
                    continue;
                }

                Collections.shuffle(tmp);
                pickedWordsSen.put(types.get(i), tmp.subList(0, count[i]));
            }
        } else {
            for (int i = 0; i < types.size(); i++) {
                pickedWordsSen.put(types.get(i), new ArrayList<String>());
                count[i] = ((int) Math.round((((double) templateWords[i]) * 0.75)));

                tmp = wordsSentence.get(types.get(i));

                if (count[i] > tmp.size())
                    count[i] = tmp.size();

                count[i] -= numOfRetries;

                if (count[i] <= 0) {
                    emptyWordsMap++;
                    continue;
                }

                Collections.shuffle(tmp);
                pickedWordsSen.put(types.get(i), tmp.subList(0, count[i]));
            }
        }
        if (emptyWordsMap == 3) {
            System.out.println(pickedWordsSen);
            throw new RetryInputException("\nERRORE: nessuna parola dell'user selezionata\n");
        }
    }

    public static Map<String, List<String>> getWords() {
        if (generatedWords == null)
            throw new NoGeneratedWordsException("ERRORE: non sono state generate parole in precedenza");
        return generatedWords;
    }

    private static void analyzeSyntacticTree(SyntacticNode tree) {
        wordsSentence = new HashMap<String, List<String>>();
        wordsSentence.put("NOUN", new ArrayList<String>());
        wordsSentence.put("VERB", new ArrayList<String>());
        wordsSentence.put("ADJECTIVE", new ArrayList<String>());
        loopOnNodes(tree);
    }

    private static void loopOnNodes(SyntacticNode node) {
        if (node != null) {
            if (types.contains(node.getPartOfSpeech())) {
                tmpForNodes = wordsSentence.get(node.getPartOfSpeech());
                tmpForNodes.add(node.getText());
                wordsSentence.put(node.getPartOfSpeech(), tmpForNodes);
            }

            for (SyntacticNode sn : node.getnode())
                loopOnNodes(sn);
        }
    }

    public static void resetNumOfRetries() {
        templateWords = null;
        pickedWordsSen = null;
        count = new int[3];
        numOfRetries = -1;
    }
}