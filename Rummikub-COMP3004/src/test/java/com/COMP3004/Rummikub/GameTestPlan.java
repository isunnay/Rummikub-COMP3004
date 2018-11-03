package com.COMP3004.Rummikub;
import junit.framework.TestCase;

public class GameTestPlan extends TestCase {
	public void testGame1() {
		Game game = new Game();
		Board board = game.getBoard();
		
		//14 tiles requirement
		for(int i=0; i<game.getAllPlayers().size(); i++) {
			assertEquals(14, game.getPlayer(i).getHand().getNumTiles());
		}
	}
	
	public void testGame2() {
		Game game = new Game();
		Human human = (Human)game.getAllPlayers().get(0);
		AI1 ai1 = (AI1)game.getAllPlayers().get(1);
		AI2 ai2 = (AI2)game.getAllPlayers().get(2);
		AI3 ai3 = (AI3)game.getAllPlayers().get(3);
		
		//Check who's turn it is
		//Play in order: Human, AI1, AI2, then AI3
		//Human goes first
		human.setTurnStatus(true);
		assertTrue(human.myTurnStatus());
		assertFalse(ai1.myTurnStatus());
		assertFalse(ai2.myTurnStatus());
		assertFalse(ai3.myTurnStatus());
		//human draws tile
		human.getHand().dealTile(game.getDeck());
		
		//AI1 goes second
		human.setTurnStatus(false);
		ai1.setTurnStatus(true);
		assertTrue(ai1.myTurnStatus());
		assertFalse(human.myTurnStatus());
		assertFalse(ai2.myTurnStatus());
		assertFalse(ai3.myTurnStatus());
		//ai1 draws a tile
		ai1.getHand().dealTile(game.getDeck());
		
		//AI2 goes third
		ai1.setTurnStatus(false);
		ai2.setTurnStatus(true);
		assertTrue(ai2.myTurnStatus());
		assertFalse(human.myTurnStatus());
		assertFalse(ai3.myTurnStatus());
		assertFalse(ai1.myTurnStatus());
		//ai2 draws a tile - no tile was placed
		ai2.getHand().dealTile(game.getDeck());
		
		//AI3 goes last
		ai2.setTurnStatus(false);
		ai3.setTurnStatus(true);
		assertTrue(ai3.myTurnStatus());
		assertFalse(human.myTurnStatus());
		assertFalse(ai2.myTurnStatus());
		assertFalse(ai1.myTurnStatus());
		//ai3 draws a tile
		ai3.getHand().dealTile(game.getDeck());
	}
	
	public void testGame3() {
		Game game = new Game();
		Human human = (Human)game.getAllPlayers().get(0);
		AI1 ai1 = (AI1)game.getAllPlayers().get(1);
		AI2 ai2 = (AI2)game.getAllPlayers().get(2);
		AI3 ai3 = (AI3)game.getAllPlayers().get(3);
		
		
	}
}
