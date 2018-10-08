import java.util.ArrayList;

public class Hand {
	private ArrayList<Tile> playerHand;
	private int size;
	
	public Hand() {
		playerHand = new ArrayList<Tile>();
		size = 0;
		createHand();
	}
	
	private void createHand() {
		for(int i=0; i<14; i++) {
			//Need to access ArrayList in the Deck
			//no getter in Deck class to access it
		}
		
		size = playerHand.size();
	}
	
	public int getNumTiles() {
		return size;
	}
}
