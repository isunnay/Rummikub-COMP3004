package com.COMP3004.Rummikub;

import com.COMP3004.Rummikub.models.Game;

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
		assertEquals(14, g.getPlayer(0).getHand().getNumTiles());
		
		// Check if second player has 14 tiles
		assertEquals(14, g.getPlayer(1).getHand().getNumTiles());
		
		// Check if third player has 14 tiles
		assertEquals(14, g.getPlayer(2).getHand().getNumTiles());
				
		// Check if fourth player has 14 tiles
		assertEquals(14, g.getPlayer(3).getHand().getNumTiles());
	}
	
	public void testWinner() {
		// Create a game object
		Game g = new Game();
		
		// Check if no one has won yet (returns 0)
		assertEquals(0, g.getWinner());
		
		// "Play" all 14 tiles for first players hand
		//for (int i = 0; i < 14; i++) {
		//	g.getPlayer(0).playTile(); // ??
		//}
		// Check if first player won (returns 1)
		assertEquals(1, g.getWinner());
	}

}
