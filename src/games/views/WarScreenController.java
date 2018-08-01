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
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
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

    private WarGamePane wgp;
    


    @FXML
    private void handleMainMenuButton(ActionEvent event) throws IOException
    {
	Parent screen2Parent = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
	Scene scene = new Scene(screen2Parent);

	Stage stage = (Stage) menuBar.getScene().getWindow();

	stage.setScene(scene);
	stage.show();
    }
    
    @FXML
    private void initGame(ActionEvent event) throws IOException{
	wgp.initialize();
	rootLayout.getChildren().remove(initButton);
    }
    
    @FXML
    private void showWinner(ActionEvent event) throws IOException{
	wgp.displayWinner();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	wgp = new WarGamePane();
	
	rootLayout.getChildren().add(wgp);
	initButton.toFront();
	
	    wgp.setOnMouseClicked(e
		-> 
		{
		    if ( !wgp.inTransition)
		    {
			if ( !wgp.cardsHaveBeenDealt )
			{
			    wgp.dealCards();
			} else
			{
			    wgp.playCards();
			}
		    }
	});
    }
}
