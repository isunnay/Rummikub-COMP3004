package com.COMP3004.Rummikub;

import java.util.ArrayList;
import java.util.Scanner;

public class Human implements PlayerType {
	Hand h;
	private boolean myTurn = true;
	private boolean hasTileBeenPlaced = false;
	public Subject game;
	public ArrayList<Spot> spotsTaken;
	public ArrayList<Tile> turnTiles;
	public ArrayList<Meld> turnMelds;
	public ArrayList<Tile> turnMoves;
	private Board board;
	
	
	public Human(Deck deck, Game game) {
		h = new Hand();
		h.createHand(deck);
		h.sortHand();
		game.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();
		turnMoves = new ArrayList<Tile>();
	}
	
	public Hand getHand() { return this.h; }
	
	public boolean myTurnStatus() { return this.myTurn; }
	
	public void setTurnStatus(boolean b) { this.myTurn = b; }
	
	public boolean hasTilesBeenPlayed() { return this.hasTileBeenPlaced; }
	
	public void setTilesBeenPlayed(boolean b) { this.hasTileBeenPlaced = b; }

	public boolean turnComplete(Hand h) {
		// Do Nothing...
		return false;
	}

	@Override
	public void update(Board board) {
	//	turnTiles.clear();
		this.spotsTaken = board.filledSpots;
		this.board = board;
	}
	
	// && x+a<board.getX() && x+meld.getMeldSize() <= board.getX()
	@Override
	public boolean canWePlaceMeld(Meld meld, int x, int y) {
		//Spot beginningSpot = board.getSpot(x, y);
		for(int a=0;a<meld.getMeldSize();a++) {
			if(x+a>=board.getSpot(0, 0).getSpotX() && x+meld.getMeldSize()<=board.getX()) {
				if(x==0) {
					if(board.getSpot(x+meld.getMeldSize(), y).isTaken = true) {
						Spot spot = board.getSpot(x+a, y);
						if(this.spotsTaken.contains(spot)) {
							return false;
							}
					}
					else {
						System.out.println("ERROR: Your meld cannot be touching other melds.");
						return false;
					}
				}
				else if(x==12) {
					if(board.getSpot(x+meld.getMeldSize()-1, y).isTaken = true) {
						Spot spot = board.getSpot(x+a, y);
						if(this.spotsTaken.contains(spot)) {
							return false;
							}
					}
					else {
						System.out.println("ERROR: Your meld cannot be touching other melds.");
						return false;
					}
					
				}
				
				else {
					if(board.getSpot(x-1, y).isTaken != true && board.getSpot(x+meld.getMeldSize(), y).isTaken != true) {
						Spot spot = board.getSpot(x+a, y);
						if(this.spotsTaken.contains(spot)) {
							return false;
							}
					}
					else {
						System.out.println("ERROR: Your meld cannot be touching other melds.");
						return false;
					}
			}
			}
			else {
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
		Spot beginningSpot = board.getSpot(x, y);
		
		
		if(beginningSpot!=null) {
			if(canWePlaceMeld(meld,x,y)==true) {
				for(int i=0;i<meld.getNumberOfTiles();i++) {
					Tile tile = meld.getTileInMeld(i);
					Spot spot = board.getSpot(x+i,y);
					spot.playTile(tile);
					tile.setSpot(spot);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
					h.removeTile(tile);
				}
				board.meldsOnBoard.add(meld);
				board.numberOfMelds++;	
				turnMelds.add(meld);
			}
			else {
				System.out.println("Meld cannot be placed here. Please try a different Location. ");
			}
		}
	}

	@Override
	public void addTile(Tile tile, int x, int y) {
		// Adding a tile between two melds
		if (x > 0 && x < 14 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken
				&& board.getSpot(x, y).isTaken == false) {
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
					spot.playTile(newTile);
					newTile.setSpot(spot);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
				}
				board.meldsOnBoard.add(newMeld);
				board.numberOfMelds++;
				turnTiles.add(tile);
				board.deleteMeld(prevTileMeld);
				board.deleteMeld(nextTileMeld);
			} else {
				System.out.println("ERROR: This is an invalid Meld.");
			}
		}
		// Adding a tile at the end of an existing meld
		else if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken == false) {
			Spot prevSpot = board.getSpot(x - 1, y);
			Tile prevTile = prevSpot.getTile();
			Meld prevTileMeld = prevTile.getMemberOfMeld();
			prevTileMeld.addTile(tile);
			if (prevTileMeld.checkIfValidMeld() == true) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnTiles.add(tile);
				h.removeTile(tile);
			} else {
				System.out.println("ERROR: This is an invalid meld.");
				prevTileMeld.removeTile(tile);
			}
		}

		// Adding a tile at the beginning of an existing meld
		else if (x >= 0 && board.getSpot(x + 1, y).isTaken) {
			Spot nextSpot = board.getSpot(x + 1, y);
			Tile nextTile = nextSpot.getTile();
			Meld nextTileMeld = nextTile.getMemberOfMeld();
			nextTileMeld.addTile(tile);
			if (nextTileMeld.checkIfValidMeld() == true) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnTiles.add(tile);
				h.removeTile(tile);
			} else {
				System.out.println("ERROR: This is an invalid meld.");
				nextTileMeld.removeTile(tile);
			}
		} 
		else {
			Spot spot = board.getSpot(x, y);
			spot.playTile(tile);
			tile.setSpot(spot);
			board.numberOfTilesOnBoard++;
			board.filledSpots.add(spot);
			turnTiles.add(tile);
			h.removeTile(tile);
		}
	}
	
	public void moveTile(Tile tile, Spot newSpot) {
		Spot oldSpot = tile.getSpot();
		oldSpot.removeTile();
		tile.removeSpot(oldSpot);
		board.filledSpots.remove(oldSpot);
		newSpot.playTile(tile);
		tile.setSpot(newSpot);
		board.filledSpots.add(newSpot);
		turnMoves.add(tile);
		//boardChanged();
	}
	
	public void undoPlayMeld(Meld meld) {
		for(int i=0; i<meld.getMeldSize();i++) {
			Tile tile = meld.getTileInMeld(i);
			Spot spot = tile.getSpot();
			spot.removeTile();
			tile.removeSpot(spot);
			board.numberOfTilesOnBoard--;
			board.filledSpots.remove(spot);
			h.addTile(tile);
		}
		board.meldsOnBoard.remove(meld);
		board.numberOfMelds--;	
		meld=null;
	}
	
	public void undoAddTile(Tile tile) {
		Spot spot = tile.getSpot();
		spot.removeTile();
		tile.removeSpot(spot);
		board.numberOfTilesOnBoard--;
		if(tile.getMemberOfMeld() != null) {
			Meld meld = tile.getMemberOfMeld();
			meld.removeTile(tile);
		}
		board.filledSpots.remove(spot);
		h.addTile(tile);
	}
	
	public void undoMove(Tile tile) {
		Spot oldSpot = tile.getOldSpot();
		Spot currentSpot = tile.getSpot();
		currentSpot.removeTile();
		board.filledSpots.remove(currentSpot);
		oldSpot.playTile(tile);
		tile.setSpot(oldSpot);
		board.filledSpots.add(oldSpot);		
	}
	
	public void undoTurn() {	
		if(turnTiles.size()>0) {	
			for(int i=0; i<turnTiles.size();i++) {
				undoAddTile(turnTiles.get(i));
			}
		}
		if(turnMelds.size()>0) {	
			for(int x=0; x<turnMelds.size();x++) {
				Meld meld = turnMelds.get(x);
				undoPlayMeld(meld);
			}
		}
		if(turnMoves.size()>0) {
			for(int move=0;move<turnMoves.size();move++) {
				undoMove(turnMoves.get(move));	
			}
		}
		
		h.sortHand();
		turnTiles.clear();
		turnMelds.clear();
		turnMoves.clear();
	}
	
	public Meld combineMelds(Meld meld1, Meld meld2, Tile tile) {
		Meld newMeld = new Meld();
		newMeld.getTiles().addAll(meld1.getTiles());
		newMeld.addTile(tile);
		newMeld.getTiles().addAll(meld2.getTiles());
		return newMeld;
	}
	
	

}
