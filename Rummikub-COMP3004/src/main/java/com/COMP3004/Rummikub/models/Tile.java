package com.COMP3004.Rummikub.models;

public class Tile {
	// Variables
	private int colour, value;
	private Spot spot, oldSpot;
	private Meld memberOfMeld;
	private boolean isJoker = false;
	private static final String[] colours = { "Red", "Green", "Blue", "Orange", "Joker" };
	
	// Constructor for string & int
	public Tile (String colour, int value) {
		// Make the string uppercase
		colour = colour.toUpperCase();
		
		// Set the colour of the tile
		if (colour.equals("R")) { this.colour = 0;}
		else if (colour.equals("G")) { this.colour = 1; }
		else if (colour.equals("B")) { this.colour = 2; }
		else if (colour.equals("O")) { this.colour = 3; }
		else if (colour.equals("JKR")) { this.colour = 4; isJoker = true; }
		
		// Set the value of the tile
		this.value = value;
		if (isJoker) { this.value = 0; }
		this.spot = null;
		this.oldSpot = null;
		this.memberOfMeld = null;
	}
	
	//Constructor to pass in two ints
	public Tile (int colour, int value) {
		this.colour = colour;
		this.value = value;
	}
	
	// Getters
	public String getColour() { return colours[this.colour]; }
	public int getValue() { return this.value; }
	public Tile getTile() { return this; }
	public Spot getSpot() { return this.spot; }
	public Spot getOldSpot() { return this.oldSpot; }
	public Meld getMemberOfMeld() { return this.memberOfMeld; }
	public boolean getJoker() { return this.isJoker; }
	public String getTileName() {
		String colourForName = String.valueOf(colour);
		String valueForName = String.valueOf(value);
		return colourForName + valueForName;
	}
	
	// Setters
	public void setSpot(Spot spot) { this.spot = spot; }
	public void setMeld(Meld meld) { this.memberOfMeld = meld; }
	public void removeMeld() { this.memberOfMeld = null; }
	public void setJoker(boolean b) { this.isJoker = b; }
	public void removeSpot(Spot spot) {
		oldSpot = this.spot;
		this.spot = null;
	}
	
	// Print
	public String toString(int colour, int value) { return colours[colour] + String.valueOf(value); }
	public String tileToString() {
		if (this.isJoker == true) {
			return "JKR";
		} else {
			return colours[colour].charAt(0) + String.valueOf(value);
		}
	}
}
