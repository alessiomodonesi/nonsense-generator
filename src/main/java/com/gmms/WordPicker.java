package com.gmms;

import java.util.*;

// custom exception
class NoGeneratedWordsException extends RuntimeException {
    public NoGeneratedWordsException(String message) {
        super(message);
    }
}

// custom exception
class TemplateNotFillable extends RuntimeException {
    public TemplateNotFillable(String message) {
        super(message);
    }
}

// -- SINGLETON ---
public final class WordPicker {
    private static final WordPicker instance = new WordPicker();
    private static final Random rand = new Random();
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

    public void startWordsExtraction() {
        if (templateWords == null)
            templateWords = TemplateController.getInstance().getWordCount();

        generatedWords = new HashMap<String, List<String>>();
        numOfRetries++;

        if (numOfRetries == 0) {
            wordsInput = SentenceController.getInstance().getSyntacticTree();
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
            if (tmp.size() < templateWords[i]) {
                if (tmp.size() == 0) {
                    throw new TemplateNotFillable("ERRORE: non è possibile riempire il template");
                }
                for (int j = 0; tmp.size() < templateWords[i]; j++) {
                    // - j per non scegliere tra le parole appena aggiunte
                    tmp.add(tmp.get(rand.nextInt(tmp.size() - j)));
                }
            }
            generatedWords.put(typesGeneratedWords.get(i), tmp);
        }

        System.out.println("\nParole scelte:");
        generatedWords.forEach((pos, words) -> {
            System.out.println(" - " + pos + ": " + String.join(", ", words));
        });
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
            System.out.println("\nParole scelte:");
            pickedWordsSen.forEach((pos, words) -> {
                System.out.println(" - " + pos + ": " + String.join(", ", words));
            });
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
        generatedWords = null;
        pickedWordsSen = null;
        templateWords = null;
        count = new int[3];
        numOfRetries = -1;
    }

    // metodo solo per WebController
    public void resetVar() {
        generatedWords = null;
        wordsSentence = null;
        pickedWordsSen = null;
        wordsInput = null;
        tmpForNodes = null;
        templateWords = null;
        count = new int[3];
        numOfRetries = -1;
    }
}