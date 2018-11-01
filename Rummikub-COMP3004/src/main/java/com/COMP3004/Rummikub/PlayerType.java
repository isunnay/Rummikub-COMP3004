package com.COMP3004.Rummikub;

public interface PlayerType {
	
	public Hand getHand();
	public boolean myTurnStatus();
	public boolean getIsItPlayersTurn ();
	public void setIsItPlayersTurn(boolean turn);
	public boolean hasTilesBeenPlayed();
	public boolean turnComplete(Hand h);

}
