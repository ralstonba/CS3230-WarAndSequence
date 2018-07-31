/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ralst
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception
    {
	Parent splashScreenRoot = FXMLLoader.load(getClass().getResource("views/splashScreen.fxml"));

	Scene splashScreenScene = new Scene(splashScreenRoot);

	stage.setScene(splashScreenScene);
	stage.show();

	Parent mainMenuParent = FXMLLoader.load(getClass().getResource("views/mainMenu.fxml"));
	Scene scene = new Scene(mainMenuParent);

	PauseTransition splashScreenDisplayDelay = new PauseTransition(Duration.seconds(2));
	splashScreenDisplayDelay.setOnFinished(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event)
	    {
		stage.setScene(scene);
		stage.show();
	    }
	});
	splashScreenDisplayDelay.play();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	launch(args);
    }

}
