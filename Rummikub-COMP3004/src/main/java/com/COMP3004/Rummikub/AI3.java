package com.COMP3004.Rummikub;

public class AI3 implements PlayerType {
	Hand h;
	private boolean myTurn = false;
	private boolean hasTileBeenPlaced = false;
	
	public AI3(Deck deck) {
		h = new Hand();
		h.createHand(deck);
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

}
