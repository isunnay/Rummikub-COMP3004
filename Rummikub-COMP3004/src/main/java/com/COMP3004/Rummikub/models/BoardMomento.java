package com.COMP3004.Rummikub.models;

public class BoardMomento {
	private Board boardState;
	
	public BoardMomento(Board board) {
		boardState = board;
	}
	
	public Board getBoardState() { return boardState; }
}
