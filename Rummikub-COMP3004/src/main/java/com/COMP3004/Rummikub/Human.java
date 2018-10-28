package com.COMP3004.Rummikub;

public class Human implements PlayerType {
	Hand h;
	private boolean myTurn = true;
	private boolean hasTileBeenPlaced = false;
	
	public Human(Deck deck) {
		h = new Hand();
		h.createHand(deck);
	}
	
	public Hand getHand() { return this.h; }
	
	public boolean myTurnStatus() { return this.myTurn; }
	
	public boolean hasTilesBeenPlayed() { return this.hasTileBeenPlaced; }

	public boolean turnComplete(Hand h) {
		// Do Nothing...
		return false;
	}

}
