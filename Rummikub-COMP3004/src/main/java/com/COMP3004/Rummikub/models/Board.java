package com.COMP3004.Rummikub.models;

import java.util.Scanner;
import java.util.ArrayList;

public class Board {
	int boardX = 15;
	int boardY = 15;
	int numberOfTilesOnBoard;
	public ArrayList<Meld> meldsOnBoard;
	//public ArrayList<Spot> availableSpots;
	public ArrayList<Spot> filledSpots;
	int numberOfMelds;
	//private ArrayList<Observer> observers;

	private Spot[][] spots = new Spot[15][15];
	
	public Board() {
		for(int row=0; row<spots.length; row++){
            for(int column=0; column<spots[row].length; column++){
                this.spots[column][row] = new Spot(column, row);
            }
        }
		meldsOnBoard = new ArrayList<Meld>();
		filledSpots = new ArrayList<Spot>();
		numberOfTilesOnBoard = 0;
		numberOfMelds = 0;
		//this.observers = new ArrayList<>();
	}
	
	public Spot[][] getSpotsArray(){
		return spots;
	}

	public int getNumberOfBoardSpots() { return boardX * boardY; }
	
	public int getX() { return boardX; }
	
	public int getY() { return boardY; }
	
    public Spot getSpot(int x, int y) {
        return spots[x][y];
    }

	/*public void playTile(Tile tile, int x, int y) {
		Spot spot = getSpot(x,y);
		spot.playTile(tile);
		tile.setSpot(spot);
		numberOfTilesOnBoard++;
		filledSpots.add(spot);  
		
		//boardChanged(tile);
	}*/
	

	public void removeTile(int x, int y) {
		Tile tileToRemove = getTileAtSpot(x,y);
		Spot spot = getLocationOfTile(tileToRemove);
		spot.removeTile();
		tileToRemove.removeSpot(spot);
		numberOfTilesOnBoard--;
		filledSpots.remove(spot);
		//boardChanged(tileToRemove);
		
		
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
	
	public void deleteMeld(Meld meld) {
		for(int i=0;i<meld.getMeldSize();i++) {
			Spot spot = meld.getTileInMeld(i).getSpot();
			spot.removeTile();
			meld.getTileInMeld(i).removeSpot(spot);
			this.numberOfTilesOnBoard--;
			this.filledSpots.remove(spot);
		}
		this.meldsOnBoard.remove(meld);
		this.numberOfMelds--;	
		meld=null;
		
	}
	
	/*public Meld getMeldAtEnd(Spot spot) {
		
		
	}
	
	public Meld getMeldAtBeg(Spot spot) {
		int x = spot.getSpotX();
		int y = spot.getSpotY();
		Tile tile = this.getTileAtSpot(x, y);
		Meld meld = tile.getPartOfMeld();
	}*/
	
	public Spot getLocationOfTile(Tile tile) {
		Spot spot = tile.getSpot();
		return spot;
	}

	/*public void moveTile(Tile tile, Spot newSpot) {
		Spot oldSpot = tile.getSpot();
		oldSpot.removeTile();
		tile.removeSpot(oldSpot);
		newSpot.playTile(tile);
		tile.setSpot(newSpot);
		//boardChanged();
	}*/
	
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
	
	//Check whether locations are available for meld to be placed in
	/*public boolean canWePlaceMeld(Meld meld, int x, int y) {
		for(int a=0;a<meld.getMeldSize();a++) {
			if(this.isSpotFilled(x+a, y) != false) {
				return false;
				}	
		}
		return true;	
	}*/
	
	
	/*public void addMeld(Meld meld) {
		/*Scanner reader = new Scanner(System.in); 
		System.out.println("Enter an x value for the beginning of the Meld (Between 0-14): ");
		int x = reader.nextInt(); 
		System.out.println("Enter an y value for the beginning of the Meld (Between 0-14): ");
		int y = reader.nextInt(); 
		reader.close();
		Spot toPlace = spots[x][y].getSpot();
		
		if(toPlace!=null) {
			if(this.canWePlaceMeld(meld,x,y)==true) {
				for(int i=0;i<meld.getNumberOfTiles();i++) {
					Tile tile = meld.getTileInMeld(i);
					this.playTile(tile, x+i, y);
					//boardChanged(tile);
				}		
				meldsOnBoard.add(meld);
				numberOfMelds++;
			}
			else {
				System.out.println("Meld cannot be placed here. Please try a different Location. ");
			}
		}	
		
		meldsOnBoard.add(meld);
		numberOfTilesOnBoard = numberOfTilesOnBoard + meld.getMeldSize();
		numberOfMelds++;
	}*/
	
	public Meld getMeld(int i) {
		return meldsOnBoard.get(i);
	}
	
	public int getNumberOfMelds() {
		return numberOfMelds;
	}
	
	public Spot getNextSpot(int x,int y) {
		return spots[x+1][y];
		
	}
	
	public boolean checkIfValidMelds() {
		for(int i=0;i<meldsOnBoard.size(); i++) {
			if(meldsOnBoard.get(i).checkIfValidMeld() == false) {
				System.out.println("We got a false in board");
				return false;
			}
		}
		return true;
	}
		
		
	

}
