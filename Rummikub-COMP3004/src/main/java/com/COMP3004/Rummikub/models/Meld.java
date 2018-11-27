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
	
	public boolean isValidSet() {
		Tile tile;
		ArrayList<String> colours = new ArrayList<String>();
		colours.add(tiles.get(0).getColour());
		int number = tiles.get(0).getValue();
		if (tiles.size()==3 || tiles.size()==4) {
			for(int i=1;i<tiles.size();i++) {
				tile = tiles.get(i);
				if(tile.getValue()!=number && !(tile.getJoker())) {
					return false;
				}
				if((colours.contains(tile.getColour()) && !(tile.getJoker()))) {
					return false;
				}
				colours.add(tile.getColour());
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValidRun() {
		Tile tile;
		String colour;
		int previousNum;
		int i;
		if(tiles.size()>2) {
			if (tiles.get(0).getJoker() == false) {
				colour = tiles.get(0).getColour(); 
				previousNum = tiles.get(0).getValue();
				i = 1;
			} else {
				colour = tiles.get(1).getColour(); 
				previousNum = tiles.get(1).getValue();
				i = 2;
			}
			while(i<tiles.size()) {
				tile = tiles.get(i); 
				if(!(colour.equals(tile.getColour())) && !(tile.getJoker())) {
					System.out.println("Invalid Colour: " + tile.tileToString());
					return false;
				}
				if(tile.getValue() == previousNum + 1 || tile.getJoker()) {
					System.out.println("Num: " + previousNum + "| Tile: " + tile.tileToString());
					previousNum++;
				} else {
					System.out.println("Invalid Number: " + tile.tileToString());
					return false; 
				}
				i++;
			}
			/*
			for(i; i < tiles.size(); i++) {
				tile = tiles.get(i); 
				if(!(colour.equals(tile.getColour()))) {
					System.out.println("Invalid Colour: " + tile.tileToString());
					return false;
				}
				if(tile.getValue() == previousNum + 1) {
					System.out.println("Num: " + previousNum + "| Tile: " + tile.tileToString());
					previousNum++;
				} else {
					System.out.println("Invalid Number: " + tile.tileToString());
					return false; 
				}
			}*/
			return true;	
		} else {
			return false;
		}
	}
	
	public boolean checkIfValidMeld(Meld meld) {
		if(meld.isValidRun() == true || meld.isValidSet() == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkIfValidMeld() {
		if(this.isValidRun() == true || this.isValidSet() == true) {
			return true;
		} else {
			return false;
		}
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
