package com.COMP3004.Rummikub;

import java.util.Scanner;
import java.util.ArrayList;

public class Board {
	int boardX = 15;
	int boardY = 15;
	int numberOfTilesOnBoard;
	public ArrayList<Meld> meldsOnBoard;
	int numberOfMelds;
	private ArrayList<Observer> observers;

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
		this.observers = new ArrayList<>();
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
		boardChanged(tile);
	}
	

	public void removeTile(int x, int y) {
		Tile tileToRemove = getTileAtSpot(x,y);
		Spot spot = getLocationOfTile(tileToRemove);
		spot.removeTile();
		tileToRemove.removeSpot(spot);
		numberOfTilesOnBoard--;
		boardChanged(tileToRemove);
		
		
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
		//boardChanged();
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
	
	//Check whether locations are available for meld to be placed in
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
				}		
				meldsOnBoard.add(meld);
				numberOfMelds++;
				boardChanged();
			}
			else {
				System.out.println("Meld cannot be placed here. Please try a different Location. ");
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
	
	public void registerObserver(Observer o) {
		observers.add(o);
	}
	
	public void removeObserver(Observer o) {
		int observerIndex = observers.indexOf(o);
		if(observerIndex>=0) {
			observers.remove(observerIndex);
		}
	}
	
	public void notifyObservers(Tile tile) {
		for(int i=0;i<observers.size();i++) {
			Observer observer = (Observer)observers.get(i);
			observer.update(tile);
		}
	}
	
	public void boardChanged(Tile tile) {
		notifyObservers(tile);
	}
		
		
	

}
