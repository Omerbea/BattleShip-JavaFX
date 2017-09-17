import com.sun.prism.paint.Color;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.text.normalizer.UCharacterProperty;

import javax.naming.Binding;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller extends Application  {

    Stage primaryStage = new Stage();
    Scene scene;
    @FXML Pane mainPane;
    @FXML Button exitGameBtn;
    @FXML Button restartGameBtn;
    @FXML public Button closeButton;
    @FXML public Button loadFileBtn;
    @FXML public Button startGameBtn;
    @FXML Button startGame;
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
    @FXML GridPane rightBoard;
    @FXML GridPane leftBoard;
    @FXML GridPane rivalShipsGridPane;
    @FXML ImageView mineImage;
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

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void restartGameHandler(){
        battleShipGame.restartGame();
        //TODO: restart UI

    }

    @FXML
    public void exitGameHandler(){
        // TODO: exit : close/hide and close everthing
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
    private void BindStatistics2Ui(){

        //Player Name
        currentPlayerNameLabel.textProperty().bind(Bindings.selectString(battleShipGame.propWhoPlayProperty()));
        if (battleShipGame.propWhoPlayProperty().getName() == "Player 2"){
            whoPlay = 1;
        }
    //Current Player
        // score
        scorePlayerLabel.textProperty().bind(Bindings.concat("Score: " ,battleShipGame.propScoreCurrentPlayer(whoPlay).getValue()));
        //Hit
        numOfHitsLabel.textProperty().bind(Bindings.concat("Hits: " , battleShipGame.propHitCurrentPlayer(whoPlay).getValue()));
        //Miss
        numOfmissLabel.textProperty().bind(Bindings.concat("Miss: ", battleShipGame.propMissCurrntPlayer(whoPlay).getValue()));
        //Average Turn Time
        averageTimeTurnLabel.textProperty().bind(Bindings.concat("Average Time for Turn: " , battleShipGame.propAverageTimeTurnCurrentPlayer(whoPlay).getValue()));
        //Number Of Turns
        numOfTurnsLabel.textProperty().bind(Bindings.concat( "Number of Turns", battleShipGame.propNumOfTurnsCurrentPlayer(whoPlay).getValue()));
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



    }

    @FXML
    public void startGameHandler() throws IOException {
        if (battleShipGame.gameStart()){
            BindStatistics2Ui();
            drawUiBoard(leftBoard);
            drawUiBoard(rightBoard);
            drawRivalShips();
            //TODO: drag and drop mins
            setDragAndDropMines();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR , "error");
            alert.showAndWait();
            System.out.println("error");
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

                mineImage.setImage(null);

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
                ((extendedButton)node).setText(Character.toString(LogicBoard[Board.getRowIndex(node)][Board.getColumnIndex(node)]));
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

    private void drawRivalShips(){
        Map<String, LinkedList<GameTool>> gameTools = battleShipGame.getGameTool(this.whoPlay);
        //count how many cell need in the grid.
        int howManycellGrid = 0;
        int i =0;
        int k =0;
        int maxSizeShip = getMaxSizeShip ( gameTools);
        //for each type tool
        for (Map.Entry<String, LinkedList<GameTool>> item : gameTools.entrySet()){
            LinkedList <GameTool> shipByType = item.getValue();
            k = i;
            if (item.getKey().equals("L_SHAPE") ) {

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

    }

    private void drawUiBoard(GridPane board) {
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

        }

    private void setDragAndDropTargetMine(extendedButton btn) {
        btn.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // change back to original color
                ColorAdjust color = new ColorAdjust();
                color.setBrightness(0);
                btn.setEffect(color);
                event.consume();
            }

        });

        btn.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getGestureSource() != btn /*TODO : for another drag*/) {
                    ColorAdjust color = new ColorAdjust();
                    color.setBrightness(-0.5);
                    btn.setEffect(color);
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
                    System.out.println(btn.getColumn() + " " + btn.getRow());
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


    private void executeMoveHandler(int row, int column) {
        battleShipGame.executeMove(column,row);
        fillBoardWithData(rightBoard , battleShipGame.getCurrentPlayerBoard());
        fillBoardWithData(leftBoard , battleShipGame.getRivalBoard());
    }

    @FXML
    public void loadFileHandler() {
     /*   try {

            battleShipGame.isFileValid("C:\\BattleShip\\Battleship\\resources\\battleShip_5_basic.xml");
        } catch (Exception e) {

        }*/
        //TODO: new Thread . maybe we need to pass a task for the new thread and until his task finished we show progress bar

        gameLoadedLabel = new Label();

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        //TODO: check that file is not null. when the user close the fileChooser without hes selected a file
        try {
            battleShipGame.isFileValid(file.getAbsolutePath());
            //TODO: handle with successes
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR , e.getMessage());
            alert.showAndWait();
           System.out.println(e.getMessage());
        }

    }

    public void dragHandler(MouseEvent mouseEvent) {

    }
}