import junit.framework.TestCase;

public class SpotTest extends TestCase{
	
	public void testSpotExists() {
		Spot spot = new Spot();
		assertNotNull(spot);
	}
	
	public void testGetSpotX() {
		Board board = new Board();
		Spot spot = board.getSpot(10,10);
		
		assertEquals(10, spot.getSpotX());
	}
	
	public void testGetSpotY() {
		Board board = new Board();
		Spot spot = board.getSpot(10,10);
		
		assertEquals(10, spot.getSpotY());
	}
	
	public void testIsEmptySpot() {
		Board board = new Board();
		Spot spot = board.getSpot(0,0);
		
		assertTrue("true", spot.isSpotEmpty());	
	}
	
	public void testOccupySpot() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		Spot spot = board.getSpot(10,10);
		spot.playTile(hand.getTile(0));
		
		assertFalse("false",spot.isSpotEmpty());
	}
	
	public void testFreeSpot() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		Spot spot = board.getSpot(10,10);
		spot.playTile(hand.getTile(0));
		spot.removeTile();
		
		assertTrue("true", spot.isSpotEmpty());	
	}
	


}
