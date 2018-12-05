package com.COMP3004.Rummikub;

import com.COMP3004.Rummikub.controller.RummikubController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RummikubApplication {
	Stage window;
	//RummikubController controller;
	

	public void start(Stage stage) throws Exception {
		/*System.out.println("In rummikubApplication.java");
		window = stage;
		window.setTitle("Rummikub - Team 22");
		Parent root = FXMLLoader.load(getClass().getResource("view/rummikubView.fxml"));
		//controller.setUpBoard();
		
		
		Scene scene = new Scene(root);
	
		window.setScene(scene);
		window.show();*/
		window = stage;
		window.setTitle("Rummikub - Team 22");
		Parent root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
		
		
		Scene scene = new Scene(root);
	
		window.setScene(scene);
		window.show();
	}

	public void run() throws Exception {
		Stage stage = new Stage();
		this.start(stage);
	}
	
	
	

}
