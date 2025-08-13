package com.gmms;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

// Mattia Gallinaro
public class Adjectives {
    List<String> adjectives = null;

    public Adjectives(String[] words, StringBuilder sb) {
        adjectives = new ArrayList<>(Arrays.asList(words));
        Collections.shuffle(adjectives);
        if (adjectives.size() > 0)
            sb.append("Nome generato : " + adjectives.get(0));
    }

    public List<String> getAdjectives(int count) {
        if (count > adjectives.size())
            count = adjectives.size();
        Collections.shuffle(adjectives);
        List<String> pickedWords = adjectives.subList(0, count);
        return pickedWords;
    }
}