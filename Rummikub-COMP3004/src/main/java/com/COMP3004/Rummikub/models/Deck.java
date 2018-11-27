package com.COMP3004.Rummikub.models;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Tile> tileDeck;
	//private int size;
	
	public Deck() {
		tileDeck = new ArrayList<Tile>();
		generateDeck();
		generateDeck();	
	}
	
	private void generateDeck() {
		for(int colour=0;colour<4;colour++) {
			for(int value=1;value<14;value++) {
				Tile tile = new Tile(colour,value);
				tile.setJoker(false);
				tileDeck.add(tile);
			}
		}
		Tile tileJoker = new Tile("JKR", 0);
		tileJoker.setJoker(true);
		tileDeck.add(tileJoker);
	} 
	
	// Getters
	public ArrayList<Tile> getTileDeck() { return tileDeck; }
	public int getDeckCount() { return tileDeck.size(); }
	
	// Setters
	public void shuffleTiles() { Collections.shuffle(tileDeck); }
	
	// Checkers (these should be in the tests)
	public boolean doesEveryTileExist(int count) {
		int counter = count;
		for(int i=0;i<4;i++) {
			for(int x=1;x<14;x++){
				Tile tile = new Tile(i,x);
				String aTileName = tileDeck.get(counter).getColour()+ tileDeck.get(counter).getValue();
				Boolean bool = tile.toString(i,x).equals(aTileName);
				if(bool == false) {
					return false;
				}
				counter++;
				}
			}
		doesEveryTileExist(54);
		return true;
	}
	public boolean isDeckShuffled(int count) {
		int counter = count;
		for(int i=0;i<4;i++) {
			for(int x=1;x<14;x++){
				Tile tile = new Tile(i,x);
				String aTileName = tileDeck.get(counter).getColour()+ tileDeck.get(counter).getValue();
				Boolean bool = tile.toString(i,x).equals(aTileName);
				if(bool == false) {
					return false;
				}
				counter++;
				}
			}
		isDeckShuffled(53);
		return true;
		
	}
}
