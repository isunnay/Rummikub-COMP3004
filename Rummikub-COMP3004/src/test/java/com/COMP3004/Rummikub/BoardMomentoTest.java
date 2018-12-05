package com.COMP3004.Rummikub;

import junit.framework.TestCase;

import com.COMP3004.Rummikub.models.Board;
import com.COMP3004.Rummikub.models.BoardMomento;

public class BoardMomentoTest extends TestCase {
	public void testBoardMomentoExists() {
		Board board = new Board();
		BoardMomento momento = new BoardMomento(board);
		
		assertNotNull(momento);
	}
	
	public void testStateExists() {
		Board board = new Board();
		BoardMomento momento = new BoardMomento(board);
		
		assertEquals(board, momento.getBoardState());
	}
}