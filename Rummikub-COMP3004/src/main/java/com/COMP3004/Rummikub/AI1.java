package com.COMP3004.Rummikub;

public class AI1 implements PlayerType {
	Hand h;
	private boolean myTurn = false;
	private boolean isTurn = false;
	private boolean hasTileBeenPlaced = false;

	
	public AI1(Deck deck) {
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
		// Gameplay: Plays all tiles when possible
		//h.meldExists();
		return false;
	}
	
	public boolean oneMeldFirstTurn() {
		if (h.numberOfMelds()==1)
			return true;
		else
			return false;
	}

	public boolean severalMeldsFirstTurn() {
		if (h.numberOfMelds()>1)
			return true;
		else
			return false;
	}
	
	public Hand drawsOnFirstTurn() {
		//System.out.println("first in draw " + h.handToString());
		if (! (h.meldExists()) ) {
			Deck deck = new Deck();
			h.dealTile(deck);
			//System.out.println("in draw " + h.handToString());
		}
		
		return h;
		
	}
	
	public boolean oneMeldSubsequentTurn(){
		Deck deck = new Deck();
		h.dealTile(deck);
		return this.oneMeldFirstTurn();
		
	}
	
	public boolean severaltMeldsSubsequentTurn(){
		Deck deck = new Deck();
		h.dealTile(deck);
		return this.severalMeldsFirstTurn();
		
	}

	public Hand drawsOnSubsequentTurn() {
		//System.out.println("first in draw " + h.handToString());
				if (! (h.meldExists()) ) {
					Deck deck1 = new Deck();
					h.dealTile(deck1);
					if (! (h.meldExists()) ) {
						Deck deck2 = new Deck();
						h.dealTile(deck2);
					}
				}
					//System.out.println("in draw " + h.handToString());
				
				return h;
				
			}
	
	
	
	

	
	
}
