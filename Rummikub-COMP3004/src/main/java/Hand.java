import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand {
	private ArrayList<Tile> playerHand;
	private int size;
	
	public Hand() {
		playerHand = new ArrayList<Tile>();;
		size = 0;
	}
	
	public void createHand(Deck deck) {
		for(int i=0; i<14; i++) {
			playerHand.add(deck.getTileDeck().remove(deck.getDeckCount()- 1));
			size++;
		}
		
		
		//size = playerHand.size();
	}
	
	public boolean dealNewTile(Deck deck) {
		if(!meldExists()) {
			dealTile(deck);
			return true;
		}
		
		return false;
	}
	
	//dealTile() method returns size of hand after a new tile is dealt to it
	public int dealTile(Deck deck) {
		playerHand.add(deck.getTileDeck().remove(deck.getDeckCount() - 1));
		size++;
		
		return size;
	}
	
	public int getNumTiles() {
		return size;
	}
	
	public class SortByValue implements Comparator<Tile> {
		public int compare(Tile x, Tile y) {
			return x.getValue() - y.getValue();
		}
	}
	
	public ArrayList<Tile> getPlayerHand() {
		return playerHand;
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
}
