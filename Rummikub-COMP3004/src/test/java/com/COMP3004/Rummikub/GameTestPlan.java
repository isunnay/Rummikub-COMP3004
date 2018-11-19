package com.COMP3004.Rummikub;
import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Scanner;

public class GameTestPlan extends TestCase {
	public void testGame1() {
		Game game = new Game();
		Board board = game.getBoard();
		
		//System.out.println(game.getAllPlayers().get(0).getHand().getNumTiles());
		int size = game.getAllPlayers().get(0).getHand().getNumTiles();
		
		for(int j=0; j<size; j++) {
			game.getAllPlayers().get(0).getHand().removeFromHand(game.getAllPlayers().get(0).getHand().getNumTiles() - 1);
		}
		
		//System.out.println(game.getAllPlayers().get(0).getHand().getNumTiles());
		
		Tile[] humanTiles = {new Tile("R", 1), new Tile("R", 2), new Tile("R", 3), new Tile("R", 4), new Tile("O", 11), new Tile("O", 12),
				new Tile("O", 13), new Tile("O", 14), new Tile("G", 6), new Tile("G", 7), new Tile("G", 8), new Tile("G", 9),
				new Tile("G", 10), new Tile("G", 11)};
		Tile[] ai1Tiles = {new Tile("R", 12), new Tile("R", 13), new Tile("R", 17), new Tile("B", 2), new Tile("B", 4), new Tile("B", 5),
				new Tile("O", 9), new Tile("O", 12), new Tile("O", 13), new Tile("G", 5), new Tile("G", 6), new Tile("G", 11),
				new Tile("G", 13), new Tile("G", 14)};
		Tile[] ai2Tiles = {new Tile("R", 1), new Tile("R", 5), new Tile("B", 1), new Tile("B", 2), new Tile("B", 3), new Tile("B", 6),
				new Tile("B", 7), new Tile("O", 2), new Tile("O", 4), new Tile("O", 5), new Tile("O", 7), new Tile("O", 8),
				new Tile("G", 1), new Tile("G", 2)};
		
		Tile[] ai3Tiles = {new Tile("R", 9), new Tile("R", 11), new Tile("R", 12), new Tile("B", 10), new Tile("B", 11), new Tile("B", 13),
				new Tile("B", 14), new Tile("O", 3), new Tile("O", 4), new Tile("O", 5), new Tile("O", 6), new Tile("Green", 4),
				new Tile("G", 5), new Tile("G", 6)};
		
		for(int i=0; i<humanTiles.length; i++) {
			game.getAllPlayers().get(0).getHand().addTile(humanTiles[i]);
		}
		//System.out.println(game.getAllPlayers().get(0).getHand().getNumTiles());
		for(int i=0; i<ai1Tiles.length; i++) {
			game.getAllPlayers().get(1).getHand().addTile(ai1Tiles[i]);
		}
		for(int i=0; i<ai2Tiles.length; i++) {
			game.getAllPlayers().get(2).getHand().addTile(ai2Tiles[i]);
		}
		/*for(int i=0; i<ai3Tiles.length; i++) {
			game.getAllPlayers().get(3).getHand().addTile(ai3Tiles[i]);
		}*/
		Human human = (Human)game.getAllPlayers().get(0);
		AI1 ai1 = (AI1)game.getAllPlayers().get(1);
		AI2 ai2 = (AI2)game.getAllPlayers().get(2);
		//AI3 ai3 = (AI3)game.getAllPlayers().get(3);
		
		for(int i=0; i<game.getAllPlayers().size(); i++) {
			//Req. 1.) The initial 14 tiles of each player can be displayed
			assertNotNull(game.getAllPlayers().get(i).getHand().handToString());
		}
		
		//Tiles are organized by color and value
		ArrayList<PlayerType> players = game.getAllPlayers();
		for(int i=0; i<players.size(); i++) {
			for(int j=0; j<players.get(i).getHand().getPlayerHand().size() - 1; j++) {
				if(players.get(i).getHand().getTile(j).getColour() == players.get(i).getHand().getTile(j+1).getColour()) {
					//Req. 1.)The hand of each player should be organized by color and for each color ascending in value
					assertTrue(players.get(i).getHand().getTile(j).getValue() <= players.get(i).getHand().getTile(j+1).getValue());
				}
			}
		}
		
		//Req. 2.) human player plays first
		human.setTurnStatus(true);
		assertTrue(human.myTurnStatus());
		assertFalse(ai1.myTurnStatus());
		assertFalse(ai2.myTurnStatus());
		//assertFalse(ai3.myTurnStatus());
		
		human.getHand().createMeld();
		human.getHand().createMeld();
		human.getHand().createMeld();
		for(int i=0; i<4; i++) {
			human.getHand().getMeld(0).addTile(human.getHand().getTile(i));
		}
		for(int i=4; i<8; i++) {
			human.getHand().getMeld(1).addTile(human.getHand().getTile(i));
		}
		for(int i=8; i<14; i++) {
			human.getHand().getMeld(2).addTile(human.getHand().getTile(i));
		}
		//Req. 8a.) Current player can play one meld
		assertTrue(human.getHand().getMeld(0).isValidRun());
		assertTrue(human.getHand().getMeld(0).checkIfValidMeld());
		assertTrue(human.getHand().getMeld(1).checkIfValidMeld());
		assertTrue(human.getHand().getMeld(2).checkIfValidMeld());
		assertEquals(3, human.getHand().getNumberOfMelds());
		//Req. 4.) Player plays a meld of more than 30
		assertTrue(human.getHand().getMeld(1).getMeldValue() > 30);
		
		//Req. 8c.) Current player can play several runs
		assertTrue(human.getHand().getMeld(1).isValidRun() && human.getHand().getMeld(2).isValidRun());
		
		int size2 = human.getHand().getPlayerHand().size();
		for(int i=0; i<size2; i++) {
			game.getAllPlayers().get(0).getHand().removeFromHand(game.getAllPlayers().get(0).getHand().getNumTiles() - 1);
		}
		//System.out.println(game.getAllPlayers().get(0).getHand().getNumTiles());
		
		//Req. 4c.) Player wins on first turn
		assertEquals(1, game.getWinner());
	}
	
	public void testGame2() {
		Game game = new Game();
		Human human = (Human)game.getAllPlayers().get(0);
		AI1 ai1 = (AI1)game.getAllPlayers().get(1);
		AI2 ai2 = (AI2)game.getAllPlayers().get(2);
		//AI3 ai3 = (AI3)game.getAllPlayers().get(3);
		
		//Check who's turn it is
		//Play in order: Human, AI1, AI2, then AI3
		//Req. 2.)Human goes first
		human.setTurnStatus(true);
		assertTrue(human.myTurnStatus());
		assertFalse(ai1.myTurnStatus());
		assertFalse(ai2.myTurnStatus());
		//assertFalse(ai3.myTurnStatus());
		//human draws tile
		Tile tile = game.getDeck().getTileDeck().get(game.getDeck().getTileDeck().size() - 1);
		assertEquals(tile, human.getHand().dealTile(game.getDeck()));
		assertTrue(human.getHand().isTileDealt(game.getDeck()));
		
		//Re1. 2.)AI1 goes second
		human.setTurnStatus(false);
		ai1.setTurnStatus(true);
		assertTrue(ai1.myTurnStatus());
		assertFalse(human.myTurnStatus());
		assertFalse(ai2.myTurnStatus());
		//assertFalse(ai3.myTurnStatus());
		//ai1 draws a tile
		ai1.getHand().dealTile(game.getDeck());
		//Req. 10e.) p1 drawas on first turn
		assertEquals(15, ai1.getHand().getNumTiles());
		
		//Req. 2.) AI2 goes third
		ai1.setTurnStatus(false);
		ai2.setTurnStatus(true);
		assertTrue(ai2.myTurnStatus());
		assertFalse(human.myTurnStatus());
		//assertFalse(ai3.myTurnStatus());
		assertFalse(ai1.myTurnStatus());
		//ai2 draws a tile - no tile was placed
		ai2.getHand().dealTile(game.getDeck());
		
		//Req. 2.) AI3 goes last - skipping AI3 for now
		/*ai2.setTurnStatus(false);
		ai3.setTurnStatus(true);
		assertTrue(ai3.myTurnStatus());
		assertFalse(human.myTurnStatus());
		assertFalse(ai2.myTurnStatus());
		assertFalse(ai1.myTurnStatus());
		//ai3 draws a tile
		ai3.getHand().dealTile(game.getDeck());*/
		
		ai2.setTurnStatus(false);
		human.setTurnStatus(true);
		assertTrue(human.myTurnStatus());
		assertFalse(ai1.myTurnStatus());
		assertFalse(ai2.myTurnStatus());
		
		human.getHand().dealTile(game.getDeck());
		
		human.setTurnStatus(false);
		ai1.setTurnStatus(true);
		assertTrue(ai1.myTurnStatus());
		assertFalse(human.myTurnStatus());
		assertFalse(ai2.myTurnStatus());
		//assertFalse(ai3.myTurnStatus());
		//ai1 draws a tile
		ai1.getHand().dealTile(game.getDeck());
		//Req. 10f.) p1 draws on subsequent turn
		assertEquals(16, ai1.getHand().getNumTiles());
	}
	
	public void testGame3() {
		Game game = new Game();
		
		Tile[] ai1Tiles = {new Tile("B", 9), new Tile("B", 10), new Tile("B", 11)};
		
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().removeFromHand(game.getAllPlayers().get(1).getHand().getNumTiles() - 1);
		}
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().addTile(ai1Tiles[i]);
		}
		
		AI1 ai1 = (AI1) game.getAllPlayers().get(1);
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		ai1.setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		game.getAllPlayers().get(0).getHand().dealTile(game.getDeck());
		
		ai1.setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		ai1.getHand().createMeld();
		
		for(int i=0; i<3; i++) {
			ai1.getHand().getMeld(0).addTile(ai1Tiles[i]);
		}
		
		//10a.) AI 1 can play a meld on the first turn
		assertTrue(ai1.oneMeldFirstTurn());
	}
	
	public void testGame4() {
		Game game = new Game();
		Human human = (Human)game.getAllPlayers().get(0);
		AI1 ai1 = (AI1)game.getAllPlayers().get(1);
		AI2 ai2 = (AI2)game.getAllPlayers().get(2);
		//AI3 ai3 = (AI3)game.getAllPlayers().get(3);
		
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
		human.getHand().getMeld(0).addTile(human.getHand().getTile(human.getHand().getNumTiles() - 3));
		human.getHand().getMeld(0).addTile(human.getHand().getTile(human.getHand().getNumTiles() - 2));
		human.getHand().getMeld(0).addTile(human.getHand().getTile(human.getHand().getNumTiles() - 1));
		/*for(int i=0; i<human.getHand().getMeld(0).getNumberOfTiles(); i++) {
			System.out.println(human.getHand().getMeld(0).getTileInMeld(i).getTile().tileToString());
		}*/
		
		//The meld to be played is greater than 30
		
		human.getHand().getMeld(1).addTile(human.getHand().getTile(human.getHand().getNumTiles() - 6));
		human.getHand().getMeld(1).addTile(human.getHand().getTile(human.getHand().getNumTiles() - 5));
		human.getHand().getMeld(1).addTile(human.getHand().getTile(human.getHand().getNumTiles() - 4));
		/*for(int i=0; i<human.getHand().getMeld(1).getNumberOfTiles(); i++) {
			System.out.println(human.getHand().getMeld(1).getTileInMeld(i).getTile().tileToString());
		}*/
		
		
		//Req. 4b2.) Player plays several meld greater than 30
		int points1 = human.getHand().getMeld(0).getMeldValue();
		int points2 = human.getHand().getMeld(1).getMeldValue();
		assertTrue(points1 + points2 > 30);
		
		//Req. 82.) Can play a mix of a run and a set
		assertTrue(human.getHand().getMeld(0).checkIfValidMeld());
		assertTrue(human.getHand().getMeld(0).isValidRun());
		assertTrue(human.getHand().getMeld(1).checkIfValidMeld());
		assertTrue(human.getHand().getMeld(1).isValidSet());
		
		assertTrue(human.canWePlaceMeld(human.getHand().getMeld(0), 1, 1));
		assertTrue(human.canWePlaceMeld(human.getHand().getMeld(1), 3, 3));
	}
	
	public void testGame5() {
		Game game = new Game();
		
		Tile[] ai1Tiles = {new Tile("B", 9), new Tile("B", 10), new Tile("B", 12)};
		
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().removeFromHand(game.getAllPlayers().get(1).getHand().getNumTiles() - 1);
		}
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().addTile(ai1Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		game.getAllPlayers().get(1).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		game.getAllPlayers().get(0).getHand().dealTile(game.getDeck());
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(1).getHand().addTile(new Tile("B", 11));

		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(1).setTurnStatus(false);
		game.getAllPlayers().get(2).getHand().dealTile(game.getDeck());
		
		//game.getAllPlayers().get(3).setTurnStatus(true);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).getHand().dealTile(game.getDeck());
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		game.getAllPlayers().get(0).getHand().dealTile(game.getDeck());
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(1).getHand().createMeld();
		
		game.getAllPlayers().get(1).getHand().getMeld(0).addTile(ai1Tiles[0]);
		game.getAllPlayers().get(1).getHand().getMeld(0).addTile(ai1Tiles[1]);
		game.getAllPlayers().get(1).getHand().getMeld(0).addTile(new Tile("B", 11));
		game.getAllPlayers().get(1).getHand().getMeld(0).addTile(ai1Tiles[2]);
		
		AI1 ai1 = (AI1)game.getAllPlayers().get(1);
		//Req. 10c.) can play one meld subsequent turn
		assertTrue(ai1.oneMeldSubsequentTurn());
	}
	
	public void testGame6() {
		Game game = new Game();
		Tile[] tiles = {new Tile("B", 5), new Tile("G", 5), new Tile("R", 5)};
		Tile[] tiles2 = {new Tile("B", 4), new Tile("B", 5), new Tile("B", 6)};
		
		for(int i=0; i<6; i++) {
			game.getAllPlayers().get(0).getHand().removeFromHand(game.getAllPlayers().get(0).getHand().getNumTiles() - 1);
		}
		for(int i=0; i<tiles.length; i++) {
			game.getAllPlayers().get(0).getHand().addTile(tiles[i]);
		}
		for(int i=0; i<tiles.length; i++) {
			game.getAllPlayers().get(0).getHand().addTile(tiles2[i]);
		}
		
		game.getAllPlayers().get(0).getHand().createMeld();
		game.getAllPlayers().get(0).getHand().createMeld();
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(0).getHand().getMeld(0).addTile(tiles[i]);
		}
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(0).getHand().getMeld(0).addTile(tiles2[i]);
		}
		
		//Req 4b2.) player plays several melds adding up to 30 points
		int value1 = game.getAllPlayers().get(0).getHand().getMeld(0).getMeldValue();
		int value2 = game.getAllPlayers().get(0).getHand().getMeld(1).getMeldValue();
		assertEquals(30, value1 + value2);
		
		for(int i=0; i<6; i++) {
			game.getAllPlayers().get(0).getHand().removeFromHand(game.getAllPlayers().get(0).getHand().getNumTiles() - 1);
		}
		
		Tile[] tiles3 = {new Tile("G", 6), new Tile("G", 7), new Tile("G", 8), new Tile("R", 7), new Tile("R", 8), new Tile("R", 9)};
		for(int i=0; i<tiles3.length; i++) {
			game.getAllPlayers().get(0).getHand().addTile(tiles3[i]);
		}
		
		game.getAllPlayers().get(0).getHand().createMeld();
		game.getAllPlayers().get(0).getHand().createMeld();
		
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(0).getHand().getMeld(2).addTile(tiles3[i]);
		}
		for(int i=3; i<5; i++) {
			game.getAllPlayers().get(0).getHand().getMeld(3).addTile(tiles3[i]);
		}
		
		//Req. 4b2.) player plays several melds for more than 30 points
		value1 = game.getAllPlayers().get(0).getHand().getMeld(2).getMeldValue();
		value2 = game.getAllPlayers().get(0).getHand().getMeld(3).getMeldValue();
		assertTrue(value1 + value2 > 30);
	}
	
	public void testGame7() {
		Game game = new Game();
		Deck deck = new Deck();
		
		Tile tile1 = new Tile("B", 9);
		Tile tile2 = new Tile("B", 10);
		Tile tile3 = new Tile("B", 11);
		//These 3 tiles are a run
		
		Tile tile4 = new Tile("G", 10);
		Tile tile5 = new Tile("O", 10);
		Tile tile6 = new Tile("R", 10);
		//These 3 tiles are a set
		
		Tile[] ai1Tiles = {tile1, tile2, tile3, tile4, tile5, tile6};
		
		for(int i=0; i<6; i++) {
			game.getAllPlayers().get(1).getHand().removeFromHand(game.getAllPlayers().get(1).getHand().getNumTiles() - 1);
		}
		for(int i=0; i<6; i++) {
			game.getAllPlayers().get(1).getHand().addTile(ai1Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		game.getAllPlayers().get(1).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		
		game.getAllPlayers().get(1).getHand().createMeld();
		game.getAllPlayers().get(1).getHand().createMeld();
		
		int j=0;
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().getMeld(0).addTile(ai1Tiles[j]);
			j++;
		}
		
		for(int i=j; i<6; i++) {
			game.getAllPlayers().get(1).getHand().getMeld(1).addTile(ai1Tiles[i]);
		}
		
		//Req. 8e.) Player can play a mix of run and set
		assertTrue(game.getAllPlayers().get(1).getHand().getMeld(0).isValidRun());
		assertTrue(game.getAllPlayers().get(1).getHand().getMeld(1).isValidSet());
	}
	
	public void testGame8() {
		Game game = new Game();
		Deck deck = new Deck();
		
		Tile tile1 = new Tile("G", 9);
		Tile tile2 = new Tile("0", 9);
		Tile tile3 = new Tile("B", 9);
		//These 3 tiles are a set
		
		Tile tile4 = new Tile("G", 10);
		Tile tile5 = new Tile("O", 10);
		Tile tile6 = new Tile("R", 10);
		//These 3 tiles are a second set
		//Will test playing several sets in a turn
		
		Tile[] ai1Tiles = {tile1, tile2, tile3, tile4, tile5, tile6};
		
		for(int i=0; i<6; i++) {
			game.getAllPlayers().get(1).getHand().removeFromHand(game.getAllPlayers().get(1).getHand().getNumTiles() - 1);
		}
		for(int i=0; i<6; i++) {
			game.getAllPlayers().get(1).getHand().addTile(ai1Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		game.getAllPlayers().get(1).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		
		game.getAllPlayers().get(1).getHand().createMeld();
		game.getAllPlayers().get(1).getHand().createMeld();
		
		int j=0;
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().getMeld(0).addTile(ai1Tiles[j]);
			j++;
		}
		
		for(int i=j; i<6; i++){
			game.getAllPlayers().get(1).getHand().getMeld(1).addTile(ai1Tiles[i]);
		}
		
		//Req. 8e.) player p1 can play multiple sets
		assertTrue(game.getAllPlayers().get(1).getHand().getMeld(0).isValidSet());
		assertTrue(game.getAllPlayers().get(1).getHand().getMeld(1).isValidSet());
	}
	
	public void testGame9() {
		Game game = new Game();
		Deck deck = new Deck();
		
		Tile tile1 = new Tile("B", 9);
		Tile tile2 = new Tile("B", 10);
		Tile tile3 = new Tile("B", 11);
		//These 3 tiles are a run
		
		Tile[] ai1Tiles = {tile1, tile2, tile3};
		
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().removeFromHand(game.getAllPlayers().get(1).getHand().getNumTiles() - 1);
		}
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().addTile(ai1Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		game.getAllPlayers().get(1).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		
		game.getAllPlayers().get(1).getHand().createMeld();
		for(int i=0; i<3; i++){
			game.getAllPlayers().get(1).getHand().getMeld(0).addTile(ai1Tiles[i]);
		}
		
		//Req. 8a.) player can play one run
		assertTrue(game.getAllPlayers().get(1).getHand().getMeld(0).isValidRun());
		
	}
	
	public void testGame10() {
		Game game = new Game();
		Deck deck = new Deck();
		
		Tile tile1 = new Tile("B", 9);
		Tile tile2 = new Tile("O", 9);
		Tile tile3 = new Tile("R", 9);
		//These 3 tiles are a set
		
		Tile[] ai1Tiles = {tile1, tile2, tile3};
		
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().removeFromHand(game.getAllPlayers().get(1).getHand().getNumTiles() - 1);
		}
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().addTile(ai1Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		game.getAllPlayers().get(1).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		
		game.getAllPlayers().get(1).getHand().createMeld();
		for(int i=0; i<3; i++){
			game.getAllPlayers().get(1).getHand().getMeld(0).addTile(ai1Tiles[i]);
		}
		
		//Req. 8b.) Player can play one set
		assertTrue(game.getAllPlayers().get(1).getHand().getMeld(0).isValidSet());
		assertTrue(game.getAllPlayers().get(1).getHand().getMeld(0).checkIfValidMeld());
	}
	
	public void testGame11() {
		Game game = new Game();
		Deck deck = new Deck();
		
		Tile tile1 = new Tile("B", 9);
		Tile tile2 = new Tile("B", 10);
		Tile tile3 = new Tile("B", 11);
		
		Tile tile4 = new Tile("G", 5);
		Tile tile5 = new Tile("G", 6);
		Tile tile6 = new Tile("G", 7);
		//These 2 groups of 3 tiles are runs
		
		Tile[] ai1Tiles = {tile1, tile2, tile3, tile4, tile5, tile6};
		
		for(int i=0; i<6; i++) {
			game.getAllPlayers().get(1).getHand().removeFromHand(game.getAllPlayers().get(1).getHand().getNumTiles() - 1);
		}
		for(int i=0; i<6; i++) {
			game.getAllPlayers().get(1).getHand().addTile(ai1Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		game.getAllPlayers().get(1).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(false);
		//game.getAllPlayers().get(3).setTurnStatus(false);
		
		game.getAllPlayers().get(1).getHand().createMeld();
		game.getAllPlayers().get(1).getHand().createMeld();
		
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(1).getHand().getMeld(0).addTile(ai1Tiles[i]);
		}
		
		for(int i=3; i<6; i++) {
			game.getAllPlayers().get(1).getHand().getMeld(1).addTile(ai1Tiles[i]);
		}
		
		//Req. 8c.) Player can play several runs
		assertTrue(game.getAllPlayers().get(1).getHand().getMeld(0).isValidRun());
		assertTrue(game.getAllPlayers().get(1).getHand().getMeld(1).isValidRun());
	}
	
	public void testGame12() {
		Game game = new Game();
		Deck deck = game.getDeck();
		
		Tile[] ai3Tiles = {new Tile("B", 9), new Tile("B", 10), new Tile("B", 11)};
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=1; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		for(int i=2; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(3).setTurnStatus(false);
		for(int i=0; i<2; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(2).getHand().dealTile(deck);
		
		game.getAllPlayers().get(3).setTurnStatus(true);
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=1; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		for(int i=2; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(3).setTurnStatus(false);
		for(int i=0; i<2; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(2).getHand().dealTile(deck);
		
		game.getAllPlayers().get(3).setTurnStatus(true);
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		
		int humanSize = game.getAllPlayers().get(0).getHand().getNumTiles();
		int ai1Size = game.getAllPlayers().get(1).getHand().getNumTiles();
		int ai2Size = game.getAllPlayers().get(2).getHand().getNumTiles();
		int ai3Size = game.getAllPlayers().get(3).getHand().getNumTiles();
		
		AI3 ai3 = (AI3)game.getAllPlayers().get(3);
		
		//Req. 14.) AI3 draws a tile if no other player has 3 fewer tiles
		assertFalse(ai3.doesAnyoneHave3LessTiles());
		game.getAllPlayers().get(3).getHand().dealTile(deck);
	}
	
	public void testGame13() {
		Game game = new Game();
		Deck deck = new Deck();
		
		Tile[] ai3Tiles = {new Tile("R", 12), new Tile("R", 13), new Tile("R", 17), new Tile("B", 2), new Tile("B", 4), new Tile("B", 5),
				new Tile("O", 9), new Tile("O", 12), new Tile("O", 13), new Tile("G", 1), new Tile("G", 5), new Tile("G", 6), new Tile("G", 11),
				new Tile("G", 14)};
		
		game.getAllPlayers().get(3).getHand().getPlayerHand().clear();
		for(int i=0; i<14; i++) {
			game.getAllPlayers().get(3).getHand().addTile(ai3Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=1; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		for(int i=2; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(3).setTurnStatus(false);
		for(int i=0; i<2; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(2).getHand().dealTile(deck);
		
		game.getAllPlayers().get(3).setTurnStatus(true);
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		Tile tile = new Tile("R", 14);
		game.getAllPlayers().get(3).getHand().addTile(tile);
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=1; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		for(int i=2; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(3).setTurnStatus(false);
		for(int i=0; i<2; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(2).getHand().dealTile(deck);
		
		game.getAllPlayers().get(3).setTurnStatus(true);
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(3).getHand().sortHand();
		game.getAllPlayers().get(3).getHand().createMeld();
		int index1 = game.getAllPlayers().get(3).getHand().getPlayerHand().indexOf(ai3Tiles[0]);
		int index2 = game.getAllPlayers().get(3).getHand().getPlayerHand().indexOf(ai3Tiles[1]);
		game.getAllPlayers().get(3).getHand().getMeld(0).addTile(game.getAllPlayers().get(3).getHand().getTile(index1));
		game.getAllPlayers().get(3).getHand().getMeld(0).addTile(game.getAllPlayers().get(3).getHand().getTile(index2));
		game.getAllPlayers().get(3).getHand().getMeld(0).addTile(tile);
		
		//Req. 11b.) p3 can play initial 30+ points on subsequent turn
		assertTrue(game.getAllPlayers().get(3).getHand().getMeld(0).checkIfValidMeld());
		assertTrue(game.getAllPlayers().get(3).getHand().getMeld(0).getMeldValue() >= 30);
		
	}
	
	public void testGame14() {
		Game game = new Game();
		Deck deck = new Deck();
		
		Tile[] ai3Tiles = {new Tile("R", 12), new Tile("R", 13), new Tile("R", 14), new Tile("B", 2), new Tile("B", 4), new Tile("B", 5),
				new Tile("O", 9), new Tile("O", 12), new Tile("O", 13), new Tile("G", 1), new Tile("G", 5), new Tile("G", 6), new Tile("G", 11),
				new Tile("G", 14)};
		
		game.getAllPlayers().get(3).getHand().getPlayerHand().clear();
		for(int i=0; i<14; i++) {
			game.getAllPlayers().get(3).getHand().addTile(ai3Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=1; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		for(int i=2; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(3).setTurnStatus(false);
		for(int i=0; i<2; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(2).getHand().dealTile(deck);
		
		game.getAllPlayers().get(3).setTurnStatus(true);
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(0).getHand().createMeld();
		
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(0).getHand().getMeld(0).addTile(ai3Tiles[i]);
		}
		
		//Req. 11a.) p3 plays initial 30+ points on first turn
		assertTrue(game.getAllPlayers().get(0).getHand().getMeld(0).getMeldValue() >= 30);
	}
	
	public void testGame15() {
		Game game = new Game();
		Deck deck = new Deck();
		
		Tile[] ai3Tiles = {new Tile("R", 12), new Tile("R", 13), new Tile("R", 14), new Tile("B", 2), new Tile("B", 3), new Tile("B", 4),
				new Tile("O", 9), new Tile("O", 10), new Tile("O", 11), new Tile("G", 4), new Tile("G", 5), new Tile("G", 6), new Tile("G", 7),
				new Tile("G", 8)};
		
		game.getAllPlayers().get(3).getHand().getPlayerHand().clear();
		for(int i=0; i<14; i++) {
			game.getAllPlayers().get(3).getHand().addTile(ai3Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=1; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		for(int i=2; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(3).setTurnStatus(false);
		for(int i=0; i<2; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(2).getHand().dealTile(deck);
		
		game.getAllPlayers().get(3).setTurnStatus(true);
		game.getAllPlayers().get(3).getHand().createMeld();
		game.getAllPlayers().get(3).getHand().createMeld();
		game.getAllPlayers().get(3).getHand().createMeld();
		game.getAllPlayers().get(3).getHand().createMeld();
		
		for(int i=0; i<14; i++) {
			if(i < 3) {
				game.getAllPlayers().get(3).getHand().getMeld(0).addTile(ai3Tiles[i]);
			}
			if(i >= 3 && i < 6) {
				game.getAllPlayers().get(3).getHand().getMeld(1).addTile(ai3Tiles[i]);
			}
			if(i >= 6 && i < 9) {
				game.getAllPlayers().get(3).getHand().getMeld(2).addTile(ai3Tiles[i]);
			}
			if(i >= 9 && i < 14) {
				game.getAllPlayers().get(3).getHand().getMeld(3).addTile(ai3Tiles[i]);
			}
		}
		
		int points1 = game.getAllPlayers().get(3).getHand().getMeld(0).getMeldValue();
		int points2 = game.getAllPlayers().get(3).getHand().getMeld(1).getMeldValue();
		int points3 = game.getAllPlayers().get(3).getHand().getMeld(2).getMeldValue();
		int points4 = game.getAllPlayers().get(3).getHand().getMeld(3).getMeldValue();
		
		game.getAllPlayers().get(3).getHand().getPlayerHand().clear();
		
		assertTrue(points1 + points2 + points3 + points4 >= 30);
		for(int i=0; i<game.getAllPlayers().get(3).getHand().getNumberOfMelds(); i++) {
			assertTrue(game.getAllPlayers().get(3).getHand().getMeld(i).checkIfValidMeld());
		}
		
		//Req. 12a.) p3 wins not using the tiles on its own table
		assertEquals(0, game.getAllPlayers().get(3).getHand().getPlayerHand().size());
		assertEquals(4, game.getWinner());
	}
	
	public void testGame16() {
		Game game = new Game();
		Deck deck = new Deck();
		
		Tile[] ai3Tiles = {new Tile("R", 12), new Tile("R", 13), new Tile("R", 17), new Tile("B", 2), new Tile("B", 4), new Tile("B", 5),
				new Tile("O", 9), new Tile("O", 12), new Tile("O", 13), new Tile("G", 1), new Tile("G", 5), new Tile("G", 6), new Tile("G", 11),
				new Tile("G", 14)};
		
		game.getAllPlayers().get(3).getHand().getPlayerHand().clear();
		for(int i=0; i<14; i++) {
			game.getAllPlayers().get(3).getHand().addTile(ai3Tiles[i]);
		}
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=1; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		for(int i=2; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(3).setTurnStatus(false);
		for(int i=0; i<2; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(2).getHand().dealTile(deck);
		
		game.getAllPlayers().get(3).setTurnStatus(true);
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		Tile tile = new Tile("R", 1);
		game.getAllPlayers().get(3).getHand().addTile(tile);
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=1; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(0).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		for(int i=2; i<4; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(3).setTurnStatus(false);
		for(int i=0; i<2; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(2).getHand().getPlayerHand().remove(0);
		}
		
		game.getAllPlayers().get(3).setTurnStatus(true);
		for(int i=0; i<3; i++) {
			game.getAllPlayers().get(i).setTurnStatus(false);
		}
		
		//Req. 13a.) AI3 has no tiles to play despite another player having 3 less tiles
		int amountai3 = game.getAllPlayers().get(3).getHand().getPlayerHand().size();
		int amountai2 = game.getAllPlayers().get(2).getHand().getPlayerHand().size();
		assertTrue(amountai3 == amountai2 + 3);
		game.getAllPlayers().get(3).getHand().dealTile(deck);
	}
	
	public void testGame17() {
		Game game = new Game();
		Deck deck = game.getDeck();
		Scanner in = new Scanner(System.in);
		
		Tile[] humanTiles = {new Tile("R", 1), new Tile("R", 2), new Tile("R", 3), new Tile("R", 5), new Tile("O", 10), new Tile("O", 12),
				new Tile("O", 13), new Tile("O", 14), new Tile("G", 6), new Tile("G", 7), new Tile("G", 9), new Tile("G", 10),
				new Tile("G", 10), new Tile("G", 12)};
		Tile[] ai2Tiles = {new Tile("R", 12), new Tile("R", 13), new Tile("R", 17), new Tile("B", 2), new Tile("B", 4), new Tile("B", 5),
				new Tile("O", 11), new Tile("O", 12), new Tile("O", 13), new Tile("G", 5), new Tile("G", 6), new Tile("G", 11),
				new Tile("G", 13), new Tile("G", 14)};
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=14; i>0; i--) {
			game.getAllPlayers().get(0).getHand().removeFromHand(i-1);
		}
		for(int i=0; i<14; i++) {
			game.getAllPlayers().get(0).getHand().addTile(humanTiles[i]);
		}
		
		game.getAllPlayers().get(0).getHand().createMeld();
		Meld meld = game.getAllPlayers().get(0).getHand().getMeld(0);
		for(int i=5; i<8; i++) {
			game.getAllPlayers().get(0).getHand().getMeld(0).addTile(game.getAllPlayers().get(0).getHand().getTile(i));
		}
		
		for(int i=5; i<8; i++) {
			game.getAllPlayers().get(0).getHand().removeFromHand(i);
		}
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(true);
		
		//Req. 15a.) Someone else has played their 30+ points so AI2 plays their initial 30
		assertTrue(game.getAllPlayers().get(0).getHand().getMeld(0).getMeldValue() >= 30);
		
		for(int i=14; i>0; i--) {
			game.getAllPlayers().get(2).getHand().removeFromHand(i-1);
		}
		for(int i=0; i<14; i++) {
			game.getAllPlayers().get(2).getHand().addTile(ai2Tiles[i]);
		}
		
		game.getAllPlayers().get(2).getHand().createMeld();
		Meld meld2 = game.getAllPlayers().get(2).getHand().getMeld(0);
		for(int i=6; i<9; i++) {
			game.getAllPlayers().get(2).getHand().getMeld(0).addTile(game.getAllPlayers().get(2).getHand().getTile(i));
		}
		
		for(int i=6; i<9; i++) {
			game.getAllPlayers().get(2).getHand().removeFromHand(i);
		}
	}
	
	public void testGame18() {
		Game game = new Game();
		Deck deck = game.getDeck();
		Scanner in = new Scanner(System.in);
		
		Tile[] humanTiles = {new Tile("R", 1), new Tile("R", 2), new Tile("R", 3), new Tile("R", 5), new Tile("O", 10), new Tile("O", 12),
				new Tile("O", 13), new Tile("O", 14), new Tile("G", 6), new Tile("G", 7), new Tile("G", 9), new Tile("G", 10),
				new Tile("G", 10), new Tile("G", 12)};
		Tile[] ai2Tiles = {new Tile("R", 1), new Tile("R", 2), new Tile("R", 3), new Tile("B", 3), new Tile("B", 4), new Tile("B", 5),
				new Tile("B", 6), new Tile("G", 3), new Tile("G", 4), new Tile("G", 5), new Tile("G", 6), new Tile("G", 7),
				new Tile("G", 13), new Tile("G", 14)};
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		for(int i=14; i>0; i--) {
			game.getAllPlayers().get(0).getHand().removeFromHand(i-1);
		}
		for(int i=0; i<14; i++) {
			game.getAllPlayers().get(0).getHand().addTile(humanTiles[i]);
		}
		
		game.getAllPlayers().get(0).getHand().createMeld();
		Meld meld = game.getAllPlayers().get(0).getHand().getMeld(0);
		for(int i=5; i<8; i++) {
			game.getAllPlayers().get(0).getHand().getMeld(0).addTile(game.getAllPlayers().get(0).getHand().getTile(i));
		}
		
		for(int i=5; i<8; i++) {
			game.getAllPlayers().get(0).getHand().removeFromHand(i);
		}
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(false);
		game.getAllPlayers().get(2).setTurnStatus(true);
		
		for(int i=14; i>0; i--) {
			game.getAllPlayers().get(2).getHand().removeFromHand(i-1);
		}
		for(int i=0; i<14; i++) {
			game.getAllPlayers().get(2).getHand().addTile(ai2Tiles[i]);
		}
		
		//Req. 15b.) Someone else has played their initial 30+ points but AI2 doesn't have any melds to play
		assertTrue(game.getAllPlayers().get(0).getHand().getMeld(0).getMeldValue() >= 30);
		game.getAllPlayers().get(2).getHand().addTile(new Tile("G", 15));
		assertEquals(15, game.getAllPlayers().get(2).getHand().getNumTiles());
		System.out.println(game.getAllPlayers().get(2).getHand().getNumTiles());
		
		game.getAllPlayers().get(0).setTurnStatus(true);
		game.getAllPlayers().get(2).setTurnStatus(false);
		game.getAllPlayers().get(2).getHand().dealTile(deck);
		
		game.getAllPlayers().get(1).setTurnStatus(true);
		game.getAllPlayers().get(0).setTurnStatus(false);
		game.getAllPlayers().get(1).getHand().dealTile(deck);
		
		game.getAllPlayers().get(2).setTurnStatus(true);
		game.getAllPlayers().get(1).setTurnStatus(false);
		
		game.getAllPlayers().get(2).getHand().createMeld();
		game.getAllPlayers().get(2).getHand().createMeld();
		game.getAllPlayers().get(2).getHand().createMeld();
		game.getAllPlayers().get(2).getHand().createMeld();
		
		for(int i=0; i<15; i++) {
			if(i < 3) {
				game.getAllPlayers().get(2).getHand().getMeld(0).addTile(ai2Tiles[i]);
			}
			if(i >= 3 && i < 7) {
				game.getAllPlayers().get(2).getHand().getMeld(1).addTile(ai2Tiles[i]);
			}
			if(i >= 7 && i < 12) {
				game.getAllPlayers().get(2).getHand().getMeld(2).addTile(ai2Tiles[i]);
			}
			if(i >= 12 && i < 15) {
				game.getAllPlayers().get(2).getHand().getMeld(3).addTile(game.getAllPlayers().get(2).getHand().getTile(i));
			}
		}
		
		game.getAllPlayers().get(2).getHand().getPlayerHand().clear();
		
		//Req. 16a.) AI2 wins not using the tiles on its tables in its last turn
		assertTrue(game.getAllPlayers().get(2).getHand().getMeld(0).checkIfValidMeld());
		assertTrue(game.getAllPlayers().get(2).getHand().getMeld(1).checkIfValidMeld());
		assertTrue(game.getAllPlayers().get(2).getHand().getMeld(2).checkIfValidMeld());
		assertTrue(game.getAllPlayers().get(2).getHand().getMeld(3).checkIfValidMeld());
	}
}