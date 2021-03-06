package com.COMP3004.Rummikub.models;

import java.util.*;


public class Meld {
	private ArrayList<Tile> tiles;
	private int numberOfTiles;
	private int meldValue;
	
	private boolean isValidMeld;
	
	public enum typesOfMeld {sameRank, straight}
	
	public Meld() {
		tiles = new ArrayList<Tile>();	
		numberOfTiles = 0;
		isValidMeld = false;
		meldValue = 0;
	}
	
	// Getters
	public Meld getMeld() { return this; }
	public ArrayList<Tile> getTiles(){ return tiles; }
	public int getMeldSize() { return tiles.size(); }
	public int getNumberOfTiles() { return numberOfTiles; }
	public boolean getIsValidMeld() { return isValidMeld; }
	public Tile getTileInMeld(int i) { return tiles.get(i); }
	public int getMeldValue() { return this.meldValue; }

	public void addTile(Tile tile) {
		tiles.add(tile);
		tile.setMeld(this);
		meldValue = meldValue + tile.getValue();
		numberOfTiles++;
	}
	
	public void addTileFront(Tile tile) {
		tiles.add(0,tile);
		tile.setMeld(this);
		meldValue = meldValue+tile.getValue();
		numberOfTiles++;
	}

	public int findTileIndex(Tile tile) {
		for(int i=0;i<tiles.size();i++) {
			if(tiles.get(i).getTile() == tile) {
				return i;
			}
		}
		return -1;
	}
	
	public void removeTile(Tile tile) {
		int i = findTileIndex(tile);
		tiles.remove(i);
		tile.removeMeld();
		meldValue = meldValue - tile.getValue();
		numberOfTiles--;
	}
	
	public void removeTile(int i) {
		Tile tile = tiles.get(i);
		tiles.remove(i);
		tile.removeMeld();
		meldValue = meldValue - tile.getValue();
		numberOfTiles--;
	}

	/*
	public boolean isValidSet() {
		Tile tile;
		//System.out.println(tiles.);
		ArrayList<String> colours = new ArrayList<String>();
		colours.add(tiles.get(0).getColour());
		int number = tiles.get(0).getValue();
		//if (tiles.size()!=3 || tiles.size()!=4) {
		if (tiles.size()==3 || tiles.size()==4) {
			for(int i=1;i<tiles.size();i++) {
				System.out.println(i);
				tile = tiles.get(i);
				if(tile.getValue()!=number) {
					System.out.println("Wrong Value");
					return false;
				}
				if((colours.contains(tile.getColour()))) {
					System.out.println("Wrong Colour");
					return false;
				}
				colours.add(tile.getColour());
			}
			return true;
			
		} else {
			System.out.println("Melds are made up of 3 or more tiles");
			return false;
		}
	}
	
	public boolean isValidRun() {
		Tile tile;
		if(tiles.size()>2) {
			String colour = tiles.get(0).getColour(); 
			int previousNum = tiles.get(0).getValue(); 
			for(int i = 1; i < tiles.size(); i++) {
				tile = tiles.get(i); 
				
				if(!(colour.equals(tile.getColour()))) {
					return false;
				}
				
				if(tile.getValue() == previousNum + 1) {
					previousNum++; 
				}
				else {
					return false; 
				}
			}
			return true;
			
		}
		else {
			System.out.println("Melds are made up of 3 or more tiles");
			return false;
=======
	
	public boolean isValidSet() {		
		// Variable declaration
		boolean newColour = true;
		boolean sameNum = true;
		boolean diffColour = true;
		String red = "", blue = "", green = "", orange = "";
		ArrayList<String> temp = new ArrayList<String>();
		
		// Automatic failure if not within bounds
		if (tiles.size() < 3 || tiles.size() > 13) { return false; }
		
		// Check if all tile colours are unique
		for (int i = 0; i < tiles.size(); i++) {
			if (!(temp.contains(tiles.get(i).getColour()))) {
				temp.add(tiles.get(i).getTile().getColour());
				if (tiles.get(i).getTile().getColour().equals("Red") && !(red.equals("taken"))) { red = "taken"; }
				else if (tiles.get(i).getTile().getColour().equals("Green") && !(green.equals("taken"))) { green = "taken"; }
				else if (tiles.get(i).getTile().getColour().equals("Blue") && !(blue.equals("taken"))) { blue = "taken"; }
				else if (tiles.get(i).getTile().getColour().equals("Orange") && !(orange.equals("taken"))) { orange = "taken"; }
			} else {
				diffColour = false;
			}
		}
		
		// Set jokerColour & jokerValue for jokers + add to meldValue, if any are in the meld
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).getJoker()) {
				if (i == 0) {
					tiles.get(i).setJokerValue((tiles.get(i+1).getValue()));
				} else {
					tiles.get(i).setJokerValue((tiles.get(i-1).getValue()));
				}
			}
			if (diffColour == false && i == tiles.size()-1) {
				if (!(red.equals("taken"))) { tiles.get(i).setJokerColour(0); }
				else if (!(green.equals("taken"))) { tiles.get(i).setJokerColour(1); }
				else if (!(blue.equals("taken"))) { tiles.get(i).setJokerColour(2); }
				else if (!(orange.equals("taken"))) { tiles.get(i).setJokerColour(3); }
			}
		}
		
		// Go through possibilities for sets
		for (int i = 0; i < tiles.size()-1; i++) {
			if (tiles.get(i).getJoker()) {
				if (tiles.get(i).getJokerValue() != tiles.get(i+1).getValue()) { sameNum = false; }
			} else if (tiles.get(i+1).getJoker()) {
				if (tiles.get(i).getValue() != tiles.get(i+1).getJokerValue()) { sameNum = false; }
			} else if (tiles.get(i).getJoker() && tiles.get(i+1).getJoker()) {
				if (tiles.get(i).getJokerValue() != tiles.get(i+1).getJokerValue()) { sameNum = false; }
			} else {
				if (tiles.get(i).getValue() != tiles.get(i+1).getValue()) { sameNum = false; }
			}
		}
		
		for (int i = 0; i < tiles.size(); i++) {
			if (sameNum && diffColour && tiles.get(i).getJoker()) {
				meldValue += tiles.get(i).getJokerValue();
				//System.out.println("JokerValue: " + tiles.get(i).getJokerValue());
				//System.out.println("JokerColour: " + tiles.get(i).getJokerColour());
			} else {
				//System.out.println("Invalid set with joker");
			}
		}
		
		System.out.println(sameNum);
		System.out.println(sameNum);
		
		return sameNum && diffColour;
	}
	
	public boolean isValidRun() {		
		// Variable declaration
		boolean diffNum = true;
		boolean sameColour = true;
		
		// Automatic failure if not within bounds
		if (tiles.size() < 3 || tiles.size() > 13) { return false; }
		
		// Set jokerColour & jokerValue for jokers + add to meldValue, if any are in the meld
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).getJoker()) {
				if (i == 0) {
					tiles.get(i).setJokerColour(tiles.get(i+1).getColourInt());
					tiles.get(i).setJokerValue((tiles.get(i+1).getValue()-1));
				} else {
					tiles.get(i).setJokerColour(tiles.get(i-1).getColourInt());
					tiles.get(i).setJokerValue((tiles.get(i-1).getValue()+1));
				}
			}
>>>>>>> f2ebd9a58d2b077ed47990a1d42b4be75c0f594b
		}
		
		// Go through possibilities for runs
		for (int i = 0; i < tiles.size()-1; i++) {
			if (tiles.get(i).getJoker()) {
				if ((tiles.get(i).getJokerValue()+1) != tiles.get(i+1).getValue()) { diffNum = false; }
				if (!(tiles.get(i).getJokerColour().equals(tiles.get(i+1).getColour()))) { sameColour = false; }
			} else if (tiles.get(i+1).getJoker()) {
				if ((tiles.get(i).getValue()+1) != tiles.get(i+1).getJokerValue()) { diffNum = false; }
				if (!(tiles.get(i).getColour().equals(tiles.get(i+1).getJokerColour()))) { sameColour = false; }
			} else if (tiles.get(i).getJoker() && tiles.get(i+1).getJoker()) {
				if ((tiles.get(i).getJokerValue()+1) != tiles.get(i+1).getJokerValue()) { diffNum = false; }
				if (!(tiles.get(i).getJokerColour().equals(tiles.get(i+1).getJokerColour()))) { sameColour = false; }
			} else {
				if ((tiles.get(i).getValue()+1) != tiles.get(i+1).getValue()) { diffNum = false; }
				if (!(tiles.get(i).getColour().equals(tiles.get(i+1).getColour()))) { sameColour = false; }
			}
		}
		
		for (int i = 0; i < tiles.size(); i++) {
			if (diffNum && sameColour && tiles.get(i).getJoker()) {
				meldValue += tiles.get(i).getJokerValue();
				//System.out.println("JokerValue: " + tiles.get(i).getJokerValue());
				//System.out.println("JokerColour: " + tiles.get(i).getJokerColour());
			} else {
				//System.out.println("Invalid run with joker");
			}
		}
		
		return diffNum && sameColour;
	}
		
	
	public boolean checkIfValidMeld(Meld meld) {
		if (meld.isValidRun()) { return true; }
		if (meld.isValidSet()) { return true; }
		
		return false;
	}
	
	public boolean checkIfValidMeld() {
		if (this.isValidRun()) { return true; }
		if (this.isValidSet()) { return true; }
		
		return false;
	}
	*/
	
	public boolean isValidSet() {		
		// Variable declaration
		boolean newColour = true;
		boolean sameNum = true;
		boolean diffColour = true;
		String red = "", blue = "", green = "", orange = "";
		ArrayList<Tile> temp = new ArrayList<Tile>();
		
		// Automatic failure if not within bounds
		if (tiles.size() < 3 || tiles.size() > 13) { return false; }
		
		// Check if all tile colours are unique
		for (int i = 0; i < tiles.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (temp.get(j).getColour().equals(tiles.get(i).getColour())) {
					diffColour = false;
				} else if (j == tiles.size() - 1) {
					newColour = true;
				}
			}
			
			if (newColour) {
				temp.add(tiles.get(i).getTile());
				if (tiles.get(i).getTile().getColour().equals("Red")) { red = "taken"; }
				else if (tiles.get(i).getTile().getColour().equals("Green")) { green = "taken"; }
				else if (tiles.get(i).getTile().getColour().equals("Blue")) { blue = "taken"; }
				else if (tiles.get(i).getTile().getColour().equals("Orange")) { orange = "taken"; }
				newColour = false;
			}
		}
		
		// Set jokerColour & jokerValue for jokers + add to meldValue, if any are in the meld
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).getJoker()) {
				if (i == 0) {
					tiles.get(i).setJokerValue((tiles.get(i+1).getValue()));
				} else {
					tiles.get(i).setJokerValue((tiles.get(i-1).getValue()));
				}
			}
			if (diffColour == false && i == tiles.size()-1) {
				if (!(red.equals("taken"))) { tiles.get(i).setJokerColour(0); }
				else if (!(green.equals("taken"))) { tiles.get(i).setJokerColour(1); }
				else if (!(blue.equals("taken"))) { tiles.get(i).setJokerColour(2); }
				else if (!(orange.equals("taken"))) { tiles.get(i).setJokerColour(3); }
			}
		}
		
		// Go through possibilities for sets
		for (int i = 0; i < tiles.size()-1; i++) {
			if (tiles.get(i).getJoker()) {
				if (tiles.get(i).getJokerValue() != tiles.get(i+1).getValue()) { sameNum = false; }
			} else if (tiles.get(i+1).getJoker()) {
				if (tiles.get(i).getValue() != tiles.get(i+1).getJokerValue()) { sameNum = false; }
			} else if (tiles.get(i).getJoker() && tiles.get(i+1).getJoker()) {
				if (tiles.get(i).getJokerValue() != tiles.get(i+1).getJokerValue()) { sameNum = false; }
			} else {
				if (tiles.get(i).getValue() != tiles.get(i+1).getValue()) { sameNum = false; }
			}
		}
		
		for (int i = 0; i < tiles.size(); i++) {
			if (sameNum && diffColour && tiles.get(i).getJoker()) {
				meldValue += tiles.get(i).getJokerValue();
				//System.out.println("JokerValue: " + tiles.get(i).getJokerValue());
				//System.out.println("JokerColour: " + tiles.get(i).getJokerColour());
			} else {
				//System.out.println("Invalid set with joker");
			}
		}
		
		return sameNum && diffColour;
	}
	
	public boolean isValidRun() {		
		// Variable declaration
		boolean diffNum = true;
		boolean sameColour = true;
		
		// Automatic failure if not within bounds
		if (tiles.size() < 3 || tiles.size() > 13) { return false; }
		
		// Set jokerColour & jokerValue for jokers + add to meldValue, if any are in the meld
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).getJoker()) {
				if (i == 0) {
					tiles.get(i).setJokerColour(tiles.get(i+1).getColourInt());
					tiles.get(i).setJokerValue((tiles.get(i+1).getValue()-1));
				} else {
					tiles.get(i).setJokerColour(tiles.get(i-1).getColourInt());
					tiles.get(i).setJokerValue((tiles.get(i-1).getValue()+1));
				}
			}
		}
		
		// Go through possibilities for runs
		for (int i = 0; i < tiles.size()-1; i++) {
			if (tiles.get(i).getJoker()) {
				if ((tiles.get(i).getJokerValue()+1) != tiles.get(i+1).getValue()) { diffNum = false; }
				if (!(tiles.get(i).getJokerColour().equals(tiles.get(i+1).getColour()))) { sameColour = false; }
			} else if (tiles.get(i+1).getJoker()) {
				if ((tiles.get(i).getValue()+1) != tiles.get(i+1).getJokerValue()) { diffNum = false; }
				if (!(tiles.get(i).getColour().equals(tiles.get(i+1).getJokerColour()))) { sameColour = false; }
			} else if (tiles.get(i).getJoker() && tiles.get(i+1).getJoker()) {
				if ((tiles.get(i).getJokerValue()+1) != tiles.get(i+1).getJokerValue()) { diffNum = false; }
				if (!(tiles.get(i).getJokerColour().equals(tiles.get(i+1).getJokerColour()))) { sameColour = false; }
			} else {
				if ((tiles.get(i).getValue()+1) != tiles.get(i+1).getValue()) { diffNum = false; }
				if (!(tiles.get(i).getColour().equals(tiles.get(i+1).getColour()))) { sameColour = false; }
			}
		}
		
		for (int i = 0; i < tiles.size(); i++) {
			if (diffNum && sameColour && tiles.get(i).getJoker()) {
				meldValue += tiles.get(i).getJokerValue();
				//System.out.println("JokerValue: " + tiles.get(i).getJokerValue());
				//System.out.println("JokerColour: " + tiles.get(i).getJokerColour());
			} else {
				//System.out.println("Invalid run with joker");
			}
		}
		
		return diffNum && sameColour;
	}
	
	public boolean checkIfValidMeld(Meld meld) {
		if (meld.isValidRun()) { return true; }
		if (meld.isValidSet()) { return true; }
		
		return false;
	}
	
	public boolean checkIfValidMeld() {
		if (this.isValidRun()) { return true; }
		if (this.isValidSet()) { return true; }
		
		return false;
	}
	public String meldToString() {
		String h = "";
		
		for (int i = 0; i < tiles.size(); i++) {
			h += this.getTileInMeld(i).tileToString();
			if (i < tiles.size()) { h += " "; }
		}
		
		return h;
	}
}
