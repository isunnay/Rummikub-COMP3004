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
	private Board board;
	
	
	public Human(Deck deck, Game game) {
		h = new Hand();
		h.createHand(deck);
		h.sortHand();
		game.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();

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
		
		
	}
	@Override
	public void addTile(Tile tile, int x, int y) {
		Spot spot = board.getSpot(x,y);
		spot.playTile(tile);
		tile.setSpot(spot);
		board.numberOfTilesOnBoard++;
		board.filledSpots.add(spot);
		turnTiles.add(tile);
		h.removeTile(tile);
	}
	
	public void undoAddTile(Tile tile) {
		Spot spot = tile.getSpot();
		spot.removeTile();
		tile.removeSpot(spot);
		board.numberOfTilesOnBoard--;
		board.filledSpots.remove(spot);
		h.addTile(tile);
	}
	


	@Override
	public boolean canWePlaceMeld(Meld meld, int x, int y) {
		for(int a=0;a<meld.getMeldSize();a++) {
			Spot spot = board.getSpot(x, y);
			if(this.spotsTaken.contains(spot)) {
				return false;
				}	
		}
		return true;	
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
		h.sortHand();
		turnTiles.clear();
		turnMelds.clear();
	}
	
	

}
