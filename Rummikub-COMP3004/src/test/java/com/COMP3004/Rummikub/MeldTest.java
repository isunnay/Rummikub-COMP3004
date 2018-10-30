package com.COMP3004.Rummikub;

import junit.framework.TestCase;

public class MeldTest extends TestCase{
	
	public void testAddCardToMeld() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		hand.createMeld();
		Meld meld = hand.getMeld(0);
		for(int i=0;i<3;i++) {
			meld.addTile(hand.getTile(i));	
		}
		assertEquals(3, meld.getNumberOfTiles());	
	}
	
	
	public void testIsValidMeldTrueStraight() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		hand.createMeld();
		Meld meld = hand.getMeld(0);
		Tile tile = new Tile(0, 8);
		Tile tile1 = new Tile(0, 7);
		Tile tile2 = new Tile(0, 6);
		meld.addTile(tile2);
		meld.addTile(tile1);
		meld.addTile(tile);
		
		assertTrue("true", meld.checkIfValidMeld(meld));
	}
	
	public void testIsValidMeldTrueValue() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		hand.createMeld();
		Meld meld = hand.getMeld(0);
		Tile tile = new Tile(0, 8);
		Tile tile1 = new Tile(1, 8);
		Tile tile2 = new Tile(2, 8);
		meld.addTile(tile2);
		meld.addTile(tile1);
		meld.addTile(tile);
		
		assertTrue("true", meld.checkIfValidMeld(meld));
	}
	
	public void testIsValidMeldFalseStraight() {
		Deck deck = new Deck();
		deck.shuffleTiles();
		Hand hand = new Hand();
		hand.createHand(deck);
		hand.createMeld();
		Meld meld = hand.getMeld(0);
		Tile tile = new Tile(0, 9);
		Tile tile1 = new Tile(0, 7);
		Tile tile2 = new Tile(0, 6);
		meld.addTile(tile2);
		meld.addTile(tile1);
		meld.addTile(tile);


		assertFalse("false", meld.checkIfValidMeld(meld));
	}
	
	public void testIsValidMeldFalseValue() {
		Deck deck = new Deck();
		deck.shuffleTiles();
		Hand hand = new Hand();
		hand.createHand(deck);
		hand.createMeld();
		Meld meld = hand.getMeld(0);
		Tile tile = new Tile(0, 6);
		Tile tile1 = new Tile(1, 7);
		Tile tile2 = new Tile(2, 6);
		meld.addTile(tile);
		meld.addTile(tile1);
		meld.addTile(tile2);
		


		assertFalse("false", meld.checkIfValidMeld(meld));
	}
	
	public void testRemoveCardFromMeld() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		hand.createMeld();
		Meld meld = hand.getMeld(0);
		for(int i=0;i<3;i++) {
			meld.addTile(hand.getTile(i));	
		}
		meld.removeTile(1);
		
		assertEquals(2,meld.getNumberOfTiles());
	}
	

	
	public void testTypeOfMeld() {
		
	}

}
