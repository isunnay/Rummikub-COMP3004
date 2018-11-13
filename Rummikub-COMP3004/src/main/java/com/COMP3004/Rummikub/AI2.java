package com.COMP3004.Rummikub;

import java.util.ArrayList;
import java.util.Scanner;

public class AI2 implements PlayerType {
	Hand h;
	private boolean initialMeldPlayed = false;
	private boolean myTurn = false;
	private boolean hasTileBeenPlaced = false;
	public Subject game;
	public int turnPoints;
	private Board board;
	public ArrayList<Meld> melds;
	public ArrayList<Spot> spotsTaken;
	public ArrayList<Tile> turnTiles;
	public ArrayList<Meld> turnMelds;
	public ArrayList<Tile> turnMoves;
	public ArrayList<Tile> usedInMeld;


	
	public AI2(Deck deck) {
		h = new Hand();
		h.createHand(deck);
		h.sortHand();
		melds = new ArrayList<Meld>();
		//game.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();
		turnMoves = new ArrayList<Tile>();
		usedInMeld = new ArrayList<Tile>();
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

	@Override
	public boolean hasInitialMeldBeenPlayed() {
		// TODO Auto-generated method stub
		return this.initialMeldPlayed;
	}

	@Override
	public void setHasInitialMeldBeenPlayed(boolean b) {
		// TODO Auto-generated method stub
		this.initialMeldPlayed = b;
		
	}

	@Override
	public void update(Board board) {
			this.spotsTaken = board.filledSpots;
			this.board = board;	
			turnTiles.clear();;
			turnMelds.clear();				
			turnMoves.clear();
			melds.clear();
			usedInMeld.clear();
			turnPoints = 0;		
	}

	@Override
	public void play(Scanner reader) {
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
	public boolean canWePlaceTile(Tile tile, int x, int y) {
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

	@Override
	public Meld combineMelds(Meld meld1, Meld meld2, Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undoMove(Tile tile) {
		// TODO Auto-generated method stub
		
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

}
