package com.COMP3004.Rummikub;

import java.util.Scanner;

public class AI3 implements PlayerType {
	Hand h;
	private boolean myTurn = false;
	private boolean hasTileBeenPlaced = false;
	
	public AI3(Deck deck) {
		h = new Hand();
		h.createHand(deck);
		h.sortHand();
	}
	
	public Hand getHand() { return this.h; }
	
	public boolean myTurnStatus() { return this.myTurn; }
	
	public void setTurnStatus(boolean b) { this.myTurn = b; }
	
	public boolean hasTilesBeenPlayed() { return this.hasTileBeenPlaced; }
	
	public void setTilesBeenPlayed(boolean b) { this.hasTileBeenPlaced = b; }

	public boolean turnComplete(Hand h) {
		// Initial Meld: ASAP
		// Gameplay: If another player has 3 less tiles than me, use AI1 strategy. Otherwise, use AI2 strategy.
		return false;
	}

	@Override
	public void update(Board board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playMeld(Meld meld, Scanner reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTile(Tile tile, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canWePlaceMeld(Meld meld, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void undoTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undoAddTile(Tile tile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undoPlayMeld(Meld meld) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveTile(Tile tile, Spot newSpot) {
		// TODO Auto-generated method stub
		
	}

}
