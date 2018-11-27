package com.COMP3004.Rummikub;
import junit.framework.TestCase;

public class TestTimer extends TestCase {
	public void testTimerExists() {
		GameTimer timer = new GameTimer();
		
		assertNotNull(timer);
	}
	
	public void testTimerStarts() {
		GameTimer timer = new GameTimer();
		
		timer.start();
		assertEquals(0, timer.getSeconds());
		assertEquals("true", timer.isStarted());
	}
	
	public void testTimerStopped() {
		GameTimer timer = new GameTimer();
		
		timer.start();
		timer.stop();
		assertTrue("true", timer.isStopped());
	}
	
	publci void testTimerRestarts() {
		GameTimer timer = new GameTimer();
		
		timer.start();
		timer.stop();
		
		timer.restart();
		
		assertTrue("true", timer.isRestarted());
	}
}