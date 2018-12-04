package com.COMP3004.Rummikub;

import junit.framework.TestCase;

public class BoardOriginatorTest extends TestCase {
	public void testOriginatorExists() {
		BoardOriginator originator = new BoardOriginator();
		
		assertNotNull(originator);
	}
}
