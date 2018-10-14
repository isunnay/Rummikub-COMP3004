import junit.framework.TestCase;

public class GameTest extends TestCase {
	public void testGameInitialization() {
		// Create a game object
		Game g = new Game();
		
		// Check if the game is in progress
		assertTrue(g.inProgress());
	}
	
	public void testPlayerCount() {
		// Create a game object
		Game g = new Game();
				
		// Check if there are 4 players
		assertEquals(4, g.getPlayerCount());
	}
	
	public void testDeckExists() {
		// Create a game object
		Game g = new Game();
						
		// Check if there's a deck
		assertNotNull(g.getDeck());
	}
	
	public void testHandDraw() {
		// Create a game object
		Game g = new Game();
								
		// Check if first player has 14 tiles
		assertEquals(14, g.getPlayer(0).getNumTiles());
		
		// Check if second player has 14 tiles
		assertEquals(14, g.getPlayer(1).getNumTiles());
		
		// Check if third player has 14 tiles
		assertEquals(14, g.getPlayer(2).getNumTiles());
				
		// Check if fourth player has 14 tiles
		assertEquals(14, g.getPlayer(3).getNumTiles());
	}

}
