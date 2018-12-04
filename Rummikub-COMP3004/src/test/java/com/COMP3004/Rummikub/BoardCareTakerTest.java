package com.COMP3004.Rummikub;

import junit.framework.TestCase;
import com.COMP3004.Rummikub.models.BoardCareTaker;

public class BoardCareTakerTest extends TestCase {
	public void testBoardCareTakerExists() {
		BoardCareTaker careTaker = new BoardCareTaker();
		
		assertNotNull(careTaker);
	}
}