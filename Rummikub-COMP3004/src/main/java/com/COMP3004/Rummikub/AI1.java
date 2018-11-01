public class AI1 implements PlayerType {
	Hand h;
	private boolean myTurn = false;
	private boolean isTurn = false;

	
	public AI1(Deck deck) {
		h = new Hand();
		h.createHand(deck);
	}
	
	public Hand getHand() { return this.h; }
	
	public boolean myTurnStatus() { return this.myTurn; }
	
	public boolean hasTilesBeenPlayed() { return this.hasTileBeenPlaced; }

	public boolean turnComplete(Hand h) {
		// Initial Meld: ASAP
		// Gameplay: Plays all tiles when possible
		return false;
	}

	

}
