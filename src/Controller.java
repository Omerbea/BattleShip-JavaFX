import javafx.application.Application;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends Application  {

    Stage primaryStage = new Stage();
    Scene scene;
    @FXML
    Pane mainPane;
    GameManager battleShipGame = new GameManager();
    @FXML Button exitGameBtn;

    @FXML Button restartGameBtn;
    @FXML
    public Button closeButton;
    @FXML
    public Button loadFileBtn;
    @FXML
    public Button startGameBtn;
    @FXML
    Button startGame;
    Label gameLoadedLabel;


    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void restartGameHandler(){
        battleShipGame.restartGame();
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


    @FXML
    public void startGameHandler() throws IOException {
        if (battleShipGame.gameStart()){
            //TODO: implement UI
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR , "error");
            alert.showAndWait();
            System.out.println("error");
        }
    }

    @FXML
    public void loadFileHandler() {
        //TODO: new Thread

        gameLoadedLabel = new Label();

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        try {
            battleShipGame.isFileValid(file.getAbsolutePath());
            //TODO: handle with succsses
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR , e.getMessage());
            alert.showAndWait();
           System.out.println(e.getMessage());
        }
    }
}