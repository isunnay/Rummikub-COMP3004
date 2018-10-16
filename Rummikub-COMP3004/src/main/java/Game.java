import java.util.ArrayList;

public class Game {
	// Variables
	private boolean gameInProgress = false;
	private Deck deck;
	//private Board board; (board class not done yet)
	private ArrayList<Hand> allPlayers = new ArrayList<Hand>();
	//private GUI GUI; (GUI class not done yet)
	
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
		
		// Create everyones hand
		p1.createHand(deck); p2.createHand(deck);
		p3.createHand(deck); p4.createHand(deck);
	}
	
	private int anyWinners() {
		if (getPlayer(0).getNumTiles() == 0) {
			return 1;
		} else if (getPlayer(1).getNumTiles() == 0) {
			return 2;
		} else if (getPlayer(2).getNumTiles() == 0) {
			return 3;
		} else if (getPlayer(3).getNumTiles() == 0) {
			return 4;
		} else {
			return 0;
		}
	}
	
	private void endGame() {
		// Variables
		int winner = getWinner();
		
		// Finish the game
		gameInProgress = false;
		
		// Winning message + calc score
		if (winner == 1) {
			System.out.println("Human (Player 1) Won!");
		} else if (winner == 2) {
			System.out.println("AI 1 (Player 2) Won!");
		} else if (winner == 3) {
			System.out.println("AI 2 (Player 3) Won!");
		} else if (winner == 4) {
			System.out.println("AI 3 (Player 4) Won!");
		}
	}
	
	public boolean inProgress() { return this.gameInProgress; }
	public Deck getDeck() { return this.deck; }
	//public Board getBoard() { return this.board; } (board class not done yet)
	public ArrayList<Hand> getAllPlayers() { return this.allPlayers; }
	public Hand getPlayer(int i) { return this.getAllPlayers().get(i); }
	public int getPlayerCount() { return this.getAllPlayers().size(); }
	public int getWinner() { return anyWinners(); }
	
}
