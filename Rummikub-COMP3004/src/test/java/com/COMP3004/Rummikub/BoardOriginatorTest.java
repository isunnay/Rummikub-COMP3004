package com.COMP3004.Rummikub;

import junit.framework.TestCase;
import com.COMP3004.Rummikub.models.BoardOriginator;;

public class BoardOriginatorTest extends TestCase {
	public void testBoardOriginator() {
		BoardOriginator originator = new BoardOriginator();
	
		assertNotNull(originator);
	}
}