package com.COMP3004.Rummikub;

public interface PlayerType {
	
	public Hand getHand();
	public boolean myTurnStatus();
	public boolean hasTilesBeenPlayed();
	public boolean turnComplete(Hand h);

}
