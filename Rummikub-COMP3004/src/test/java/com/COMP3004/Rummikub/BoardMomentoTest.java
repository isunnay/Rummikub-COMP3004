package com.COMP3004.Rummikub;

import junit.framework.TestCase;
import com.COMP3004.Rummikub.models.BoardMomento;

public class BoardMomentoTest extends TestCase {
	public void testBoardMomentoExists() {
		BoardMomento momento = new BoardMomento();
		
		assertNotNull(momento);
	}
}