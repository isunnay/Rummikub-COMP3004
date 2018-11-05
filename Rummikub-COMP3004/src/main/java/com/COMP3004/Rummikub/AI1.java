package com.COMP3004.Rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.COMP3004.Rummikub.Hand.SortByValue;

public class AI1 implements PlayerType {
	Hand h;
	private boolean myTurn = false;
	//private boolean isTurn = false;
	private boolean hasTileBeenPlaced = false;
	private ArrayList<Meld> melds;
	public Subject game;
	public ArrayList<Spot> spotsTaken;
	public ArrayList<Tile> turnTiles;
	public ArrayList<Meld> turnMelds;
	public ArrayList<Tile> turnMoves;
	public ArrayList<Meld> testMelds;
	private Board board;

	
	public AI1(Deck deck) {
		h = new Hand();
		h.createHand(deck);
		h.sortHand();
		melds = new ArrayList<Meld>();
		//game.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();
		turnMoves = new ArrayList<Tile>();
		testMelds = new ArrayList<Meld>();
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
			//h.createHand(deck);
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

	@Override
	public void update(Board board) {
		// TODO Auto-generated method stub
		this.spotsTaken = board.filledSpots;
		this.board = board;
		
	}

	@Override
	public void playMeld(Meld meld, Scanner reader) {
		int x= 5;
		int y=5;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canWePlaceMeld(Meld meld, int x, int y) {
		for(int a=0;a<meld.getMeldSize();a++) {
			if(x+a<board.getX()) {
				//int nextX = x+meld.getMeldsize;
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
			else {
				System.out.println("ERROR: Make sure your meld is placed within the board spots.");
				return false;
			}
		}
		return true;	
	}
	
	public class SortByValue implements Comparator<Tile> {
		public int compare(Tile x, Tile y) {
			return x.getValue() - y.getValue();
		}
	}
	
	public void sortByValue(ArrayList<Tile> hand) {
		Collections.sort(hand, new SortByValue());
	}
	
	@Override
	public void undoTurn() {
		// TODO Auto-generated method stub
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
		turnMoves.clear();
		
	}

	@Override
	public void undoAddTile(Tile tile) {
		// TODO Auto-generated method stub
		
	}

	@Override
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

	@Override
	public void moveTile(Tile tile, Spot newSpot) {
		// TODO Auto-generated method stub
		
	}
	
	public void findAllMelds() {
		int numMelds = 0;
		Collections.sort(this.h.getPlayerHand(), new SortByValue());
		
		for(int i=0; i<this.getHand().size; i++) {
			if(i < this.getHand().size - 2) {
				if(this.getHand().getTile(i).getValue() == this.getHand().getTile(i+1).getValue() - 1) {
					if(this.getHand().getTile(i+1).getValue() == this.getHand().getTile(i+2).getValue() - 1) {
						if(this.getHand().getTile(i+2).getValue() == this.getHand().getTile(i+3).getValue() - 1) {
							String color1 = this.getHand().getTile(i).getColour();
							String color2 = this.getHand().getTile(i+1).getColour();
							String color3 = this.getHand().getTile(i+2).getColour();
							if((color1 == color2) && (color2 == color3)) {
								
								Meld newMeld = new Meld();
								newMeld.addTile(this.getHand().getTile(i));
								newMeld.addTile(this.getHand().getTile(i+1));
								newMeld.addTile(this.getHand().getTile(i+2));
								//System.out.println(newMeld.checkIfValidMeld());
								this.testMelds.add(newMeld);
								//System.out.println(testMelds.size());
							}
						}
					}
				}
			}
			//System.out.println("i = "+i);
		}	

		for(int i=0; i<this.getHand().size; i++) {
			if(i < this.getHand().size - 2) {
				int handValue = this.getHand().getTile(i).getValue();
				if(handValue == this.getHand().getTile(i+1).getValue()) {
					if(handValue == this.getHand().getTile(i+2).getValue()) {
						String color = this.getHand().getTile(i).getColour();
						String color2 = this.getHand().getTile(i+1).getColour();
						String color3 = this.getHand().getTile(i+2).getColour();
						if((color != color2) && (color != color3) && (color2 != color3)) {
							Meld newMeld = new Meld();
							newMeld.addTile(this.getHand().getTile(i));
							newMeld.addTile(this.getHand().getTile(i+1));
							newMeld.addTile(this.getHand().getTile(i+2));
							//System.out.println(newMeld.checkIfValidMeld());
							this.testMelds.add(newMeld);
							//System.out.println(testMelds.size());
						}
					}
				}
			}
		}
		
		
		
		//return numMelds;
	}
	
	
	

	
	
}
