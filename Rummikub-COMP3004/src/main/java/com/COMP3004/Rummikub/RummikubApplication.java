package com.COMP3004.Rummikub;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RummikubApplication {
	Stage window;
	
	private Pane root = new Pane();
	
	private Parent createContent() {
		//root.setPrefSize(600, 800);
		return root;
	}

	public void start(Stage stage) throws Exception {
		System.out.println("In rummikubApplication.java");
		window = stage;
		window.setTitle("Rummikub - Team 22");
		//Scene scene = new Scene (root, 800/2, 600/2);
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        Button button3 = new Button("Button 3");
        Button button4 = new Button("Button 4");
        Button button5 = new Button("Button 5");
        Button button6 = new Button("Button 6");

        GridPane gridPane = new GridPane();

        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);
        gridPane.add(button3, 2, 0, 1, 1);
        gridPane.add(button4, 0, 1, 1, 1);
        gridPane.add(button5, 1, 1, 1, 1);
        gridPane.add(button6, 2, 1, 1, 1);

        Scene scene = new Scene(gridPane, 240, 100);
		window.setScene(scene);
		window.show();
	}

	public void run() throws Exception {
		Stage stage = new Stage();
		this.start(stage);
	}
	
	
	

}
