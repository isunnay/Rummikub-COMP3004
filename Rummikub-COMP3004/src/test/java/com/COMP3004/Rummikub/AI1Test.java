package com.COMP3004.Rummikub;
import com.COMP3004.Rummikub.models.AI1;
import com.COMP3004.Rummikub.models.Deck;
import com.COMP3004.Rummikub.models.Game;

import junit.framework.TestCase;

public class AI1Test extends TestCase {
	
public void testMaint() {
		Game game = new Game();
		Deck deck = new Deck();
		deck.shuffleTiles();
		
		AI1 ai1 = new AI1(deck,game);
	/*	Tile aTile = new Tile("B",4);
		Tile tile = new Tile("B", 5);
		Tile tile1 = new Tile("B", 6);
		Tile tile2 = new Tile("R", 7);
		Tile tile3 = new Tile("G", 7);
		Tile tile4 = new Tile("B", 7);
		Tile tile5 = new Tile("O", 7);
		Tile tile6 = new Tile("B", 8);
		Tile tile7 = new Tile("B", 9);
		Tile tile8 = new Tile("B", 10);
		
		
		ai1.getHand().addTile(aTile);
		ai1.getHand().addTile(tile);
		ai1.getHand().addTile(tile1);
		ai1.getHand().addTile(tile2);
		ai1.getHand().addTile(tile3);
		ai1.getHand().addTile(tile4);
		ai1.getHand().addTile(tile5);
		ai1.getHand().addTile(tile6);
		ai1.getHand().addTile(tile7);
		ai1.getHand().addTile(tile8);*/
		
		
		ai1.getHand().sortHand();
		System.out.println(ai1.h.handToString());
		

		ai1.findAllMelds();
		//System.out.println(ai1.h.handToString());
		System.out.println(ai1.melds.size());
		for(int i=0;i<ai1.melds.size();i++) {
			System.out.println(ai1.melds.get(i).meldToString());
		}
		//System.out.println(ai1.h.handToString());
		
		
		
		
	/*	System.out.println("AI1Hand " + ai1.h.handToString());
		System.out.println("Number of Melds " + ai1.h.numberOfMelds());
		System.out.println(ai1.oneMeldFirstTurn());
		for(int i=0;i<ai1.getHand().size;i++) {
			
		}*/
		//assertTrue("true", ai1.oneMeldFirstTurn());
	}

public void testSeveralMeldsFirstTurn() {
	Deck deck = new Deck();
	AI1 ai1 = new AI1(deck);
	//System.out.println("AI1Hand " + ai1.h.handToString());
	//System.out.println("Number of Melds " + ai1.h.numberOfMelds());
	System.out.println(ai1.severalMeldsFirstTurn());
	//assertTrue("true", ai1.oneMeldFirstTurn());
	
	}

public void testdrawsOnFirstTurn() {
	Deck deck = new Deck();
	AI1 ai1 = new AI1(deck);
	System.out.println(ai1.drawsOnFirstTurn().handToString());

}

public void testOneMeldSubSequentTurn() {
	Deck deck = new Deck();
	AI1 ai1 = new AI1(deck);
	//System.out.println("AI1Hand " + ai1.h.handToString());
	//System.out.println("Number of Melds " + ai1.h.numberOfMelds());
	System.out.println(ai1.oneMeldSubsequentTurn());
	//System.out.println("AI1Hand after " + ai1.h.handToString());
	
	}

public void testSeveralMeldsSubSequentTurn() {
	Deck deck = new Deck();
	AI1 ai1 = new AI1(deck);
	//System.out.println("AI1Hand " + ai1.h.handToString());
	//System.out.println("Number of Melds " + ai1.h.numberOfMelds());
	System.out.println(ai1.severaltMeldsSubsequentTurn());
	//System.out.println("AI1Hand after " + ai1.h.handToString());
	
	}

public void testdrawsOnSubsequentTurn() {
	Deck deck = new Deck();
	AI1 ai1 = new AI1(deck);
	System.out.println(ai1.drawsOnSubsequentTurn().handToString());

}




}


/*
10: p1 plays as many melds as it can, as soon as it can:
10a: p1 can play one meld on its first turn
10b: p1 can play several melds on its first turn
10c: p1 can play one meld on a subsequent turn
10d: p1 can play several melds on a subsequent turn
10e: p1 draws on first turn
10f: p1 draws on a subsequent turn

*/