package com.COMP3004.Rummikub;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//import com.COMP3004.Rummikub.controller.GameEngine;
import com.COMP3004.Rummikub.models.Game;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class RummikubMain extends Application{
	
	StringBuilder sb;
	Stage window;
	//private GameEngine engine;
	Game game;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Rummikub-Team22");
		
		Pane root = new Pane();
		BorderPane menuBarLayout = new BorderPane();
		MenuBar menuBar = new MenuBar();
		VBox wholeLayout = new VBox(20);
		HBox buttonLayout = new HBox(20);
		Region background = new Region();
		Rectangle board = new Rectangle(750, 490);
//		Button btnHit = new Button("Hit");
//        Button btnStand = new Button("Stand");
        
		root.setPrefSize(800, 600);
        background.setPrefSize(800, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");
        board.setArcWidth(50);
        board.setArcHeight(50);
        board.setFill(Color.GREEN);
  //      btnHit.setDisable(true);
//	    btnStand.setDisable(true);
	    
		
		// Input file menu
		Menu fileMenu = new Menu ("_File");

		// New Game Menu Item
		MenuItem newGame = new MenuItem("_New Game...");
		newGame.setOnAction(e -> {
			System.out.println("-------------------------");
			System.out.println("Creating new game...");
			//game = new Game();
			RummikubApplication rummikubApp = new RummikubApplication();
			try {
				rummikubApp.run();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			/*try {
				game.play();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		});
		fileMenu.getItems().add(newGame);
		fileMenu.getItems().add(new SeparatorMenuItem());
		
		// Input File Menu Item
		MenuItem inputFile = new MenuItem("_Input File...");
		inputFile.setOnAction(e -> {
			System.out.println("Inputting file...");
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter(".txt File", "*.txt"));
			File file = fileChooser.showOpenDialog(null);
			
			if (file != null) {
				try {
					sb = new StringBuilder();
					Scanner input = new Scanner(file);
					
					while(input.hasNext()) {
						sb.append(input.nextLine());
						sb.append("\n");
					}
					
					System.out.println(sb);
					
					input.close();
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			} else {
				System.out.println("Invalid file.");
			}
			
		});
		fileMenu.getItems().add(inputFile);
		fileMenu.getItems().add(new SeparatorMenuItem());
		
		// Exit Menu Item
		MenuItem exitWindow = new MenuItem("_Exit");
		exitWindow.setOnAction(e -> { window.close(); });
		fileMenu.getItems().add(exitWindow);
		
		// Main menu bar
		menuBar.getMenus().addAll(fileMenu);
		menuBarLayout.setTop(menuBar);
		menuBarLayout.setPrefWidth(800);
		buttonLayout.setPrefWidth(800);
		buttonLayout.setAlignment(Pos.CENTER);
		
        // Adding children
        wholeLayout.getChildren().addAll(menuBarLayout, new StackPane(board), buttonLayout);
        root.getChildren().addAll(background, wholeLayout);
		
		Scene scene = new Scene (root, 800/2, 600/2);
		window.setScene(scene);
		window.show();
	}
}
