package com.COMP3004.Rummikub;

import junit.framework.TestCase;

public class BoardMomentoTest extends TestCase {
	public void testBoardMomentoExists() {
		BoardMomento momento = new BoardMomento();
		
		assertNotNull(momento);
	}
}