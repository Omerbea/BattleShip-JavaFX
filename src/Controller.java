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
    @FXML GridPane rightBoard;
    @FXML GridPane leftBoard;
    Label gameLoadedLabel;
    GameManager battleShipGame = new GameManager();
/*    SimpleStringProperty propcurrentPlayerName = new SimpleStringProperty();
    SimpleStringProperty propOfmiss= new SimpleStringProperty();
    SimpleStringProperty propAverageTimeTurn = new SimpleStringProperty();
    SimpleStringProperty propNumOfTurns = new SimpleStringProperty();
    SimpleStringProperty propScorePlayer = new SimpleStringProperty();
    SimpleStringProperty propNumOfHits = new SimpleStringProperty();*/

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
        // score
        scorePlayerLabel.textProperty().bind(Bindings.selectString(battleShipGame.propScoreCurrentPlayer()));
        //Hit
        numOfHitsLabel.textProperty().bind(Bindings.selectString(battleShipGame.propHitCurrentPlayer()));
        //Miss
        numOfmissLabel.textProperty().bind(Bindings.selectString(battleShipGame.propMissCurrntPlayer()));
        //Average Turn Time
        averageTimeTurnLabel.textProperty().bind(Bindings.selectString(battleShipGame.propAverageTimeTurnCurrentPlayer()));
        //Number Of Turns
        numOfTurnsLabel.textProperty().bind(Bindings.selectString(battleShipGame.propNumOfTurnsCurrentPlayer()));

    }

    @FXML
    public void startGameHandler() throws IOException {
        if (battleShipGame.gameStart()){
            //TODO: implement UI, for this we need to bind from gameManager to controller and UI
            BindStatistics2Ui();
            drawUiBoard(leftBoard);
            drawUiBoard(rightBoard);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR , "error");
            alert.showAndWait();
            System.out.println("error");
        }


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
                        btn.setDisable(true);
                        btn.setStyle("-fx-opacity: 1.0 ;");
                    }
                    board.add(btn, i, j);
                }
            }
        fillBoardWithData(rightBoard , battleShipGame.getCurrentPlayerBoard());
        fillBoardWithData(leftBoard , battleShipGame.getRivalBoard());

        }

    private void executeMoveHandler(int row, int column) {
        battleShipGame.executeMove(column,row);
        fillBoardWithData(rightBoard , battleShipGame.getCurrentPlayerBoard());
        fillBoardWithData(leftBoard , battleShipGame.getRivalBoard());
    }

    @FXML
    public void loadFileHandler() {
/*        try {
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
}