package com.COMP3004.Rummikub.controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.COMP3004.Rummikub.models.AI2;
import com.COMP3004.Rummikub.models.Board;
import com.COMP3004.Rummikub.models.Deck;
import com.COMP3004.Rummikub.models.Human;
import com.COMP3004.Rummikub.models.Observer;
import com.COMP3004.Rummikub.models.PlayerType;
import com.COMP3004.Rummikub.models.Spot;
import com.COMP3004.Rummikub.models.Subject;
import com.COMP3004.Rummikub.models.Tile;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class RummikubController implements Subject{
	private MouseGestures mg;
	private int numberOfPlayers;
	private boolean gameInProgress = false;
	private Deck deck;
	private Board board;
	private ArrayList<PlayerType> allPlayers;
	private ArrayList<Observer> observers;
	Scanner reader;
	
	
	@FXML
	public GridPane gridPane;
	@FXML
	public TilePane tilePane;
	//private Group boardSpots;
	
	public RummikubController() {
		System.out.println("Testing Constructor");

	}
	
	public void initData(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		System.out.println(numberOfPlayers);
		
	}
	
	@FXML
	public void startGame() {
		setUpBoard();
		// Start the game
		gameInProgress = true;
		// Initialize the deck
		deck = new Deck();
		// Shuffle the deck
		deck.shuffleTiles();
		mg=new MouseGestures();
		
		allPlayers = new ArrayList<PlayerType>();
		observers = new ArrayList<Observer>();
		
		// Add human's based on input
		for (int i = 0; i < numberOfPlayers; i++) {
			allPlayers.add(new Human(deck, this));
			observers.add(allPlayers.get(i));
		//	System.out.println(allPlayers.get(i).getHand().handToString());
		}
		
		// Fill in the rest with AI (random chance of each AI strategy)
	/*	for (int i = allPlayers.size(); i < 4; i++) {
			int foo = (int) (Math.random() * 100);
			if (foo < 34) {
				allPlayers.add(new AI1(deck, this));
			} else if (foo < 67){
				//allPlayers.add(new AI2(deck)); 
				allPlayers.add(new AI2(deck, this));
			} else {
				//allPlayers.add(new AI3(deck, this));
				allPlayers.add(new AI3(deck, this));
			}
			observers.add(allPlayers.get(i));
		}*/
		
		// Determine who starts
		determineStarter();
		setUpPlayerHand(whosTurn()-1);
	}
	
	@FXML
	public void setUpBoard() {
		board = new Board();

	   for(int i=0; i<board.getSpotsArray().length; i++) {
	        for(int j=0; j<board.getSpotsArray()[i].length; j++) {
	        	System.out.println("adding"+i+j);
	        	Spot spot = board.getSpot(i, j);
	        	StackPane stackPane = new StackPane();
	        	stackPane.getChildren().add(spot);
	        	stackPane.setUserData(spot);
	        	gridPane.getChildren().addAll(stackPane);
	        	GridPane.setRowIndex(stackPane, j);
	        	GridPane.setColumnIndex(stackPane, i);
	        	GridPane.setFillWidth(stackPane, true);
	        	GridPane.setFillHeight(stackPane, true);
	        	
	        	gridPane.getChildren().forEach(item -> {
	        		item.setOnMousePressed(e->{
		        		System.out.println(((Spot) item.getUserData()));
		        		System.out.println(item.getLayoutX());
		        		System.out.println(item.getLayoutY());
		        	});
	        	});
	        }
	   }
	   Tile tile = new Tile("R", 2);
	   board.getSpot(1, 0).playTile(tile);
	   GridPane.setConstraints(tile, 1, 0);
	   gridPane.getChildren().addAll(tile);
	   System.out.println(tile.getParent());
	   
	}
	
	@FXML
	public void setUpPlayerHand(int player) {
		System.out.println("Showing players hand: " + (player+1));
   
        tilePane.setVgap(50);
        tilePane.setHgap(50);
        tilePane.setStyle("-fx-background-color: lightgrey;");

		for(int i=0;i<allPlayers.get(player).getHand().size;i++) {
			Tile tile = allPlayers.get(player).getHand().getTile(i);
			
        	tilePane.getChildren().forEach(item -> {
        		item.setOnMouseReleased(e->{
        			handleDrop(e);
	        	});
        	});
        	
			tilePane.getChildren().addAll(tile);
			
		}
	}
	

	private void handleDrop(MouseEvent event) {
		boolean test = false;
		

        Node node = (Node) event.getSource();
        Tile tile = (Tile)node;
        mg.fixPosition(node);
        node.setManaged(true);
        
        if(tilePane.getChildren().contains(node)) {
        	tilePane.getChildren().remove(tile);
            gridPane.getChildren().addAll(tile);
        }
        
        int x = getSpotX(node);
        int y = getSpotY(node);
        
        board.getSpot(x, y).playTile((Tile)node);
 	   GridPane.setConstraints(tile, x, y);

        if(test) {
        	mg.moveToSource(node);
        }
      
	}
	
	private int getSpotX(Node node) {
		System.out.println("Node LayoutX: " + node.getLayoutX());
		
		int start = 10;
		int end = 92;
		
		for(int i=0;i<15;i++) {
			System.out.println(i);

			if(node.getLayoutX()>=start && node.getLayoutX()<end) {	
				return i;
			}
			else {
				start+=82;
				end+=82;	
			}
		}
		return -1;
	}
	
	private int getSpotY(Node node) {
		System.out.println("Node LayoutY: " + node.getLayoutY());
		
		int start = -595;
		int end = -558;
		
		for(int i=0;i<15;i++) {
			System.out.println(i);

			if(node.getLayoutY()>=start && node.getLayoutY()<end) {	
				return i;
			}
			else {
				start+=37;
				end+=37;	
			}
		}
		return -1;
	}
	
	

	private boolean isValidPlay() {
		return false;
	}
	

	
	@FXML
	public void drawTile() {
		int who = whosTurn()-1;
		
		if (allPlayers.get(who).hasTilesBeenPlayed() == false) {
			Tile t = allPlayers.get(who).getHand().dealTile(deck);
			tilePane.getChildren().addAll(t);
			tilePane.getChildren().clear();
			nextPlayersTurn(who);
		} else {
			tilePane.getChildren().clear();
			nextPlayersTurn(who);
		}
	}
	
	@FXML
	public void reorganizeTiles() {
		tilePane.getChildren().clear();
		setUpPlayerHand(whosTurn()-1);
	}


	
	
	public void clickGrid(javafx.scene.input.MouseEvent event) {
	    Node clickedNode = event.getPickResult().getIntersectedNode();
	   // if (clickedNode != gridPane) {
	        // click on descendant node
	        Integer colIndex = GridPane.getColumnIndex(clickedNode);
	        Integer rowIndex = GridPane.getRowIndex(clickedNode);
	        System.out.println("Mouse clicked cell: " + colIndex + " And: " + rowIndex);
	       // System.out.println(board.getSpot(colIndex, rowIndex).isSpotEmpty());
        	
	        if(clickedNode instanceof StackPane) {
	        	System.out.println(clickedNode.getUserData());
	        }
	   // }
	}
	
	private int anyWinners() {
		if (getPlayer(0).getHand().getNumTiles() == 0) {
			return 1;
		} else if (getPlayer(1).getHand().getNumTiles() == 0) {
			return 2;
		} else if (getPlayer(2).getHand().getNumTiles() == 0) {
			return 3;
		} else if (getPlayer(3).getHand().getNumTiles() == 0) {
			return 4;
		} else {
			return 0;
		}
	}
	private void endGame() {
		// Variables
		int winner = getWinner();

		// Finish the game
		gameInProgress = false;

		// Winning message + calculate score
		if (winner == 1) {
			System.out.println("Player 1 Won!");
		} else if (winner == 2) {
			System.out.println("Player 2 Won!");
		} else if (winner == 3) {
			System.out.println("Player 3 Won!");
		} else if (winner == 4) {
			System.out.println("Player 4 Won!");
		}
	}
	
	public void nextPlayersTurn(int i) {
		// Sets next players turn
		allPlayers.get(i).setTurnStatus(false);
		
		if (i <= 2) {
			allPlayers.get(i+1).setTilesBeenPlayed(false);
			allPlayers.get(i+1).setTurnStatus(true);
			setUpPlayerHand(i+1);
		} else {
			allPlayers.get(0).setTilesBeenPlayed(false);
			allPlayers.get(0).setTurnStatus(true);
			setUpPlayerHand(0);
		}
	}
	
	public void determineStarter() {
		// TD = Tiles Drawn
		// MV = Max Value
		int p1TD = 0, p2TD = 0, p3TD = 0, p4TD = 0,
			p1MV = 0, p2MV = 0, p3MV = 0, p4MV = 0;
		
		while (true) {
			p1MV = allPlayers.get(0).getHand().getTile(p1TD).getValue();
			p2MV = allPlayers.get(1).getHand().getTile(p2TD).getValue();
			p3MV = allPlayers.get(2).getHand().getTile(p3TD).getValue();
			p4MV = allPlayers.get(3).getHand().getTile(p4TD).getValue();
			
			// 0 = Joker. While no one has a Joker
			if (p1MV == 0) { allPlayers.get(0).getHand().getTile(p1TD).getValue(); p1TD++; }
			if (p2MV == 0) { allPlayers.get(1).getHand().getTile(p2TD).getValue(); p2TD++; }
			if (p3MV == 0) { allPlayers.get(2).getHand().getTile(p3TD).getValue(); p3TD++; }
			if (p4MV == 0) { allPlayers.get(3).getHand().getTile(p4TD).getValue(); p4TD++; }
			
			// if anyone equals someone else
			if (p1MV == p2MV) { 
				p1MV = allPlayers.get(0).getHand().getTile(p1TD).getValue(); p1TD++;
				p2MV = allPlayers.get(1).getHand().getTile(p2TD).getValue(); p2TD++;
			} else if (p1MV == p3MV) {
				p1MV = allPlayers.get(0).getHand().getTile(p1TD).getValue(); p1TD++;
				p3MV = allPlayers.get(2).getHand().getTile(p3TD).getValue(); p3TD++;
			} else if (p1MV == p4MV) {
				p1MV = allPlayers.get(0).getHand().getTile(p1TD).getValue(); p1TD++;
				p4MV = allPlayers.get(3).getHand().getTile(p4TD).getValue(); p4TD++;
			} else if (p2MV == p3MV) {
				p2MV = allPlayers.get(1).getHand().getTile(p2TD).getValue(); p2TD++;
				p3MV = allPlayers.get(2).getHand().getTile(p3TD).getValue(); p3TD++;
			} else if (p2MV == p4MV) {
				p2MV = allPlayers.get(1).getHand().getTile(p2TD).getValue(); p2TD++;
				p4MV = allPlayers.get(3).getHand().getTile(p4TD).getValue(); p4TD++;
			} else if (p3MV == p4MV) {
				p3MV = allPlayers.get(2).getHand().getTile(p3TD).getValue(); p3TD++;
				p4MV = allPlayers.get(3).getHand().getTile(p4TD).getValue(); p4TD++;
			} else if (p1MV >= 10 || p2MV >= 10 || p3MV >= 10 || p4MV >= 10) {
				break;
			} else {
				/*System.out.println(allPlayers.get(0).getHand().getTile(p1TD).tileToString() + " - " + p1TD);
				System.out.println(allPlayers.get(1).getHand().getTile(p2TD).tileToString() + " - " + p2TD);
				System.out.println(allPlayers.get(2).getHand().getTile(p3TD).tileToString() + " - " + p3TD);
				System.out.println(allPlayers.get(3).getHand().getTile(p4TD).tileToString() + " - " + p4TD);*/
				p1TD++; p2TD++; p3TD++; p4TD++;
				break;
			}
		}
		
		System.out.println("P" + checkHighestValue(p1MV, p2MV, p3MV, p4MV) + " has the highest value and therefore will start the game.");
		
		if (checkHighestValue(p1MV, p2MV, p3MV, p4MV) == "1") {
			allPlayers.get(0).setTurnStatus(true); allPlayers.get(1).setTurnStatus(false);
			allPlayers.get(2).setTurnStatus(false); allPlayers.get(3).setTurnStatus(false);
		} else if (checkHighestValue(p1MV, p2MV, p3MV, p4MV) == "2") {
			allPlayers.get(0).setTurnStatus(false); allPlayers.get(1).setTurnStatus(true);
			allPlayers.get(2).setTurnStatus(false); allPlayers.get(3).setTurnStatus(false);
		} else if (checkHighestValue(p1MV, p2MV, p3MV, p4MV) == "3") {
			allPlayers.get(0).setTurnStatus(false); allPlayers.get(1).setTurnStatus(false);
			allPlayers.get(2).setTurnStatus(true); allPlayers.get(3).setTurnStatus(false);
		} else if (checkHighestValue(p1MV, p2MV, p3MV, p4MV) == "4") {
			allPlayers.get(0).setTurnStatus(false); allPlayers.get(1).setTurnStatus(false);
			allPlayers.get(2).setTurnStatus(false); allPlayers.get(3).setTurnStatus(true);
		}
		
		for (int i = 0; i < 4; i++) {
			//System.out.println(allPlayers.get(i).getHand().handToString());
			allPlayers.get(i).getHand().sortHand();
		}
	}
	
	private String checkHighestValue(int p1, int p2, int p3, int p4) {		
	    int max = p1;
	    String player = "1";
	    
	    if (p2 > max) { max = p2; player = "2"; }
	    if (p3 > max) { max = p3; player = "3"; }
	    if (p4 > max) { max = p4; player = "4"; }
		
	    return player;
	}
	
	/*

	public void playTurn(int i) {
		printAll();
		// Play if human
		if (allPlayers.get(i).isAI() == false && allPlayers.get(i).myTurnStatus() == true) {
			System.out.println("Player " + (i+1) + "'s Hand[" + allPlayers.get(i).getHand().size + "]: " + allPlayers.get(i).getHand().handToString());
			try {
				allPlayers.get(i).play(reader, deck);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Play if AI
		if (allPlayers.get(i).isAI() == true && allPlayers.get(i).myTurnStatus() == true) {
			allPlayers.get(i).play(reader);
			
			if (allPlayers.get(i).hasTilesBeenPlayed() == false) {
				Tile t = allPlayers.get(i).getHand().dealTile(deck);
				System.out.println("Turn ended: Player " + (i+1) + " has decided to draw a tile.");
				System.out.println("Tile drawn: " + t.tileToString());
				nextPlayersTurn(i);
			}
		}
		// Sets next players turn
		nextPlayersTurn(i);
	}
*/
	public void play() throws InterruptedException {
		notifyObservers();
		if (anyWinners() > 0) {
			endGame();
		}
	}

	public int whosTurn() {
		if (allPlayers.get(0).myTurnStatus() == true) {
			return 1;
		} else if (allPlayers.get(1).myTurnStatus() == true) {
			return 2;
		} else if (allPlayers.get(2).myTurnStatus() == true) {
			return 3;
		} else if (allPlayers.get(3).myTurnStatus() == true) {
			return 4;
		} else {
			return -1;
		}
	}
	
	public boolean inProgress() {
		return this.gameInProgress;
	}

	public Deck getDeck() {
		return this.deck;
	}

	public Board getBoard() {
		return this.board;
	}

	public ArrayList<PlayerType> getAllPlayers() {
		return this.allPlayers;
	}

	public PlayerType getPlayer(int i) {
		return this.getAllPlayers().get(i);
	}

	public int getPlayerCount() {
		return this.getAllPlayers().size();
	}
	
	public int getWinner() {
		return anyWinners();
	}
	
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		int observerIndex = observers.indexOf(o);
		if (observerIndex >= 0) {
			observers.remove(observerIndex);
		}
	}

	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer) observers.get(i);
			observer.update(board);
		}
	}
	
   /* private class Delta {
        public double x;
        public double y;
    }*/


}




//