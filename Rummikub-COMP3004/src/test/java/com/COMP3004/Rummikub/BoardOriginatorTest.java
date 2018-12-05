package com.COMP3004.Rummikub;

import junit.framework.TestCase;

import com.COMP3004.Rummikub.models.Board;
import com.COMP3004.Rummikub.models.BoardMomento;
import com.COMP3004.Rummikub.models.BoardOriginator;;

public class BoardOriginatorTest extends TestCase {
	public void testBoardOriginator() {
		Board board = new Board();
		BoardOriginator originator = new BoardOriginator();
	
		assertNotNull(originator);
	}
	
	public void testStateSavedToMomento() {
		Board board = new Board();
		BoardOriginator originator = new BoardOriginator();
		
		BoardMomento momento = new BoardMomento(board);
		
		originator.saveStateToMomento();
		
		assertEquals(momento.getBoardState(), originator.getState());
	}
	
	public void testGetStateFromMomento() {
		Board board = new Board();
		BoardOriginator originator = new BoardOriginator();
		
		Board board2 = new Board();
		
		BoardMomento momento = new BoardMomento(board2);
		
		originator.getStateFromMomento(momento);
		
		assertEquals(board2, originator.getState());
	}
}