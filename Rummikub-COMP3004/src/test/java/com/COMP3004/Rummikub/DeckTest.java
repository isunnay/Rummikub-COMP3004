package com.COMP3004.Rummikub;

import com.COMP3004.Rummikub.models.Deck;

import junit.framework.TestCase;

public class DeckTest extends TestCase {
	public void testDeckExists() {
		Deck deck = new Deck();
		assertNotNull(deck);	
	}
	
	public void testNumberOfTiles() {
		Deck deck = new Deck();
		assertEquals(104, deck.getDeckCount());	
	}
	

	public void testEveryTileExists(){
		Deck deck = new Deck();
		assertTrue("true",deck.doesEveryTileExist(0));		
	}
	
	public void testIsDeckShuffled() {
		Deck deck = new Deck();
		deck.shuffleTiles();
		assertFalse("false",deck.isDeckShuffled(0));
	}
}
