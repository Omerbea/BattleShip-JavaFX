//import GameParser.BattleShipGame;
import javafx.beans.property.SimpleStringProperty;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Player {

    class PlayerStatistics {
        private int score = 0;
        private int missNum = 0;
        private long avargeTimeTurn = 0;
        private long timeTurn =0;
        private int turns =0 ;
        private int hits =0;
        private int numofMines =0;
        private SimpleStringProperty propScoreCurrentPlayer = new SimpleStringProperty("0");
        private SimpleStringProperty propHitCurrentPlayer = new SimpleStringProperty("0");
        private SimpleStringProperty propAverageTimeTurnCurrentPlayer = new SimpleStringProperty("0");
        private SimpleStringProperty propMissCurrntPlayer = new SimpleStringProperty("0");
        private SimpleStringProperty propNumOfTurnsCurrentPlayer = new SimpleStringProperty("0");
        public int getTurns() {
            return turns;
        }

        public int getHits() {
            return hits;
        }

        public int getNumofMines() {
            return numofMines ;
        }

        public void setNumofMines(int numofMines) {
            this.numofMines = numofMines;
        }

        public SimpleStringProperty getPropScoreCurrentPlayer() {
            return propScoreCurrentPlayer;
        }

        public SimpleStringProperty getPropAverageTimeTurnCurrentPlayer() {
            return propAverageTimeTurnCurrentPlayer;
        }

        public SimpleStringProperty getPropHitCurrentPlayer() {
            return propHitCurrentPlayer;
        }

        public SimpleStringProperty getPropMissCurrntPlayer() {
            return propMissCurrntPlayer;
        }

        public SimpleStringProperty getPropNumOfTurnsCurrentPlayer() {
            return propNumOfTurnsCurrentPlayer;
        }

        public int getMissNum() {
            return missNum;
        }

        public int getScore() {
            return score;
        }

        public String getAverageTimeTurn() {
            String hms = String.format("%02d:%02d",
                    TimeUnit.NANOSECONDS.toMinutes(this.avargeTimeTurn) - TimeUnit.HOURS.toMinutes(TimeUnit.NANOSECONDS.toHours(this.avargeTimeTurn)),
                    TimeUnit.NANOSECONDS.toSeconds(this.avargeTimeTurn) - TimeUnit.NANOSECONDS.toSeconds(TimeUnit.NANOSECONDS.toMinutes(this.avargeTimeTurn)));
            //hms.replaceFirst("^0+(?!$)", "");
            return hms;
        }

        public void incHits (){
            this.hits += 1;
            String test = String.valueOf(this.hits);
            propHitCurrentPlayer.set(String.valueOf(this.hits));
        }



        public void setAvarageTimeTurn(long time) {
            if (this.avargeTimeTurn == 0) {
                this.avargeTimeTurn = time;
                this.timeTurn = time;
                this.turns = 1;
            }
            else{
                this.turns++;
                this.timeTurn += time;
                long test = (this.timeTurn)/(this.turns);
                this.avargeTimeTurn = test;
            }
            propAverageTimeTurnCurrentPlayer.set(getAverageTimeTurn());
            System.out.print(getAverageTimeTurn());
            propNumOfTurnsCurrentPlayer.set(String.valueOf(this.turns));
        }

        public void incMissNum() {
            this.missNum += 1;
            propMissCurrntPlayer.set(String.valueOf(this.missNum));
        }

        public void incScore(int inc) {
            this.score += inc;
            propScoreCurrentPlayer.set((String.valueOf(this.score)));
        }
    }


    public SimpleStringProperty propScoreCurrentPlayer(){
        return this.playerStatistics.getPropScoreCurrentPlayer();
    }
    public SimpleStringProperty propHitCurrentPlayer(){
    return this.playerStatistics.getPropHitCurrentPlayer();
    }
    public SimpleStringProperty propAverageTimeTurnCurrentPlayer(){
        return this.playerStatistics.getPropAverageTimeTurnCurrentPlayer();
    }
    public SimpleStringProperty propMissCurrntPlayer(){
        return this.playerStatistics.getPropMissCurrntPlayer();
    }

    public SimpleStringProperty propNumOfTurnsCurrentPlayer(){
        return  this.playerStatistics.getPropNumOfTurnsCurrentPlayer();
    }
    int counterMine = 0;
    GameTool[][] myBoard ;
    char[][] rivalBoard ;
    String Name ;
    int numOfShip;
    private  boolean isAlive;
    private int size;
    private PlayerStatistics playerStatistics = new PlayerStatistics();
    private Map<String , LinkedList<GameTool>> playerGameTools = new HashMap<String , LinkedList<GameTool>>();

    public Map<String, LinkedList<GameTool>> getPlayerGameTools() {
        return this.playerGameTools;
    }

    public int getNumOfMines (){
        return this.playerStatistics.getNumofMines()- this.counterMine;

    }

    public void setNumOfMines(int num){
        this.playerStatistics.setNumofMines(num);
    }

    public String getName() {
        return Name;
    }

    public int getScore(){
        return  this.playerStatistics.getScore();
    }


    public int getMissNum(){
        return  playerStatistics.getMissNum();
    }

    public String getAvargeTime (){
        return  playerStatistics.getAverageTimeTurn();
    }

    public void setAvargeTimeTurn(long time) {
        playerStatistics.setAvarageTimeTurn(time);
    }
    /* name - player name
        size - size board
        newPlayreBoard -  is must be a valid before we call to the constractor*/
    public Player(String i_name, int i_size, GameTool [][] i_newPlayerBoard, int i_numOfship , Map<String ,LinkedList<GameTool>> gameTools , int i_numberOfMines) {
        Name = i_name;
        myBoard = i_newPlayerBoard;
        rivalBoard = new char[i_size][i_size];
        numOfShip = i_numOfship;
        isAlive = true;
        size = i_size;
        playerGameTools = gameTools;
        setNumOfMines(i_numberOfMines);

    }

    /* return "non" if no hit
    *  otherwise return the name of the GameTool : "mine" or "BattelShip"*/
    public ArrayList<String> whoFindThere(int row, int column) {
        ArrayList<String> result = new ArrayList<String>();
        if (myBoard[row][column] == null) {
            result.add("non");
            return result;
        }

        if (myBoard[row][column].getIsAlive()) {
            //result.add(myBoard[row][column].getSpecies());
            result.add((myBoard[row][column].getType()));
            result.add((myBoard[row][column].getTypeId()));
            result.add(myBoard[row][column].getCategory());
        }
        else{
            result.add("beenThere");
        }
        return result;
    }

    public int getHits(){
        return this.playerStatistics.hits;
    }

    public int getNumOfTurns(){
        return this.playerStatistics.turns;
    }

    public boolean iHaveMoreMine(){
        return  (2 > this.counterMine);
    }

    private  void updateStatisticsMyTurn (int row, int column, boolean iHit, String typeGameTool , int score){
        if (iHit){
            if (typeGameTool == "Mine"){
                playerStatistics.incHits();
            }
            else {
                playerStatistics.incScore(score);
                playerStatistics.incHits();
            }
        }
        else{
            playerStatistics.incMissNum();
        }
    }

    public boolean updateIMissMyTurn (int row, int column ){
        updateStatisticsMyTurn(row, column, false, null , 0);
        updateRivalBoard(row, column, false);
        return  true;
    }

    public boolean setMine (int row, int column){
        if (this.myBoard[row][column] != null){
            return  false;
        }

        this.myBoard[row][column] = new Mine("Mine");
        this.counterMine += 1 ;
        return  true;
    }

    public  boolean getIsAlive (){
        return  isAlive;
    }

    public void updateIHitMyTurn (int row, int column, String typeGameTool, int score){
        updateStatisticsMyTurn(row, column , true, typeGameTool, score);
        updateRivalBoard (row,column, true);
    }

    private boolean updateRivalBoard (int row, int column, boolean iHit){
        if (iHit) {
            rivalBoard[row][column] = 'X';
        }
        else {
            rivalBoard[row][column] = '-';
        }
        return true;
    }

   public String updateHitMe (ArrayList<Integer> cooredinates, boolean isMine){

        /* update at my board where hit me  */
        Position position = new Position(cooredinates.get(0), cooredinates.get(1));
        this.myBoard[position.row][position.column].updateHitMe(position);
        /*check if ship destroyed */
        if (! this.myBoard[position.row][position.column].getIsAlive()){
            numOfShip -= 1;
            //check if game over
            if (numOfShip <= 0){
                return "You destroyed the last ship, well done! Game Over...";
            }
            return "You destroyed a ship !!";

        }
        return "You hit in a ship! you have one more turn for that...";
    }

    public char [][] getMyBoardForPrint() {
        char [][] boardForPrint = new char[this.size][this.size];
        for (int row =0 ; row< size ; row++){
            for (int column =0 ; column < size ; column++){
                if (this.myBoard[row][column] != null) {
                    if (this.myBoard[row][column].isHitMyThere(new Position(row, column))) {
                        boardForPrint[row][column] = 'X';
                    } else {
                        boardForPrint[row][column] = this.myBoard[row][column].getMySing();
                    }
                }
                else{
                    boardForPrint[row][column] = ' ';
                }
            }
        }
        return  boardForPrint;
    }

    public char[][] getRivalBoard() {
        return rivalBoard;
    }


    public GameTool getGameToolByCoordinate(int row, int column) {
        return myBoard[row][column];
    }


}
