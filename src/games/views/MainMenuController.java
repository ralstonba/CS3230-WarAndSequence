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
public class MainMenuController implements Initializable {

    @FXML
    private Button warButton;
    
    @FXML
    private Button sequenceButton;
    
    @FXML
    private void handleWarButton(ActionEvent event) throws IOException {
        System.out.println("Lets play war!!");
        Parent screen2Parent = FXMLLoader.load(getClass().getResource("warDescription.fxml"));
        Scene scene = new Scene(screen2Parent);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
	stage.centerOnScreen();
        stage.show();
    }
    
    @FXML
    private void handleSequenceButton(ActionEvent event) throws IOException {
        System.out.println("Lets play war!!");
        Parent screen2Parent = FXMLLoader.load(getClass().getResource("sequenceDescription.fxml"));
        Scene scene = new Scene(screen2Parent);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
	stage.centerOnScreen();
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	// TODO
    }    
    
}
