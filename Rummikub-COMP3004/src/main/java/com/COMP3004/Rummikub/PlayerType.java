package com.COMP3004.Rummikub;

import java.util.ArrayList;

public interface PlayerType extends Observer{
	
	public Hand getHand();
	public boolean myTurnStatus();
	public void setTurnStatus(boolean b);
	public boolean hasTilesBeenPlayed();
	public void setTilesBeenPlayed(boolean b);
	public boolean turnComplete(Hand h);
	
	public ArrayList<Spot> spotsTaken = new ArrayList<Spot>();

}
