
 public class Player {
	
 	
	private boolean isTurn = false;
	private boolean hasTileBeenTaken = false;
	private boolean hasTileBeenPlaced = false;

 	public Player () {
	}
	
	public boolean getIsItPlayersTurn () {
		return this.isTurn; 
	}
	
	public void setIsItPlayersTurn(boolean turn) { 
		this.isTurn  = turn; 
	}
	
	public boolean getHasTileBeenTaken () { 
		return this.hasTileBeenTaken; 
	}
	
	public void setHasTileBeenTaken(boolean tileTaken) {  
		this.hasTileBeenTaken  = tileTaken; 
	}
	
	public boolean getHasTileBeenPlaced () { 
		return this.hasTileBeenPlaced; 
	}
	
	public void setHasTileBeenPlaced(boolean tilePlaced) { 
		this.hasTileBeenPlaced  = tilePlaced; 
	}
	

}
