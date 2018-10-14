import java.util.ArrayList;

public class Game {
	// Variables
	//private Board board; (board class not done yet)
	private boolean gameInProgress = false;
	private Deck deck;
	private ArrayList<Hand> allPlayers = new ArrayList<Hand>();
	
	// Constructor
	public Game () {
		initializeGame();
	}
	
	// Initialize the game
	private void initializeGame() {
		// Start the game
		gameInProgress = true;
		
		// Initialize the game board
		// board = new Board(); (board class not done yet)
		
		// Create all 4 players
		Hand p1 = new Hand(); Hand p2 = new Hand();
		Hand p3 = new Hand(); Hand p4 = new Hand();
		
		// Put the players into the player ArrayList
		allPlayers.add(p1); allPlayers.add(p2);
		allPlayers.add(p3); allPlayers.add(p4);
		
		// Initialize the deck
		deck = new Deck();
		
		// Shuffle the deck
		deck.shuffleTiles();
		
		// Draw tiles (Coming soon)
	}
	
	/*
	 * 
	 * More functions to come
	 * 
	 */
	
	private void endGame() {
		// Finish the game
		gameInProgress = false;
		
		// Calculate the scores (Coming soon)
		// Am i missing something else that happens at the end of the game?
	}
	
	public boolean inProgress() { return this.gameInProgress; }
	//public Board getBoard() { return this.board; } (board class not done yet)
	public Deck getDeck() { return this.deck; }
	public ArrayList<Hand> getAllPlayers() { return this.allPlayers; }
	public Hand getPlayer(int i) { return this.getAllPlayers().get(i); }
	public int getPlayerCount() { return this.getAllPlayers().size(); }
	
}
