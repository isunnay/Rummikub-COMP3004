package com.COMP3004.Rummikub;

import com.COMP3004.Rummikub.models.Deck;
import com.COMP3004.Rummikub.models.Game;
import com.COMP3004.Rummikub.models.Tile;

import junit.framework.TestCase;

public class FileInputTest extends TestCase {
	public void testFile() {
		Game game = new Game();
		
		//assertNotNull(game.chooseFile());
	}
	
	public void testAI1hasInitialMeld() {
		Game game = new Game();
		
		//game.chooseFile();
		Tile tile = new Tile("R", 4);
		Tile tile2 = new Tile("R", 5);
		Tile tile3 = new Tile("R", 6);
		Tile tile4 = new Tile("R", 7);
		Tile tile5 = new Tile("R", 8);
		
		Tile[] meldTiles = {tile, tile2, tile3, tile4, tile5};
		
		Deck deck = new Deck();
		
		for(int i=0; i<meldTiles.length; i++) {
			//game.getPlayer(1).getPlayerHand().add(meldTiles[i]);
			deck.getTileDeck().remove(meldTiles[i]);
		}
		
		for(int i=0; i<9; i++) {
			//game.getPlayer(1).getPlayerHand().add(deck.getTileDeck().remove(deck.getTileDeck().size() - 1));
		}
		
		//assertTrue("true", game.hasInitialMeld(game.getPlayer(1)));
	}
	
	public void testTilesPlayed() {
		Game game = new Game();
		
		//game.chooseFile();
		//game.getPlayer(1).playTile();
		//assertTrue("true", game.tilesPlayed(game.getPlayer(1)));
	}
}
