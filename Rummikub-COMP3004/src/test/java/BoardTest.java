
public class BoardTest {
	public void testDeckExists() {
		Board board = new Board();
		assertNotNull(board);	
	}
	
	public void testAddTile() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand playerHand = new Hand();
		playerHand.createHand(deck);
		String colour = playerHand.getTile(1).getColour();
		int value = playerHand.getTile(1).getValue();
		board.add(playerHand.getTile(colour, value));
		
		assertEquals(1, getNumberOfBoardTiles());		
	} 
	
	public void testGetBoardSize() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand playerHand = new Hand();
		playerHand.createHand(deck);
		String colour = playerHand.getTile(1).getColour();
		int value = playerHand.getTile(1).getValue();
		board.add(playerHand.getTile(colour, value), 1, 1);
		
		assertEquals(1, getNumberOfBoardTiles());		
	
	}
	
	public void testIsLocationFilled() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		int xLocation = 1;
		int yLocation = 1;		
		board.add(hand.getTile(1), xLocation, yLocation);

		assertTue("true", isLocationFilled(xLocation, yLocation));		
	}
	
	public void testMoveTiles() {
		Board board = new Board();
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		int xLocation = 1;
		int yLocation = 1;		
		board.add(hand.getTile(1), xLocation, yLocation);
		int newX = 2;
		int newY = 2;
		board.move(board.getTile(xLocation,yLocation), newX, newY);
		
		assertTrue("true", hasTileMoved(board.getTile(xLocation,yLocation)));
		
	}
}
