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
		int number;
		int i;
		ArrayList<String> colours = new ArrayList<String>();
		if (tiles.size()==3 || tiles.size()==4) {
			if (tiles.get(0).getJoker() == false) {
				colours.add(tiles.get(0).getColour());
				number = tiles.get(0).getValue();
				i = 1;
			} else {
				meldValue += tiles.get(1).getValue();
				colours.add(tiles.get(1).getColour());
				number = tiles.get(1).getValue();
				i = 2;
			}
			while(i<tiles.size()) {
				tile = tiles.get(i);
				if (tile.getJoker() == true) { meldValue += number; }
				if(tile.getValue()!=number && !(tile.getJoker())) {
					return false;
				}
				if((colours.contains(tile.getColour()) && !(tile.getJoker()))) {
					return false;
				}
				colours.add(tile.getColour());
				i++;
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
				if (tile.getJoker() == true) { meldValue += previousNum-1; }
				if(!(colour.equals(tile.getColour())) && !(tile.getJoker())) {
					return false;
				}
				if(tile.getValue() == previousNum + 1 || tile.getJoker()) {
					previousNum++;
				} else {
					return false; 
				}
				i++;
			}
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
