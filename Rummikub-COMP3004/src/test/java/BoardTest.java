
public class BoardTest {
	public void testDeckExists() {
		Board board = new Board();
		assertNotNull(board);	
	}
	
	public void testAddTile() {
		Board board = new Board();
		Hand playerHand = new Hand();
		
		board.add(playerHand.meld);
	}

}
