package com.COMP3004.Rummikub.models;

import com.COMP3004.Rummikub.controller.MouseGestures;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends StackPane{
	// Variables
	private int colour, value;
	private MouseGestures mg;
	private int jokerColour, jokerValue;
	private Spot spot, oldSpot;
	private Meld memberOfMeld;
	private boolean isJoker = false;
	private static final String[] colours = { "Red", "Green", "Blue", "Orange", "Joker" };
	Rectangle rectangle;
	
	
	// Constructor for string & int
	public Tile (String colour, int value) {
		mg = new MouseGestures();
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
		
	/*	setWidth(40);
		setHeight(20);
		Text text;*/
		
		Text text;
		//Rectangle rectangle = new Rectangle(40,20);
		rectangle = new Rectangle(40,20);
		//if(this.isJoker == false) {
			
			text = new Text(String.valueOf(value));
			System.out.println(text);
			if(this.colour==0) {
				//rectangle = new Rectangle(40,20);
				rectangle.setFill(null);
				rectangle.setStrokeWidth(5);
				rectangle.setStroke(Color.RED);
			}
			else if(this.colour==1) {
			//	rectangle = new Rectangle(40,20);
				rectangle.setFill(null);
				rectangle.setStrokeWidth(5);
				rectangle.setStroke(Color.GREEN);
			}
			else if(this.colour==2) {
			//	rectangle = new Rectangle(40,20);
				rectangle.setFill(null);
				rectangle.setStrokeWidth(5);
				rectangle.setStroke(Color.BLUE);
			}
			else if(this.colour==3) {
			//	rectangle = new Rectangle(40,20);
				rectangle.setFill(null);
				rectangle.setStrokeWidth(5);
				rectangle.setStroke(Color.ORANGE);
			}
		//}
		if(this.isJoker == true){
		//	rectangle = new Rectangle(40,20);
			text = new Text("JKR");
			System.out.println(text);
			rectangle.setFill(null);
			rectangle.setStrokeWidth(5);
			rectangle.setStroke(Color.BLACK);
		}
		mg.makeDraggable(this);
		getChildren().addAll(rectangle, text);
		
	}
	
	//Constructor to pass in two ints
	public Tile (int colour, int value) {
		mg = new MouseGestures();
		this.colour = colour;
		this.value = value;
		
		Text text;
		//Rectangle rectangle = new Rectangle(40,20);
		rectangle = new Rectangle(40,20);
		//if(this.isJoker == false) {
			
			text = new Text(String.valueOf(value));
			System.out.println(text);
			if(this.colour==0) {
				//rectangle = new Rectangle(40,20);
				rectangle.setFill(null);
				rectangle.setStrokeWidth(5);
				rectangle.setStroke(Color.RED);
			}
			else if(this.colour==1) {
			//	rectangle = new Rectangle(40,20);
				rectangle.setFill(null);
				rectangle.setStrokeWidth(5);
				rectangle.setStroke(Color.GREEN);
			}
			else if(this.colour==2) {
			//	rectangle = new Rectangle(40,20);
				rectangle.setFill(null);
				rectangle.setStrokeWidth(5);
				rectangle.setStroke(Color.BLUE);
			}
			else if(this.colour==3) {
			//	rectangle = new Rectangle(40,20);
				rectangle.setFill(null);
				rectangle.setStrokeWidth(5);
				rectangle.setStroke(Color.ORANGE);
			}
		//}
		if(this.isJoker == true){
		//	rectangle = new Rectangle(40,20);
			text = new Text("JKR");
			System.out.println(text);
			rectangle.setFill(null);
			rectangle.setStrokeWidth(5);
			rectangle.setStroke(Color.BLACK);
		}
		mg.makeDraggable(this);
		getChildren().addAll(rectangle, text);
	}
	
	public void setX(double offsetX) {
		// TODO Auto-generated method stub
		this.rectangle.setX(offsetX);
	}
	
	public void setY(double offsetY) {
		this.rectangle.setY(offsetY);
	}
	
	
	// Getters
	public String getColour() { return colours[this.colour]; }
	public int getValue() { return this.value; }
	public String getJokerColour() { return colours[this.jokerColour]; }
	public int getJokerValue() { return this.jokerValue; }
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
	public int getColourInt() {
		if (this.getColour().equals("Red")) { return 0;}
		else if (this.getColour().equals("Green")) { return 1; }
		else if (this.getColour().equals("Blue")) { return 2; }
		return 3;
	}
	
	// Setters
	public void setSpot(Spot spot) { this.spot = spot; }
	public void setMeld(Meld meld) { this.memberOfMeld = meld; }
	public void removeMeld() { this.memberOfMeld = null; }
	public void setJoker(boolean b) { this.isJoker = b; }
	public void setJokerValue(int value) { if (this.getJoker()) { this.jokerValue = value; } }
	public void setJokerColour(int colour) { if (this.getJoker()) { this.jokerColour = colour; } }
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

	public int getIntColor() {
		return this.colour;
	}


}
