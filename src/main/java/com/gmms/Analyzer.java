package com.gmms;
// Alessio Modonesi

public class Analyzer {
    public static String analyzeSentence(String sentenceDesc) throws Exception {
        // Invocazione della funzione getSyntaxAnalysis dalla classe ApiCaller
        return ApiCaller.getSyntaxAnalysis(sentenceDesc);
    }
}