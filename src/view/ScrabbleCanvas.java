/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package view;

import control.GameManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.*;

/**
 *
 * @author Alex
 */
public class ScrabbleCanvas extends Canvas {
    
    private final Board board;
    private int score = 0;
    private final UsableLetters usableLetters;
    private final GameManager gm;
    private final GraphicsContext gc = this.getGraphicsContext2D();
    
    private static final int CASESIZE = 45;
    private static final int PADDING = 10;
    
    LetterShape selection;
    private int startX, startY;
    private int currentX, currentY;
    private int endX, endY;
    private int shapeStartX, shapeStartY;
    private int diffX, diffY;
    
    public ScrabbleCanvas(Board b, UsableLetters usableLetters, GameManager gm){
        board = b;
        this.usableLetters = usableLetters;
        this.gm = gm;
        //this.usableCases = usableCases;
        layout();
        handlers();
    }
    
    public final void layout(){
        setWidth(getBoardSize() + 2*PADDING + 150);
        setHeight(getBoardSize() + CASESIZE + 6*PADDING);
    }    
    
    public void paint(){
        gc.setFill(Color.DARKCYAN);
        gc.fillRect(0, 0, getWidth(), getHeight());
        paintBoard();
        paintRack();
        paintGarbage();
        paintLetters();
        paintScore();
        paintBag();
    }
    
    private void paintBag(){
        int x = getBoardSize() + 2*PADDING;
        int y = 4*PADDING;
        int letters = gm.getBag().size();
        gc.setStroke(Color.BLACK);
        gc.strokeText("Lettres restantes : "+ letters, x, y);
    }
    
    private void paintScore(){
        int score = gm.getGameScore();
        int x = getBoardSize() + 2*PADDING;
        int y = 2*PADDING;
        gc.setStroke(Color.BLACK);
        gc.strokeText("Score : "+score, x, y);
    }
    
    private void paintGarbage(){
        gc.setFill(Color.WHITE);
        for(int i=0; i<7; ++i){
            gc.fillRect(getGarbagePlacePosX(i), getGarbagePlacePosY(), CASESIZE, CASESIZE);
            gc.strokeRect(getGarbagePlacePosX(i), getGarbagePlacePosY(), CASESIZE, CASESIZE);
        }
        gc.strokeText("EXCHANGER", getGarbagePlacePosX(0), getGarbagePlacePosY()+CASESIZE+2*PADDING);
    }
    
    private int getGarbagePlacePosX(int i){
        return PADDING + getRackWidth()+(i+1)*CASESIZE;
    }
    
    private int getGarbagePlacePosY(){
        return 2*PADDING + getBoardSize();
    }
    
    public void paintLetters(){
        for(LetterShape ls : usableLetters.getList())
            paintUsableLetter(ls);
    }

    private void paintUsableLetter(LetterShape ls){
        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(ls.getX(), ls.getY(), ls.getSize(), ls.getSize());
        gc.setStroke(Color.RED);
        gc.strokeRect(ls.getX(), ls.getY(), ls.getSize(), ls.getSize());
        gc.setStroke(Color.BLACK);
        gc.strokeText(String.valueOf(ls.getLetter().getCharacter()), getCharacterPosX(ls.getX()), getCharacterPosY(ls.getY()));
        gc.strokeText(String.valueOf(ls.getLetter().getValue()), getValuePosX(ls.getX()), getValuePosY(ls.getY()));
    }    
    
    private void paintLetter(LetterShape ls){
        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(ls.getX(), ls.getY(), ls.getSize(), ls.getSize());
        gc.strokeRect(ls.getX(), ls.getY(), ls.getSize(), ls.getSize());
        gc.strokeText(String.valueOf(ls.getLetter().getCharacter()), getCharacterPosX(ls.getX()), getCharacterPosY(ls.getY()));
        gc.strokeText(String.valueOf(ls.getLetter().getValue()), getValuePosX(ls.getX()), getValuePosY(ls.getY()));
    }
    
    public void paintBoard(){
        for(int x=0; x<15; ++x)
            for(int y=0; y<15; ++y){
                Case currentCase = board.getCase(x, y);
                if(currentCase.getLetter() == null)
                    paintCase(currentCase);
                else
                    paintLetter(new LetterShape(getCasePosX(x), getCasePosY(y), currentCase.getLetter(), CASESIZE));
            }
    }
    
    public void paintCase(Case c){
        gc.setFill(getCaseColor(c));
        gc.fillRect(getCasePosX(c.getX()), getCasePosY(c.getY()), CASESIZE, CASESIZE);
        gc.strokeRect(getCasePosX(c.getX()), getCasePosY(c.getY()), CASESIZE, CASESIZE);
        gc.strokeText(getCaseText(c), (getCasePosX(c.getX())+CASESIZE/3), (getCasePosY(c.getY())+CASESIZE/2));
    }
    
    private String getCaseText(Case c) {
        if(c instanceof OrdinaryCase)
            return "";
        else if(c instanceof LetterDoubleCase)
            return "LD";
        else if(c instanceof LetterTripleCase)
            return "LT";
        else if(c instanceof WordDoubleCase)
            return "WD";
        else if(c instanceof WordTripleCase)
            return "WT";
        else
            return ""; 
    }
    
    public Color getCaseColor(Case c){
        if(c instanceof OrdinaryCase)
            return Color.GREEN;
        else if(c instanceof LetterDoubleCase)
            return Color.LIGHTBLUE;
        else if(c instanceof LetterTripleCase)
            return Color.BLUE;
        else if(c instanceof WordDoubleCase)
            return Color.PINK;
        else if(c instanceof WordTripleCase)
            return Color.RED;
        else
            return Color.CHOCOLATE;      
    }
    
    public int getCasePosX(int indexX){
        return indexX*CASESIZE + PADDING;
    }
    
    public int getCasePosY(int indexY){
        return indexY*CASESIZE + PADDING;
    }
    
    public void paintRack(){
        gc.setFill(Color.WHITE);
        for(int i=0; i<7; ++i){
            gc.fillRect(getRackPlacePosX(i), getRackPlacePosY(), CASESIZE, CASESIZE);
            gc.strokeRect(getRackPlacePosX(i), getRackPlacePosY(), CASESIZE, CASESIZE);
        }
        gc.strokeText("RACK", getRackX0(), getRackY0()+CASESIZE+2*PADDING);
    }
    
    public static int getRackPlacePosX(int index){
        return index*CASESIZE + PADDING;
    }
    
    public static int getRackPlacePosY(){
        return 2*PADDING + getBoardSize();
    }
    
    public int getCharacterPosX(int letterPosX){
        return letterPosX + CASESIZE/4;
    }
    
    public int getCharacterPosY(int letterPosY){
        return letterPosY + CASESIZE/2;
    }
    
    public int getValuePosX(int letterPosX){
        return letterPosX + CASESIZE/2;
    }
    
    public int getValuePosY(int letterPosY){
        return letterPosY + CASESIZE-CASESIZE/5; 
    }
    
    public static int getBoardSize(){
        return 15*CASESIZE;
    }
    
    public static int getRackWidth(){
        return 7*CASESIZE;
    }
    
    public static int getCaseSize(){
        return CASESIZE;
    }
    
    public static int getBoardXY0(){
        return PADDING;
    }
    
    public static int getRackX0(){
        return PADDING;
    }
    
    public static int getRackY0(){
        return 2*PADDING + getBoardSize();
    }
    
    private void handlers(){
        mousePressed();
        mouseDragged();
        mouseReleased();
    }
    
    public void mouseReleased(){
        this.setOnMouseReleased((MouseEvent m)->{
            if(selection != null){
                setEndXY((int)m.getX(), (int)m.getY());
                if(onAnotherShapeThanSelection(endX, endY))
                    switchShapeWithSelectionFirstPlace(getOtherShapeThanSelectionAt(endX, endY));
                else if(onTheBoard(endX, endY))
                    placeSelectionOnTheBoard(endX, endY);
                else if(onTheRack(endX, endY))
                    placeSelectionOnTheRack(endX, endY);
                else if(onTheGarbage(endX, endY))
                    placeSelectionOnTheGarbage(endX, endY);
                else
                    replaceSelectionToFirstPlace();
            }
        });
    }
    
    public void replaceSelectionToFirstPlace(){
        selection.setPos(shapeStartX, shapeStartY);
    }
    
    private void placeSelectionOnTheGarbage(int x, int y){
        int newX = getGarbagePlacePosX(getGarbageIndex(x));
        int newY = getGarbagePlacePosY();
        selection.setPos(newX, newY);
    }
    
    public void placeSelectionOnTheRack(int x, int y){
        int newX = getRackPlacePosX(getRackIndex(x));
        int newY = getRackPlacePosY();
        selection.setPos(newX, newY);
    }
    
    public static int getGarbageIndex(int posX){
        return (posX-PADDING-CASESIZE-getRackWidth())/CASESIZE;
    }
    
    public static int getRackIndex(int posX){
        return (posX-PADDING)/CASESIZE;
    }
    
    public static boolean onTheRack(int x, int y){
        return x>= getRackX0()
                && x<= getRackX0()+getRackWidth()-1
                && y>= getRackY0()
                && y<= getRackY0()+CASESIZE;                  
    }
    
    public static boolean onTheGarbage(int x, int y){
        return x>= getGarbageX0()
                && x<= getGarbageX0()+getRackWidth()-1
                && y>= getRackY0()
                && y<= getRackY0()+CASESIZE;                  
    }
    
    private static int getGarbageX0(){
        return PADDING + getRackWidth() + CASESIZE;
    }
    
    public void placeSelectionOnTheBoard(int x, int y){
        if(caseIsUsable(getBoardIndexX(x), getBoardIndexY(y))){
            int newX = getCasePosX(getBoardIndexX(x));
            int newY = getCasePosY(getBoardIndexY(y));
            selection.setPos(newX, newY);
        }
        else
            replaceSelectionToFirstPlace();
    }
    
    public boolean caseIsUsable(int indexX, int indexY){
        return board.getCase(indexX, indexY).getLetter() == null;//demeter
    }
    
    //probleme si posX pas dans le plateau surtout en static
    public static int getBoardIndexX(int posX){
        return (posX-PADDING)/CASESIZE;
    }
    
    public static int getBoardIndexY(int posY){
        return (posY-PADDING)/CASESIZE;
    }
    
    public static boolean onTheBoard(int x, int y){
        return x >= getBoardXY0()
                && x<= getBoardXY0() + getBoardSize()-1
                && y >= getBoardXY0()
                && y<= getBoardXY0() + getBoardSize()-1;
    }
    
    public void switchShapeWithSelectionFirstPlace(LetterShape ls){
        int tempX = ls.getX();
        int tempY = ls.getY();
        ls.setPos(shapeStartX, shapeStartY);
        selection.setPos(tempX, tempY);
    }
    
    public boolean onAnotherShapeThanSelection(int x, int y){
        return getOtherShapeThanSelectionAt(x, y) != null;
    }
    
    public LetterShape getOtherShapeThanSelectionAt(int x, int y){
        for(LetterShape ls : usableLetters.getList())
            if(ls != selection)
                if(mouseInShape(x, y, ls))
                    return ls;
        return null;
    }
    
    public void setEndXY(int x, int y){
        endX = x;
        endY = y;
    }
    
    public void mouseDragged(){
        this.setOnMouseDragged((MouseEvent m)->{
            if(selection != null){
                setCurrentXY((int)m.getX(), (int)m.getY());
                selection.setPos(currentX-diffX, currentY-diffY);
            }
        });
    }
    
    public void setCurrentXY(int x, int y){
        currentX = x;
        currentY = y;
    }
    
    public void setDiffXY(){
        diffX = startX - selection.getX();
        diffY = startY - selection.getY();
    }
    
    public void mousePressed(){
        this.setOnMousePressed((MouseEvent m)->{
            setStartXY((int)m.getX(), (int)m.getY());
            selection = getShapeAt(startX, startY); 
            if(selection != null){
                setSelectionAtFirstPlan();
                setDiffXY();
                setShapeStartXY(selection.getX(), selection.getY());
            }
        });
    }
    
    public void setSelectionAtFirstPlan(){
        usableLetters.remove(selection);
        usableLetters.add(selection);
    }
    
    public void setShapeStartXY(int x, int y){
        shapeStartX = x;
        shapeStartY = y;
    }
    
    public LetterShape getShapeAt(int x, int y){
        for(LetterShape ls : usableLetters.getList())
                if(mouseInShape(startX, startY, ls))
                    return ls;
        return null;
    }
    
    public void setStartXY(int x, int y){
        startX = x;
        startY = y;
    }
    
    public boolean mouseInShape(int x, int y, LetterShape ls){
        return x>=ls.getX()
                && x<=ls.getX()+ls.getSize()-1
                && y>=ls.getY()
                && y<=ls.getY() + ls.getSize()-1;
    }
    
}
