import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import javafx.scene.control.ProgressBar;

public class Controller extends Application  {

    Stage primaryStage = new Stage();
    Scene scene;
    @FXML Label errorLabel;
    @FXML VBox rivalStatistics;
    @FXML VBox rightStatistics;
    @FXML Pane mainPane;
    @FXML Button exitGameBtn;
    @FXML Button restartGameBtn;
    @FXML public Button closeButton;
    @FXML public Button loadFileBtn;
    @FXML public Button startGameBtn;
    //@FXML Button startGame;
    @FXML Label currentPlayerNameLabel;
    @FXML Label scorePlayerLabel ;
    @FXML Label numOfHitsLabel ;
    @FXML Label numOfmissLabel ;
    @FXML Label averageTimeTurnLabel ;
    @FXML Label numOfTurnsLabel ;
    @FXML Label rivalPlayerDetailsLabel;
    @FXML Label rivalNumMines ;
    @FXML Label rivalNumOfHitsLabel ;
    @FXML Label rivalNumOfmissLabel ;
    @FXML Label rivalAverageTimeTurnLabel ;
    @FXML Label rivalNumOfTurnsLabel ;
    @FXML Button nextButton;
    @FXML Button prevButton;
    @FXML GridPane rightBoard;
    @FXML GridPane leftBoard;
    @FXML GridPane rivalShipsGridPane;
    @FXML ImageView mineImage;
    @FXML Label meesegeLabel;
    Label gameLoadedLabel;
    GameManager battleShipGame = new GameManager();
/*    SimpleStringProperty propcurrentPlayerName = new SimpleStringProperty();
    SimpleStringProperty propOfmiss= new SimpleStringProperty();
    SimpleStringProperty propAverageTimeTurn = new SimpleStringProperty();
    SimpleStringProperty propNumOfTurns = new SimpleStringProperty();
    SimpleStringProperty propScorePlayer = new SimpleStringProperty();
    SimpleStringProperty propNumOfHits = new SimpleStringProperty();*/

    int whoPlay =0;
    extendedButton [][]player1Board;
    extendedButton [][]player2Board;
    boolean afterRestart = false;
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void restartGameHandler(){
        this.afterRestart = true;

    //    errorLabel.setText("restarting.");
    //    errorLabel.setText("restarting..");
  //      errorLabel.setText("restarting...");
//        errorLabel.setText("restarting....");


        battleShipGame.restartGame();
        rightBoard.setVisible(false);
        leftBoard.setVisible(false);
        //rightBoard.getChildren().clear();
        //leftBoard.getChildren().clear();
        rightStatistics.setVisible(false);
        rivalStatistics.setVisible(false);
        prevButton.setVisible(false);
        nextButton.setVisible(false);
        //rivalShipsGridPane.getChildren().clear();
       /* try {
            startGameHandler();
        }
        catch (Exception e){

        }*/
        errorLabel.setText("");
        errorLabel.setVisible(false);
        loadFileBtn.setDisable(false);
        restartGameBtn.setDisable(true);
        new Thread(){
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setMessageLabel("  Game restarted !" , true);

                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setMessageLabel("" , true);

                    }
                });
            }

        }.start();

    }

    @FXML
    public void exitGameHandler(){
        this.battleShipGame = null;
        Platform.exit();
        // TODO: exit : exit UI
    }

    @Override
    public void start(Stage i_primaryStage) throws Exception {

        primaryStage = i_primaryStage;
        Pane root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));


        primaryStage.setTitle("Battleship Game");

        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    //TODO: back the comment below to be active. I comment this only for tests
    //private void printBaordsAndMenu(String name, char[][] boardOne, char[][] boardTwo, int score, List<String> menu){
    private void bindStatistics2Ui(){

        //Player Name
        currentPlayerNameLabel.textProperty().bind(Bindings.selectString(battleShipGame.propWhoPlayProperty()));

        if (battleShipGame.propWhoPlayProperty().getValue() == "Player 2"){
            whoPlay = 1;
        }
        else if (battleShipGame.propWhoPlayProperty().getValue() == "Player 1"){
            whoPlay =0;
        }
        else{
            System.out.print("Error");
        }
    //Current Player
        // score
        scorePlayerLabel.textProperty().bind(Bindings.concat("Score: " ,battleShipGame.propScoreCurrentPlayer(whoPlay)));
        //Hit
        numOfHitsLabel.textProperty().bind(Bindings.concat( "Hits: ", battleShipGame.propHitCurrentPlayer(whoPlay)));
        //Miss
        numOfmissLabel.textProperty().bind(Bindings.concat("Miss: ", battleShipGame.propMissCurrntPlayer(whoPlay)));
        //Average Turn Time
        averageTimeTurnLabel.textProperty().bind(Bindings.concat("Average Time for Turn: " , battleShipGame.propAverageTimeTurnCurrentPlayer(whoPlay)));
        //Number Of Turns
        numOfTurnsLabel.textProperty().bind(Bindings.concat( "Number of Turns: ", battleShipGame.propNumOfTurnsCurrentPlayer(whoPlay)));
    //Raivel Player
        //Title rival
        rivalPlayerDetailsLabel.setText("Rival Details");
        //num Mines
        //TODO: add mines to the bind
        rivalNumMines.textProperty().bind(Bindings.concat("Mines: " , battleShipGame.getNumOfMinesFromPlayer(1-whoPlay)));
        /*
        // Mines
        .textProperty().bind(Bindings.selectString(battleShipGame.propScoreCurrentPlayer(1- whoPlayer)));
        //Hit
        rivalNumOfHitsLabel.textProperty().bind(Bindings.selectString(battleShipGame.propHitCurrentPlayer(1-whoPlayer)));
        //Miss
        rivalNumOfmissLabel.textProperty().bind(Bindings.selectString(battleShipGame.propMissCurrntPlayer(1-whoPlayer)));
        //Average Turn Time
        rivalAverageTimeTurnLabel.textProperty().bind(Bindings.selectString(battleShipGame.propAverageTimeTurnCurrentPlayer(1-whoPlayer)));
        //Number Of Turns
        rivalNumOfTurnsLabel.textProperty().bind(Bindings.selectString(battleShipGame.propNumOfTurnsCurrentPlayer(1-whoPlayer)));
        */

        rightStatistics.setVisible(true);

    }

    @FXML
    public void startGameHandler() throws IOException {
        try {
            if (battleShipGame.gameStart()) {
                loadFileBtn.setDisable(true);
                restartGameBtn.setDisable(false);
                bindStatistics2Ui();
                if (this.afterRestart) {
                    fillBoardWithData(rightBoard , battleShipGame.getCurrentPlayerBoard());
                    fillBoardWithData(leftBoard , battleShipGame.getRivalBoard());
                    leftBoard.setVisible(true);
                    rightBoard.setVisible(true);
                }
                else{
                    drawUiBoard(leftBoard);
                    drawUiBoard(rightBoard);
                }
                drawRivalShips(null);
                //TODO: drag and drop mins
                if(battleShipGame.getNumOfMinesCurrentPlayer() >0) {
                    mineImage.setImage(new Image("mine.png"));

                }
                setDragAndDropMines();
                mineImage.setVisible(true);
                startGameBtn.setDisable(true);
            }
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING ,   e.getMessage());
            alert.setTitle("BattleShip Game");
            alert.setHeaderText("Can not perform this task");
            alert.showAndWait();
            System.out.println(e.getMessage());
        }

    }

    private void setDragAndDropMines() {
        mineImage.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("start handle");
                Dragboard db = mineImage.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(new Image("mine.png" , 50 ,50,false,false));

                db.setContent(content);


                event.consume();
            }
        });
        mineImage.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getTransferMode() == TransferMode.MOVE) {
                    System.out.println("ok");
                } else {
                    System.out.println("fail");
                }
                event.consume();
            }
        });

    }

    private void fillBoardWithData(GridPane Board , char[][] LogicBoard) {

        /*for (Node node : Board.getChildren()) {

            if(battleShipGame.getGameToolFromBoard(Board.getRowIndex(node) ,Board.getColumnIndex(node)) != null) {
                ((extendedButton)node).setText(Character.toString(LogicBoard[Board.getRowIndex(node)][Board.getColumnIndex(node)]));
            } else {
                ((extendedButton)node).setText(" ");
            }
        }*/

        for (Node node : Board.getChildren()) {
                if((LogicBoard[Board.getRowIndex(node)][Board.getColumnIndex(node)]) == '-') {
                    ((extendedButton)node).setDisable(true);
                    ((extendedButton)node).setStyle("    -fx-background-color:\n" +
                            "        linear-gradient(#f04f35, #f04f35),\n" +
                            "        radial-gradient(center 50% -40%, radius 200%, #b84e36 45%, #b84e36 50%);\n" +
                            "    -fx-background-radius: 6, 5;\n" +
                            "    -fx-background-insets: 0, 1;\n" +
                            "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" +
                            "    -fx-text-fill: #391306;");



                }

                else if((LogicBoard[Board.getRowIndex(node)][Board.getColumnIndex(node)]) == 'M') {
                    ((extendedButton)node).setDisable(true);
                    /*ImageView img = new ImageView("Mine.png");

                    img.fitWidthProperty().bind(((extendedButton) node).widthProperty());
                    img.fitHeightProperty().bind(((extendedButton) node).heightProperty());*/
                    ((extendedButton)node).setText("M");


                }
                else if ((LogicBoard[Board.getRowIndex(node)][Board.getColumnIndex(node)]) == 'X' &&
                        Board == leftBoard){
                    String s = ((extendedButton)node).getText();
                    ((extendedButton)node).setStyle("    -fx-background-color:\n" +
                            "        linear-gradient(#6eff84, #b7ff72),\n" +
                            "        radial-gradient(center 50% -40%, radius 200%, #acee7c 45%, #93c87e 50%);\n" +
                            "    -fx-background-radius: 6, 5;\n" +
                            "    -fx-background-insets: 0, 1;\n" +
                            "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" );
                    ((extendedButton)node).setText("X");
                }
                else if ((LogicBoard[Board.getRowIndex(node)][Board.getColumnIndex(node)]) == '#' &&
                        Board == rightBoard){
                    String s = ((extendedButton)node).getText();
                    ((extendedButton)node).setStyle("    -fx-background-color:\n" +
                            "        linear-gradient(#8993ff, #8993ff),\n" +
                            "        radial-gradient(center 50% -40%, radius 200%, #8993ff 45%, #8993ff 50%);\n" +
                            "    -fx-background-radius: 6, 5;\n" +
                            "    -fx-background-insets: 0, 1;\n" +
                            "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" );
                    ((extendedButton)node).setText("#");
                }
                else if ((LogicBoard[Board.getRowIndex(node)][Board.getColumnIndex(node)]) == 'X' &&
                        Board == rightBoard){
                    String s = ((extendedButton)node).getText();
                    ((extendedButton)node).setStyle("    -fx-background-color:\n" +
                            "        linear-gradient(#252745, #3a406e),\n" +
                            "        radial-gradient(center 50% -40%, radius 200%, #252745 45%, #252745 50%);\n" +
                            "    -fx-background-radius: 6, 5;\n" +
                            "    -fx-background-insets: 0, 1;\n" +
                            "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" );
                    ((extendedButton)node).setText("X");
                }
                else {
                    ((extendedButton) node).setText(Character.toString(LogicBoard[Board.getRowIndex(node)][Board.getColumnIndex(node)]));
                    ((extendedButton) node).setStyle("");
                    ((extendedButton)node).setDisable(false);
                }
        }



    }

    private int getMaxSizeShip(Map<String, LinkedList<GameTool>> gameTools){
        int max =0 ;
        for (Map.Entry<String, LinkedList<GameTool>> item : gameTools.entrySet()){
            int currSizeShip = item.getValue().getFirst().getSize();
            if (max < currSizeShip){
                max = currSizeShip;
            }
        }
        return max;
    }

    private void drawRivalShips(Map<String, LinkedList<GameTool>> gameToolsReplay){
        rivalShipsGridPane.getChildren().clear();
        Map<String, LinkedList<GameTool>> gameTools;
        if (gameToolsReplay == null) {
            gameTools = battleShipGame.getGameTool(this.whoPlay);
        }
        else{
            gameTools = gameToolsReplay;
        }
        //count how many cell need in the grid.
        int howManycellGrid = 0;
        int i =0;
        int k =0;
        int maxSizeShip = getMaxSizeShip ( gameTools);
        //for each type tool
        for (Map.Entry<String, LinkedList<GameTool>> item : gameTools.entrySet()){
            LinkedList <GameTool> shipByType = item.getValue();
            k = i;
            if (shipByType.getFirst().getCategory().equals("L_SHAPE") ) {

                //for size of ship. rival ship present by buttons
                for (int j = 0; j < shipByType.getFirst().getSize(); j++) {

                    Button buttonRow = new Button();
                    buttonRow.setPrefSize(10, 10);
                    rivalShipsGridPane.add(buttonRow, 0, i);
                    System.out.print(" 0," + Integer.toString(i) + " ");
                    if (i == 0 && i == 0) {
                        i++;
                        continue;
                    }
                    Button buttonColumn = new Button();
                    buttonColumn.setPrefSize(10, 10);

                    rivalShipsGridPane.add(buttonColumn, j, k);
                    System.out.print(" " + Integer.toString(j) + "," + Integer.toString(k));
                    i++;

                }
                //print amount per shipType
                Label amountShip = new Label();
                amountShip.setText(Integer.toString(shipByType.size()));
                rivalShipsGridPane.add(amountShip, i, i);
            }
            else{
                for (int indexButton=0;indexButton < shipByType.getFirst().getSize(); indexButton++){
                    Button buttonColumn = new Button();
                    buttonColumn.setPrefSize(10,10);
                    rivalShipsGridPane.add(buttonColumn,indexButton,k);
                    i+=1;
                }
                //print amount per shipType
                Label amountShip = new Label();
                amountShip.setText(Integer.toString(shipByType.size()));
                rivalShipsGridPane.add(amountShip, maxSizeShip, k );
            }
            Label space = new Label();
            space.setPrefSize(10,10);
            rivalShipsGridPane.add(space,0,i);
            i+=1;
            // for space between two type of ships
        }
        rivalStatistics.setVisible(true);
    }


    private void drawUiBoard(GridPane board) {
        board.getColumnConstraints().clear();
        board.getRowConstraints().clear();
        LinkedList<ColumnConstraints> columnConstraints = new LinkedList<>();
        //hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"

        for(int i = 0 ; i < battleShipGame.getBoardSize() ; i++) {
            columnConstraints.add(i , new ColumnConstraints(0.0,50.0,Double.MAX_VALUE,Priority.ALWAYS, HPos.LEFT,true));
        }
        LinkedList<RowConstraints> rowConstraints = new LinkedList<>();
        //minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"
        for(int i = 0 ; i < battleShipGame.getBoardSize() ; i++) {
            rowConstraints.add(i , new RowConstraints(0.0,50.0,Double.MAX_VALUE,Priority.ALWAYS, VPos.BOTTOM,true));
        }

        for(int i = 0; i < battleShipGame.getBoardSize() - 1  ; i++ ) {
            board.addRow(i);
            for (int j = 0 ; j < battleShipGame.getBoardSize() - 1 ; j++) {
                board.addColumn(j);
            }
        }

        //board.setStyle("-fx-grid-lines-visible: true");
        board.getColumnConstraints().addAll(columnConstraints);
        board.getRowConstraints().addAll(rowConstraints);


        for (int i = 0; i < battleShipGame.getBoardSize(); i++) {

            for (int j = 0; j < battleShipGame.getBoardSize(); j++) {
                    extendedButton btn = new extendedButton(i, j);
                    btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    if(board == leftBoard) {
                        btn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                executeMoveHandler(((extendedButton) event.getSource()).getRow(), ((extendedButton) event.getSource()).getColumn());
                            }
                        });
                        btn.setStyle("-fx-focus-color: transparent;");
                    } else {
                       //btn.setDisable(true);
                        setDragAndDropTargetMine(btn);
                        btn.setStyle("-fx-opacity: 1.0 ;");
                    }
                    board.add(btn, i, j);
                }
            }
        fillBoardWithData(rightBoard , battleShipGame.getCurrentPlayerBoard());
        fillBoardWithData(leftBoard , battleShipGame.getRivalBoard());
        board.setVisible(true);
        }

    private void setDragAndDropTargetMine(extendedButton btn) {
        btn.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // change back to original color
                btn.setStyle("");
                event.consume();
            }

        });

        btn.setOnDragEntered(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {

                if(event.getGestureSource() != btn /*TODO : for another drag*/) {
                    if (goodPostion(btn.getRow(), btn.getColumn())) {
                        System.out.println("green");
                        btn.setStyle("    -fx-background-color:\n" +
                                "        linear-gradient(#f0ff35, #a9ff00),\n" +
                                "        radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);\n" +
                                "    -fx-background-radius: 6, 5;\n" +
                                "    -fx-background-insets: 0, 1;\n" +
                                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" +
                                "    -fx-text-fill: #395306;");

                    }
                    else{
                        //TODO: omer
                        btn.setStyle("    -fx-background-color:\n" +
                                "        linear-gradient(#ff4861, #ff0716),\n" +
                                "        radial-gradient(center 50% -40%, radius 200%, #ee5869 45%, #c80606 50%);\n" +
                                "    -fx-background-radius: 6, 5;\n" +
                                "    -fx-background-insets: 0, 1;\n" +
                                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" +
                                "    -fx-text-fill: #395306;");
                    }
                }
                event.consume();
            }

        });



        btn.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("===========================");
            }
        });

        btn.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean sucess = false ;

                if(db.hasImage()) {
                    //TODO : set mine image
                    try {
                        battleShipGame.addMine(btn.getColumn(), btn.getRow());
                        fillBoardWithData(rightBoard , battleShipGame.getCurrentPlayerBoard());
                        if(battleShipGame.getNumOfMinesCurrentPlayer() <= 0) {

                            mineImage.setImage(null);
                        }
                    } catch (Exception e) {
                        System.out.println("problem with mine");
                    }
                    sucess = true ;
                }
                event.setDropCompleted(sucess);
                event.consume();
            }
        });

        btn.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getGestureSource() != btn) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

    }

    private boolean goodPostion(int row, int column) {

        return battleShipGame.canSetMine(row,column);
       /* if(battleShipGame.getMineList().stream().filter(pos -> pos.getRow() == row && pos.getColumn() == column ).count() > 0){
            return true;
        }
        return false ;*/
    }


    private void executeMoveHandler(int row, int column) {
        setMessageLabel("" , true);
        String result = battleShipGame.executeMove(column,row);
        fillBoardWithData(rightBoard , battleShipGame.getCurrentPlayerBoard());
        fillBoardWithData(leftBoard , battleShipGame.getRivalBoard());
        bindStatistics2Ui();
        this.drawRivalShips(null);
        if (result.contains("Win")){
            //omer: show button prev and next and connect click event to prevHandler() and nextHandler() that already exist!
            setMessageLabel("   " + battleShipGame.propWhoPlayProperty().getValue() +  " the Winner ! "  , true);
            nextButton.setVisible(true);
            prevButton.setVisible(true);


            System.out.print("we have a winner!");
        }

        else if(result.contains("destroyed")) {
            new Thread(){
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel("  BattleShip Destroyed!",true);


                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel("" , true);

                        }
                    });

                }

            }.start();
        }
        else if (result.contains("hit")){
            bindStatistics2Ui();

            new Thread(){
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel("  You Hit !" , true);

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel("" , true);

                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel(" Play another turn!" , true);

                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel("" , true);

                        }
                    });

                }

            }.start();

        }
        else if (result.contains("non")){
            bindStatistics2Ui();

            new Thread(){
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel("  You Miss !" , true);

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel("" , true);

                        }
                    });
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel("   " + battleShipGame.propWhoPlayProperty().getValue() + " Play!" , true);

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setMessageLabel("" , true);

                        }
                    });

                }

            }.start();

        }
    }
    @FXML
    public void prevHandler(){
        Replay prevTurnReplay = this.battleShipGame.getPrevReplayTurn();
        if (prevTurnReplay == null){
            // we don't have anymore prev turn
        }
        else {
            if (prevTurnReplay.getIsMine() == false) {
                // omer: update 2 board by prevTurnReplay boject
                fillBoardWithData(leftBoard, prevTurnReplay.getRivalBoard());
                fillBoardWithData(rightBoard, prevTurnReplay.getPlayerBoard());
                paintMoveHigalight(prevTurnReplay.getColumn(), prevTurnReplay.getRow(), leftBoard);
                updateStatisticsReplay(prevTurnReplay);
                // jonathan : update statistics by prevTurnReplay boject
            } else {
                //fillBoardWithData(leftBoard , prevTurnReplay.getPlayerBoard());
                unHighLight(leftBoard);
                paintMoveHigalight( prevTurnReplay.getColumn() , prevTurnReplay.getRow() , rightBoard);
                //TODO: updateReplay set mine

            }
        }
        //check if we have anther prev turn to show
        if (! this.battleShipGame.isExisistPrevTurn()){
            //No!
            prevButton.setDisable(true);

        }
        nextButton.setDisable(false);
    }

    private void paintMoveHigalight(int row, int column, GridPane board) {
        for(Node node : board.getChildren()) {
            if(((extendedButton)node).getRow() == row && ((extendedButton)node).getColumn() == column) {
                String s = ((extendedButton)node).getText();
                ((extendedButton)node).setStyle("    -fx-background-color:\n" +
                        "        linear-gradient(#9c73dd, #ac8bf3),\n" +
                        "        radial-gradient(center 50% -40%, radius 200%, #9c73dd 45%, #9c73dd 50%);\n" +
                        "    -fx-background-radius: 6, 5;\n" +
                        "    -fx-background-insets: 0, 1;\n" +
                        "    -fx-text-fill: #000000;");
                ((extendedButton)node).setText(s);
            }
        }
    }

    private char[][] GameToolBoardToCharBaord(GameTool[][] board) {
        int size = battleShipGame.getBoardSize();
        char [][] boardForPrint = new char[size][size];
        for (int row =0 ; row< size ; row++){
            for (int column =0 ; column < size ; column++){
                if (board[row][column] != null) {
                    if (board[row][column].isHitMyThere(new Position(row, column))) {
                        boardForPrint[row][column] = 'X';
                    } else {
                        boardForPrint[row][column] = board[row][column].getMySing();
                    }
                }
                else{
                    boardForPrint[row][column] = ' ';
                }
            }
        }
        return  boardForPrint;

    }
    private void updateStatisticsReplay (Replay turn){
        drawRivalShips(turn.rivalGetGameTool);
        unBindGameStatistic();
        bindReplayStatistics(turn);
    }

    private void unBindGameStatistic(){
            //Player Name
            currentPlayerNameLabel.textProperty().unbind();
            // score
            scorePlayerLabel.textProperty().unbind();
            //Hit
            numOfHitsLabel.textProperty().unbind();
            //Miss
            numOfmissLabel.textProperty().unbind();
            //Average Turn Time
            averageTimeTurnLabel.textProperty().unbind();
            //Number Of Turns
            numOfTurnsLabel.textProperty().unbind();
            rivalNumMines.textProperty().unbind();
    }

    private void bindReplayStatistics ( Replay turn){

        //Current Player

        //Player Name
        currentPlayerNameLabel.setText((turn.getPlayerName()));
        // score
        scorePlayerLabel.setText(("Score: " + String.valueOf(turn.getScore())));
        //Hit
        numOfHitsLabel.setText( "Hits: "+ String.valueOf(turn.getHits()));
        //Miss
        numOfmissLabel.setText("Miss: "+ String.valueOf(turn.getMiss()));
        //Average Turn Time
        averageTimeTurnLabel.setText("Average Time for Turn: " + (turn.getAvargeTimeTurn()));
        //Number Of Turns
        numOfTurnsLabel.setText( "Number of Turns: " + String.valueOf(turn.getNumOfTurns()));

        //Raivel Player

        //Title rival
        rivalPlayerDetailsLabel.setText("Rival Details");
        //num Mines
        //TODO: add mines to the bind
        rivalNumMines.setText("Mines: " + String.valueOf(turn.getRivalMines()));
    }
    @FXML
    private void nextHandler(){

        Replay nextTurnReplay = this.battleShipGame.getNextReplayturn();
        if (nextTurnReplay == null){
            //we don't have anymore next turn
        }
        else {
            if (nextTurnReplay.getIsMine() == false) {
                //omer: update 2 board by nextTurnReplay obj
                fillBoardWithData(leftBoard, nextTurnReplay.getRivalBoard());
                fillBoardWithData(rightBoard, nextTurnReplay.getPlayerBoard());
                paintMoveHigalight(nextTurnReplay.getColumn(), nextTurnReplay.getRow(), leftBoard);
                updateStatisticsReplay(nextTurnReplay);
                //jonathan: update statistics by nextTurnReplay obj
            }
            else{
                unHighLight(leftBoard);
                paintMoveHigalight(nextTurnReplay.getColumn(),nextTurnReplay.getRow(), rightBoard);
            }
        }
        prevButton.setDisable(false);

        //check if we have anther next turn to show
        if (!this.battleShipGame.isExisistNextTurn()){
            //No!
            nextButton.setDisable(true);
        }



    }

    private void updateRivalDetails(String ship){
        if (ship != null){
            updateShipDestroyed(ship);
        }
    }

    private void updateShipDestroyed (String ship){


    }
    @FXML
    public void loadFileHandler() throws InterruptedException {
     /*   try {

            battleShipGame.isFileValid("C:\\BattleShip\\Battleship\\resources\\battleShip_5_basic.xml");
        } catch (Exception e) {

        }*/


     //   if (this.battleShipGame.getIsGameLoaded() == false) {
            gameLoadedLabel = new Label();

            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);


            if (file != null) {
                LoadFile(file);
                startGameBtn.setDisable(false);
            }
     //   }
       /* else{
            Alert alert = new Alert(Alert.AlertType.WARNING ,  "game already run...you have to restart game for load a new game ");
            alert.setTitle("BattleShip Game");
            alert.setHeaderText("Can not perform this task");
            alert.showAndWait();
        }*/

    }

    private void LoadFile(File file) {
        final SimpleDoubleProperty prop = new SimpleDoubleProperty(0);
        ProgressBar progress = new ProgressBar();
        progress.progressProperty().bind(prop);
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        Label status = new Label("");
        root.getChildren().addAll(progress , status);

        Scene scene = new Scene(root, 300, 150);

        primaryStage.setTitle("Parsing File");

        primaryStage.setScene(scene);
        primaryStage.show();
        new Thread(){
            @Override
            public void run(){
                try {

                    for(double i=0; i<=1; i+=0.1){
                        prop.set(i);
                        if(i==0.1) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    status.setText("Loading File");
                                }
                            });

                        }
                        if(i==0.5) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    status.setText("Parsing XML");
                                }
                            });

                        }

                        Thread.sleep(100);
                    }

                    try {
                        battleShipGame.isFileValid(file.getAbsolutePath());
                    } catch (Exception e) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                                alert.setTitle("BattleShip Game");
                                alert.setHeaderText("Error loading FXML file.");
                                alert.showAndWait();
                            }
                        });
                    }

                } catch (InterruptedException ex) {
                    System.err.println("Error on Thread Sleep");
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        primaryStage.close();
                    }
                });

            }

        }.start();

    }

    public void setMessageLabel(String message , boolean toShow) {
        if(toShow) {
            meesegeLabel.setText(message);
            meesegeLabel.setVisible(true);
        } else {
            meesegeLabel.setVisible(false);
        }
    }

    private void unHighLight(GridPane board) {
            for(Node node : board.getChildren()) {
                    ((extendedButton)node).setStyle("");
            }
    }

}