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
		hand.dealTile(deck);
		
		
		assertTrue("true", hand.isTileDealt(deck));
	}
	
	public void testSortByValue() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		deck.shuffleTiles();
		hand.createHand(deck);
		
		hand.sortByValue(hand.getPlayerHand());
		
		assertTrue("true", hand.isSortedByValue());
		
		
		
	}
	
	public void testSortByColor() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		deck.shuffleTiles();
		hand.createHand(deck);
		
		hand.sortByColor();
		
		assertTrue("true", hand.isSortedByColor());
		
	}
	
	public void testSort() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		deck.shuffleTiles();
		hand.createHand(deck);
		
		hand.sortHand();
		
		assertTrue("true", hand.isSorted());
		
	}
	
	
	
}
