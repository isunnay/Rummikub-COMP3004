
public class Board {
	int boardX = 15;
	int boardY = 15;
	int numberOfTilesOnBoard;

	private Spot[][] spots = new Spot[15][15];
	
	public Board() {
		for(int i=0; i<spots.length; i++){
            for(int j=0; j<spots.length; j++){
                this.spots[i][j] = new Spot(i, j);
            }
        }
		numberOfTilesOnBoard = 0;
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
		//Spot newSpot = getSpot(newX, newY);
		oldSpot.removeTile();
		tile.removeSpot(oldSpot);
		newSpot.playTile(tile);
		tile.setSpot(newSpot);
	}
	

}
