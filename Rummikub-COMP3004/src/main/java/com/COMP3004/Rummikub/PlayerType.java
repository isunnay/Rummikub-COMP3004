package com.COMP3004.Rummikub;

import java.util.*;

public interface PlayerType extends Observer{
//extends Observer{
	public Hand getHand();
	public boolean myTurnStatus();

	public void setTurnStatus(boolean b);

	public boolean hasTilesBeenPlayed();
	public void setTilesBeenPlayed(boolean b);
	public boolean turnComplete(Hand h);
	
	public boolean hasInitialMeldBeenPlayed();
	public void setHasInitialMeldBeenPlayed(boolean b);
	
	
	public void update(Board board);
	
	public void playMeld(Meld meld, Scanner reader);
	public void addTile(Tile tile, int x, int y);
	public boolean canWePlaceMeld(Meld meld, int x, int y);
	public void undoTurn();
	public void undoAddTile(Tile tile);
	public void undoPlayMeld(Meld meld);
	public void moveTile(Tile tile, Spot newSpot);

}
