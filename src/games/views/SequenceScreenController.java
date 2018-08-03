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
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
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
    private void handleMainMenuButton(ActionEvent event) throws IOException
    {
	Parent screen2Parent = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
	Scene scene = new Scene(screen2Parent);

	Stage stage = (Stage) menuBar.getScene().getWindow();

	stage.setScene(scene);
        stage.centerOnScreen();
	stage.show();
    }
    
    @FXML
    private void initGame(){
        rootLayout.getChildren().remove(initButton);
        sp.initilize();
        sp.shuffle();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	sp = new SequencePane();
	rootLayout.getChildren().add(sp);
        initButton.toFront();
    }   
    
}
