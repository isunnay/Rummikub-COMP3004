package com.COMP3004.Rummikub.models;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
	private int seconds;
	private boolean started;
	private boolean stopped;
	private Timer timer;
	private TimerTask task = new TimerTask() {
		
		public void run() {
			seconds++;
			System.out.print(seconds + ", ");
			if(seconds % 30 == 0) {
				System.out.println();
			}
			if(seconds == 10) {
				stop();
			}
		}
	};
	
	public GameTimer() {
		started = false;
		stopped = true;
		seconds = 0;
		timer = new Timer();
	}
	
	public void start() {
		started = true;
		stopped = false;
		//timer.schedule(task, 0, 1000);
		new Timer(true).schedule(task, 0, 1000);
		//System.out.println("Timer Started");
		
		/*if(seconds == 10) {
			stop();
		}*/
	}
	
	public boolean isStarted() { return started; }
	
	public void stop() {
		task.cancel();
		seconds = 0;
		//System.out.println("Timer Stopped");
		
		stopped = true;
	}
	
	public boolean isStopped() { return stopped; }
	
	public int getSeconds() { return seconds; }
	
	public boolean stopsAtRightTime() {
		start();
		/*System.out.println(seconds);
		int secondsPassed = 0;
		
		do {
			secondsPassed = seconds;
			System.out.println(seconds);
		}
		while(seconds <= 10);
		System.out.println(secondsPassed);
		
		this.stop();
		
		if(secondsPassed == 10) {
			return true;
		}*/
		int secondsPassed = 0;
		
		try {
			Thread.sleep(60000);
			secondsPassed = seconds;
			System.out.println(seconds);
		}
		catch(InterruptedException e) {
			
		}
		if(secondsPassed == 60 || secondsPassed == 0) {
			//stop();
			return true;
		}
		
		return false;
	}
}