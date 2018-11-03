package com.COMP3004.Rummikub;

import java.util.ArrayList;
import java.util.Scanner;

public class Human implements PlayerType {
	Hand h;
	private boolean myTurn = true;
	private boolean hasTileBeenPlaced = false;
	public Subject game;
	public ArrayList<Spot> spotsTaken;
	private Board board;
	
	
	public Human(Deck deck, Game game) {
		h = new Hand();
		h.createHand(deck);
		h.sortHand();
		game.registerObserver(this);
		ArrayList<Spot> spotsTaken = new ArrayList<Spot>();

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
		this.spotsTaken = board.filledSpots;
		this.board = board;
	}

	@Override
	public void playMeld(Meld meld) {
		Scanner reader = new Scanner(System.in); 
		System.out.println("Enter an x value for the beginning of the Meld (Between 0-14): ");
		int x = reader.nextInt(); 
		System.out.println("Enter an y value for the beginning of the Meld (Between 0-14): ");
		int y = reader.nextInt(); 
		reader.close();
		System.out.println(x);
		System.out.println(y);
		System.out.println(board.getSpot(x, y));
		Spot beginningSpot = board.getSpot(x, y);
		
		
		if(beginningSpot!=null) {
			if(canWePlaceMeld(meld,x,y)==true) {
				for(int i=0;i<meld.getNumberOfTiles();i++) {
					Tile tile = meld.getTileInMeld(i);
					addTile(tile, x+i, y);
					board.numberOfTilesOnBoard++;
				}		
			}
			else {
				System.out.println("Meld cannot be placed here. Please try a different Location. ");
			}
		}
		
		board.meldsOnBoard.add(meld);
		//numberOfTilesOnBoard = numberOfTilesOnBoard + meld.getMeldSize();
		board.numberOfMelds++;
		
	}

	@Override
	public void addTile(Tile tile, int x, int y) {
		Spot spot = board.getSpot(x,y);
		spot.playTile(tile);
		tile.setSpot(spot);
		board.numberOfTilesOnBoard++;
		board.filledSpots.add(spot);
		
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
	
	

}
