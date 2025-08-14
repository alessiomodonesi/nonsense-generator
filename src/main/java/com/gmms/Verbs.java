package com.gmms;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// Mattia Gallinaro
public class Verbs {
    List<String> verbs = null;

    public Verbs(List<String> words, StringBuilder sb) {
        verbs = new ArrayList<>(words);
        Collections.shuffle(verbs);
        if (verbs.size() > 0)
            sb.append("Verbo generato : " + verbs.get(0));
    }

    public List<String> getVerbs(int count) {
        if (count > verbs.size())
            count = verbs.size();
        Collections.shuffle(verbs);
        List<String> pickedWords = verbs.subList(0, count);
        return pickedWords;
    }
}