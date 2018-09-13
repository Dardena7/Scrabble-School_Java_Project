/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Alex
 */
public class Move {
    private final Map<Coordinates, Letter> map = new LinkedHashMap<>();
    private int size = 0;
    
    public Move(){};
    
    public Map<Coordinates, Letter> getMap(){
        return map;
    }
    
    public Set<Entry<Coordinates, Letter>> getEntrySet(){
        return map.entrySet();
    }
    
    public Coordinates getCoordinate(int i){
        if(i >= size || i<0)
            throw new RuntimeException("index is out of bound");
        int j = 0;
        Coordinates res = null;
        for(Entry e : map.entrySet()){
            if(i == j)
                res = (Coordinates)e.getKey();
            ++j;
        }
        return res;
    }
    
    public Letter getLetter(int i){
        if(i >= size || i<0)
            throw new RuntimeException("index is out of bound");
        int j = 0;
        Letter res = null;
        for(Entry e : map.entrySet()){
            if(i == j)
                res = (Letter)e.getValue();
            ++j;
        }
        return res;
    }
    
    public void add(Letter l, Coordinates c){
        map.put(c, l);
        ++size;
    }
    
    public void remove(int i){
        if(i >= size || i<0)
            throw new RuntimeException("index is out of bound");
        int j = 0;
        Coordinates c = null;
        for(Entry e : map.entrySet()){
            if(i == j)
                c = (Coordinates)e.getKey();
            ++j;
        }
        map.remove(c);
        --size;
    }
    
    public void clear(){
        map.clear();
        size = 0;
    }
    
    public int size(){
        return size;
    }
    
    @Override
    public String toString(){
        String res = "Mot: ";
        for(Entry e : map.entrySet())
            res += e.getKey()+" "+e.getValue()+" ,";
        return res;
    }
}
