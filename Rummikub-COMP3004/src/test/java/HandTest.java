import junit.framework.TestCase;

public class HandTest extends TestCase {
	public void testHandExists() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		
		hand.createHandFromDeck(deck);
		
		assertNotNull(hand);
	}
	
	public void testNumTiles() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		
		hand.createHandFromDeck(deck);
		
		assertEquals(14, hand.getNumTiles());
	}
	
	public void testMeld() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		
		hand.createHandFromDeck(deck);
		
		assertTrue("true", hand.meldExists());
	}
}
