package com.COMP3004.Rummikub.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand {
	private ArrayList<Tile> playerHand;
	private ArrayList<Tile> greenHand;
	private ArrayList<Tile> blueHand;
	private ArrayList<Tile> redHand;
	private ArrayList<Tile> orangeHand;
	private ArrayList<Tile> sortedHand;
	private ArrayList<Meld> melds;
	
	public int size;
	private int numberOfMelds;
	
	public Hand() {
		playerHand = new ArrayList<Tile>();
		greenHand = new ArrayList<Tile>();
		blueHand = new ArrayList<Tile>();
		redHand = new ArrayList<Tile>();
		orangeHand = new ArrayList<Tile>();
		sortedHand = new ArrayList<Tile>();
		melds = new ArrayList<Meld>();
		numberOfMelds = 0;
		size = 0;
	}
	
	public void createHand(Deck deck) {
		for(int i=0; i<14; i++) {
			playerHand.add(deck.getTileDeck().remove(deck.getDeckCount()- 1));
			size++;
		}
	}
	
	public boolean isTileDealt(Deck deck) {
		if(this.getNumTiles() > 14) {
			return true;
		}
		
		return false;
	}
	

	//dealTile() method returns size of hand after a new tile is dealt to it
	public Tile dealTile(Deck deck) {
		Tile tile = deck.getTileDeck().remove(deck.getDeckCount() - 1);
		playerHand.add(tile);
		this.size++;
		this.sortHand();
		return tile;
	}
	
	public int getNumTiles() {
		return this.size;
	}
	
	public class SortByValue implements Comparator<Tile> {
		public int compare(Tile x, Tile y) {
			return x.getValue() - y.getValue();
		}
	}
	
	public void sortByValue(ArrayList<Tile> hand) {
		Collections.sort(hand, new SortByValue());
	}
	
	public void sortByColor() {
		for(int i=0; i<playerHand.size(); i++) {
			if(playerHand.get(i).getColour()=="Green")
				greenHand.add(playerHand.get(i));
			
			else if (playerHand.get(i).getColour()=="Blue")
				blueHand.add(playerHand.get(i));
			
			else if (playerHand.get(i).getColour()=="Red")
				redHand.add(playerHand.get(i));
			
			else if (playerHand.get(i).getColour()=="Orange")
				orangeHand.add(playerHand.get(i));
		}
	}
	
	
	public ArrayList<Tile> getPlayerHand() {
		return this.playerHand;
	}
	
	public void sortHand() {

		if(greenHand.size()>0) {
			greenHand.clear();
		}
		if(redHand.size()>0) {
			redHand.clear();
		}
		if(blueHand.size()>0) {
			blueHand.clear();
		}
		if(orangeHand.size()>0) {
			orangeHand.clear();
		}

		//System.out.println(playerHand.size());
		this.sortByColor();
		this.sortByValue(greenHand);
		this.sortByValue(redHand);
		this.sortByValue(blueHand);
		this.sortByValue(orangeHand);
		
		sortedHand.clear();
		for(int i=0; i<redHand.size(); i++) {
			sortedHand.add(redHand.get(i));
		}
		
		for(int i=0; i<greenHand.size(); i++) {
			sortedHand.add(greenHand.get(i));
		}
		
		for(int i=0; i<blueHand.size(); i++) {
			sortedHand.add(blueHand.get(i));
		}
		
		for(int i=0; i<orangeHand.size(); i++) {
			sortedHand.add(orangeHand.get(i));
		}	
		//System.out.println(sortedHand.size());
		//playerHand.clear();
		//myObject = (ArrayList<Object>)myTempObject.clone();
		//playerHand = (ArrayList<Tile>)sortedHand.clone();
		playerHand = sortedHand;	
	}
	
	public boolean meldExists() {
		Collections.sort(playerHand, new SortByValue());

		for(int i=0; i<playerHand.size(); i++) {
			if(i != playerHand.size() - 2) {
				if(playerHand.get(i).getValue() == playerHand.get(i+1).getValue() - 1) {
					if(playerHand.get(i+1).getValue() == playerHand.get(i+2).getValue() - 1) {
						if(playerHand.get(i+2).getValue() == playerHand.get(i+3).getValue() - 1) {
							String color1 = playerHand.get(i).getColour();
							String color2 = playerHand.get(i+1).getColour();
							String color3 = playerHand.get(i+2).getColour();
							if((color1 == color2) && (color2 == color3)) {
								return true;
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

		for(int i=0; i<playerHand.size(); i++) {
			if(i != playerHand.size() - 2) {
				int handValue = playerHand.get(i).getValue();
				if(handValue == playerHand.get(i+1).getValue()) {
					if(handValue == playerHand.get(i+2).getValue()) {
						String color = playerHand.get(i).getColour();
						String color2 = playerHand.get(i+1).getColour();
						String color3 = playerHand.get(i+2).getColour();
						if((color != color2) && (color != color3) && (color2 != color3)) {
							return true;
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
		
		return false;
	}
	
   //new method to test #of melds
	public int numberOfMelds() {
		
		int numMelds = 0;
		Collections.sort(playerHand, new SortByValue());
		
		for(int i=0; i<playerHand.size(); i++) {
			if(i < playerHand.size() - 2) {
				if(playerHand.get(i).getValue() == playerHand.get(i+1).getValue() - 1) {
					if(playerHand.get(i+1).getValue() == playerHand.get(i+2).getValue() - 1) {
						if(playerHand.get(i+2).getValue() == playerHand.get(i+3).getValue() - 1) {
							String color1 = playerHand.get(i).getColour();
							String color2 = playerHand.get(i+1).getColour();
							String color3 = playerHand.get(i+2).getColour();
							if((color1 == color2) && (color2 == color3)) {
								numMelds = numMelds+1;
								i = i+3;
							}
						}
					}
				}
			}
			//System.out.println("i = "+i);
		}	



		for(int i=0; i<playerHand.size(); i++) {
			if(i < playerHand.size() - 2) {
				int handValue = playerHand.get(i).getValue();
				if(handValue == playerHand.get(i+1).getValue()) {
					if(handValue == playerHand.get(i+2).getValue()) {
						String color = playerHand.get(i).getColour();
						String color2 = playerHand.get(i+1).getColour();
						String color3 = playerHand.get(i+2).getColour();
						if((color != color2) && (color != color3) && (color2 != color3)) {
							numMelds = numMelds+1;
							i = i+3;
						}
					}
				}
			}
		}
		
		
		
		return numMelds;
	}
	
	//method to check if meld is more then 30
	public boolean meldPoints() {
		Collections.sort(playerHand, new SortByValue());

		for(int i=0; i<playerHand.size(); i++) {
			if(i != playerHand.size() - 2) {
				if(playerHand.get(i).getValue() == playerHand.get(i+1).getValue() - 1) {
					if(playerHand.get(i+1).getValue() == playerHand.get(i+2).getValue() - 1) {
						if(playerHand.get(i+2).getValue() == playerHand.get(i+3).getValue() - 1) {
							String color1 = playerHand.get(i).getColour();
							String color2 = playerHand.get(i+1).getColour();
							String color3 = playerHand.get(i+2).getColour();
							if((color1 == color2) && (color2 == color3)) {							
								if(playerHand.get(i).getValue() + playerHand.get(i+1).getValue() +  playerHand.get(i+2).getValue() >= 30) {
									return true;
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
					continue;
				}
			}
			else {
				break;
			}
		}

		for(int i=0; i<playerHand.size(); i++) {
			if(i != playerHand.size() - 2) {
				int handValue = playerHand.get(i).getValue();
				if(handValue == playerHand.get(i+1).getValue()) {
					if(handValue == playerHand.get(i+2).getValue()) {
						String color = playerHand.get(i).getColour();
						String color2 = playerHand.get(i+1).getColour();
						String color3 = playerHand.get(i+2).getColour();
						if((color != color2) && (color != color3) && (color2 != color3)) {
							if(playerHand.get(i).getValue() + playerHand.get(i+1).getValue() +  playerHand.get(i+2).getValue() >= 30) {
								
								return true;
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
		
		return false;
	}


	public boolean isSortedByValue() {
		for(int i=0;i<this.size-1;i++) {
			if(!(playerHand.get(i).getValue()<=playerHand.get(i+1).getValue())) {
				return false;
			}
		}
		return true;
	}

	public boolean isSortedByColor() {
		boolean redComplete = false;
		boolean blueComplete = false;
		boolean greenComplete = false;
		boolean orangeComplete = false;
		for(int i=0; i<redHand.size(); i++) {
			if(redHand.get(i).getColour() != "Red") {
				return false;
			}
			redComplete = true;
		}
		for(int i=0; i<blueHand.size(); i++) {
			if(blueHand.get(i).getColour() != "Blue") {
				return false;
			}
			blueComplete = true;
		}
		
		for(int i=0; i<greenHand.size(); i++) {
			if(greenHand.get(i).getColour() != "Green") {
				return false;
			}
			greenComplete = true;
		}
		
		for(int i=0; i<orangeHand.size(); i++) {
			if(orangeHand.get(i).getColour() != "Orange") {
				return false;
			}
			orangeComplete = true;
		}
		
		
		if(orangeComplete && greenComplete && blueComplete && redComplete) {
			return true;
		}
		return false;
		
	}

	public boolean isSorted() {
		for(int i=0; i<playerHand.size(); i++) {
			if(playerHand.get(i).getColour() == "Red" && playerHand.get(i+1).getColour() == "Red") {
				if(playerHand.get(i).getValue()>playerHand.get(i+1).getValue()) {
					return false;
				}
				continue;
			}
			if(playerHand.get(i).getColour() == "Blue" && playerHand.get(i+1).getColour() == "Blue") {
				if(playerHand.get(i).getValue()>playerHand.get(i+1).getValue()) {
					return false;
				}
				continue;
			}
			if(playerHand.get(i).getColour() == "Green" && playerHand.get(i+1).getColour() == "Green") {
				if(playerHand.get(i).getValue()>playerHand.get(i+1).getValue()) {
					return false;
				}
				continue;
			}
			if(playerHand.get(i).getColour() == "Orange" && playerHand.size() != i+1 && playerHand.get(i+1).getColour() == "Orange") {
				if(playerHand.get(i).getValue()>playerHand.get(i+1).getValue()) {
					return false;
				}
			}
			
		}

		return true;

	}
	
	public Tile getTile(String colour, int value) {
		for(int i=0; i<playerHand.size();i++) {
			if((playerHand.get(i).getColour() == colour)&& (playerHand.get(i).getValue() == value)) {
				return playerHand.get(i);
			}
		}
		return null;
	}
	
	public Tile getTile(String tileName) {
		for(int i=0; i<playerHand.size();i++) {
			if(playerHand.get(i).tileToString().equals(tileName)){
				return playerHand.get(i);
			}
		}
		return null;
	}
	
	
	public Tile getTile(int i) {
		return playerHand.get(i);
	}
	
	public Tile playTile(String colour, int value) {
		Tile tile = null;

		for(int i=0; i<playerHand.size(); i++) {
			if((playerHand.get(i).getColour() == colour) && (playerHand.get(i).getValue() == value)) {
				tile = playerHand.get(i);
				playerHand.remove(i);
				this.size--;
				break;
			}
		}
		
		return tile;
	}
	
	public boolean isTileInHand(Tile tile) {
		for(int i=0; i<playerHand.size(); i++) {
			if(tile == playerHand.get(i)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String handToString() {
		String h = "";
		
		for (int i = 0; i < playerHand.size(); i++) {
			h += this.getTile(i).tileToString();
			if (i < playerHand.size()) { h += " "; }
		}
		
		return h;
	}
	
	
	public void createMeld() {
		Meld meld = new Meld();
		melds.add(meld);
		numberOfMelds++;
	}

	public Meld getMeld(int i) {
		return melds.get(i);
	}
	
	public int getNumberOfMelds() {
		return numberOfMelds;
	}
	
	public void addTile(Tile tile) {
		playerHand.add(tile);
		this.size++;
	}
	

	public void removeTile(Tile tile) {
		playerHand.remove(tile);
		this.size--;

	}
	
	public Tile removeFromHand(int i) {
		Tile tile = null;
		
		if(this.getNumTiles() > 0) {
			tile = playerHand.remove(i);
			this.size--;
		}
		
		return tile;
	}
	
}
