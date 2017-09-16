import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.naming.Binding;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    @FXML Label rivalPlayerNameLabel;
    @FXML Label rivalScorePlayerLabel ;
    @FXML Label rivalNumOfHitsLabel ;
    @FXML Label rivalNumOfmissLabel ;
    @FXML Label rivalAverageTimeTurnLabel ;
    @FXML Label rivalNumOfTurnsLabel ;
    Label gameLoadedLabel;
    GameManager battleShipGame = new GameManager();

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
        int whoPlayer =0;
        //Player Name
        currentPlayerNameLabel.textProperty().bind(Bindings.selectString(battleShipGame.propWhoPlayProperty()));
        if (battleShipGame.propWhoPlayProperty().getName() == "Player 2"){
            whoPlayer = 1;
        }
    //Current Player
        // score
        scorePlayerLabel.textProperty().bind(Bindings.selectString(battleShipGame.propScoreCurrentPlayer(whoPlayer)));
        //Hit
        numOfHitsLabel.textProperty().bind(Bindings.selectString(battleShipGame.propHitCurrentPlayer(whoPlayer)));
        //Miss
        numOfmissLabel.textProperty().bind(Bindings.selectString(battleShipGame.propMissCurrntPlayer(whoPlayer)));
        //Average Turn Time
        averageTimeTurnLabel.textProperty().bind(Bindings.selectString(battleShipGame.propAverageTimeTurnCurrentPlayer(whoPlayer)));
        //Number Of Turns
        numOfTurnsLabel.textProperty().bind(Bindings.selectString(battleShipGame.propNumOfTurnsCurrentPlayer(whoPlayer)));
    //Raivel Player
        //Title rival
        rivalPlayerNameLabel.setText("Rival Details");
        /*
        // score
        rivalScorePlayerLabel.textProperty().bind(Bindings.selectString(battleShipGame.propScoreCurrentPlayer(1- whoPlayer)));
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
            //TODO: implement UI, for this we need to bind from gameManager to controller and UI
            BindStatistics2Ui();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR , "error");
            alert.showAndWait();
            System.out.println("error");
        }
    }

    @FXML
    public void loadFileHandler() {
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