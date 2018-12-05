package com.COMP3004.Rummikub;

import junit.framework.TestCase;

import com.COMP3004.Rummikub.models.Board;
import com.COMP3004.Rummikub.models.BoardCareTaker;
import com.COMP3004.Rummikub.models.BoardMomento;

public class BoardCareTakerTest extends TestCase {
	public void testBoardCareTakerExists() {
		BoardCareTaker careTaker = new BoardCareTaker();
		
		assertNotNull(careTaker);
	}
	
	public void testMementoAdded() {
		Board board = new Board();
		BoardMomento memento = new BoardMomento(board);
		
		BoardCareTaker careTaker = new BoardCareTaker();
		careTaker.addMomento(memento);
		
		assertTrue(careTaker.momentoExists(memento));
	}
}