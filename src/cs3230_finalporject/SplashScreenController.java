/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs3230_finalporject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ralst
 */
public class SplashScreenController implements Initializable {

    @FXML
    private Text splashScreenText;
    
    private void startSplashScreenDelay() throws IOException{
	Parent mainMenuParent = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(mainMenuParent);
        
        Stage stage = (Stage)splashScreenText.getScene().getWindow();
        
        stage.setScene(scene);
	
	PauseTransition splashScreenDisplayDelay = new PauseTransition(Duration.seconds(2));
	splashScreenDisplayDelay.setOnFinished(event -> stage.show());
	splashScreenDisplayDelay.play();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	try
	{
	    startSplashScreenDelay();
	} catch (IOException ex)
	{
	    Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }    
    
}
