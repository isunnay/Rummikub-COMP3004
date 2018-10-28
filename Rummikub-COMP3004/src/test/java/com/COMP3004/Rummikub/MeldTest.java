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
	
	
	public void testIsValidMeldTrue() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		hand.createMeld();
		Meld meld = hand.getMeld(0);
		for(int i=0;i<3;i++) {
			meld.addTile(hand.getTile(i));	
		}
		assertTrue("true", meld.isValidMeld);
	}
	
	public void testIsValidMeldFalse() {
		Deck deck = new Deck();
		Hand hand = new Hand();
		hand.createHand(deck);
		hand.createMeld();
		Meld meld = hand.getMeld(0);
		for(int i=0;i<3;i++) {
			meld.addTile(hand.getTile(i));	
		}
		
		assertFalse("false", meld.isValidMeld);
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
