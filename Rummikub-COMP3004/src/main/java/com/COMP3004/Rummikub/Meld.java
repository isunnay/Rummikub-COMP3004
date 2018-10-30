package com.COMP3004.Rummikub;

import java.util.ArrayList;

public class Meld {
	private ArrayList<Tile> tiles;
	private int numberOfTiles;
	
	private boolean isValidMeld;
	
	public enum typesOfMeld {sameRank, straight}
	
	public Meld() {
		tiles = new ArrayList<Tile>();	
		numberOfTiles = 0;
		isValidMeld = true;
	}
	
	public Meld getMeld() {
		return this;
	}
	
	public boolean getIsValidMeld() {
		return isValidMeld;
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
	
	/*if (x.size() <= index || x.get(index) == null) {
	    ...
	}
	
	if (index < x.size() && x.get(index) != null) {
	    ...
	}*/
	
	public boolean isValidMeldValue() {
		if (tiles.size() > 2) {
			for (int i = 0; i < tiles.size(); i++) {
				if (i < tiles.size() - 1 && tiles.get(i + 1) != null) {
					if (tiles.get(i).getValue() == tiles.get((i + 1)).getValue()
							&& tiles.get(i).getColour() == tiles.get((i + 1)).getColour()) {
						// System.out.println("Hello?");
						isValidMeld = false;
						return false;
					}
				}
			}
			return true;
		} else {
			System.out.println("Melds are made up of 3 or more tiles");
			return false;
		}
	}
	
	public boolean isValidMeldStraight() {
		if (tiles.size() > 2) {
			for (int i = 0; i < tiles.size(); i++) {
				if (i < tiles.size() - 1 && tiles.get(i + 1) != null) {
					if (tiles.get(i).getColour() != tiles.get((i + 1)).getColour() 
						&& (tiles.get(i + 1).getValue() - tiles.get(i).getValue() != 1)) {
						return false;
					}
				}
			}
			return true;
		}
		else {
			System.out.println("Melds are made up of 3 or more tiles");
			return false;
		}
	}
	
	public boolean checkIfValidMeld(Meld meld) {
		if(meld.isValidMeldStraight() == true || meld.isValidMeldValue() == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*public void checkIfValidMeld() {
		for(int i=0; i<tiles.size(); i++) {
			if(i != tiles.size() - 2) {
				if(tiles.get(i).getValue() == tiles.get(i+1).getValue() - 1) {
					if(tiles.get(i+1).getValue() == tiles.get(i+2).getValue() - 1) {
						if(tiles.get(i+2).getValue() == tiles.get(i+3).getValue() - 1) {
							String color1 = tiles.get(i).getColour();
							String color2 = tiles.get(i+1).getColour();
							String color3 = tiles.get(i+2).getColour();
							if((color1 == color2) && (color2 == color3)) {
								isValidMeld = true;
							}
							else {
								continue;
							}
						}
						else {
							continue;
						}
					}
					else {
						continue;
					}
				}
				else {
					continue;
				}
			}
			else {
				break;
			}
		}

		for(int i=0; i<tiles.size(); i++) {
			if(i != tiles.size() - 2) {
				int handValue = tiles.get(i).getValue();
				if(handValue == tiles.get(i+1).getValue()) {
					if(handValue == tiles.get(i+2).getValue()) {
						String color = tiles.get(i).getColour();
						String color2 = tiles.get(i+1).getColour();
						String color3 = tiles.get(i+2).getColour();
						if((color != color2) && (color != color3) && (color2 != color3)) {
							isValidMeld = true;
						}
						else {
							continue;
						}
					}
					else {
						continue;
					}
				}
				else {
					continue;
				}
			}
			else {
				break;
			}
		}
		
		isValidMeld = false;
	}*/

}
