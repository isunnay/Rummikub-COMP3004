import java.util.ArrayList;
import java.util.Collections;



public class Deck {
	private ArrayList<Tile> tileDeck;
	private int size;
	
	public Deck() {
		tileDeck = new ArrayList<Tile>();
		generateDeck();
		generateDeck();
	}
	
	private void generateDeck() {
		for(int colour=0;colour<4;colour++) {
			for(int value=0;value<13;value++) {
				Tile tile = new Tile(colour,value);
				tileDeck.add(tile);
			}
		}
	}

	public int getDeckCount() {
		size = tileDeck.size();
		return size;
		
	}

	public boolean doesEveryTileExist(int count) {
		int counter = count;
		for(int i=0;i<4;i++) {
			for(int x=0;x<13;x++){
				Tile tile = new Tile(i,x);
				String aTileName = tileDeck.get(counter).getColour()+ tileDeck.get(counter).getValue();
				Boolean bool = tile.toString(i,x).equals(aTileName);
				if(bool == false) {
					return false;
				}
				counter++;
				}
			}
		doesEveryTileExist(53);
		return true;
		}
	
	public void shuffleTiles() {
		Collections.shuffle(tileDeck);
	}

	public boolean isDeckShuffled(int count) {
		int counter = count;
		for(int i=0;i<4;i++) {
			for(int x=0;x<13;x++){
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
