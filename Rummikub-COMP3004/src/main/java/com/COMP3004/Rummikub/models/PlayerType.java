package com.COMP3004.Rummikub.models;

import java.util.*;

import javafx.scene.layout.GridPane;

public interface PlayerType extends Observer {
	
	public Hand getHand();
	public boolean myTurnStatus();

	public void setTurnStatus(boolean b);

	public boolean hasTilesBeenPlayed();
	public void setTilesBeenPlayed(boolean b);
	public boolean turnComplete(Hand h);
	
	public boolean hasInitialMeldBeenPlayed();
	public void setHasInitialMeldBeenPlayed(boolean b);
	
	
	public void update(Board board);
	
	public void play(Scanner reader);
	public boolean makeAPlay(Scanner reader);
	public void playMeld(Meld meld, Scanner reader);
	public void addTile(Tile tile, int x, int y);
	public boolean canWePlaceMeld(Meld meld, int x, int y);
	public boolean canWePlaceTile(Tile tile, int x, int y);
	public void undoTurn();
	public void undoAddTile(Tile tile);
	public void undoPlayMeld(Meld meld);
	public void moveTile(Tile tile, Spot newSpot);
	public Meld combineMelds(Meld meld1, Meld meld2, Tile tile);
	public void undoMove(Tile tile);
	public void setTurnPoints();
	public int getTurnPoints();
	public void play(Scanner reader, Deck deck) throws InterruptedException;
	public boolean isAI();
	public boolean play(GridPane gridPane);
	
}
