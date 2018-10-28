package com.COMP3004.Rummikub;

public class Tile {
	// Variables
	private int colour;
	private int value;
	private static final String[] colours = { "Red", "Green", "Blue", "Orange" };
	private Spot spot;
	
	// Constructor for string & int
	public Tile (String colour, int value) {
		// Make the string uppercase
		colour = colour.toUpperCase();
		
		// Set the colour of the tile
		if (colour.equals("R")) { this.colour = 0;}
		else if (colour.equals("G")) { this.colour = 1; }
		else if (colour.equals("B")) { this.colour = 2; }
		else if (colour.equals("O")) { this.colour = 3; }
		
		// Set the value of the tile
		this.value = value;
		this.spot = null;
	}
	
	//Constructor to pass in two ints
	public Tile (int colour, int value) {
		this.colour = colour;
		this.value = value;
	}
	
	public String getColour() { return colours[this.colour]; } // Getter for tile colour
	public int getValue() { return this.value; }	// Getter for tile value
	
	public String getTileName() {
		String colourForName = String.valueOf(colour);
		String valueForName = String.valueOf(value);
		return colourForName + valueForName;
	}
	
	public String tileToString() {
		return colours[colour].charAt(0) + String.valueOf(value);
	}
	
	public String toString(int colour, int value) {
		return colours[colour] + String.valueOf(value);
	}
	
	public Tile getTile() {
		return this;
	}
	
	public void setSpot(Spot spot) {
		this.spot = spot;
	}
	
	public void removeSpot(Spot spot) {
		this.spot = null;
	}
	
	public Spot getSpot() {
		return this.spot;
	}
}
