package com.COMP3004.Rummikub.models;

public class BoardOriginator {
	private Board boardState;
	
	public Board getState() { return boardState; }
	
	public void setBoardState(Board board) { boardState = board; }
	
	public BoardMomento saveStateToMomento() {
		return new BoardMomento(boardState);
	}
	
	public void getStateFromMomento(BoardMomento momento) {
		boardState = momento.getBoardState();
	}
}