/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class MoveChecker {
    
    private final Board board;
    private final Move move;
    
    public MoveChecker(Board b, Move m){
        board = b;
        move = m;
    }
    
    //main check method
    public String checkMove(){
        String msg = "";
        if(checkMovePlacement()){
            List<Word> words = getWordsList();
            //Pour voir si les mots sont bien repris dans la liste
            for(int i = 0; i<words.size(); ++i)
                System.out.println(words.get(i).getWordString());
            List<Word> badWords = checkWordsInDictionnary(words);
            if(badWords.isEmpty()){
                return msg;
            } else {
                msg = "These words are not in the dictionnary : \n" + generateWordsString(badWords);
            }
        } else {
            msg = "Bad letter placement";
        }
        return msg;
    }
    
    //checks if words in given list are in dictionnary and returns a list of words that are not
    private List<Word> checkWordsInDictionnary(List<Word> words){
        ArrayList<Word> badWords = new ArrayList<>();
        for(Word word : words) {
            if(!word.isInDictionnary())
                badWords.add(word);
        }
        return badWords;
    }
    
    //generate a String of the words contained in the given list
    private String generateWordsString(List<Word> words) {
        String msg = "";
        for(Word w : words) {
            msg = msg + w.getWordString() + ", ";
        }
        msg = msg.substring(0,msg.length()-2);
        return msg;
    }
    
    //generates a list of played words
    public List<Word> getWordsList(){
        List<Word> list = new ArrayList<>();
        Word word = getMainWord();
        if(word.size() > 1)
            list.add(word);
        addSecondWords(list);
        return list;
    }
    
    private void addSecondWords(List<Word> list){
        if(checkAxis())
            addSecondWordsLeftToRight(list);
        else
            addSecondWordsUpToDown(list);
    }
    
    private void addSecondWordsUpToDown(List<Word> list){
        Word word;
        for(int i=0; i<move.size(); ++i){
            word = getSecondWordUpToDown(move.getCoordinate(i), move.getLetter(i));
            if(word.size() > 1)
                list.add(word);
        }
    }
    
    private void addSecondWordsLeftToRight(List<Word> list){
        Word word;
        for(int i=0; i<move.size(); ++i){
            word = getSecondWordLeftToRight(move.getCoordinate(i), move.getLetter(i));
            if(word.size() > 1)
                list.add(word);
        }
    }
    
    private Word getSecondWordLeftToRight(Coordinates startCoord, Letter l){
        Map<Coordinates, Letter> map = new LinkedHashMap<>();
        Coordinates wordFirstCoord = getFirstCoordLeft(startCoord);
        setLettersLeftToRightSecond(map, wordFirstCoord, l);
        return new Word(map);
    }
    
    private void setLettersLeftToRightSecond(Map<Coordinates, Letter> map, Coordinates wordFirstCoord, Letter l){
        int i = 0;
        int x = wordFirstCoord.getX();
        int y = wordFirstCoord.getY();
        boolean wordComplete = false;
        while(!wordComplete){
            if(board.getCase(x, y).isEmpty()){
                map.put(new Coordinates(x, y), l);
                ++i;
            }
            else
                map.put(new Coordinates(x, y), board.getCase(x, y).getLetter());
            ++x;
            if(x > 14 || (board.getCase(x, y).isEmpty() && i == 1))
                wordComplete = true;
        }
    }
    
    private Word getSecondWordUpToDown(Coordinates startCoord, Letter l){
        Map<Coordinates, Letter> map = new LinkedHashMap<>();
        Coordinates wordFirstCoord = getFirstCoordUp(startCoord);
        setLettersUpToDownSecond(map, wordFirstCoord, l);
        return new Word(map);
    }
    
    private void setLettersUpToDownSecond(Map<Coordinates, Letter> map, Coordinates wordFirstCoord, Letter l){
        int i = 0;
        int x = wordFirstCoord.getX();
        int y = wordFirstCoord.getY();
        boolean wordComplete = false;
        while(!wordComplete){
            if(board.getCase(x, y).isEmpty()){
                map.put(new Coordinates(x, y), l);
                ++i;
            }
            else
                map.put(new Coordinates(x, y), board.getCase(x, y).getLetter());
            ++y;
            if(y > 14 || (board.getCase(x, y).isEmpty() && i == 1))
                wordComplete = true;
        }
    }
    
    private Word getMainWord(){
        Coordinates moveStartCoordinate = move.getCoordinate(0);
        if(checkAxis())
            return getWordUpToDown(moveStartCoordinate);
        else
            return getWordLeftToRight(moveStartCoordinate);
    }
    
    private Word getWordLeftToRight(Coordinates startCoord){
        Map<Coordinates, Letter> map = new LinkedHashMap<>();
        Coordinates wordFirstCoord = getFirstCoordLeft(startCoord);
        setLettersLeftToRight(map, wordFirstCoord);
        return new Word(map);
    }
    
    private void setLettersLeftToRight(Map<Coordinates, Letter> map, Coordinates wordFirstCoord){
        int i = 0;
        int x = wordFirstCoord.getX();
        int y = wordFirstCoord.getY();
        boolean wordComplete = false;
        while(!wordComplete){
            if(board.getCase(x, y).isEmpty()){
                map.put(new Coordinates(x, y), move.getLetter(i));
                ++i;
            }
            else
                map.put(new Coordinates(x, y), board.getCase(x, y).getLetter());
            ++x;
            if(x > 14 || (board.getCase(x, y).isEmpty() && move.size() == i))
                wordComplete = true;
        }
    }
    
    private Coordinates getFirstCoordLeft(Coordinates startCoord){
        int x = startCoord.getX();
        int y = startCoord.getY();
        while(x-1 >= 0 && !board.getCase(new Coordinates(x-1, y)).isEmpty())
            --x;
        return new Coordinates(x, y);
    }
    
    private Word getWordUpToDown(Coordinates startCoord){
        Map<Coordinates, Letter> map = new LinkedHashMap<>();
        Coordinates wordFirstCoord = getFirstCoordUp(startCoord);
        setLettersUpToDown(map, wordFirstCoord);
        return new Word(map);
    }
    
    private void setLettersUpToDown(Map<Coordinates, Letter> map, Coordinates wordFirstCoord){
        int i = 0;
        int x = wordFirstCoord.getX();
        int y = wordFirstCoord.getY();
        boolean wordComplete = false;
        while(!wordComplete){
            if(board.getCase(x, y).isEmpty()){
                map.put(new Coordinates(x, y), move.getLetter(i));
                ++i;
            }
            else
                map.put(new Coordinates(x, y), board.getCase(x, y).getLetter());
            ++y;
            if(y > 14 || (board.getCase(x, y).isEmpty() && move.size() == i))
                wordComplete = true;
        }
    }
    
    private Coordinates getFirstCoordUp(Coordinates startCoord){
        int x = startCoord.getX();
        int y = startCoord.getY();
        while(y-1 >= 0 && !board.getCase(new Coordinates(x, y-1)).isEmpty())
            --y;
        return new Coordinates(x, y);
    }
    
    //Permet de vérifier la validité du placement d'un coup(move)
    private boolean checkMovePlacement(){
        if(board.isEmpty())
            return checkMoveFirstTurn();
        else
            return checkMoveNextTurns();
    }
    
    private boolean checkMoveFirstTurn(){
        boolean valid = move.size() > 1;
        if(valid)
            valid = checkOneLetterIsInTheCenter();
        if(valid)
            valid = checkLettersAreOnTheSameLine();
        if(valid) 
            valid = checkLettersTouches();
        return valid;
    }
    
    private boolean checkMoveNextTurns(){
        boolean valid = move.size() > 0;
        if(valid)
            valid = checkTouchALetterOnTheBoard();
        if(valid)
            valid = checkLettersAreOnTheSameLine();
        if(valid) 
            valid = checkLettersTouches();
        return valid;
    }
    
    private boolean checkTouchALetterOnTheBoard(){
        for(Map.Entry e : move.getEntrySet()){
            Coordinates c = (Coordinates)e.getKey();
            int x = c.getX(); 
            int y = c.getY();
            if(checkUpCase(x, y))
                return true;
            if(checkRightCase(x, y))
                return true;
            if(checkDownCase(x, y))
                return true;
            if(checkLeftCase(x, y))
                return true;
        }    
        return false;
    }
    
    //Vérifie la case du haut sauf si y est plus petit que 0
    private boolean checkUpCase(int x, int y){
        --y;
        if(y < 0)
            return false;
        else
            return board.getCase(x, y).getLetter() != null;
    }
    
    //Vérifie la case de droite sauf si x est plus grand que 14
    private boolean checkRightCase(int x, int y){
        ++x;
        if(x > 14)
            return false;
        else
            return board.getCase(x, y).getLetter() != null;
    }
    
    //Vérifie la case du bas sauf si y est plus grand que 14
    private boolean checkDownCase(int x, int y){
        ++y;
        if(y > 14)
            return false;
        else
            return board.getCase(x, y).getLetter() != null;
    }
    
    //Vérifie la case de gauche sauf si x est plus petit que 0
    private boolean checkLeftCase(int x, int y){
        --x;
        if(x < 0)
            return false;
        else
            return board.getCase(x, y).getLetter() != null;
    }
    
    private boolean checkOneLetterIsInTheCenter(){
        for(Map.Entry e : move.getEntrySet()){
            Coordinates c = (Coordinates)e.getKey();
            if(c.getX() == 7 & c.getY() == 7)
                return true;
        }
        return false;
    }
    
    private boolean checkLettersAreOnTheSameLine(){
        if(checkAxis())
            return true;
        if(checkOrdinates())
            return true;
        return false;
    }
    
    private boolean checkAxis(){
        int i = 0;
        int x = -1;
        for(Map.Entry e : move.getEntrySet()){
            Coordinates c = (Coordinates) e.getKey();
            if(i == 0)
                x = c.getX();
            else
                if(x != c.getX())
                    return false;
            ++i;
        }
        return true;
    }
    
    private boolean checkOrdinates(){
        int i = 0;
        int y = -1;
        for(Map.Entry e : move.getEntrySet()){
            Coordinates c = (Coordinates) e.getKey();
            if(i == 0)
                y = c.getY();
            else
                if(y != c.getY())
                    return false;
            ++i;
        }
        return true;
    }
    
    private boolean checkLettersTouches(){
        Coordinates firstCoordinate = move.getCoordinate(0);
        if(checkAxis())
            return checkLettersTouchesDown(firstCoordinate);
        else
            return checkLettersTouchesRight(firstCoordinate);
    }
    
    private boolean checkLettersTouchesDown(Coordinates firstCoordinate){
        boolean result = true;
        int x = firstCoordinate.getX();
        int y = firstCoordinate.getY();
        for(int i=1; i<move.size(); ++i){
            ++y;
            if(y != move.getCoordinate(i).getY()){
                if(board.getCase(x, y).getLetter() == null)
                    return false;
                else
                    --i;
            }
        }
        return result;
    }
    
    private boolean checkLettersTouchesRight(Coordinates firstCoordinate){
        boolean result = true;
        int x = firstCoordinate.getX();
        int y = firstCoordinate.getY();
        for(int i=1; i<move.size(); ++i){
            ++x;
            if(x != move.getCoordinate(i).getX()){
                if(board.getCase(x, y).getLetter() == null)
                    return false;
                else
                    --i;
            }
        }
        return result;
    }

}
