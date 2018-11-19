package com.COMP3004.Rummikub;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game implements Subject {
	// Variables
	private boolean gameInProgress = false;
	private Deck deck;
	private Board board;
	private ArrayList<PlayerType> allPlayers = new ArrayList<PlayerType>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
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
		
		// Ask how many players
		reader = new Scanner(System.in);
		System.out.println("How many human players will be playing? (1-4)");
		int amountOfPlayers = reader.nextInt();
		
		
		
		/*
		if (amountOfPlayers > 0 || amountOfPlayers <= 4) {
			for (int i = 0; i < amountOfPlayers; i++) {
				//allPlayers.add(new Human(deck, this));
				//observers.add(allPlayers.get(i));
				System.out.println("Human added to game...");
			}
		}*/
		
		// Fill in the rest with AI (random chance of each AI strategy)
		/*
		for (int i = allPlayers.size()3; i < (4-allPlayers.size()); i++) {
			int foo = (int) (Math.random() * 100);
			if (foo < 34) {
				//allPlayers.add(new AI1(deck, this));
				System.out.println("AI1");
			} else if (foo < 67){
				//allPlayers.add(new AI2(deck));
				System.out.println("AI2");
			} else {
				//allPlayers.add(new AI3(deck, this));
				System.out.println("AI3");
			}
			//observers.add(allPlayers.get(i));
			System.out.println("added to game...");
		}*/
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

	public void play() throws InterruptedException {
		notifyObservers();
		while (anyWinners() == 0) {
			if (whosTurn() == 1) {
				printAll();
				System.out.println("Player 1 Hand: " + allPlayers.get(0).getHand().handToString());
				if (allPlayers.get(0).myTurnStatus() == true) {
					allPlayers.get(0).play(reader, deck);
				}
				/*
				if (allPlayers.get(0).isAI() == true) {
					// Do something...
				}
				*/
			} else if (whosTurn() == 2) {
				// Nothing yet...
			} else if (whosTurn() == 3) {
				// Nothing yet...
			} else if (whosTurn() == 4) {
				// Nothing yet...
			}/* else if (timesTriedPlaying >= 4) {
				System.out.println("There seems to be a problem with our AI!");
				System.out.println("Either it had trouble playing or it hasn't been implemented yet.");
				System.out.println("Please try restarting the game.");
				System.out.println("----------------------------------------");
				break;
			}*/ else {
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
