/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.views;

import games.sequence.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Brendan Ralston
 */
public class SequenceScreenController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private StackPane rootLayout;
    @FXML
    private Button initButton;

    private SequencePane sp;

    @FXML
    private void handleMainMenuButton(ActionEvent event) throws IOException {
        Parent screen2Parent = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(screen2Parent);

        Stage stage = (Stage) menuBar.getScene().getWindow();

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void handleInstructions(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("War Instructions");
        alert.setHeaderText(null);

        alert.getButtonTypes().setAll(ButtonType.OK);

        TextArea ta = new TextArea();
        ta.setText("Object of the Game:\n"
                + "\n"
                + "The first player to make a connected series of five pieces in their color in a straight line wins.\n"
                + "\n"
                + "Directions:\n"
                + "\n"
                + "With Blue starting, each turn a player will select a card from their hand corresponding to an open space on the board. The card will be discarded and a piece of their color will be added to that place. They will draw a new card to replace the played card. The game ends when a player creates a string of five pieces in any direction in a line.\n"
                + "\n"
                + "Red jacks are wild, they can be used to play on any open tile. Black jacks allow you to remove an opponents token. The four corner tiles are considered wild and count for either player towards their line of 5.\n"
                + "\n"
                + "How to play:\n"
                + "\n"
                + "At the beginning of a game click the deck to shuffle and deal the cards. To play a card drag it from the players hand to the tile you wish to place a token on.");
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setMaxWidth(Double.MAX_VALUE);
        ta.setMaxHeight(Double.MAX_VALUE);

        alert.getDialogPane().setContent(ta);

        alert.showAndWait();
    }

    @FXML
    private void initGame() {
        rootLayout.getChildren().remove(initButton);
        sp.initialize();
        sp.shuffle();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sp = new SequencePane();

        rootLayout.getChildren().add(sp);
        initButton.toFront();

        sp.setOnMouseClicked(e -> {
            sp.dealCards();
            sp.setOnMouseClicked(null);
        });
    }

}
