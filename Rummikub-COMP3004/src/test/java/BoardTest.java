import junit.framework.TestCase;

public class BoardTest extends TestCase {
	
	public void testBoardExists() {
		Board board = new Board();
		assertNotNull(board);	
	}
	
	public void testPlayTile() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand playerHand = new Hand();
		playerHand.createHand(deck);
		String colour = playerHand.getTile(1).getColour();
		int value = playerHand.getTile(1).getValue();
		board.playTile(playerHand.getTile(colour, value), x, y);
		
		assertEquals(1, getNumberOfBoardTiles());		
	} 
	
	public void testGetBoardNumberOfTiles() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand playerHand = new Hand();
		playerHand.createHand(deck);
		String colour = playerHand.getTile(1).getColour();
		int value = playerHand.getTile(1).getValue();
		board.playTile(playerHand.getTile(colour, value), 1, 1);
		
		assertEquals(1, getNumberOfBoardTiles());		
	}
	
	public void testBoardSize() {
		Board board = new Board();
		int x = board.getX();
		int y = board.getY();
		
		assertEquals(15, x+y);
	}
	
	public void testIsLocationFilled() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		int xLocation = 1;
		int yLocation = 1;		
		board.playTile(hand.getTile(1), xLocation, yLocation);

		assertTue("true", isSpotFilled(xLocation, yLocation));		
	}
	
	public void testMoveTiles() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		int xLocation = 1;
		int yLocation = 1;		
		board.playTile(hand.getTile(1), xLocation, yLocation);
		int newX = 2;
		int newY = 2;
		board.moveTile(board.getTileAtSpot(xLocation,yLocation), newX, newY);
		assertTrue("true", hasTileMoved(board.getTile(xLocation,yLocation)));	
	}
	
	public void testIsValidMeld() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		//boolean isValidMeld = false;
		board.playTile(hand.getTile(0), 0, 0);
		board.playTile(hand.getTile(1), 1, 0);
		board.playTile(hand.getTile(2), 2, 0);
		
		assertTrue("true", board.isValidMeld(2));	
	}
	
	public void testIsBoardValidTrue() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		board.playTile(hand.getTile(0), 0, 0);
		board.playTile(hand.getTile(1), 1, 0);
		board.playTile(hand.getTile(2), 2, 0);
		
		assertTrue("true",board.isBoardValid());		
	}
	
	public void testIsBoardValidFalse() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		board.playTile(hand.getTile(0), 0, 1);
		board.playTile(hand.getTile(1), 1, 10);
		board.playTile(hand.getTile(2), 5, 6);
		
		assertFalse("false",board.isBoardValid());		
	}
	
	public void testRemoveTile() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand playerHand = new Hand();
		playerHand.createHand(deck);
		String colour = playerHand.getTile(1).getColour();
		int value = playerHand.getTile(1).getValue();
		int x = 5; int y = 5;
		board.playTile(playerHand.getTile(colour, value), x, y);
		
		board.removeTile(x,y);
	}
}
