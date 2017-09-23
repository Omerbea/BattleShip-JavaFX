import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringPropertyBase;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Replay {

    private String playerName;
    private SimpleStringProperty propPlayerName= new SimpleStringProperty();
    private int score;
    private SimpleStringProperty propScore =  new SimpleStringProperty();
    private int Hits;
    private SimpleStringProperty propHits = new SimpleStringProperty();
    private int Miss;
    private SimpleStringProperty propMiss = new SimpleStringProperty();
    private String avergeTimeTurn;
    private SimpleStringProperty propAvergeTimeTurn = new SimpleStringProperty();
    private int numOfTurns;
    private SimpleStringProperty propNumOfTurns = new SimpleStringProperty();
    private int rivalMines;
    private SimpleStringProperty propRivalMines = new SimpleStringProperty();
    Map<String, LinkedList<GameTool>> rivalGetGameTool;
    private char[][] playerBoard;
    private char[][] rivalBoard;
    private int row;
    private int column ;

    private boolean isMine = false;

    public void setIsMine (boolean flag){
      isMine = flag;
    }

    public boolean getIsMine (){
        return this.isMine;
    }
    public int getRow() {
        return row;
    }



    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public char[][] getPlayerBoard() {
        return this.playerBoard;
    }


    public void setPlayerBoard(char[][] board) {
        char [][] newBoard = new char[board.length][];
        for (int i =0 ; i < board.length; i++){
            newBoard[i] = Arrays.copyOf(board[i], board[i].length);
        }
        this.playerBoard = newBoard;
    }

    public void setAvergeTimeTurn(String avergeTimeTurn) {
        this.avergeTimeTurn = avergeTimeTurn;
        this.propAvergeTimeTurn.set(avergeTimeTurn);
    }

    public char[][] getRivalBoard() {
        return rivalBoard;
    }

    public void setRivalBoard(char[][] board) {
        char [][] newBoard = new char[board.length][];
        for (int i =0 ; i < board.length; i++){
            newBoard[i] = Arrays.copyOf(board[i], board[i].length);
        }
        this.rivalBoard = newBoard;
    }

    public void setAvargeTimeTurn(String avargeTimeTurn) {
        this.avergeTimeTurn = avargeTimeTurn;
        this.propAvergeTimeTurn.set(avargeTimeTurn);
    }

    public void setHits(int hits) {
        Hits = hits;
        this.propHits.set(String.valueOf(hits));
    }

    public void setMiss(int miss) {
        Miss = miss;
        this.propHits.set(String.valueOf(miss));
    }

    public void setNumOfTurns(int numOfTurns) {
        this.numOfTurns = numOfTurns;
        this.propNumOfTurns.set(String.valueOf(numOfTurns));
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        propPlayerName.set(playerName);
    }

    public void setRivalGetGameTool(Map<String, LinkedList<GameTool>> rivalGetGameTool) {
        this.rivalGetGameTool = rivalGetGameTool;
    }

    public void setRivalMines(int rivalMines) {
        this.rivalMines = rivalMines;
        this.propRivalMines.set(String.valueOf(rivalMines));
    }

    public void setScore(int score) {
        this.score = score;
        this.propScore.set(String.valueOf(score));
    }

    public int getRivalMines() {
        return rivalMines;
    }

    public Map<String, LinkedList<GameTool>> getRivalGetGameTool() {
        return rivalGetGameTool;
    }

    public String getAvargeTimeTurn() {
        return avergeTimeTurn;
    }

    public int getHits() {
        return Hits;
    }

    public int getMiss() {
        return Miss;
    }

    public int getNumOfTurns() {
        return numOfTurns;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return this.score;
    }

}
