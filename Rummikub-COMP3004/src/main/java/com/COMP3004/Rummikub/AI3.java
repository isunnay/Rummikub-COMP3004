package com.COMP3004.Rummikub;

import java.util.ArrayList;

public class AI3 implements PlayerType {
	Hand h;
	private boolean initialMeldPlayed = false;
	private boolean myTurn = false;
	private boolean hasTileBeenPlaced = false;
	public Subject game;
	private Board board;
	private ArrayList<PlayerType> allPlayers = new ArrayList<PlayerType>();

	
	public AI3(Deck deck, Game game) {
		h = new Hand();
		h.createHand(deck);
		h.sortHand();
		game.registerObserver(this);
	}
	
	public Hand getHand() { return this.h; }
	
	public boolean myTurnStatus() { return this.myTurn; }
	
	public void setTurnStatus(boolean b) { this.myTurn = b; }
	
	public boolean hasTilesBeenPlayed() { return this.hasTileBeenPlaced; }
	
	public void setTilesBeenPlayed(boolean b) { this.hasTileBeenPlaced = b; }
	
	public void update(Board board, Game game) {
		this.allPlayers = game.getAllPlayers();
		this.board = board;
	}
	
	public boolean doesAnyoneHave3LessTiles() {
		for (int i = 0; i < allPlayers.size()-1; i++) {
			if ((this.getHand().size - allPlayers.get(i).getHand().size) >= 3) {
				return true;
			}
		}
		return false;
	}

	public boolean turnComplete(Hand h) {
		// Initial Meld: ASAP
		// Gameplay: If another player has 3 less tiles than me, use AI1 strategy. Otherwise, use AI2 strategy.
		if (initialMeldPlayed == false) {
			/*
			 * 
			 * 
			 * Insert AI1's strategy
			 * 
			 * 
			 */
			initialMeldPlayed = true;
			return true;
		} else if (initialMeldPlayed == true) {
			if (doesAnyoneHave3LessTiles() == true) {
				/*
				 * 
				 * 
				 * Insert AI1's strategy
				 * 
				 * 
				 */
			} else {
				/*
				 * 
				 * 
				 * Insert AI2's strategy
				 * 
				 * 
				 */
			}
			return true;
		}
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
		if (! (h.meldExists()) ) {
			Deck deck = new Deck();
			h.dealTile(deck);
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
				if (! (h.meldExists()) ) {
					Deck deck1 = new Deck();
					h.dealTile(deck1);
					if (! (h.meldExists()) ) {
						Deck deck2 = new Deck();
						h.dealTile(deck2);
					}
				}
				return h;
			}
}