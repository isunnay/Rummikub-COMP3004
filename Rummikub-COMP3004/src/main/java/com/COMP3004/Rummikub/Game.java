package com.COMP3004.Rummikub;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
		AI1 ai1 = new AI1(deck);
		AI2 ai2 = new AI2(deck);
		AI3 ai3 = new AI3(deck);
		
		// Put the players into the player ArrayList
		allPlayers.add(human);
		allPlayers.add(ai1);
		allPlayers.add(ai2);
		allPlayers.add(ai3);
		
		//printAll();
		try {
			play();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			System.out.println("Human (Player 1) Won!");
		} else if (winner == 2) {
			System.out.println("AI 1 (Player 2) Won!");
		} else if (winner == 3) {
			System.out.println("AI 2 (Player 3) Won!");
		} else if (winner == 4) {
			System.out.println("AI 3 (Player 4) Won!");
		}
	}
	
	public void play() throws InterruptedException {
		int timesTriedPlaying = 0;
		while (anyWinners() == 0) {
			System.out.println(timesTriedPlaying);
			if (whosTurn() == 1) {
				System.out.println("Human Playing...");
				printAll();
				
				Scanner reader = new Scanner(System.in);
				System.out.println("Enter the tile you wish to play (0-" + (allPlayers.get(0).getHand().size-1) + "): ");
				int tileV = reader.nextInt();
				Tile tile = allPlayers.get(0).getHand().getTile(tileV);
				System.out.println("Enter the x-coordinate (0-14): ");
				int x = reader.nextInt();
				System.out.println("Enter the y-coordinate (0-14): ");
				int y = reader.nextInt();
				reader.close();
				
				this.board.playTile(tile, x, y);
				allPlayers.get(0).setTilesBeenPlayed(true); allPlayers.get(0).setTurnStatus(false);
				allPlayers.get(1).setTilesBeenPlayed(false); allPlayers.get(1).setTurnStatus(true);
			}else if (whosTurn() == 2 && timesTriedPlaying < 4) {
				System.out.println("AI1 Playing...");
				timesTriedPlaying++;
				TimeUnit.SECONDS.sleep(4);
				if (allPlayers.get(1).turnComplete(allPlayers.get(1).getHand()) == true) {
					allPlayers.get(1).setTilesBeenPlayed(true); allPlayers.get(1).setTurnStatus(false);
					allPlayers.get(2).setTilesBeenPlayed(false); allPlayers.get(2).setTurnStatus(true);
					timesTriedPlaying = 0;
					printAll();
				}
			}else if (whosTurn() == 3 && timesTriedPlaying < 4) {
				System.out.println("AI2 Playing...");
				timesTriedPlaying++;
				TimeUnit.SECONDS.sleep(4);
				if (allPlayers.get(2).turnComplete(allPlayers.get(2).getHand()) == true) {
					allPlayers.get(2).setTilesBeenPlayed(true); allPlayers.get(2).setTurnStatus(false);
					allPlayers.get(3).setTilesBeenPlayed(false); allPlayers.get(3).setTurnStatus(true);
					timesTriedPlaying = 0;
					printAll();
				}
				printAll();
			}else if (whosTurn() == 4 && timesTriedPlaying < 4) {
				System.out.println("AI3 Playing...");
				timesTriedPlaying++;
				TimeUnit.SECONDS.sleep(4);
				if (allPlayers.get(3).turnComplete(allPlayers.get(3).getHand()) == true) {
					allPlayers.get(3).setTilesBeenPlayed(true); allPlayers.get(3).setTurnStatus(false);
					allPlayers.get(0).setTilesBeenPlayed(false); allPlayers.get(0).setTurnStatus(true);
					timesTriedPlaying = 0;
					printAll();
				}
				printAll();
			} else if (timesTriedPlaying >= 4) {
				System.out.println("There seems to be a problem with our AI!");
				System.out.println("Either it had trouble playing or it hasn't been implemented yet.");
				System.out.println("Please try restarting the game.");
				break;
			} else {
				System.out.println("There seems to be a problem. Please try restarting the game.");
				break;
			}
		}endGame();
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
		System.out.println("Board:"); board.boardToString();
		System.out.println("Current turn: Player " + Integer.toString(whosTurn()));
		System.out.println("----------------------------------------");
		System.out.println("Human Hand: " + allPlayers.get(0).getHand().handToString());
		//System.out.println("AI 1 Hand: " + allPlayers.get(1).getHand().handToString());
		//System.out.println("AI 2 Hand: " + allPlayers.get(2).getHand().handToString());
		//System.out.println("AI 3 Hand: " + allPlayers.get(3).getHand().handToString());
	}
	
	public boolean inProgress() { return this.gameInProgress; }
	public Deck getDeck() { return this.deck; }
	public Board getBoard() { return this.board; }
	public ArrayList<PlayerType> getAllPlayers() { return this.allPlayers; }
	public PlayerType getPlayer(int i) { return this.getAllPlayers().get(i); }
	public int getPlayerCount() { return this.getAllPlayers().size(); }
	public int getWinner() { return anyWinners(); }
}
