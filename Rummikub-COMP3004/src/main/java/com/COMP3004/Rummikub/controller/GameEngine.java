package com.COMP3004.Rummikub.controller;

import java.awt.Event;

import java.awt.Graphics;

public class GameEngine {

	/** handle the Event passed from the main applet **/

	public boolean handleEvent(Event e) {
		switch (e.id) {
			case Event.KEY_PRESS:
	
			case Event.KEY_ACTION:
				// key pressed
				break;
			case Event.KEY_RELEASE:
				// key released
				break;
			case Event.MOUSE_DOWN:
				// mouse button pressed
				break;
			case Event.MOUSE_UP:
				// mouse button released
				break;
			case Event.MOUSE_MOVE:
				// mouse is being moved
				break;
			case Event.MOUSE_DRAG:
				// mouse is being dragged (button pressed)
				break;
		}
		return false;
	}

	/** the update method with the deltaTime in seconds **/
	public void update(float deltaTime) {
		
	}

	/** this will render the whole world **/
	public void render(Graphics g) {
		
	}
}
