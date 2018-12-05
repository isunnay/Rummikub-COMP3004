package com.COMP3004.Rummikub.models;

import java.util.ArrayList;

public class BoardCareTaker {
	private ArrayList<BoardMomento> boardMomentos = new ArrayList<BoardMomento>();
	
	public void addMomento(BoardMomento momento) {
		boardMomentos.add(momento);
	}
	
	public BoardMomento getMomento(int i) {
		return boardMomentos.get(i);
	}
	
	public boolean momentoExists(BoardMomento momento) {
		return boardMomentos.contains(momento);
	}
	
	public ArrayList<BoardMomento> getMomentos() { return boardMomentos; }
}