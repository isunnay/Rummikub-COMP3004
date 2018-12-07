package com.COMP3004.Rummikub.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	@FXML
	TextField howManyPlayers;
	
	public void MainController() {
	
	}
	
	public void newGameButtonClick(ActionEvent event) throws IOException {
		//System.out.println(howManyPlayers.getText());
		int numberOfPlayers = Integer.parseInt(howManyPlayers.getText());
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/rummikubView.fxml"));
		Parent gameViewParent = loader.load();
		RummikubController controller = loader.getController();
		controller.initData(numberOfPlayers);
		//controller.startGame();
		
		
		Scene gameViewScene = new Scene(gameViewParent);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(gameViewScene);
		window.show();
	}

}
