import java.util.ArrayList;

public class Game {
	// Variables
	private boolean gameInProgress = false;
	private Deck deck;
	private Board board;
	private ArrayList<PlayerType> allPlayers = new ArrayList<PlayerType>();
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
		board = new Board();
		
		// Initialize the deck
		deck = new Deck();
		
		// Shuffle the deck
		deck.shuffleTiles();
		
		// Create all 4 players
		Human human = new Human(deck);
		// AI 1
		// AI 2
		// AI 3
		
		// Put the players into the player ArrayList
		allPlayers.add(human);
		// AI 1
		// AI 2
		// AI 3
	}
	
	private int anyWinners() {
		if (getPlayer(0).getHand().getNumTiles() == 0) {
			return 1;
		} else if (getPlayer(1).getHand().getNumTiles() == 0) {
			return 2;
		} else if (getPlayer(2).getHand().getNumTiles() == 0) {
			return 3;
		} else if (getPlayer(3).getHand().getNumTiles() == 0) {
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
	public Board getBoard() { return this.board; }
	public ArrayList<PlayerType> getAllPlayers() { return this.allPlayers; }
	public PlayerType getPlayer(int i) { return this.getAllPlayers().get(i); }
	public int getPlayerCount() { return this.getAllPlayers().size(); }
	public int getWinner() { return anyWinners(); }
	
}
