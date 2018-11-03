package com.COMP3004.Rummikub;

public class AI2 implements PlayerType {
	Hand h;
	private boolean myTurn = false;
	private boolean hasTileBeenPlaced = false;
	
	public AI2(Deck deck) {
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
		// Initial Meld: After another played has placed tiles
		// Gameplay: Only uses existing melds on the board. Keeps melds not do not require current tiles on board.
		return false;
	}

}
