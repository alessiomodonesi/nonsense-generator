package com.gmms;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
// Mattia Gallinaro
public class Verbs {
    List<String> words;
    Random random;

    //da testare
    public String Verbs(){
        return words.get(random.nextInt(words.size()));
    }

    public Verbs(String[] words){
        random = new Random();
    }
    
    public String[] getVerbs(int count){
        String[] pickedWords = new String[count];
        List<String> copyList = new ArrayList<String>(words);
        for(int i  = 0; i < count ; i++){
            int pos = random.nextInt(copyList.size());
            pickedWords[i] = copyList.get(pos);
            copyList.remove(pos);
        } 
        return pickedWords;
    }
}