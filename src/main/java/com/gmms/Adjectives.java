package com.gmms;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
// Mattia Gallinaro
public class Adjectives {
    List<String> words;
    Random random;

    //da testare
    public String Adjectives(){
        return words.get(random.nextInt(words.size()));
    }

    public Adjectives(String[] words){
        random = new Random();
    }
    
    public String[] getAdjectives(int count){
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