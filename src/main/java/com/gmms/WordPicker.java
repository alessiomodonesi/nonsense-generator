package com.gmms;

import java.util.*;

// errore unchecked
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

// -- SINGLETON ---
public class WordPicker {
    private static final WordPicker instance = new WordPicker();
    private List<String> typesInputSentence = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJ"));
    private List<String> typesGeneratedWords = new ArrayList<String>(Arrays.asList("NOUN", "VERB", "ADJECTIVE"));
    private Map<String, List<String>> generatedWords = null;
    private Map<String, List<String>> wordsSentence = null;
    private Map<String, List<String>> pickedWordsSen = null;
    private SyntacticNode wordsInput = null;
    private List<String> tmpForNodes = null;
    private int[] templateWords = null;
    private int[] count = new int[3];
    private int numOfRetries = -1;

    // costruttore
    private WordPicker() {
    }

    // per inizializzare un singleton
    public static WordPicker getInstance() {
        return instance;
    }

    public void startWordsExtraction(TemplateController controller) {
        if (templateWords == null)
            templateWords = controller.getWordCount();

        generatedWords = new HashMap<String, List<String>>();
        numOfRetries++;

        if (numOfRetries == 0) {
            wordsInput = SentenceController.getSyntacticTree();
            pickedWordsSen = new HashMap<String, List<String>>();
        }

        analyzeSyntacticTree(wordsInput);
        pickSentenceWords();

        int[] dictionaryWords = templateWords.clone();
        for (int i = 0; i < dictionaryWords.length; i++) {
            dictionaryWords[i] -= pickedWordsSen.get(typesInputSentence.get(i)).size();
        }

        Map<String, List<String>> pickedDictWords = SystemDictionary.getInstance().pickDictionaryWords(dictionaryWords);

        for (int i = 0; i < typesInputSentence.size(); i++) {
            List<String> tmp = pickedWordsSen.get(typesInputSentence.get(i));
            tmp.addAll(pickedDictWords.get(typesGeneratedWords.get(i)));
            generatedWords.put(typesGeneratedWords.get(i), tmp);
        }

        System.out.println(generatedWords);
    }

    private void pickSentenceWords() {
        /*
         * qt = [ x, y, z]
         * x = numero di sostantivi
         * y = numero di verbi
         * z = numero di aggettivi
         */

        int emptyWordsMap = 0;
        List<String> tmp = new ArrayList<String>();

        if (numOfRetries != 0) {
            for (int i = 0; i < typesInputSentence.size(); i++) {
                tmp = wordsSentence.get(typesInputSentence.get(i));

                count[i] -= 1;

                if (count[i] <= 0) {
                    emptyWordsMap++;
                    continue;
                }

                Collections.shuffle(tmp);
                pickedWordsSen.put(typesInputSentence.get(i), tmp.subList(0, count[i]));
            }
        } else {
            for (int i = 0; i < typesInputSentence.size(); i++) {
                pickedWordsSen.put(typesInputSentence.get(i), new ArrayList<String>());
                count[i] = ((int) Math.round((((double) templateWords[i]) * 0.75)));

                tmp = wordsSentence.get(typesInputSentence.get(i));

                if (count[i] > tmp.size())
                    count[i] = tmp.size();

                count[i] -= numOfRetries;

                if (count[i] <= 0) {
                    emptyWordsMap++;
                    continue;
                }

                Collections.shuffle(tmp);
                pickedWordsSen.put(typesInputSentence.get(i), tmp.subList(0, count[i]));
            }
        }
        if (emptyWordsMap == 3) {
            System.out.println(pickedWordsSen);
            throw new RetryInputException("\nERRORE: nessuna parola dell'user selezionata\n");
        }
    }

    public Map<String, List<String>> getWords() {
        if (generatedWords == null)
            throw new NoGeneratedWordsException("ERRORE: non sono state generate parole in precedenza");
        return generatedWords;
    }

    private void analyzeSyntacticTree(SyntacticNode syntactictree) {
        wordsSentence = new HashMap<String, List<String>>();
        wordsSentence.put("NOUN", new ArrayList<String>());
        wordsSentence.put("VERB", new ArrayList<String>());
        wordsSentence.put("ADJ", new ArrayList<String>());
        loopOnNodes(syntactictree);
    }

    private void loopOnNodes(SyntacticNode node) {
        if (node != null) {
            if (typesInputSentence.contains(node.getPartOfSpeech())) {
                tmpForNodes = wordsSentence.get(node.getPartOfSpeech());
                tmpForNodes.add(node.getText());
                wordsSentence.put(node.getPartOfSpeech(), tmpForNodes);
            }

            for (SyntacticNode sn : node.getnode())
                loopOnNodes(sn);
        }
    }

    public void resetNumOfRetries() {
        templateWords = null;
        pickedWordsSen = null;
        count = new int[3];
        numOfRetries = -1;
    }
}