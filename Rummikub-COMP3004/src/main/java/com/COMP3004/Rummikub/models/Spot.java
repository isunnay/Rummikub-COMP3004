package com.COMP3004.Rummikub.models;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
//import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class Spot extends Rectangle{
	int x;
	int y;
	Tile tile;
	boolean isTaken;
	
	public Spot(int i, int j) {
		this.x = i;
		this.y = j;
		tile = null;
		isTaken = false;
		setWidth(0);
		setHeight(0);
		setX(x);
		setY(y);
	}
	
	
	public Spot getSpot() {
		return this; 
	}
	
	public int getSpotX() {
		return this.x;
	}
	
	public int getSpotY() {
		return this.y;
	}

	public boolean isSpotEmpty() {
		if(this.tile == null) {
			return true;
		}
		return false;
	}

	public void playTile(Tile tile) {
		if(this.tile==null) {
			this.tile = tile;
			//tile.setSpot(this);
			isTaken = true;

		}
		else {
			System.out.println("This Spot is taken!");
			isTaken = true;	
		}	
	}
	
	public void removeTile() {
		if(this.tile!=null) {
			this.tile=null;
			isTaken = false;
		}
		else {
			System.out.println("This spot is already free");
			isTaken = false;
		}
	}
	
	public Tile getTile() {
		return this.tile;
	}
	
	public void setIsTaken(boolean bool) {
		this.isTaken = bool;
	}
	

	
	/*public void spotToString() {
		System.out.println(x);
	}*/
 

}
