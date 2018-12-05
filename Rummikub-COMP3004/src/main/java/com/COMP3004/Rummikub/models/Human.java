package com.COMP3004.Rummikub.models;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.awt.event.ActionEvent;

import com.COMP3004.Rummikub.controller.RummikubController;

import javafx.scene.layout.GridPane;

public class Human implements PlayerType {
	Hand h;
	private boolean initialMeldPlayed = false;
	private boolean myTurn = true;
	private boolean hasTileBeenPlaced = false;
	public Subject game;
	public ArrayList<Spot> spotsTaken;
	public ArrayList<Tile> turnTiles;
	public ArrayList<Meld> turnMelds;
	public ArrayList<Tile> turnMoves;
	private Board board;
	private int turnValue;
	Scanner reader;
	private boolean isAI = false;
	private GameTimer timer;
	private char decision;
	private char decision2;
	private boolean returned;
	private BoardOriginator originator;
	private BoardCareTaker careTaker;

	public Human(Deck deck, Game game) {
		h = new Hand();
		h.createHand(deck);
		//h.sortHand();
		game.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();
		turnMoves = new ArrayList<Tile>();
		
		originator = new BoardOriginator();
		originator.setBoardState(game.getBoard());
		
		careTaker = new BoardCareTaker();
		careTaker.addMomento(originator.saveStateToMomento());
		
		originator.getStateFromMomento(careTaker.getMomento(careTaker.getMomentos().size() - 1));
	}


	public Human(Deck deck, RummikubController rummikubController) {
		h = new Hand();
		h.createHand(deck);
		//h.sortHand();
		rummikubController.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();
		turnMoves = new ArrayList<Tile>();
	}


	public Hand getHand() {
		return this.h;
	}

	public boolean myTurnStatus() {
		return this.myTurn;
	}

	public void setTurnStatus(boolean b) {
		/*if(b==true){
			timer.start();
		}
		else if(b==false){
			timer.stop();
		}*/
		turnTiles.clear();
		turnMelds.clear();
		turnMoves.clear();
		this.myTurn = b;
	}

	public boolean hasTilesBeenPlayed() {
		return this.hasTileBeenPlaced;
	}

	public void setTilesBeenPlayed(boolean b) {
		this.hasTileBeenPlaced = b;
	}

	
	public boolean hasInitialMeldBeenPlayed() {
		return this.initialMeldPlayed;
	}
	public void setHasInitialMeldBeenPlayed(boolean b) {
		this.initialMeldPlayed = b;
	}

	public boolean turnComplete(Hand h) {
		// Do Nothing...
		return false;
	}

	@Override
	public void update(Board board) {
		// turnTiles.clear();
		this.spotsTaken = board.filledSpots;
		this.board = board;
		
	}

	// && x+a<board.getX() && x+meld.getMeldSize() <= board.getX()
	@Override
	public boolean canWePlaceMeld(Meld meld, int x, int y) {
		// Spot beginningSpot = board.getSpot(x, y);
		for (int a = 0; a < meld.getMeldSize(); a++) {
			if (x + a >= board.getSpot(0, 0).getSpotX() && x + meld.getMeldSize() <= board.getX()) {
				if (x == 0) {
					if (board.getSpot(x + meld.getMeldSize(), y).isTaken = true) {
						Spot spot = board.getSpot(x + a, y);
						if (this.spotsTaken.contains(spot)) {
							return false;
						}
					} else {
						System.out.println("ERROR: Your meld cannot be touching other melds.");
						return false;
					}
				} else if (x == 12) {
					if (board.getSpot(x + meld.getMeldSize() - 1, y).isTaken = true) {
						Spot spot = board.getSpot(x + a, y);
						if (this.spotsTaken.contains(spot)) {
							return false;
						}
					} else {
						System.out.println("ERROR: Your meld cannot be touching other melds.");
						return false;
					}
				} else {
					if (board.getSpot(x - 1, y).isTaken != true
							&& board.getSpot(x + meld.getMeldSize(), y).isTaken != true) {
						Spot spot = board.getSpot(x + a, y);
						if (this.spotsTaken.contains(spot)) {
							return false;
						}
					} else {
						System.out.println("ERROR: Your meld cannot be touching other melds.");
						return false;
					}
				}
			} else {
				System.out.println("ERROR: Make sure your meld is placed within the board spots.");
				return false;
			}
		}
		return true;
	}

	@Override
	public void playMeld(Meld meld, Scanner aReader) {
		System.out.println("Enter an x value for the beginning of the Meld (Between 0-14): ");
		int x = aReader.nextInt();
		System.out.println("Enter an y value for the beginning of the Meld (Between 0-14): ");
		int y = aReader.nextInt();
		System.out.println(x);
		System.out.println(y);

		
		Spot beginningSpot = board.getSpot(x, y);
		if (beginningSpot != null) {
			if (canWePlaceMeld(meld, x, y) == true) {
				for (int i = 0; i < meld.getNumberOfTiles(); i++) {
					Tile tile = meld.getTileInMeld(i);
					Spot spot = board.getSpot(x + i, y);
					spot.playTile(tile);
					tile.setSpot(spot);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
					//h.removeTile(tile);
				}
				board.meldsOnBoard.add(meld);
				board.numberOfMelds++;
				turnMelds.add(meld);
				this.setTilesBeenPlayed(true);
			} else {
				System.out.println("Meld cannot be placed here. Please try a different Location. ");
				playMeld(meld, aReader);
			}
		}
	}
	
	@Override
	public boolean canWePlaceTile(Tile tile, int x, int y) {
		// Adding a tile between two melds
				//System.out.println("Adding between two melds");
				if (x > 0 && x < 14 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken
						&& board.getSpot(x, y).isTaken == false) {
					Spot prevSpot = board.getSpot(x - 1, y);
					Tile prevTile = prevSpot.getTile();
					Meld prevTileMeld = prevTile.getMemberOfMeld();
					//Spot beginningOfMeld = prevTileMeld.getTiles().get(0).getSpot();
					Spot nextSpot = board.getSpot(x + 1, y);
					//int newX = beginningOfMeld.getSpotX();
					Tile nextTile = nextSpot.getTile();
					Meld nextTileMeld = nextTile.getMemberOfMeld();
					Meld newMeld = combineMelds(prevTileMeld, nextTileMeld, tile);
					if (newMeld.checkIfValidMeld() != true) {
						return false;
					}
						
					}
				// Adding a tile at the end of an existing meld
				else if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken == false) {
					//System.out.println("Adding after a meld");
					Spot prevSpot = board.getSpot(x - 1, y);
					Tile prevTile = prevSpot.getTile();
					Meld prevTileMeld = prevTile.getMemberOfMeld();
					prevTileMeld.addTile(tile);
					if (prevTileMeld.checkIfValidMeld() != true) {
						return false;
					}
				}
				
				// Adding a tile at the beginning of an existing meld
				else if (x >= 0 && board.getSpot(x + 1, y).isTaken) {
					//System.out.println("Adding before a meld");
					Spot nextSpot = board.getSpot(x + 1, y);
					Tile nextTile = nextSpot.getTile();
					Meld nextTileMeld = nextTile.getMemberOfMeld();
					nextTileMeld.addTile(tile);
					if (nextTileMeld.checkIfValidMeld() != true) {
						return false;
					}
				}
				return true;	
	}
	
	public void addTile(Tile tile, int x, int y) {
		if(x>=0 && x<=14) {
			//Adding between Melds
			if (x > 0 && x < 14 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken && board.getSpot(x, y).isTaken == false) {
				Spot prevSpot = board.getSpot(x - 1, y);
				Tile prevTile = prevSpot.getTile();
				Meld prevTileMeld = prevTile.getMemberOfMeld();
				Spot beginningOfMeld = prevTileMeld.getTiles().get(0).getSpot();
				Spot nextSpot = board.getSpot(x + 1, y);
				int newX = beginningOfMeld.getSpotX();
				Tile nextTile = nextSpot.getTile();
				Meld nextTileMeld = nextTile.getMemberOfMeld();
				Meld newMeld = combineMelds(prevTileMeld, nextTileMeld, tile);
				if (newMeld.checkIfValidMeld() == true) {
					board.deleteMeld(prevTileMeld);
					board.deleteMeld(nextTileMeld);
					for (int i = 0; i < newMeld.getNumberOfTiles(); i++) {
						Tile newTile = newMeld.getTileInMeld(i);
						Spot spot = board.getSpot(newX + i, y);
						tile.setOldSpot(spot);
						spot.playTile(newTile);
						newTile.setSpot(spot);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
						
					}
					board.meldsOnBoard.add(newMeld);
					board.numberOfMelds++;
					turnTiles.add(tile);
					h.removeTile(tile);
					
					//board.deleteMeld(prevTileMeld);
					//board.deleteMeld(nextTileMeld);
					this.setTilesBeenPlayed(true);
				}
			}
			//ADDING TILE AFTER A MELD
			else if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken == false) {
				System.out.println("Adding after a meld");
				Spot prevSpot = board.getSpot(x - 1, y);
				Tile prevTile = prevSpot.getTile();
				Meld prevTileMeld = prevTile.getMemberOfMeld();
				//prevTileMeld.addTile(tile);
				//if (prevTileMeld.checkIfValidMeld() == true) {
					Spot spot = board.getSpot(x, y);
					spot.playTile(tile);
					tile.setSpot(spot);
					tile.setOldSpot(spot);
					prevTileMeld.addTile(tile);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
					turnTiles.add(tile);
					h.removeTile(tile);
					tile.setOldSpot(spot);
					this.setTilesBeenPlayed(true);
			}
			//ADDING TILE BEFORE A MELD
			else if (x >= 0 && board.getSpot(x + 1, y).isTaken) {
				System.out.println("Adding before an existing meld");
				Spot nextSpot = board.getSpot(x + 1, y);
				Tile nextTile = nextSpot.getTile();
				Meld nextTileMeld = nextTile.getMemberOfMeld();
				Spot spot = board.getSpot(x, y);
				tile.setOldSpot(spot);
				spot.playTile(tile);
				tile.setSpot(spot);
				nextTileMeld.addTileFront(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnTiles.add(tile);
				h.removeTile(tile);
				
				this.setTilesBeenPlayed(true);
			}
			//Creating a new meld
			else {
				Spot spot = board.getSpot(x, y);
				tile.setOldSpot(spot);
				spot.playTile(tile);
				tile.setSpot(spot);
				System.out.println("Creating a New meld");
				Meld meld = new Meld();
				meld.addTile(tile);
				board.meldsOnBoard.add(meld);
				board.numberOfMelds++;
				turnMelds.add(meld);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnTiles.add(tile);
				h.removeTile(tile);
				
				this.setTilesBeenPlayed(true);
			}
		}
		else {
			System.out.println("Try playing somewhere else");
		}
		
	}
	
	
	@Override
	public void moveTile(Tile tile, Spot newSpot) {
		
		Spot oldSpot = tile.getSpot();
		tile.setOldSpot(oldSpot);
		int oldX = oldSpot.getSpotX();
		System.out.println("Old X: " +oldX);
		int oldY = oldSpot.getSpotY();
		int x = newSpot.getSpotX();
		System.out.println("new X: " +x);
		int y = newSpot.getSpotY();
		if(x>=0 && x<=14 ) {
			// Moving a tile between two melds
			//System.out.println("Moving between two melds");
			if (x > 0 && x < 14 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken && board.getSpot(x, y).isTaken == false) {
					oldSpot.removeTile();
					tile.removeSpot(oldSpot);
					board.filledSpots.remove(oldSpot);
					Spot prevSpot = board.getSpot(x - 1, y);
					Tile prevTile = prevSpot.getTile();
					Meld prevTileMeld = prevTile.getMemberOfMeld();
					Spot beginningOfMeld = prevTileMeld.getTiles().get(0).getSpot();
					Spot nextSpot = board.getSpot(x + 1, y);
					int newX = beginningOfMeld.getSpotX();
					Tile nextTile = nextSpot.getTile();
					Meld nextTileMeld = nextTile.getMemberOfMeld();
					Meld newMeld = combineMelds(prevTileMeld, nextTileMeld, tile);
					if (newMeld.checkIfValidMeld() == true) {
						board.deleteMeld(prevTileMeld);
						board.deleteMeld(nextTileMeld);
						// board.deleteMeld(nextTileMeld);
						for (int i = 0; i < newMeld.getNumberOfTiles(); i++) {
							Tile newTile = newMeld.getTileInMeld(i);
							Spot spot = board.getSpot(newX + i, y);
							spot.playTile(newTile);
							newTile.setSpot(spot);
							board.numberOfTilesOnBoard++;
							board.filledSpots.add(spot);
						}
						board.meldsOnBoard.add(newMeld);
						board.numberOfMelds++;
						turnMoves.add(tile);
						System.out.println("Old X: " +oldX);
					} else {
						System.out.println("ERROR: You can't play this here. ");
						// undoMoveTile();
					}
				}
				
				//Moving TILE AFTER A MELD
				//else if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken == false) {
				else if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken == false) {
					oldSpot.removeTile();
					tile.removeSpot(oldSpot);
					board.filledSpots.remove(oldSpot);
					System.out.println("Moving to after a meld");
					Spot prevSpot = board.getSpot(x - 1, y);
					Tile prevTile = prevSpot.getTile();
					Meld prevTileMeld = prevTile.getMemberOfMeld();
		
					Spot spot = board.getSpot(x, y);
					spot.playTile(tile);
					tile.setSpot(spot);
					prevTileMeld.addTile(tile);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
					turnMoves.add(tile);
					// h.removeTile(tile);
					System.out.println("Old X: " +oldX);
					// this.setTilesBeenPlayed(true);
					
		
				}
				//Moving before an existing Meld
					else if (x >= 0 && board.getSpot(x + 1, y).isTaken) {
							oldSpot.removeTile();
							tile.removeSpot(oldSpot);
							board.filledSpots.remove(oldSpot);
							System.out.println("Moving before an existing meld");
							Spot nextSpot = board.getSpot(x + 1, y);
							Tile nextTile = nextSpot.getTile();
							Meld nextTileMeld = nextTile.getMemberOfMeld();
							Spot spot = board.getSpot(x, y);
							spot.playTile(tile);
							tile.setSpot(spot);
							nextTileMeld.addTileFront(tile);
							board.numberOfTilesOnBoard++;
							board.filledSpots.add(spot);
							turnMoves.add(tile);
							System.out.println("Old X: " +oldX);
				}
							
						//Creating a new meld
						else {
							oldSpot.removeTile();
							tile.removeSpot(oldSpot);
							board.filledSpots.remove(oldSpot);
							newSpot.playTile(tile);
							tile.setSpot(newSpot);
							System.out.println("Creating a New meld");
							Meld meld = new Meld();
							meld.addTile(tile);
							board.meldsOnBoard.add(meld);
							board.numberOfMelds++;
							turnMelds.add(meld);
							board.numberOfTilesOnBoard++;
							board.filledSpots.add(newSpot);
							System.out.println("Old X: " +oldX);
							//turnMoves.add(tile);
						//	h.removeTile(tile);
							//this.setTilesBeenPlayed(true);
						}
			}
		else {
			System.out.println("You can not move a tile by the middle of the meld.");
			undoMove(tile);
			GridPane gp = (GridPane) tile.getParent();
			gp.setConstraints(tile, tile.getOldSpot().getSpotX(),tile.getOldSpot().getSpotY());
		}
	}
	
	@Override
	public void undoPlayMeld(Meld meld) {
		for (int i = 0; i < meld.getMeldSize(); i++) {
			Tile tile = meld.getTileInMeld(i);
			Spot spot = tile.getSpot();
			if(tile.getOldSpot() != null) {
				undoMove(tile);
			}
			else {
				spot.removeTile();
				tile.removeSpot(spot);
				board.numberOfTilesOnBoard--;
				board.filledSpots.remove(spot);
				h.addTile(tile);	
			}
		}
		board.meldsOnBoard.remove(meld);
		board.numberOfMelds--;
		meld = null;
	}
	
	@Override
	public void undoAddTile(Tile tile) {
		Spot spot = tile.getSpot();
		spot.removeTile();
		tile.removeSpot(spot);
		board.numberOfTilesOnBoard--;
		if (tile.getMemberOfMeld() != null) {
			Meld meld = tile.getMemberOfMeld();
			meld.removeTile(tile);
		}
		board.filledSpots.remove(spot);
		h.addTile(tile);
	}
	@Override
	public void undoMove(Tile tile) {
		if(tile.justSwitched == false) {
			Spot currentSpot = tile.getOldSpot();
			Spot oldSpot = tile.getSpot();
			oldSpot.removeTile();
			board.filledSpots.remove(oldSpot);
			tile.removeSpot(oldSpot);
			oldSpot = null;
			currentSpot.playTile(tile);
			tile.setSpot(currentSpot);
			board.filledSpots.add(currentSpot);
		}
	}
	
	@Override
	public void undoTurn() {
		if (turnTiles.size() > 0) {
			for (int i = 0; i < turnTiles.size(); i++) {
				undoAddTile(turnTiles.get(i));
			}
		}
		if (turnMelds.size() > 0) {
			for (int x = 0; x < turnMelds.size(); x++) {
				Meld meld = turnMelds.get(x);
				undoPlayMeld(meld);
			}
		}
		if (turnMoves.size() > 0) {
			for (int move = 0; move < turnMoves.size(); move++) {
				undoMove(turnMoves.get(move));
			}
		}

		h.sortHand();
		turnTiles.clear();
		turnMelds.clear();
		turnMoves.clear();
	}
	@Override
	public Meld combineMelds(Meld meld1, Meld meld2, Tile tile) {
		Meld newMeld = new Meld();
		newMeld.getTiles().addAll(meld1.getTiles());
		newMeld.addTile(tile);
		newMeld.getTiles().addAll(meld2.getTiles());
		board.deleteMeld(meld1);
		board.deleteMeld(meld2);
		return newMeld;
	}
	
	public boolean makeAPlay(Scanner reader) {
		returned = false;
		while(true) {
		System.out.println("Choose one of the following commands:");
		System.out.println(" - 'M' to play a meld.");
		System.out.println(" - 'T' to play an individual tile.");
		System.out.println(" - 'B' to move an existing tile on the board.");
		System.out.println(" - 'L' to exit this sequence.");
		
		decision2 = reader.next().toUpperCase().charAt(0);
		
		/*TimerTask timerTask = new TimerTask() {
			public void run() {
				if(decision2 == 'K') {
					decision2 = 'L';
					return;
				}
			}
		};
		
		try {
			Timer timer = new Timer();
			timer.schedule(timerTask, 10000 - getTimer().getSeconds()*1000);
			decision2 = (decision2 == 'L')? 'L': reader.next().toUpperCase().charAt(0);
			timer.cancel();
		}
		catch(Exception e) {
			
		}*/
		
		if (decision2 == 'M') {
			String tileChoice = "";
			Meld meld = new Meld();
			this.getHand().createMeld();
			
			while (!tileChoice.equals("D")) {
				if (this.getHand().size == 0) { break; }
				System.out.println("Current Meld: " + meld.meldToString());
				System.out.println("Hand: " + this.getHand().handToString());
				System.out.println("Select a tile you'd like to add to your meld, type 'D' to exit.");
				tileChoice = reader.next().toUpperCase();
				if (tileChoice.equals("D")) { break; }
				for (int i = 0; i < this.getHand().size; i++) {
					if (this.getHand().getTile(i).tileToString().equals(tileChoice)) {
						meld.addTile(this.getHand().getTile(i));
						this.getHand().removeFromHand(i);
						break;
					} else if (i == (this.getHand().size - 1) && !(tileChoice.equals("D"))) {
						System.out.println("It seems that the tile " + tileChoice + " isn't in your posession. Please try again.");
					}
				}
			}
			if (tileChoice.equals("D")) {
				if (meld.getMeldSize() >= 3 && meld.checkIfValidMeld() == true) {
					
					for (int i = 0; i < meld.getMeldSize(); i++) {
						System.out.println(meld.getTileInMeld(i).tileToString());
					}
					
					this.playMeld(meld, reader);
					turnValue = turnValue + meld.getMeldValue();
				} else {
					System.out.println("Invalid meld. Please try again.");
					for (int i = 0; i < meld.getMeldSize(); i++) {
						this.getHand().addTile(meld.getTileInMeld(i));
					}
					this.getHand().sortHand();
					tileChoice = "";
				}
			}
		}
		
		if (decision == 'T') {
			if (initialMeldPlayed == true) {
				String tileChoice = "";
				System.out.println("Hand: " + this.getHand().handToString());
				System.out.println("Which tile would you like to add to the board?");
				tileChoice = reader.next().toUpperCase();
				Tile tempTile = this.getHand().getTile(tileChoice);
				
				if (this.getHand().getPlayerHand().contains(tempTile)) {
					System.out.println("Where would you like to put " + tempTile.tileToString() + " ?");
					System.out.println("X-Coordinate: ");
					int xTile = reader.nextInt();
					System.out.println("Y-Coordinate: ");
					int yTile = reader.nextInt();
					
					for (int i = 0; i < this.getHand().size; i++) {
						if (this.getHand().getTile(i).tileToString().equals(tileChoice)) {
							this.addTile(this.getHand().getTile(i), xTile, yTile);
						}
					}
					
					System.out.println("Board:");
					board.boardToString();
				} else {
					System.out.println("It seems that you don't have " + tileChoice + " isn't in your hand. Please try again.");
				}
			} else {
				System.out.println("You cannot play individual tiles on the board during your initial meld.");
			}
		}
		
		if (decision == 'B') {
			if (initialMeldPlayed == true) {
				while(true) {
					board.boardToString();
					System.out.println("Which tile would you like to move on the board?");
					System.out.println("Current X-Coordinate ('-1' to exit): ");
					int xTile = reader.nextInt(); if (xTile == -1) { break; }
					System.out.println("Current Y-Coordinate ('-1' to exit): ");
					int yTile = reader.nextInt(); if (yTile == -1) { break; }
					Spot oldSpot = board.getSpot(xTile, yTile);
					Tile tile = oldSpot.getTile();
					System.out.println("Where would you like to move tile " + tile.tileToString() + " to?");
					System.out.println("New X-Coordinate: ");
					int xTileNew = reader.nextInt();
					System.out.println("New Y-Coordinate: ");
					int yTileNew = reader.nextInt();
					Spot newSpot = board.getSpot(xTileNew, yTileNew);
					this.moveTile(tile, newSpot);
				}
			} else {
				System.out.println("You cannot manipulate the board during your initial meld.");
			}
		}
		
		if (decision == 'L') {
			if (board.checkIfValidMelds() == false) {
				System.out.println("That wasn't a valid move. Please try again.");
				this.setTilesBeenPlayed(false);
				//this.undoTurn();
				board = originator.getState();
				board.boardToString();
				return false;
			} else {
				originator.setBoardState(board);
				careTaker.addMomento(originator.saveStateToMomento());
				board = careTaker.getMomentos().get(careTaker.getMomentos().size() - 1).getBoardState();
				board.boardToString();
				return false;
			}
		}
		return returned;
		}
		//return false;
	}
	
	public GameTimer getTimer() { return timer; }

	public void play(Scanner reader, Deck deck) /*throws InterruptedException*/ {
		turnValue = 0;
		timer = new GameTimer();
		long startTime = System.currentTimeMillis();
		while( /*(System.currentTimeMillis()-startTime)<10000 ||*/ myTurn == true /*||timer.getSeconds()!=10*/) {
			//reader = new Scanner(System.in);
			System.out.println("Choose one of the following commands:");
			System.out.println(" - 'P' to play your turn.");
			System.out.println(" - 'S' to skip your turn & draw a tile.");
			System.out.println(" - 'E' to end your turn if you've already played atleast one tile.");
			
			
			/*if(timer.getSeconds() == 0) {
				timer.start();
			}*/
			
			//while(timer.getSeconds() != 10) {
				
			//System.out.print(timer.isStopped());

			/*else if(timer.getSeconds() == 10) {
				//this.setHasInitialMeldBeenPlayed(false);
				this.setTilesBeenPlayed(false);
				this.undoTurn();
				Tile t = this.getHand().dealTile(deck);
				System.out.println("Out of time");
				System.out.println("Turn ended: Player drew " + t.tileToString() + ".");
				System.out.println("----------------------------------------");
				this.setTurnStatus(false);
				timer.stop();
				
			}*/
			/*decision = 'N'; //'N' for no input
			
			TimerTask task = new TimerTask() {
				public void run() {
					decision = 'N';
					if(decision == 'N') {
						Tile t = getHand().dealTile(deck);
						System.out.println("Timeout");
						System.out.println("Turn ended: Player drew " + t.tileToString() + ".");
						System.out.println("----------------------------------------");
						setTilesBeenPlayed(false);
						undoTurn();
						setTurnStatus(false);
						//return;
					}
					if(timer.getSeconds() == 10) {
						
					}
				}
			};
			
			try {
			
				Timer timer = new Timer();
				timer.schedule(task, 10000);
				timer.cancel();
				while(!getTimer().isStopped())
				decision = (getTimer().getSeconds() == 10)? 'S':  reader.next().toUpperCase().charAt(0);
			}
			catch (Exception e) {
				
			}*/
			decision = reader.next().toUpperCase().charAt(0);
			
			if (decision == 'P') {
				makeAPlay(reader);
			} else if (decision == 'S') {
				if (hasTileBeenPlaced == false) {
					Tile t = this.getHand().dealTile(deck);
					System.out.println("Turn ended: Player drew " + t.tileToString() + ".");
					System.out.println("----------------------------------------");
					this.setTurnStatus(false);
					timer.stop();
				} else {
					System.out.println("You've already made a play. Try typing 'E' to end your turn.");
				}
			} else if (decision == 'E') {
				if (initialMeldPlayed == false) {

	
					if (turnValue >= 2) {

						System.out.println("Initial Meld Completed.");
						System.out.println("----------------------------------------");
						this.setHasInitialMeldBeenPlayed(true);
						this.setTilesBeenPlayed(true);
						this.setTurnStatus(false);
						timer.stop();
						originator.setBoardState(board);
						careTaker.addMomento(originator.saveStateToMomento());
						board = careTaker.getMomentos().get(careTaker.getMomentos().size() - 1).getBoardState();
						board.boardToString();
					} else {
						System.out.println("Your Initial Meld total must be greater than or equal to 30 points.");
						System.out.println("You played: " + turnValue + ". Please try again.");	
						this.setHasInitialMeldBeenPlayed(false);
						this.setTilesBeenPlayed(false);
						//this.undoTurn();
						board = originator.getState();
						turnValue = 0;
						board.boardToString();
					}
				} else if (initialMeldPlayed == true) {
					if (hasTileBeenPlaced == true) {
						this.setTurnStatus(false);
						timer.stop();
						originator.setBoardState(board);
						careTaker.addMomento(originator.saveStateToMomento());
						board = careTaker.getMomentos().get(careTaker.getMomentos().size() - 1).getBoardState();
						board.boardToString();
					} else {
						//this.undoTurn();
						board = originator.getState();
						board.boardToString();
						System.out.println("You must either play your turn or draw a tile.");
					}
				}
			}
			else {
				System.out.println("You may have entered the wrong character. Please try again.");
			}
			//}
		}
			
	}


	@Override
	public void setTurnPoints() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getTurnPoints() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void play(Scanner reader) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isAI() { return isAI; }


	@Override
	public boolean play(GridPane gridPane) {
		return false;
		// TODO Auto-generated method stub
		
	}

}
