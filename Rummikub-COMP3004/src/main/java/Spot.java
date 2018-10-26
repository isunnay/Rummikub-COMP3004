
public class Spot {
	int x;
	int y;
	Tile tile;
	boolean isTaken;
	
	public Spot(int x, int y) {
		this.x = x;
		this.y = y;
		tile = null;
		isTaken = false;
	}
	
	public Spot getSpot() {
		return this; 
	}
	
	public int getSpotX() {
		return this.x;
	}
	
	public int getSpotY() {
		return this.y;
	}

	public boolean isSpotEmpty() {
		if(this.tile == null) {
			return true;
		}
		return false;
	}

	public void playTile(Tile tile) {
		if(this.tile==null) {
			this.tile = tile;
			System.out.println("Tile" + tile.getTileName() + "now occupies this spot-> x: " + this.getSpotX() + "y: " + this.getSpotY());
		}
		else {
			System.out.println("This Spot is taken!");
			isTaken = true;	
		}	
	}
	
	public void removeTile() {
		if(this.tile!=null) {
			this.tile=null;
		}
		else {
			System.out.println("This spot is already free");
		}
	}
 

}
