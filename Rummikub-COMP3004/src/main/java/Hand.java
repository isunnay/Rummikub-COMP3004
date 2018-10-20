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
	
	public int size;
	
	public Hand() {
		playerHand = new ArrayList<Tile>();
		greenHand = new ArrayList<Tile>();
		blueHand = new ArrayList<Tile>();
		redHand = new ArrayList<Tile>();
		orangeHand = new ArrayList<Tile>();
		sortedHand = new ArrayList<Tile>();

	}
	
	public void createHand(Deck deck) {
		for(int i=0; i<14; i++) {
			playerHand.add(deck.getTileDeck().remove(deck.getDeckCount()- 1));
			size++;
		}
		
		
		//size = playerHand.size();
	}
	
	public boolean isTileDealt(Deck deck) {
		if(this.getNumTiles() > 14) {
			return true;
		}
		
		return false;
	}
	
	//dealTile() method returns size of hand after a new tile is dealt to it
	public void dealTile(Deck deck) {
		playerHand.add(deck.getTileDeck().remove(deck.getDeckCount() - 1));
		this.size++;
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
		
		for(int i=0; i<this.size; i++) {
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
		this.sortByColor();
		
		this.sortByValue(greenHand);
		this.sortByValue(redHand);
		this.sortByValue(blueHand);
		this.sortByValue(orangeHand);
		
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
	
	public Tile getTile(int i) {
		return playerHand.get(i);
	}
}
