/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author alex
 */
public class Word {
    
    private final Map<Coordinates, Letter> wordMap;
    private final String wordString;
    
    public Word(Map<Coordinates, Letter> w){
        wordMap = w;
        wordString = setWordString(wordMap);
    }
    
    private String setWordString(Map<Coordinates, Letter> w){
        String word = "";
        for(Entry e : w.entrySet()){    
            Letter l = (Letter)e.getValue();
            word += l.getCharacter();
        }
        return word;
    }
    
    public Map<Coordinates, Letter> getWordMap() {
        return wordMap;
    }
    
    public String getWordString(){
        return wordString;
    }
    
    public void addLetter(Letter l, Coordinates c){
        wordMap.put(c, l);
    }
    
    public int size(){
        return wordMap.size();
    }
    
    public boolean isInDictionnary() {
        Set<String> dictionnary = Game.getDictionnary();
        return dictionnary.contains(this.wordString);
    }
}
