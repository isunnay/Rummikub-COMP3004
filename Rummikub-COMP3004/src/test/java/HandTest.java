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
	
	public void testNewTileDeal() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		
		hand.createHand(deck);
		
		boolean meld = hand.meldExists();
		
		assertEquals(!meld, hand.dealNewTile(deck));
	}
	
	public void testTileDealt() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		
		hand.createHand(deck);
		
		int num = hand.getNumTiles();
		
		assertEquals(num + 1, hand.dealTile(deck));
	}
}
