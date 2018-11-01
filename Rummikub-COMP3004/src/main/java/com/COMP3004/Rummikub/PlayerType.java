package com.COMP3004.Rummikub;

public interface PlayerType {
	
	public Hand getHand();
	public boolean myTurnStatus();

	public void setTurnStatus(boolean b);

	public boolean hasTilesBeenPlayed();
	public void setTilesBeenPlayed(boolean b);
	public boolean turnComplete(Hand h);

}
