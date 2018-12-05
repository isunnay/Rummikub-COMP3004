package com.COMP3004.Rummikub;

import junit.framework.TestCase;

import com.COMP3004.Rummikub.models.Board;
import com.COMP3004.Rummikub.models.BoardMomento;
import com.COMP3004.Rummikub.models.BoardOriginator;;

public class BoardOriginatorTest extends TestCase {
	public void testBoardOriginator() {
		Board board = new Board();
		BoardOriginator originator = new BoardOriginator(board);
	
		assertNotNull(originator);
	}
	
	public void testStateSavedToMomento() {
		Board board = new Board();
		BoardOriginator originator = new BoardOriginator(board);
		
		BoardMomento momento = new BoardMomento(baord);
		
		assertEquals(momento, originator.saveStateToMomento());
	}
	
	public void testGetStateFromMomento() {
		Board board = new Board();
		BoardOriginator originator = new BoardOriginator(board);
		
		Board board2 = new Board();
		
		BoardMomento momento = new BoardMomento(board2);
		
		originator.getStateFromMomento(momento);
		
		assertEquals(board2, originator.getState());
	}
}