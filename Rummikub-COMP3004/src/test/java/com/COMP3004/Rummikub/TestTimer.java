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
		assertTrue(timer.isStarted());
	}
	
	public void testTimerStopped() {
		GameTimer timer = new GameTimer();
		
		timer.start();
		timer.stop();
		assertTrue("true", timer.isStopped());
	}
	
	public void testStopsAtRightTime() {
		GameTimer timer = new GameTimer();
		
		timer.start();
		assertTrue(timer.stopsAtRightTime());
	}
}