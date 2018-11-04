package com.COMP3004.Rummikub;
import junit.framework.TestCase;
import java.util.Random;

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
		Tile tile = new Tile(0, 6);
		Tile tile1 = new Tile(0, 7);
		Tile tile2 = new Tile(0, 8);
		hand.addTile(tile);
		hand.addTile(tile1);
		hand.addTile(tile2);
		
		assertTrue("true", hand.meldExists());
		//System.out.println(hand.numberOfMelds());
		//hand.numberOfMelds();
		
	}
	
	public void testNumberOfMelds() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		
		hand.createHand(deck);
		Tile tile = new Tile(0, 6);
		Tile tile1 = new Tile(0, 7);
		Tile tile2 = new Tile(0, 8);
		hand.addTile(tile);
		hand.addTile(tile1);
		hand.addTile(tile2);
		
		
		System.out.println("AI1 hand: " + hand.handToString());
		System.out.println("Number of Melds : " + hand.numberOfMelds());
		
		
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
	
	public void testPlayTile() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		deck.shuffleTiles();
		hand.createHand(deck);
		
		Random rand = new Random();
		int num = rand.nextInt(14);
		int index = num % 14;
		
		Tile tile = hand.getTile(index);
		String colour = tile.getColour();
		int value = tile.getValue();
		
		assertEquals(tile, hand.playTile(colour, value));
	}
	
	public void testIsTilePlayed() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		deck.shuffleTiles();
		hand.createHand(deck);
		
		Random rand = new Random();
		int num = rand.nextInt(14);
		int index = num % 14;
		
		Tile tile = hand.getTile(index);
		
		String colour = tile.getColour();
		int value = tile.getValue();
		
		hand.playTile(colour, value);
		
		int num2 = rand.nextInt(13);
		int index2 = num2 % 13;
		
		Tile tile2 = hand.getTile(index2);
		
		assertFalse("false", hand.isTileInHand(tile));
		assertTrue("true", hand.isTileInHand(tile2));
	}
	
	public void testAddMelds() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		deck.shuffleTiles();
		hand.createHand(deck);
		
		hand.createMeld();
		hand.createMeld();
		Meld meld1 = hand.getMeld(0);
		Meld meld2 = hand.getMeld(1);
		
		System.out.println(meld1);
		System.out.println(meld2);
		
		assertEquals(2, hand.getNumberOfMelds());
		
	}
	
	public void testRemoveTile() {
		Hand hand = new Hand();
		Deck deck = new Deck();
		deck.shuffleTiles();
		hand.createHand(deck);
		
		Random rand = new Random();
		int num = rand.nextInt(14);
		int index = num % 14;
		
		Tile tile = hand.getTile(index);
		
		assertEquals(tile, hand.removeTile(index));
	}	
}