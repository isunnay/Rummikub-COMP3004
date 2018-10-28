package com.COMP3004.Rummikub;

import java.util.ArrayList;

public class Meld {
	private ArrayList<Tile> tiles;
	private int numberOfTiles;
	private boolean isValidMeld;
	
	public Meld() {
		tiles = new ArrayList<Tile>();	
		numberOfTiles = 0;
		isValidMeld = true;
	}
	
	public Meld getMeld() {
		return this;
	}
	
	public Tile getTileInMeld(int i) {
		return tiles.get(i);
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
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
	}
	
	public void removeTile(int i) {
		tiles.remove(i);
		numberOfTiles--;
	}

	public int getNumberOfTiles() {
		return this.numberOfTiles;
	}
	
	public void checkIfValidMeld() {
		
	}

}
