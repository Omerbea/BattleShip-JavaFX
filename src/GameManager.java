import GameParser.BattleShipGame;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import sun.applet.Main;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;
//comment1--
public class GameManager {

    class GameStatistic {
        int howManyTurn =0 ;
        long startTime = System.nanoTime();

        public int getHowManyTurn() {
            return howManyTurn;
        }

        public  String getGameTime () {
            long timeNano= ((System.nanoTime() - startTime));
            String hms = String.format("%02d:%02d",
                     TimeUnit.NANOSECONDS.toMinutes(timeNano) - TimeUnit.HOURS.toMinutes(TimeUnit.NANOSECONDS.toHours(timeNano)),
                    TimeUnit.NANOSECONDS.toSeconds(timeNano) - TimeUnit.NANOSECONDS.toSeconds(TimeUnit.NANOSECONDS.toMinutes(timeNano)));
            return hms;
        }
        public  void incrementTurn (){
            howManyTurn += 1;
        }
    }

    private  GameStatistic gameStatistic ;
    private ArrayList<String> mainMenu = new ArrayList<>();
    private  boolean isGameRun = false;
    private  boolean isGameLoaded = false;
    private boolean isGameOver = false;
    private  Factory factory ;
    private Player []players;
    private int whoPlay =0;
    private SimpleStringProperty propWhoPlay = new SimpleStringProperty("Player 1");



    public SimpleStringProperty propWhoPlayProperty() {
        return propWhoPlay;
    }

    private Validator validator ;
    private UserInterface userInterface = new UserInterface();
    public GameManager(){
        setMainMenu();
        gameStatistic = new GameStatistic();
    }

  /*  public static void main(String [] args) throws Exception{
        GameManager gameManager = new GameManager();
        gameManager.start();
    }*/
    public SimpleStringProperty propScoreCurrentPlayer(int player){
        return players[player].propScoreCurrentPlayer();
    }
    public SimpleStringProperty propHitCurrentPlayer(int player){
        return players[player].propHitCurrentPlayer();
    }
    public SimpleStringProperty propAverageTimeTurnCurrentPlayer(int player){
        return players[player].propAverageTimeTurnCurrentPlayer();
    }
    public SimpleStringProperty propMissCurrntPlayer(int player){
        return players[player].propMissCurrntPlayer();
    }

    public SimpleStringProperty propNumOfTurnsCurrentPlayer(int player){
        return players[player].propNumOfTurnsCurrentPlayer();
    }

    public int getNumOfMinesFromPlayer (int player){
        return this.players[player].getNumOfMines();
    }

    public Map<String, LinkedList<GameTool>>  getGameTool (int player){
        return this.players[player].getPlayerGameTools();
    }

  private void  setMainMenu(){
        this.mainMenu.add("read file");  //1
        this.mainMenu.add("start game"); //2
        this.mainMenu.add("show game state"); //3
        this.mainMenu.add("play your turn"); //4
        this.mainMenu.add("statistics"); //5
        this.mainMenu.add("restart"); //6
        this.mainMenu.add("add mine"); //7
        this.mainMenu.add("quit game"); //8
    }

    private void start() {
        this.userInterface.printMenu(mainMenu,"middle");
        int input = -1;

        while (true) {
            try {
                input = this.userInterface.getMenuOption();
            } catch (Exception e) {
                //send it to the console
                this.backToMainMenu("Please enter number");
                input = -1;
            }
            switch (input) {
                case 1:
                    if (this.isGameRun){
                        backToMainMenu("the game already run");
                        continue;
                    }
                    userInterface.printMassage("Please Enter full path for xml file :");
                    String xmlpath = new Scanner(System.in).nextLine();
                    if (true/*this.loadGame(xmlpath)*/) {
                        backToMainMenu("your file is loaded...");

                    }
                    else{
                    }
                    break;
                case 2:
                    this.gameStart();
                    break;
                case 3:
                    this.showStatusGame();
                    break;
                case 4:
                   // this.executeMove();
                    break;
                case 5:
                    this.showStatistic();
                    break;
                case 6:
                    this.restartGame();
                    break;
                case 7:
                   // this.addMine();
                    break;
                case 8:
                    this.quiteGame();
                    return;
                default:
                    // send it to the console
                    System.out.println("Please choose number 1-7");
            }
        }
    }



    public Boolean isFileValid(String fileName ) throws Exception {
            Boolean fileValid = false;
            if (this.isGameRun){
                 return false;
            }
            if (this.loadGame(fileName)) {
                fileValid =  true;
            }
            return fileValid;
    }

    public boolean restartGame (){
        if (! this.isGameRun){
            backToMainMenu("cannot do restart because no game run..");
        }
        userInterface.printMassage("restarting...");
        this.whoPlay=0;
        this.propWhoPlay.set ("Player 1");
        this.players = null;
        this.gameStatistic = null;
        this.isGameRun =false;
        this.isGameLoaded= false;
        userInterface.printMassage(("restart successfully!"));
        this.userInterface.printMenu(mainMenu,"middle");

        return true;
    }

    public boolean addMine (int row , int column) throws Exception {
/*        if (!this.isGameRun){
            backToMainMenu("cannot add mine when no game run...");
            return  false;
        }
        if (! players[whoPlay].iHaveMoreMine()){
            backToMainMenu("You don't have anymore mines to set");
            return false;
        }
        while (true) {
            userInterface.printMassage("please insert coordinates ");*/
            Mine mine = new Mine("Mine");
/*            try {
                ArrayList<Integer> coordinates = userInterface.waitForCoordinates();
                //Fixing user row to start from 0
                coordinates.set(0 , coordinates.get(0) - 1);
                if ( !validator.isCordinateInRange(coordinates.get(0)) || ! validator.isCordinateInRange((coordinates.get(1)))){
                    userInterface.printMassage("the coordinates not in the range, please try anther coordinates in range...");
                    continue;
                }*/

                mine.setCoordinates(row,column);

                // check if mine can be set there


                if (validator.canGameToolBePlaced(mine, players[whoPlay].myBoard)) {
                    if (players[whoPlay].setMine(row, column)) {
                        //backToMainMenu("set mine! ");
                        //break;
                    }
                userInterface.printMassage(" canot set Mine in these place. please insert new coordinates... ");

        }
        return true;
    }
    public   boolean quiteGame(){
        userInterface.printMassage("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        userInterface.printMassage("Hope you enjoyed:)");
        userInterface.printMassage("Thank you for playing... see you next time ;)");
        userInterface.printMassage("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        userInterface.printMassage("Quit!");
        return true;
    }
    public boolean showStatistic () {
        if (!this.isGameRun) {
            this.backToMainMenu("no game run...");
            return  false;
        }
        userInterface.printMassage(" ");
        userInterface.printMassage("Statistics: ");
        userInterface.printMassage("Number of Turns: " + gameStatistic.howManyTurn);
        userInterface.printMassage("Game Time: " + gameStatistic.getGameTime());
        showStatisticOnePlayer(whoPlay);
        showStatisticOnePlayer(1-whoPlay);
        backToMainMenu(" ");
        return  true;
    }

    private void showStatisticOnePlayer (int player){
        //scure
        userInterface.printMassage( players[player].getName() +  " -- Score: " +  players[player].getScore());
        //miss
        userInterface.printMassage( players[player].getName() +  " -- Miss:  "+  players[player].getMissNum());
        //Average time
        userInterface.printMassage( players[player].getName() + " -- Average time: " +  players[player].getAvargeTime());
    }
    private void backToMainMenu (String msg){
                userInterface.printMassage(msg);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                }
                catch (InterruptedException e){

                }
                userInterface.printMenu(this.mainMenu, "middel");
    }

    public boolean executeMove(int row , int column) {
        long finishTime = 0;
        long startTime =0;
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(0 , row);
        coordinates.add(1 , column);
/*
            if (! validator.isCordinateInRange(coordinates.get(0)) || ! validator.isCordinateInRange((coordinates.get(1))))
            {
                userInterface.printMassage(("your coordinates not in the range , please try again with cooredinates that are in range..."));
                continue;
            }


            finishTime = System.nanoTime();
            if (this.checkIfgGuessed (coordinates) == false){
                break;
            }
            userInterface.printMassage("You guessed already this cooredinates. try again..");*/

        //Validator
        long deltaTime = finishTime - startTime ;
        players[whoPlay].setAvargeTimeTurn(deltaTime);
        gameStatistic.incrementTurn();

        ArrayList <String> gameToolType = players[1- whoPlay].whoFindThere(coordinates.get(0), coordinates.get(1));
        executeByTypeTool (gameToolType , coordinates , whoPlay, false);
        showStatusGame();

        return true;
    }

    private  boolean checkIfgGuessed(ArrayList<Integer> coordinates){
         return players[whoPlay].rivalBoard[coordinates.get(0)][ coordinates.get(1)] != 0;
    }
    private boolean executeByTypeTool (ArrayList<String> gameToolType , ArrayList<Integer> coordinates , int player, boolean mine) {
        switch (gameToolType.get(0)) {
            case "non":
                players[player].updateIMissMyTurn(coordinates.get(0), coordinates.get(1));
                this.changePlayer();
                //userInterface.printMassage(("You miss :( "));
                if (mine){
                    backToMainMenu("player" + (player+1) + " your mine explode and miss");
                }
                else {
                    backToMainMenu("You miss :( ");
                }
                return true;
            case "battleShip":
                if (mine){
                    userInterface.printMassage("player " + (player +1) + " you mine hit in a ship! wall done!");
                }
                String msg = players[1-player].updateHitMe(coordinates , false);
                int tmpScore =0;
                if (msg.contains("destroyed")){
                    tmpScore = factory.getScoreByShipTypeId(gameToolType.get(1));
                }
                players[player].updateIHitMyTurn(coordinates.get(0), coordinates.get(1), gameToolType.get(0),tmpScore);

                if (msg == "Game Over"){
                    this.finishTheGame();
                }
                backToMainMenu(msg);
                return  true;
            case "Mine":

                userInterface.printMassage( players[whoPlay].getName() +" You hit in Mine :/");
                this.executeMine(coordinates, player);
                backToMainMenu( "");
                return true ;
             default: return false;
        }
    }

    public void  finishTheGame(){
        this.isGameOver = true;
        this.isGameLoaded =false;
        this.isGameRun = false;
        this.userInterface.printMassage("Well Done! you won");
    }

    private  boolean executeMine(ArrayList<Integer> coordinates, int player){

        ArrayList <String> gameToolType = players[whoPlay].whoFindThere(coordinates.get(0), coordinates.get(1));
        if (gameToolType.get(0) == "Mine"){
            //players[whoPlay].rivalBoard[coordinates.get(0)][coordinates.get(1)] = '-';
            //players[1 - whoPlay].rivalBoard[coordinates.get(0)][coordinates.get(1)] = '-';
            players[whoPlay].updateIHitMyTurn(coordinates.get(0), coordinates.get(1),"mine", 0);
            players[ 1- whoPlay].updateIMissMyTurn(coordinates.get(0), coordinates.get(1));
            players[whoPlay].updateHitMe(coordinates, true);
            players[1- whoPlay].updateHitMe((coordinates), true);
            userInterface.printMassage(players[ 1 - whoPlay].getName() + " your mine Hit at your mine arrival :/ ");
            return true;
        }
        players[whoPlay].updateIHitMyTurn(coordinates.get(0), coordinates.get(1), gameToolType.get(0), factory.getScoreByShipTypeId(gameToolType.get(0)));
        players[1-whoPlay].updateHitMe(coordinates, true);
        this.executeByTypeTool(gameToolType, coordinates ,1- whoPlay , true);

        return true;
    }
    private  void changePlayer (){
        whoPlay = 1- whoPlay;
        if (whoPlay == 0){
            this.propWhoPlay.set("Player 1");
        }
        else{
            this.propWhoPlay.set("Player 2");
        }
    }



    public  boolean gameStart(){
        if (! this.isGameLoaded){
            //ERROR: the game not loaded.
            backToMainMenu("game not loaded...");
            return false;
        }
        if (this.isGameRun){
            backToMainMenu("game already run.");
            return false;
        }
        this.isGameRun = true;

        // for Ex2 we mark the line below
        //this.showStatusGame();
        return  true;
    }

     public boolean showStatusGame (){
         if (! this.isGameRun){
             backToMainMenu("no game run...");
                return false;
        }
        try {
            userInterface.printBaordsAndMenu(players[whoPlay].getName() ,players[whoPlay].getMyBoardForPrint(),players[whoPlay].getRivalBoard(), players[whoPlay].getScore(), this.mainMenu );
         //   return (players[whoPlay].getName() ,players[whoPlay].getMyBoardForPrint(),players[whoPlay].getRivalBoard(), players[whoPlay].getScore(), this.mainMenu );
        }
        catch (Exception e){
            return  false;
        }

        return  true;
    }

    private boolean loadGame (String xmlPath) throws Exception {
        if ( this.isGameRun){
            //TODO: present error to the ui and back to the loop
            backToMainMenu("Game is already run... ");
            return  false;
        }
        try{

            this.factory = new Factory(xmlPath);
            this.players = factory.createPlayers();
            this.validator = factory.getGameDataValidator();
            this.userInterface.setBoardSize(factory.GameData.getBoardSize());
            this.isGameLoaded = true;
            return true;
        }
        catch (Exception e){
            throw e;
        }
    }

    public int getBoardSize() {
         return factory.GameData.getBoardSize();
    }

    public GameTool getGameToolFromBoard(int row , int column) {
         return players[whoPlay].getGameToolByCoordinate(row , column);
    }


    public char[][] getCurrentPlayerBoard() {
         return players[whoPlay].getMyBoardForPrint();
    }

    public char[][] getRivalBoard() {
        return players[whoPlay].getRivalBoard();
    }



}

