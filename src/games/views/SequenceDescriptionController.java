/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ralst
 */
public class SequenceDescriptionController implements Initializable {

    @FXML
    private Button playButton;

    @FXML
    private void handleMainMenuButton(ActionEvent event) throws IOException {
        Parent screen2Parent = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(screen2Parent);

        Stage stage = (Stage) playButton.getScene().getWindow();

        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    private void handlePlayButton(ActionEvent event) throws IOException {
        Parent screen2Parent = FXMLLoader.load(getClass().getResource("sequenceScreen.fxml"));
        Scene scene = new Scene(screen2Parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setFullScreenExitHint("");
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
