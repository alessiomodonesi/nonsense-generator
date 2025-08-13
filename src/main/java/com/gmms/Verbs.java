package com.gmms;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

// Mattia Gallinaro
public class Verbs {
    List<String> verbs = null;

    public Verbs(String[] words, StringBuilder sb) {
        verbs = new ArrayList<>(Arrays.asList(words));
        Collections.shuffle(verbs);
        if (verbs.size() > 0)
            sb.append("Nome generato : " + verbs.get(0));
    }

    public List<String> getVerbs(int count) {
        if (count > verbs.size())
            count = verbs.size();
        Collections.shuffle(verbs);
        List<String> pickedWords = verbs.subList(0, count);
        return pickedWords;
    }
}