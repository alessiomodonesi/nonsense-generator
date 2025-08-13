package com.gmms;
// Alessio Modonesi

import java.util.List;
import java.util.Map;

public class Analyzer {
    public static String analyzeSentence(String sentenceDesc) throws Exception {
        Map<String, List<String>> syntacticTree;
        // Invocazione della funzione getSyntaxAnalysis dalla classe ApiCaller
        return ApiCaller.getSyntaxAnalysis(sentenceDesc);
    }
}