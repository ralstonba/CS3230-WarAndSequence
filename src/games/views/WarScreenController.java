/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.views;

import games.war.WarGamePane;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ralst
 */
public class WarScreenController implements Initializable {
    
    @FXML
    private MenuBar menuBar;
    @FXML
    private StackPane rootLayout;
    @FXML
    private Button initButton;
    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;
    
    private WarGamePane wgp;
    
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
        ta.setText("Rules:\n"
                + "\n"
                + "The deck is shuffled and dealed between the two players. Each round the players will place the top card from their pile face down onto the play area. With Ace being high, the cards are compared and the cards go to the player with the highest value. If the players cards are the same then three cards are discarded facedown from the top of the deck and then two cards are played face up with the winner taking the entire pile. \n"
                + "\n"
                + "How to play:\n"
                + "\n"
                + "At the beginning of a game click the deck to shuffle and deal the cards. From then on click in the play area to initiate a round.");
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setMaxWidth(Double.MAX_VALUE);
        ta.setMaxHeight(Double.MAX_VALUE);
        
        alert.getDialogPane().setContent(ta);
        
        alert.showAndWait();
    }
    
    @FXML
    private void initGame(ActionEvent event) throws IOException {
        wgp.initialize();
        rootLayout.getChildren().remove(initButton);
        
    }
    
    @FXML
    private void showWinner(ActionEvent event) throws IOException {
        wgp.displayWinner();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wgp = new WarGamePane();
        
        rootLayout.getChildren().add(wgp);
        initButton.toFront();
        
        wgp.setOnMouseClicked(e
                -> {
            if (!wgp.inTransition) {
                if (!wgp.cardsHaveBeenDealt) {
                    wgp.dealCards();
                    player1Label.setVisible(true);
                    player2Label.setVisible(true);
                } else {
                    wgp.playCards();
                }
            }
        });
    }
}
