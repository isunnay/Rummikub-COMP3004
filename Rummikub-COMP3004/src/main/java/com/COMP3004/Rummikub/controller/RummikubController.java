package com.COMP3004.Rummikub.controller;

//import java.awt.Label;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.COMP3004.Rummikub.models.AI1;
import com.COMP3004.Rummikub.models.AI2;
import com.COMP3004.Rummikub.models.Board;
import com.COMP3004.Rummikub.models.Deck;
import com.COMP3004.Rummikub.models.Human;
import com.COMP3004.Rummikub.models.Observer;
import com.COMP3004.Rummikub.models.PlayerType;
import com.COMP3004.Rummikub.models.Spot;
import com.COMP3004.Rummikub.models.Subject;
import com.COMP3004.Rummikub.models.Tile;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
import javafx.scene.text.Text;


public class RummikubController implements Subject{
	private MouseGestures mg;
	private int numberOfPlayers;
	private boolean gameInProgress = false;
	private Deck deck;
	private Board board;
	private ArrayList<PlayerType> allPlayers;
	private ArrayList<Observer> observers;
	Scanner reader;
	private ArrayList<Node> nodesOnTurnTileToGrid;
	private ArrayList<Node> nodesOnTurnGridToGrid;
	private ArrayList<Spot> tempArrayList = new ArrayList<Spot>();
	private Board snapshot;
	
	
	@FXML
	public GridPane gridPane;
	@FXML
	public TilePane tilePane;
	@FXML
	private Label whosTurnLabel;
	@FXML
	private Label initialMeld;
	
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
		nodesOnTurnTileToGrid = new ArrayList<Node>();
		nodesOnTurnGridToGrid = new ArrayList<Node>();
		
		// Add human's based on input
		for (int i = 0; i < numberOfPlayers; i++) {
			allPlayers.add(new Human(deck, this));
			observers.add(allPlayers.get(i));
		//	System.out.println(allPlayers.get(i).getHand().handToString());
		}
		
		// Fill in the rest with AI (random chance of each AI strategy)
		for (int i = allPlayers.size(); i < 4; i++) {
			int foo = (int) (Math.random() * 100);
			if (foo < 34) {
				allPlayers.add(new AI1(deck, this));
			}
		else if (foo < 67){
				//allPlayers.add(new AI2(deck)); 
				allPlayers.add(new AI1(deck, this));
			} else {
				//allPlayers.add(new AI3(deck, this));
				allPlayers.add(new AI1(deck, this));
			}
			observers.add(allPlayers.get(i));
		}
		
		// Determine who starts
		determineStarter();
		nextPlayersTurn(whosTurn()-1);
		
	}
	
	
	
	@FXML
	public void setUpBoard() {
		board = new Board();
		snapshot = new Board();

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
	        		item.setStyle("-fx-background-color: white; -fx-border-color: black");
	        		item.setOnMousePressed(e->{
		        		System.out.println(((Spot) item.getUserData()));
		        		System.out.println(item.getLayoutX());
		        		System.out.println(item.getLayoutY());
		        		//item.setStyle("-fx-background-color: #cf1020");
		        	});
	        	});
	        }
	   }

	}
	
	@FXML void setUpAIHand(int player) {
		notifyObservers();
		System.out.println("Showing players hand: " + (player+1));
   
        tilePane.setVgap(50);
        tilePane.setHgap(50);
        tilePane.setStyle("-fx-background-color: lightgrey;");
        System.out.println("IS AI HAND VISIBLE: " + tilePane.isVisible());
        //tilePane.setVisible(false);

		for(int i=0;i<allPlayers.get(player).getHand().size;i++) {
			Tile tile = allPlayers.get(player).getHand().getTile(i);
			
			tilePane.getChildren().addAll(tile);
			
		}
	}
	
	 @FXML
	 public void updateCurrentPlayer() {
		 boolean aiOrNaw = allPlayers.get(whosTurn()-1).isAI();
		 String whosPlaying = "";
		 if (aiOrNaw) { whosPlaying = "(AI)"; } else { whosPlaying = "(Human)"; }
		 whosTurnLabel.setText(String.valueOf(whosTurn()) + whosPlaying);
		 
		 
		 boolean initDoneOrNaw = allPlayers.get(whosTurn()-1).hasInitialMeldBeenPlayed();
		 String displayText = "";
		 if (initDoneOrNaw) { displayText = "Complete"; } else { displayText = "Incomplete"; }
		 initialMeld.setText(displayText);
	 }
	
	@FXML
	public void setUpPlayerHand(int player) {
		notifyObservers();
		//tilePane.setVisible(true);
		System.out.println("Showing players hand: " + (player+1));
		System.out.println("IS Player HAND VISIBLE: " + tilePane.isVisible());
   
        tilePane.setVgap(50);
        tilePane.setHgap(50);
        tilePane.setStyle("-fx-background-color: lightgrey;");

		for(int i=0;i<allPlayers.get(player).getHand().size;i++) {
			Tile tile = allPlayers.get(player).getHand().getTile(i);

			tilePane.getChildren().addAll(tile);
			
		}
		tilePane.getChildren().forEach(item -> {
    		item.setOnMouseReleased(e->{
    			handleDrop(e);
    			
        	});
    	});
	}
	

	private void handleDrop(MouseEvent event) {

        Node node = (Node) event.getSource();
        Tile tile = (Tile)node;
        
        node.setManaged(true);
        
        //From Hand to board
        if(tilePane.getChildren().contains(node)) {
        	System.out.println("Trying to drag in different panes");
        	mg.fixPosition(node);
        	tilePane.getChildren().remove(tile);
            gridPane.getChildren().addAll(tile);
            
            /*
            gridPane.getChildren().forEach(item -> {
        		item.setStyle("-fx-background-color: lightgrey; -fx-border-color: black");
        	});
        	*/
            
            int x = getSpotX(node);
            int y = getSpotY(node);
            if(x!=-1 && y!=-1) {
            	tile.justSwitched = true;
	     	    //GridPane.setConstraints(tile, x, y);
	     	    GridPane.setColumnIndex(tile, x);
	     	    GridPane.setRowIndex(tile, y);
	     	    allPlayers.get(whosTurn()-1).addTile(tile, x, y);
	     	    this.nodesOnTurnTileToGrid.add(node);
	     	   // Spot spot = board.getSpot(x, y);
	     	    //tile.setOldSpot(spot);
            }
            else {
            	this.nodesOnTurnTileToGrid.remove(node);
            	gridPane.getChildren().remove(tile);
            	tilePane.getChildren().addAll(tile);
            	//mg.moveToSource(node);
            }
        }
        //Grid to Grid
        else if(gridPane.getChildren().contains(node)) {
        	//node.setManaged(false);
        	mg.fixPosition(node);
        	System.out.println("Trying to drag within same pane");
        	int x = getSpotOnGridX(node);
        	int y = getSpotOnGridY(node);
        	Spot oldSpot = tile.getOldSpot();
        	oldSpot.setIsTaken(false);
        	System.out.println(oldSpot);
        	
        	if(x!=-1 && y!=-1) {
	        	GridPane.setConstraints(node, x, y);
	        	Spot spot = board.getSpot(x, y);
	        	//if(oldSpot.)
	        	allPlayers.get(whosTurn()-1).moveTile(tile,spot);
	        	this.nodesOnTurnGridToGrid.add(node);
	        	//tile.setOldSpot(oldSpot);
        	 }
        	 else {
             	//mg.moveToSource(node);
             }
        	
        }
        else {
        	System.out.println("You are out of location. End turn to try again.");
        }
      
	}
	
	private int getSpotOnGridY(Node node) {
		System.out.println("Node LayoutY: " + node.getLayoutY());
		int start = 4;
		int end = 41;
		
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

	private int getSpotOnGridX(Node node) {
		//System.out.println("Node LayoutX: " + node.getLayoutX());
		
		int start = -10;
		int end = 73;
		
		for(int i=0;i<15;i++) {
			System.out.println(i);

			if(node.getLayoutX()>=start && node.getLayoutX()<end) {	
				return i;
			}
			else {
				start+=83;
				end+=83;	
			}
		}
		return -1;
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
	
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren())
            if (GridPane.getColumnIndex(node) != null
                    && GridPane.getColumnIndex(node) != null
                    && GridPane.getRowIndex(node) == row
                    && GridPane.getColumnIndex(node) == col)
                return node;
        return null;
    }
	

    
    public void highlightRecent() {
    	
    	for (int col = 0; col < 15; col++) {
			for (int row = 0; row < 15; row++) {
				if (board.isSpotFilled(col, row)) {
					System.out.println("Board Spot filled");
					System.out.println(snapshot.isSpotFilled(col, row));
					//if (snapshot.isSpotFilled(col, row) == false) {
					if(!(tempArrayList.contains(board.getSpot(col, row)))) {
						tempArrayList.add(board.getSpot(col, row));
						System.out.println("new tiles in play at: " + col + "," + row);
						getNodeFromGridPane(gridPane, col, row).setStyle("-fx-background-color: lightgrey; -fx-border-color: black");
					}
				}
			}
		}
    }
    
    public void removeHighlight() {
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				if (board.isSpotFilled(row, col) && snapshot.isSpotFilled(row, col) && board.getTileAtSpot(row, col).equals(snapshot.getTileAtSpot(row, col))) {
					System.out.println("old tiles in play at: " + row + "," + col);
					getNodeFromGridPane(gridPane, row, col).setStyle("-fx-background-color: white; -fx-border-color: black");
				}
			}
		}
    }

	
	@FXML
	public void drawTile() {
		int who = whosTurn()-1;
		
		board.boardToString();
		
		//if (allPlayers.get(who).hasTilesBeenPlayed()) { nextPlayersTurn(who); }
		//if (allPlayers.)


		 if(board.checkIfValidMelds()==false) {	
 	    	//Adding back to hand
 	    	if(nodesOnTurnTileToGrid.size()>0) {
 	    		System.out.println("NodesOnTurnTileToGrid: "+ nodesOnTurnTileToGrid.size());
	 	    	for(int i=0;i<nodesOnTurnTileToGrid.size();i++) {
	 	 	        if(gridPane.getChildren().contains(nodesOnTurnTileToGrid.get(i))) {
	 	 	        	System.out.println("IntheSwitch");
	 	 	        	gridPane.getChildren().remove(nodesOnTurnTileToGrid.get(i));
	 	 	            tilePane.getChildren().addAll(nodesOnTurnTileToGrid.get(i));
	 	 	          allPlayers.get(who).undoTurn();
	 	 	          allPlayers.get(who).setTilesBeenPlayed(false);
	 	 	        }
	 	    		//mg.moveToSource(nodesOnTurn.get(i));
	 	    	}
	 	    	nodesOnTurnTileToGrid.clear();
 	    	}
 	    	//Adding back to old board location
 	    	else if(nodesOnTurnGridToGrid.size()>0) {
 	    		System.out.println("NodesOnTurnGridToGrid: "+ nodesOnTurnGridToGrid.size());
	 	    	for(int i=0;i<nodesOnTurnGridToGrid.size();i++) {
	 	    		Tile tile = (Tile) this.nodesOnTurnGridToGrid.get(i);
	 	    		//System.out.println(tile);
	 	    		System.out.println(nodesOnTurnGridToGrid.get(i));
	 	    		System.out.println(tile.justSwitched);
	 	 	        if(tile.justSwitched == false && gridPane.getChildren().contains(nodesOnTurnGridToGrid.get(i))) {
	 	 	        	System.out.println("ARE WE IN HERE");
	 	 	        	//gridPane.getChildren().setAll(nodesOnTurnGridToGrid.get(i));
	 	 	        	//Tile tile = (Tile) this.nodesOnTurnGridToGrid.get(i).getUserData();
	 	 	        	System.out.println(tile.getOldSpot());
	 	 	        	int anX = tile.getOldSpot().getSpotX();
	 	 	        	int aY = tile.getOldSpot().getSpotY();
	 	 	        	System.out.println(anX);
	 	 	        	System.out.println(aY);

	 	 	        	GridPane.setColumnIndex(tile, anX);
	 	 	        	GridPane.setRowIndex(tile, aY);
	 	 	        	allPlayers.get(who).undoTurn();
	 	 	        	//gridPane.add(nodesOnTurnGridToGrid.get(i),anX, aY);
	 	 	        	//gridPane.getChildren().add(tile);
	 	 	        	//gridPane.add(tile, anX, aY);
	 	 	        }
	 	    		//mg.moveToSource(nodesOnTurn.get(i));
	 	    	}
	 	    	nodesOnTurnGridToGrid.clear();
 	    		//MAke sure we add to an available spot
 	    	}
 	    	
 	    }
	    else if (allPlayers.get(who).hasTilesBeenPlayed() == false && board.checkIfValidMelds()==true) {
			Tile t = allPlayers.get(who).getHand().dealTile(deck);
			tilePane.getChildren().addAll(t);
			tilePane.getChildren().clear();
			notifyObservers();
			nextPlayersTurn(who);
			takeSnap(true);
		} else {
			tilePane.getChildren().clear();
			notifyObservers();
			nextPlayersTurn(who);
			takeSnap(false);
			System.out.println("Hello WE ARE IN THE ELSE");
		}
	}
	
	public void takeSnap(boolean drew) {
		System.out.println("Board:");
		board.boardToString();
		
		System.out.println("Snapshot:");
		snapshot.boardToString();
		
    	highlightRecent();
		
		if (!drew) {
			System.out.println("========================== Taking Snapshot ==========================");
			removeHighlight();
			//snapshot = new Board();
			snapshot.clearBoard();
			for (int row = 0; row < 15; row++) {
				for (int col = 0; col < 15; col++) {
					Tile t = board.getSpot(row, col).getTile();
					snapshot.setTile(t, row, col);
				}
			}
		}
		//highlightRecent();
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
		tilePane.getChildren().clear();
		
		if (i <= 2) {
			if (allPlayers.get(i+1).isAI()==true) {
				allPlayers.get(i+1).setTilesBeenPlayed(false);
				allPlayers.get(i+1).setTurnStatus(true);
					setUpAIHand(i+1);
					allPlayers.get(i+1).play(reader);
					drawTile();
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
				}
			else if(allPlayers.get(i+1).isAI()==false) {
				allPlayers.get(i+1).setTilesBeenPlayed(false);
				allPlayers.get(i+1).setTurnStatus(true);
				setUpPlayerHand(i+1);	
			}

		} 
		else {
			if (allPlayers.get(0).isAI()==true) {
				allPlayers.get(0).setTilesBeenPlayed(false);
				allPlayers.get(0).setTurnStatus(true);
				setUpAIHand(0);
				allPlayers.get(0).play(reader);

				drawTile();
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//nextPlayersTurn(whosTurn()-1);
			}
			else if(allPlayers.get(0).isAI()==false) {
				allPlayers.get(0).setTilesBeenPlayed(false);
				allPlayers.get(0).setTurnStatus(true);
				setUpPlayerHand(0);	
			}
		}
		updateCurrentPlayer();
		
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
			System.out.println("Player1 Turn: " + allPlayers.get(0).myTurnStatus());
//			whosTurnLabel.setText("Player 1");
			return 1;
		} else if (allPlayers.get(1).myTurnStatus() == true) {
			System.out.println("Player2 Turn: " +allPlayers.get(1).myTurnStatus());
//			whosTurnLabel.setText("Player 2");
			return 2;
		} else if (allPlayers.get(2).myTurnStatus() == true) {
			System.out.println("Player3 Turn: " +allPlayers.get(2).myTurnStatus());
//			whosTurnLabel.setText("Player 3");
			return 3;
		} else if (allPlayers.get(3).myTurnStatus() == true) {
			System.out.println("Player4 Turn: " + allPlayers.get(3).myTurnStatus());
//			whosTurnLabel.setText("Player 4");
			return 4;
		} else {
			return -1;
		}
	}
	/*public void getWhosTurn() {
		if (allPlayers.get(0).myTurnStatus() == true) {
			whosTurnLabel.setText("Player 1");
		} else if (allPlayers.get(1).myTurnStatus() == true) {
			whosTurnLabel.setText("Player 2");
		} else if (allPlayers.get(2).myTurnStatus() == true) {
			whosTurnLabel.setText("Player 3");
		} else if (allPlayers.get(3).myTurnStatus() == true) {
			whosTurnLabel.setText("Player 4");
		} 
		
	}*/
	
	
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
		for(int i= 0;i<this.nodesOnTurnTileToGrid.size();i++) {
			Tile tile = (Tile) nodesOnTurnTileToGrid.get(i);
			tile.justSwitched = false;
		}
		this.nodesOnTurnTileToGrid.clear();
		this.nodesOnTurnTileToGrid.clear();
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer) observers.get(i);
			observer.update(board);
		}
	}
	


}




//