import junit.framework.TestCase;

public class DeckTest extends TestCase {
	public void testDeckExists() {
		Deck deck = new Deck();
		AssertNotNull(deck);	
	}
	
	public void testNumberOfTiles() {
		Deck deck = new Deck();
		assertEquals(104, deck.getDeckCount());	
	}
	

	public void testEveryTileExists(){
		Deck deck = new Deck();
		assertTrue("true",deck.doesEveryTileExist());		
	}
	
	public void testIsDeckShuffled() {
		Deck deck = new Deck();
		deck.shuffleTiles();
		assertTrue("true",deck.isDeckShuffled());
	}
}
