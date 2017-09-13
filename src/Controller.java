import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML
    Button startGame;
    Label gameLoadedLabel;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage i_primaryStage) throws Exception {

        primaryStage = i_primaryStage;
        Pane root = FXMLLoader.load(getClass().getResource("startWindow.fxml"));
        primaryStage.setTitle("Battleship Game");
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @FXML
    public void startGameHandler() throws IOException {
        Stage news = new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("sample1.fxml"));
        news.setScene(new Scene(root, 300, 275));
        // scene.setRoot(root);
        news.setScene(scene);

        news.show();
        ((Stage) primaryStage.getScene().getWindow()).close();
        //Stage primary = (Stage) btnn.getScene().getWindow();
        //primary.close();
        //primaryStage.close();
    }

    @FXML
    public void loadFileHandler() {
        gameLoadedLabel = new Label();

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        try {
            battleShipGame.isFileValid(file.getAbsolutePath());
            //TODO: handle with succsses
        } catch (Exception e) {
            //TODO: handle with message

            gameLoadedLabel.setPrefWidth(e.getMessage().length() * 6);
            gameLoadedLabel.setPrefHeight(10);
            gameLoadedLabel.setVisible(true);
            gameLoadedLabel.textProperty().bind(new SimpleStringProperty(e.getMessage()));
            mainPane.getChildren().add(gameLoadedLabel);
           // System.out.println(e.getMessage());
        }
    }
}