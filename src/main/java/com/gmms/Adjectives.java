package com.gmms;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// Mattia Gallinaro
public class Adjectives {
    List<String> adjectives = null;

    public Adjectives(List<String> words, StringBuilder sb) {
        adjectives = new ArrayList<>(words);
        Collections.shuffle(adjectives);
        if (adjectives.size() > 0)
            sb.append("Aggettivo generato : " + adjectives.get(0));
    }

    public List<String> getAdjectives(int count) {
        if (count > adjectives.size())
            count = adjectives.size();
        Collections.shuffle(adjectives);
        List<String> pickedWords = adjectives.subList(0, count);
        return pickedWords;
    }
}