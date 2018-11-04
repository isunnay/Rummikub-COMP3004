package com.COMP3004.Rummikub;
import junit.framework.TestCase;
import java.util.ArrayList;

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
		
		//Tiles are organized by color and value
		ArrayList<PlayerType> players = game.getAllPlayers();
		for(int i=0; i<players.size(); i++) {
			//displaying the hand
			assertNotNull(players.get(i).getHand().handToString());
			for(int j=0; j<players.get(i).getHand().getNumTiles() - 1; j++) {
				if(players.get(i).getHand().getTile(j).getColour() == players.get(i).getHand().getTile(j+1).getColour()) {
					//System.out.println(players.get(i).getHand().getTile(j).getValue() + ", " + players.get(i).getHand().getTile(j+1).getValue());
					assertTrue(players.get(i).getHand().getTile(j).getValue() <= players.get(i).getHand().getTile(j+1).getValue());
				}
			}
		}
	}
	
	public void testGame4() {
		Game game = new Game();
		Human human = (Human)game.getAllPlayers().get(0);
		AI1 ai1 = (AI1)game.getAllPlayers().get(1);
		AI2 ai2 = (AI2)game.getAllPlayers().get(2);
		AI3 ai3 = (AI3)game.getAllPlayers().get(3);
		
		String[] colours = {"Red", "Green", "Blue"};
		
		//Rigging to get a meld equal to 30 and greater than 30 for human player
		for(int i=0; i<6; i++) {
			human.getHand().removeFromHand(human.getHand().getNumTiles() - 1);
		}
		for(int i=0; i<3; i++) {
			human.getHand().addTile(new Tile(colours[i], 10));
		}
		for(int i=0; i<3; i++) {
			human.getHand().addTile(new Tile(colours[0], i + 10));
		}
		
		human.setTurnStatus(true);
		//having two melds to play
		human.getHand().createMeld();
		human.getHand().createMeld();
		human.getHand().getMeld(0).addTile(human.getHand().removeFromHand(human.getHand().getNumTiles() - 3));
		human.getHand().getMeld(0).addTile(human.getHand().removeFromHand(human.getHand().getNumTiles() - 2));
		human.getHand().getMeld(0).addTile(human.getHand().removeFromHand(human.getHand().getNumTiles() - 1));
		/*for(int i=0; i<human.getHand().getMeld(0).getNumberOfTiles(); i++) {
			System.out.println(human.getHand().getMeld(0).getTileInMeld(i).getTile().tileToString());
		}*/
		assertTrue(human.getHand().getMeld(0).checkIfValidMeld());
		assertTrue(human.getHand().getMeld(0).isValidRun());
		
		int sum = 0;
		for(int i=0; i<human.getHand().getMeld(0).getNumberOfTiles(); i++) {
			sum += human.getHand().getMeld(0).getTileInMeld(i).getValue();
		}
		//The meld to be played is greater than 30
		assertTrue(sum > 30);
		
		human.getHand().getMeld(1).addTile(human.getHand().removeFromHand(human.getHand().getNumTiles() - 1));
		human.getHand().getMeld(1).addTile(human.getHand().removeFromHand(human.getHand().getNumTiles() - 1));
		human.getHand().getMeld(1).addTile(human.getHand().removeFromHand(human.getHand().getNumTiles() - 1));
		for(int i=0; i<human.getHand().getMeld(1).getNumberOfTiles(); i++) {
			System.out.println(human.getHand().getMeld(1).getTileInMeld(i).getTile().tileToString());
		}
		
		//assertTrue(human.getHand().getMeld(1).checkIfValidMeld());
		//assertTrue(human.getHand().getMeld(1).isValidSet());
		
		int sum2 = 0;
		for(int i=0; i<human.getHand().getMeld(1).getNumberOfTiles(); i++) {
			sum2 += human.getHand().getMeld(1).getTileInMeld(i).getValue();
		}
		//The meld to be played is equal to 30
		assertTrue(sum == 30);
		
		assertEquals(2, human.getHand().getNumberOfMelds());
	}
}