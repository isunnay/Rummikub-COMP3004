package com.COMP3004.Rummikub.models;

public class BoardOriginator {
	private Board boardState;
	
	public BoardOriginator(Board board) {
		boardState = board;
	}
	
	public Board getState() { return boardState; }
	
	public BoardMomento saveStateToMomento() {
		return new BoardMomento(boardState);
	}
	
	public void getStateFromMomento(BoardMomento momento) {
		boardState = momento.getBoardState();
	}
}