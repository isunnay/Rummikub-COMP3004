import junit.framework.TestCase;

public class HandTest extends TestCase {
	public void testHandExists() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		
		hand.createHand(deck);
		
		assertNotNull(hand);
	}
	
	public void testNumTiles() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		
		hand.createHand(deck);
		
		assertEquals(14, hand.getNumTiles());
	}
	
	public void testMeld() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		
		hand.createHand(deck);
		
		assertTrue("true", hand.meldExists());
	}
}
