package com.COMP3004.Rummikub.models;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.COMP3004.Rummikub.controller.RummikubController;

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

	public Human(Deck deck, Game game) {
		h = new Hand();
		h.createHand(deck);
		//h.sortHand();
		game.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();
		turnMoves = new ArrayList<Tile>();
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

	@Override
	public void addTile(Tile tile, int x, int y) {
		if (x == 0) {
			//adding before a meld
			//new meld
			//finishing meld
		}
		
		if (x == 1) {
			//adding before a meld
			//new meld
			//finishing meld
		}
		
		if (x >= 2 && x <= 12) {
			//adding before a meld
			//adding after a meld
			//new meld
			//finishing meld
			//adding between melds
		}
		
		if (x == 13) {
			//adding after a meld
			//finishing meld
		}
		
		if (x == 14) {
			//adding after a meld
			//finishing meld
		}
		
		// Adding a tile between two melds
				//System.out.println("Adding between two melds");
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
						//board.deleteMeld(nextTileMeld);
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
						turnTiles.add(tile);
						h.removeTile(tile);
						//board.deleteMeld(prevTileMeld);
						//board.deleteMeld(nextTileMeld);
						this.setTilesBeenPlayed(true);
					} else {
						System.out.println("ERROR: You can't play this here. ");
						//undoAddTile(tile);
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
					
					//Checking if possible run
					if (tile.getValue() - prevTile.getValue() == 1) {
						Spot spot = board.getSpot(x, y);
						spot.playTile(tile);
						tile.setSpot(spot);
						prevTileMeld.addTile(tile);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
						turnTiles.add(tile);
						h.removeTile(tile);
						this.setTilesBeenPlayed(true);
					}
					//checking if possible set
					else if(prevTile.getValue() == tile.getValue() && tile.getColour()!= prevTile.getColour()) {
						Spot spot = board.getSpot(x, y);
						spot.playTile(tile);
						tile.setSpot(spot);
						prevTileMeld.addTile(tile);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
						turnTiles.add(tile);
						h.removeTile(tile);
						this.setTilesBeenPlayed(true);
					}
					else {
						System.out.println("ERROR: You cannot play this here.");
					}
				}	

				//ADDING TILE BEFORE A MELD
				else if (x >= 0 && board.getSpot(x + 1, y).isTaken) {
					System.out.println("Adding before an existing meld");
					Spot nextSpot = board.getSpot(x + 1, y);
					Tile nextTile = nextSpot.getTile();
					Meld nextTileMeld = nextTile.getMemberOfMeld();
					//Checking if possible run
					if (nextTile.getValue() - tile.getValue() == 1) {
						Spot spot = board.getSpot(x, y);
						spot.playTile(tile);
						tile.setSpot(spot);
						nextTileMeld.addTileFront(tile);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
						turnTiles.add(tile);
						h.removeTile(tile);
						this.setTilesBeenPlayed(true);
					} 
					//checking if possible set
					else if(nextTile.getValue() == tile.getValue() && tile.getColour()!= nextTile.getColour()) {
						Spot spot = board.getSpot(x, y);
						spot.playTile(tile);
						tile.setSpot(spot);
						nextTileMeld.addTileFront(tile);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
						turnTiles.add(tile);
						h.removeTile(tile);
						this.setTilesBeenPlayed(true);	
					}
					else {
						System.out.println("ERROR: You cannot play this here.");
						//nextTileMeld.removeTile(tile);
					}	
				}
				//Creating a new meld
				else {
					Spot spot = board.getSpot(x, y);
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
					//turnTiles.add(tile);
					h.removeTile(tile);
					this.setTilesBeenPlayed(true);
				}
		
		/*
		// Adding a tile between two melds
		if (x > 2 && x < 12 && board.getSpot(x - 1, y).isTaken && board.getSpot(x - 2, y).isTaken && board.getSpot(x - 3, y).isTaken && board.getSpot(x + 1, y).isTaken && board.getSpot(x + 2, y).isTaken && board.getSpot(x + 3, y).isTaken && !(board.getSpot(x, y).isTaken)) {
			System.out.println("Adding between two melds");
			
			// Left Meld
			Spot prevSpot = board.getSpot(x - 1, y);
			Tile prevTile = prevSpot.getTile();
			Meld prevTileMeld = prevTile.getMemberOfMeld();

			// Right Meld
			Spot nextSpot = board.getSpot(x + 1, y);
			Tile nextTile = nextSpot.getTile();
			Meld nextTileMeld = nextTile.getMemberOfMeld();
			
			// Temp Meld to test validity
			Meld tempMeld = new Meld();
			
			// Location to place meld
			Spot beginningOfMeld = prevTileMeld.getTiles().get(0).getSpot();
			int newX = beginningOfMeld.getSpotX();
			
			// Add left meld to temp meld 
			for (int i = 0; i < prevTileMeld.getMeldSize(); i++) {
				tempMeld.addTile(prevTileMeld.getTileInMeld(i));
			}
			
			// Add new tile to temp meld
			tempMeld.addTile(tile);
			
			// Add right meld to temp meld
			for (int i = 0; i < nextTileMeld.getMeldSize(); i++) {
				tempMeld.addTile(nextTileMeld.getTileInMeld(i));
			}
			
			if (tempMeld.getMeldSize() > 3) {
				if (tempMeld.isValidRun() || tempMeld.isValidSet()) {
					board.deleteMeld(prevTileMeld);
					board.deleteMeld(nextTileMeld);
					for (int i = 0; i < tempMeld.getNumberOfTiles(); i++) {
						Tile newTile = tempMeld.getTileInMeld(i);
						Spot spot = board.getSpot(newX + i, y);
						spot.playTile(newTile);
						newTile.setSpot(spot);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
					}
					board.meldsOnBoard.add(tempMeld);
					board.numberOfMelds++;
					turnTiles.add(tile);
					h.removeTile(tile);
					this.setTilesBeenPlayed(true);
				} else {
					System.out.println("ERROR: You can't play this here. ");
				}
			}
		}
		//}
		//ADDING TILE AFTER A MELD
		//else if(x>0 &&x<14&& board.getSpot(x-1, y).isTaken && !(board.getSpot(x+1, y).isTaken)) {
		//if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x - 2, y).isTaken && board.getSpot(x - 3, y).isTaken) {
		else if ((x >= 2 && board.getSpot(x - 1, y).isTaken && board.getSpot(x - 2, y).isTaken && board.getSpot(x - 3, y).isTaken) ||
				 (x <= 14 && board.getSpot(x - 1, y).isTaken && board.getSpot(x - 2, y).isTaken && board.getSpot(x - 3, y).isTaken)) {
			System.out.println("Adding after a meld");
			
			Spot prevSpot = board.getSpot(x - 1, y);
			Tile prevTile = prevSpot.getTile();
			Meld prevTileMeld = prevTile.getMemberOfMeld();
			
			Meld tempMeld = new Meld();
			
			for (int i = 0; i < prevTileMeld.getMeldSize(); i++) {
				tempMeld.addTile(prevTileMeld.getTileInMeld(i));
			}
			
			tempMeld.addTile(tile);
			
			if (tempMeld.getMeldSize() > 3) {
				if (tempMeld.isValidRun() || tempMeld.isValidSet()) {
					Spot spot = board.getSpot(x, y);
					spot.playTile(tile);
					tile.setSpot(spot);
					prevTileMeld.addTile(tile);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
					turnTiles.add(tile);
					h.removeTile(tile);
					this.setTilesBeenPlayed(true);
				} else {
					System.out.println("ERROR: You cannot play this here.");
				}
			}
		}

		//ADDING TILE BEFORE A MELD
		//else if (x >= 0 && board.getSpot(x + 1, y).isTaken && board.getSpot(x + 2, y).isTaken && board.getSpot(x + 3, y).isTaken) {
		else if ((x >= 0 && board.getSpot(x + 1, y).isTaken && board.getSpot(x + 2, y).isTaken && board.getSpot(x + 3, y).isTaken) ||
				 (x <= 11 && board.getSpot(x + 1, y).isTaken && board.getSpot(x + 2, y).isTaken && board.getSpot(x + 3, y).isTaken)) {
			
			System.out.println("Adding before an existing meld");
			
			Spot nextSpot = board.getSpot(x + 1, y);
			Tile nextTile = nextSpot.getTile();
			Meld nextTileMeld = nextTile.getMemberOfMeld();
			
			Meld tempMeld = new Meld();
			
			tempMeld.addTile(tile);
			
			for (int i = 0; i < nextTileMeld.getMeldSize(); i++) {
				tempMeld.addTile(nextTileMeld.getTileInMeld(i));
			}
			
			if (tempMeld.getMeldSize() > 3) {			
				if (tempMeld.isValidRun() || tempMeld.isValidSet()) {
					Spot spot = board.getSpot(x, y);
					spot.playTile(tile);
					tile.setSpot(spot);
					nextTileMeld.addTileFront(tile);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
					turnTiles.add(tile);
					h.removeTile(tile);		
					this.setTilesBeenPlayed(true);
				} else {
					System.out.println("ERROR: You cannot play this here.");
				}
			}

		}
		
		//Creating a new meld
		//else if (x >= 1 &&  !(board.getSpot(x + 1, y).isTaken) && !(board.getSpot(x - 1, y).isTaken)) {
		
		else if ((x >= 0 && x <= 12 && !(board.getSpot(x + 1, y).isTaken) && !(board.getSpot(x + 2, y).isTaken)) ||
				 (x >= 1 && x <= 12 && !(board.getSpot(x + 1, y).isTaken) && !(board.getSpot(x - 1, y).isTaken) ||
				 (x <= 12 && !(board.getSpot(x + 1, y).isTaken)) && !(board.getSpot(x + 2, y).isTaken))) {
			Spot spot = board.getSpot(x, y);
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
			//turnTiles.add(tile);
			h.removeTile(tile);
			this.setTilesBeenPlayed(true);
		} else {
			// finished a meld
			System.out.println("Trying to finish a meld");
			Spot spot = board.getSpot(x, y);
			spot.playTile(tile);
			tile.setSpot(spot);

			if (board.getSpot(x - 1, y).isTaken) {
				// get left meld
				
				Spot prevSpot = board.getSpot(x - 1, y);
				Tile prevTile = prevSpot.getTile();
				Meld prevTileMeld = prevTile.getMemberOfMeld();
				
				prevTileMeld.addTile(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				h.removeTile(tile);
			}
			
			if (board.getSpot(x + 1, y).isTaken) {
				// get right meld
				
				Spot nextSpot = board.getSpot(x + 1, y);
				Tile nextTile = nextSpot.getTile();
				Meld nextTileMeld = nextTile.getMemberOfMeld();
				
				nextTileMeld.addTile(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				h.removeTile(tile);
			}
		}
		*/
	}
	
	@Override
	public void moveTile(Tile tile, Spot newSpot) {
		// Moving a tile between two melds
				//System.out.println("Moving between two melds");
		Spot oldSpot = tile.getSpot();
		int x = newSpot.getSpotX();
		int y = newSpot.getSpotY();
		if (x > 0 && x < 14 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken
				&& board.getSpot(x, y).isTaken == false) {
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
				// board.deleteMeld(prevTileMeld);
				// board.deleteMeld(nextTileMeld);
				// this.setTilesBeenPlayed(true);
			} else {
				System.out.println("ERROR: You can't play this here. ");
				// undoMoveTile();
			}
		}
		
		//Moving TILE AFTER A MELD
		else if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken == false) {
			oldSpot.removeTile();
			tile.removeSpot(oldSpot);
			board.filledSpots.remove(oldSpot);
			System.out.println("Moving to after a meld");
			Spot prevSpot = board.getSpot(x - 1, y);
			Tile prevTile = prevSpot.getTile();
			Meld prevTileMeld = prevTile.getMemberOfMeld();
			//prevTileMeld.addTile(tile);
			//if (prevTileMeld.checkIfValidMeld() == true) {
			
			//Checking if possible run
			if (tile.getValue() - prevTile.getValue() == 1) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				prevTileMeld.addTile(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnMoves.add(tile);
				//h.removeTile(tile);
				//this.setTilesBeenPlayed(true);
			}
			//checking if possible set
			else if(prevTile.getValue() == tile.getValue() && tile.getColour()!= prevTile.getColour()) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				prevTileMeld.addTile(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnMoves.add(tile);
				//h.removeTile(tile);
				//this.setTilesBeenPlayed(true);
			}
			else {
				System.out.println("ERROR: You cannot play this here.");
				//undoMove(tile);
			}
		}	
		
		//Moving TILE BEFORE A MELD
				else if (x >= 0 && board.getSpot(x + 1, y).isTaken) {
					oldSpot.removeTile();
					tile.removeSpot(oldSpot);
					board.filledSpots.remove(oldSpot);
					System.out.println("Moving before an existing meld");
					Spot nextSpot = board.getSpot(x + 1, y);
					Tile nextTile = nextSpot.getTile();
					Meld nextTileMeld = nextTile.getMemberOfMeld();
					//Checking if possible run
					if (nextTile.getValue() - tile.getValue() == 1) {
						Spot spot = board.getSpot(x, y);
						spot.playTile(tile);
						tile.setSpot(spot);
						nextTileMeld.addTileFront(tile);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
						turnMoves.add(tile);
						//h.removeTile(tile);
						//this.setTilesBeenPlayed(true);
					} 
					//checking if possible set
					else if(nextTile.getValue() == tile.getValue() && tile.getColour()!= nextTile.getColour()) {
						Spot spot = board.getSpot(x, y);
						spot.playTile(tile);
						tile.setSpot(spot);
						nextTileMeld.addTileFront(tile);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
						turnMoves.add(tile);
						//h.removeTile(tile);
						//this.setTilesBeenPlayed(true);	
					}
					else {
						System.out.println("ERROR: You cannot play this here.");
						//nextTileMeld.removeTile(tile);
					}
					
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
					//turnMoves.add(tile);
				//	h.removeTile(tile);
					//this.setTilesBeenPlayed(true);
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
		while(true) {
		System.out.println("Choose one of the following commands:");
		System.out.println(" - 'M' to play a meld.");
		System.out.println(" - 'T' to play an individual tile.");
		System.out.println(" - 'B' to move an existing tile on the board.");
		System.out.println(" - 'L' to exit this sequence.");
		char decision = reader.next().toUpperCase().charAt(0);
		
		
		if (decision == 'M') {
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
				this.undoTurn();
				return false;
			} else {
				return false;
			}
		}
		
		}
		//return false;
	}

	public void play(Scanner reader, Deck deck) throws InterruptedException {
		turnValue = 0;
		while(myTurn == true) {
			//reader = new Scanner(System.in);
			System.out.println("Choose one of the following commands:");
			System.out.println(" - 'P' to play your turn.");
			System.out.println(" - 'S' to skip your turn & draw a tile.");
			System.out.println(" - 'E' to end your turn if you've already played atleast one tile.");
			char decision = reader.next().toUpperCase().charAt(0);		
			
			if (decision == 'P') {
				makeAPlay(reader);
			} else if (decision == 'S') {
				if (hasTileBeenPlaced == false) {
					Tile t = this.getHand().dealTile(deck);
					System.out.println("Turn ended: Player drew " + t.tileToString() + ".");
					System.out.println("----------------------------------------");
					this.setTurnStatus(false);
				} else {
					System.out.println("You've already made a play. Try typing 'E' to end your turn.");
				}
			} else if (decision == 'E') {
				if (initialMeldPlayed == false) {
					if (turnValue >= 1) {
						System.out.println("Initial Meld Completed.");
						System.out.println("----------------------------------------");
						this.setHasInitialMeldBeenPlayed(true);
						this.setTilesBeenPlayed(true);
						this.setTurnStatus(false);
					} else {
						System.out.println("Your Initial Meld total must be greater than or equal to 30 points.");
						System.out.println("You played: " + turnValue + ". Please try again.");	
						this.setHasInitialMeldBeenPlayed(false);
						this.setTilesBeenPlayed(false);
						this.undoTurn();
						turnValue = 0;
					}
				} else if (initialMeldPlayed == true) {
					if (hasTileBeenPlaced == true) {
						this.setTurnStatus(false);
					} else {
						this.undoTurn();
						System.out.println("You must either play your turn or draw a tile.");
					}
				}
			} else {
				System.out.println("You may have entered the wrong character. Please try again.");
			}
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

}
