package com.COMP3004.Rummikub;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
	private final int perSecond = 1000000000; //amount of nano-seconds in one second
	private int seconds;
	private boolean started;
	private boolean stopped;
	private Timer timer;
	private TimerTask task = new TimerTask() {
		public void run() {
			seconds++;
		}
	};
	
	public GameTimer() {
		started = false;
		stopped = true;
		seconds = 0;
		timer = new Timer();
	}
	
	public void start() {
		timer.scheduleAtFixedRate(task, 1000, 1000);
		
		started = true;
		stopped = false;
	}
	
	public boolean isStarted() { return started; }
	
	public void stop() {
		task.cancel();
		seconds = 0;
		
		stopped = true;
	}
	
	public boolean isStopped() { return stopped; }
	
	public int getSeconds() { return seconds; }
}