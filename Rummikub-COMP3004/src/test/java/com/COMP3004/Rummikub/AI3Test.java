package com.COMP3004.Rummikub;
import com.COMP3004.Rummikub.models.AI3;
import com.COMP3004.Rummikub.models.Deck;

import junit.framework.TestCase;

public class AI3Test extends TestCase {
	
	public void testThirtyPointsFirstTurn() {
		Deck deck = new Deck();
		AI3 ai3 = new AI3(deck);
		System.out.println("AI3Hand " + ai3.h.handToString());
		System.out.println("Meld Points " + ai3.h.meldPoints());
		System.out.println(ai3.thirtyPointsFirstTurn());
		//assertTrue("true", ai1.oneMeldFirstTurn());
	}
	
	public void testThirtPointsSubsequentTurn() {
		Deck deck = new Deck();
		AI3 ai3 = new AI3(deck);
		System.out.println("AI3Hand " + ai3.h.handToString());
		System.out.println("Number of Melds " + ai3.h.meldPoints());
		System.out.println(ai3.thirtyPointsSubsequentTurn());
		//assertTrue("true", ai1.oneMeldFirstTurn());
		System.out.println("AI3HandAfter " + ai3.h.handToString());
	}

	
	//-11a: p3 can play 30+ points on its first turn
	//-11b: p3 plays 30+ points on subsequent turn


}
