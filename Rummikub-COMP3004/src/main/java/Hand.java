import java.util.ArrayList;

public class Hand {
	private Deck deckToAccess;
	private ArrayList<Tile> playerHand;
	private int size;
	
	public Hand() {
		playerHand = new ArrayList<Tile>();
		deckToAccess = new Deck();
		size = 0;
		createHand();
	}
	
	private void createHand() {
		for(int i=0; i<14; i++) {
			playerHand.add(deckToAccess.getTileDeck().remove(deckToAccess.getDeckCount()- 1));
		}
		
		size = playerHand.size();
	}
	
	public int getNumTiles() {
		return size;
	}
}
