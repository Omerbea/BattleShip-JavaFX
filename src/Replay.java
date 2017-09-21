import java.util.LinkedList;
import java.util.Map;

public class Replay {

    private String playerName;
    private int score;
    private int Hits;
    private int Miss;
    private String avergeTimeTurn;
    private int numOfTurns;
    private int rivalMines;
    Map<String, LinkedList<GameTool>> rivalGetGameTool;
    private GameTool[][] board1;
    private GameTool[][] board2;
    private int row;
    private int column ;

    public int getRow() {
        return row;
    }

    public String getAvergeTimeTurn() {
        return avergeTimeTurn;
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

    public GameTool[][] getBoard1() {
        return board1;
    }

    public GameTool[][] getBoard2() {
        return board2;
    }

    public void setBoard1(GameTool[][] board1) {
        this.board1 = board1;
    }

    public void setAvergeTimeTurn(String avergeTimeTurn) {
        this.avergeTimeTurn = avergeTimeTurn;
    }

    public void setBoard2(GameTool[][] board2) {
        this.board2 = board2;
    }

    public void setAvargeTimeTurn(String avargeTimeTurn) {
        this.avergeTimeTurn = avargeTimeTurn;
    }

    public void setHits(int hits) {
        Hits = hits;
    }

    public void setMiss(int miss) {
        Miss = miss;
    }

    public void setNumOfTurns(int numOfTurns) {
        this.numOfTurns = numOfTurns;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setRivalGetGameTool(Map<String, LinkedList<GameTool>> rivalGetGameTool) {
        this.rivalGetGameTool = rivalGetGameTool;
    }

    public void setRivalMines(int rivalMines) {
        this.rivalMines = rivalMines;
    }

    public void setScore(int score) {
        this.score = score;
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
        return score;
    }

}
