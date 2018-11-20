package com.COMP3004.Rummikub;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game implements Subject {
	// Variables
	private boolean gameInProgress = false;
	private Deck deck;
	private Board board;
	private ArrayList<PlayerType> allPlayers;
	private ArrayList<Observer> observers;
	Scanner reader;

	// Constructor
	public Game() {
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
		
		//Re-init arraylists
		allPlayers = new ArrayList<PlayerType>();
		observers = new ArrayList<Observer>();
		
		// Ask how many players
		reader = new Scanner(System.in);
		int amountOfPlayers;
		while (true) {
			System.out.println("How many human players will be playing? (1-4)");
			amountOfPlayers = reader.nextInt();
			
			if (amountOfPlayers > 4 || amountOfPlayers < 0) {
				System.out.println("Invalid input. Please try again.");
			} else {
				break;
			}
		}
		
		// Add human's based on input
		for (int i = 0; i < amountOfPlayers; i++) {
			allPlayers.add(new Human(deck, this));
			observers.add(allPlayers.get(i));
			//System.out.println("Human added to game...");
		}
	
		// Fill in the rest with AI (random chance of each AI strategy)
		for (int i = allPlayers.size(); i < 4; i++) {
			int foo = (int) (Math.random() * 100);
			if (foo < 34) {
				allPlayers.add(new AI1(deck, this));
				//System.out.println("AI1");
			} else if (foo < 67){
				//allPlayers.add(new AI2(deck)); /* Uncomment when other strategies are complete */
				allPlayers.add(new AI1(deck, this));
				//System.out.println("AI2");
			} else {
				//allPlayers.add(new AI3(deck, this)); /* Uncomment when other strategies are complete */
				allPlayers.add(new AI1(deck, this));
				//System.out.println("AI3");
			}
			observers.add(allPlayers.get(i));
			//System.out.println("added to game...");
		}
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

		// Winning message + calculate score
		if (winner == 1) {
			System.out.println("Player 1 Won!");
		} else if (winner == 2) {
			System.out.println("Player 2 Won!");
		} else if (winner == 3) {
			System.out.println("Player 3 Won!");
		} else if (winner == 4) {
			System.out.println("Player 4 Won!");
		}
	}
	
	public void playTurn(int i) {
		printAll();
		// Play if human
		if (allPlayers.get(i).myTurnStatus() == true && allPlayers.get(i).isAI() == false) {
			//System.out.println("Player " + (i+1) + " is playing...");
			System.out.println("Player " + (i+1) + "'s Hand: " + allPlayers.get(i).getHand().handToString());
			try {
				allPlayers.get(i).play(reader, deck);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Play if AI
		if (allPlayers.get(i).myTurnStatus() == true && allPlayers.get(i).isAI() == true) {
			//System.out.println("Player " + (i+1) + " is playing...");
			allPlayers.get(i).play(reader);
			
			if (allPlayers.get(i).hasTilesBeenPlayed() == false) {
				Tile t = allPlayers.get(i).getHand().dealTile(deck);
				System.out.println("Turn ended: Player " + (i+1) + " has decided to draw a tile.");
				System.out.println("Tile drawn: " + t.tileToString());
			}
		}
		// Sets next players turn
		if (i <= 2) {
			allPlayers.get(i+1).setTilesBeenPlayed(false);
			allPlayers.get(i+1).setTurnStatus(true);
		} else {
			allPlayers.get(0).setTilesBeenPlayed(false);
			allPlayers.get(0).setTurnStatus(true);
		}
	}

	public void play() throws InterruptedException {
		while (anyWinners() == 0) {
			if (whosTurn() == 1) {
				// Nothing yet...
				System.out.println("Player 1's turn.");
				playTurn(0);
				notifyObservers();
			} else if (whosTurn() == 2) {
				// Nothing yet...
				System.out.println("Player 2's turn.");
				playTurn(1);
				notifyObservers();
			} else if (whosTurn() == 3) {
				// Nothing yet...
				System.out.println("Player 3's turn.");
				playTurn(2);
				notifyObservers();
			} else if (whosTurn() == 4) {
				// Nothing yet...
				System.out.println("Player 4's turn.");
				playTurn(3);
				notifyObservers();
			} else {
				System.out.println("There seems to be a problem. Please try restarting the game.");
				System.out.println("----------------------------------------");
				break;
			}
		}
		endGame();
	}

	public int whosTurn() {
		if (allPlayers.get(0).myTurnStatus() == true) {
			return 1;
		} else if (allPlayers.get(1).myTurnStatus() == true) {
			return 2;
		} else if (allPlayers.get(2).myTurnStatus() == true) {
			return 3;
		} else if (allPlayers.get(3).myTurnStatus() == true) {
			return 4;
		} else {
			return -1;
		}
	}

	public void printAll() {
		System.out.println("Board:");
		board.boardToString();
		System.out.println("Current turn: Player " + Integer.toString(whosTurn()));
		System.out.println("----------------------------------------");
	}

	public boolean inProgress() {
		return this.gameInProgress;
	}

	public Deck getDeck() {
		return this.deck;
	}

	public Board getBoard() {
		return this.board;
	}

	public ArrayList<PlayerType> getAllPlayers() {
		return this.allPlayers;
	}

	public PlayerType getPlayer(int i) {
		return this.getAllPlayers().get(i);
	}

	public int getPlayerCount() {
		return this.getAllPlayers().size();
	}

	public int getWinner() {
		return anyWinners();
	}

	public void registerObserver(Observer o) {
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		int observerIndex = observers.indexOf(o);
		if (observerIndex >= 0) {
			observers.remove(observerIndex);
		}
	}

	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer) observers.get(i);
			observer.update(board);
		}
	}

}
