/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs3230_finalproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
public class WarScreenController implements Initializable {

    @FXML
    private Button mainMenuButton;
    
    @FXML
    private void handleMainMenuButton(ActionEvent event) throws IOException {
        Parent screen2Parent = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(screen2Parent);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	// TODO
    }
    
    
    
}
