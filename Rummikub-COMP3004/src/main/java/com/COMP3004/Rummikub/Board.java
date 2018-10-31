package com.COMP3004.Rummikub;

import java.util.Scanner;
import java.util.ArrayList;

public class Board {
	int boardX = 15;
	int boardY = 15;
	int numberOfTilesOnBoard;
	public ArrayList<Meld> meldsOnBoard;
	int numberOfMelds;

	private Spot[][] spots = new Spot[15][15];
	
	public Board() {
		for(int row=0; row<spots.length; row++){
            for(int column=0; column<spots[row].length; column++){
                this.spots[column][row] = new Spot(column, row);
            }
        }
		meldsOnBoard = new ArrayList<Meld>();
		numberOfTilesOnBoard = 0;
		numberOfMelds = 0;
	}

	public int getNumberOfBoardSpots() { return boardX * boardY; }
	
	public int getX() { return boardX; }
	
	public int getY() { return boardY; }
	
    public Spot getSpot(int x, int y) {
        return spots[x][y];
    }

	public void playTile(Tile tile, int x, int y) {
		Spot spot = getSpot(x,y);
		spot.playTile(tile);
		tile.setSpot(spot);
		numberOfTilesOnBoard++;
	}
	

	public void removeTile(int x, int y) {
		Tile tileToRemove = getTileAtSpot(x,y);
		Spot spot = getLocationOfTile(tileToRemove);
		spot.removeTile();
		tileToRemove.removeSpot(spot);
		numberOfTilesOnBoard--;
		
		
	}

	public boolean isSpotFilled(int x, int y) {
		Spot spot = getSpot(x,y);
		if(spot.isTaken == false) {
			return false;
		}
		return true;
	}

	public Tile getTileAtSpot(int x, int y) {
		Spot spot = getSpot(x,y);
		if(spot.isTaken == false) {
			return null;
		}
		else {
			return spot.getTile();
		}
	}
	
	public Spot getLocationOfTile(Tile tile) {
		Spot spot = tile.getSpot();
		return spot;
	}

	public void moveTile(Tile tile, Spot newSpot) {
		Spot oldSpot = tile.getSpot();
		oldSpot.removeTile();
		tile.removeSpot(oldSpot);
		newSpot.playTile(tile);
		tile.setSpot(newSpot);
	}
	
	public void boardToString() {
		for (int i = 0; i < spots.length; i++) {
		    for (int j = 0; j < spots[i].length; j++) {
		        if(spots[j][i].getTile() != null) {
		        	if(spots[j][i].getTile().tileToString().length() == 3) {
		        		System.out.print("[" + spots[j][i].getTile().tileToString() + "]" );
		        	}
		        	if(spots[j][i].getTile().tileToString().length() == 2) {
		        		System.out.print("[" + spots[j][i].getTile().tileToString() + " ]" );
		        	}
		        	
		        	}
		        else {
		        	System.out.print("[   ]");
		        }
		    }
		    System.out.println();
		}
	}
	
	public boolean canWePlaceMeld(Meld meld, int x, int y) {
		for(int a=0;a<meld.getMeldSize();a++) {
			if(this.isSpotFilled(x+a, y) != false) {
				return false;
				
			}	
		}
		return true;	
	}
	
	
	public void addMeld(Meld meld) {
		Scanner reader = new Scanner(System.in); 
		System.out.println("Enter an x value for the beginning of the Meld: ");
		int x = reader.nextInt(); 
		System.out.println("Enter an y value for the beginning of the Meld: ");
		int y = reader.nextInt(); 
		reader.close();
		Spot toPlace = spots[x][y].getSpot();
		
		if(toPlace!=null) {
			System.out.println("We in first if");
			if(this.canWePlaceMeld(meld,x,y)==true) {
				System.out.println("We in Second if");
				for(int i=0;i<meld.getNumberOfTiles();i++) {
					Tile tile = meld.getTileInMeld(i);
					this.playTile(tile, x+i, y);
				}		
				System.out.println(meld == null);
				meldsOnBoard.add(meld);
				numberOfMelds++;
			}			
		}	
	}
	
	public Meld getMeld(int i) {
		return meldsOnBoard.get(i);
	}
	
	public int getNumberOfMelds() {
		return numberOfMelds;
	}
	
	public Spot getNextSpot(int x,int y) {
		return spots[x+1][y];
		
	}
		
		
	

}
