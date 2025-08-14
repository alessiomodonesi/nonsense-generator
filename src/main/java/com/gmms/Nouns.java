package com.gmms;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// Mattia Gallinaro
public class Nouns {
    List<String> nouns = null;

    public Nouns(List<String> words, StringBuilder sb) {
        nouns = new ArrayList<>(words);
        Collections.shuffle(nouns);
        if (nouns.size() > 0)
            sb.append("Nome generato : " + nouns.get(0));
    }

    public List<String> getNouns(int count) {
        if (count > nouns.size())
            count = nouns.size();
        Collections.shuffle(nouns);
        List<String> pickedWords = nouns.subList(0, count);
        return pickedWords;
    }
}