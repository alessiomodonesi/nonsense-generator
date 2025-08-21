package com.gmms;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

// -- SINGLETON ---
public class Analyzer {
    private static final Analyzer instance = new Analyzer();

    // costruttore
    private Analyzer() {
    }

    // per inizializzare un singleton
    public static Analyzer getInstance() {
        return instance;
    }

    // Classi interne per il mapping del JSON
    // Queste classi rispecchiano fedelmente la struttura del JSON di input.
    private record JsonText(String content, int beginOffset) {
    }

    private record PartOfSpeech(String tag) {
    }

    private record DependencyEdge(int headTokenIndex, String label) {
    }

    private record Token(JsonText text, PartOfSpeech partOfSpeech, DependencyEdge dependencyEdge, String lemma) {
    }

    private record SyntaxAnalysis(List<Token> tokens) {
    }

    // private record Language(String language) {}

    public void analyzeSentence(String sentenceDesc) throws Exception {
        String jsonData = ApiCaller.getInstance().getSyntaxAnalysis(sentenceDesc); // json in output dall'api
        // System.out.println(jsonData);
        SyntacticNode syntacticTree = buildSyntacticTree(jsonData);
        SentenceController.setSentenceTree(syntacticTree);
    }

    // analizza il JSON in output dall'api e restituisce la radice
    public SyntacticNode buildSyntacticTree(String jsonInput) throws Exception {
        Gson gson = new Gson();
        // Language lang = gson.fromJson(jsonInput, Language.class);
        // if (!lang.language.equals("it")) { throw new IOException(); }

        SyntaxAnalysis analysis = gson.fromJson(jsonInput, SyntaxAnalysis.class);
        List<Token> parsedTokens = analysis.tokens;

        if (parsedTokens == null || parsedTokens.isEmpty()) {
            return null;
        }

        // creazione di una lista contente tutti i nodi dell'albero
        List<SyntacticNode> nodes = new ArrayList<>();
        for (Token token : parsedTokens) {
            nodes.add(new SyntacticNode(token.text.content, token.lemma, token.partOfSpeech.tag));
        }

        // itera di nuovo sui token per stabilire le relazioni padre-figlio
        SyntacticNode root = null;
        for (int i = 0; i < parsedTokens.size(); i++) {
            Token currentToken = parsedTokens.get(i);
            int headIndex = currentToken.dependencyEdge.headTokenIndex;
            String depLabel = currentToken.dependencyEdge.label;

            // se l'indice della testa è uguale all'indice corrente, questo è il nodo ROOT
            if (headIndex == i) {
                if ("ROOT".equals(depLabel)) {
                    root = nodes.get(i);
                    root.setDependencyLabel("ROOT");
                }
            } else { // altrimenti, trova il nodo padre e aggiungi il nodo corrente come suo figlio
                SyntacticNode parentNode = nodes.get(headIndex);
                SyntacticNode childNode = nodes.get(i);
                parentNode.addChild(childNode, depLabel);
            }
        }
        return root;
    }
}