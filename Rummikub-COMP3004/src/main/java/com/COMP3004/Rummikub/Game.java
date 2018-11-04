package com.COMP3004.Rummikub;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game implements Subject{
	// Variables
	private boolean gameInProgress = false;
	private Deck deck;
	private Board board;
	private ArrayList<PlayerType> allPlayers = new ArrayList<PlayerType>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
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
		Human human = new Human(deck, this);
		observers.add(human);
		AI1 ai1 = new AI1(deck);
		AI2 ai2 = new AI2(deck);
		AI3 ai3 = new AI3(deck);
		
		// Put the players into the player ArrayList
		allPlayers.add(human);
		allPlayers.add(ai1);
		allPlayers.add(ai2);
		allPlayers.add(ai3);
		
		//printAll();
		/*try {
			play();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		notifyObservers();
		int timesTriedPlaying = 0;
		while (anyWinners() == 0) {
			if (whosTurn() == 1) {
				printAll();
				System.out.println("Human Playing...");
				
				// Ask for humans decision
				Scanner reader = new Scanner(System.in);
				System.out.println("Would you like to (P)lay your turn or (S)kip and draw a tile? " );
				char decision = reader.next().toUpperCase().charAt(0);
				if (decision == 'P') {
					while(true) {
						System.out.println("Would you like to play a (M)eld or an individual (T)ile? ");
						System.out.println("Type e to exit at any time");
						char nextDecision = reader.next().toUpperCase().charAt(0);
						
						if(nextDecision== 'E') {
							break;
						}
						else if(nextDecision == 'M') {
							printAll();
							// Variables
							int tileChoice = 0;
							Meld meld = new Meld();
							// Create meld within hand
							allPlayers.get(0).getHand().createMeld();
							// Ask for tiles for meld on loop
							while(tileChoice >= 0) {
								System.out.println("Current Meld: " + meld.meldToString());
								// Print hand out
								System.out.println("Human Hand: " + allPlayers.get(0).getHand().handToString());
								System.out.println("Which tile from your hand (Any negative value will close the sequence | tiles from 0-" + (allPlayers.get(0).getHand().getNumTiles()-1) + "): ");
								tileChoice = reader.nextInt();
								if (tileChoice >= 0) {
									meld.addTile(allPlayers.get(0).getHand().getTile(tileChoice));
								}
							}
							// Check if valid meld
							if (tileChoice < 0 && meld.checkIfValidMeld() == true) {
								allPlayers.get(0).playMeld(meld,reader);
								notifyObservers();
								printAll();
							} 
							else {
								System.out.println("Invalid meld. Please try again.");
								System.out.println("----------------------------------------");
								tileChoice = 0;
							}
						}
						else if(nextDecision == 'T') {	
							
							//System.out.println("Human Hand: " + allPlayers.get(0).getHand().handToString());
							System.out.println("Which tile from your hand (Any negative value will close the sequence | tiles from 0-" + (allPlayers.get(0).getHand().getNumTiles()-1) + "): ");
							int tileChoice = reader.nextInt();
							Tile tileToAdd = allPlayers.get(0).getHand().getTile(tileChoice);
							System.out.println("Please enter an available location to play the tile.");
							System.out.println("Enter an x value for the spot(Between 0-14): ");
							int x = reader.nextInt(); 
							System.out.println("Enter a y value for the spot(Between 0-14): ");
							int y = reader.nextInt(); 
							allPlayers.get(0).addTile(tileToAdd, x, y);	
							printAll();
							notifyObservers();
							//allPlayers.get(0).setTilesBeenPlayed(true); allPlayers.get(0).setTurnStatus(false);
							//allPlayers.get(1).setTilesBeenPlayed(false); allPlayers.get(1).setTurnStatus(true);
						}
						
						else {
							System.out.println("You may have entered the wrong character. Try again (M / T).");
							nextDecision = reader.next().toUpperCase().charAt(0);
						}
					}
					allPlayers.get(0).setTilesBeenPlayed(true); allPlayers.get(0).setTurnStatus(false);
					allPlayers.get(1).setTilesBeenPlayed(false); allPlayers.get(1).setTurnStatus(true);
					
				}	
				else if (decision == 'S') {
					// Draw tile
					allPlayers.get(0).getHand().dealTile(deck);
					System.out.println("Turn ended: Human has decided to draw a tile.");
					//allPlayers.get(0).getHand().sortHand();
					//System.out.println("Human Hand: " + allPlayers.get(0).getHand().handToString());
					System.out.println("----------------------------------------");
					// Switch turn
					allPlayers.get(0).setTilesBeenPlayed(true); allPlayers.get(0).setTurnStatus(false);
					allPlayers.get(1).setTilesBeenPlayed(false); allPlayers.get(1).setTurnStatus(true);
					// Close the reader
					reader.close();
					
				} else {
					System.out.println("You may have entered the wrong character. Try again (P / S).");
					decision = reader.next().toUpperCase().charAt(0);
				}
			}
			else if (whosTurn() == 2 && timesTriedPlaying < 4) {
				printAll();
				System.out.println("Try #" + (timesTriedPlaying+1) + ": AI1 Playing...");
				timesTriedPlaying++;
				TimeUnit.SECONDS.sleep(4);
				if (allPlayers.get(1).turnComplete(allPlayers.get(1).getHand()) == true) {
					allPlayers.get(1).setTilesBeenPlayed(true); allPlayers.get(1).setTurnStatus(false);
					allPlayers.get(2).setTilesBeenPlayed(false); allPlayers.get(2).setTurnStatus(true);
					timesTriedPlaying = 0;
					printAll();
				}System.out.println("----------------------------------------");
			}else if (whosTurn() == 3 && timesTriedPlaying < 4) {
				printAll();
				System.out.println("Try #" + (timesTriedPlaying+1) + ": AI2 Playing...");
				timesTriedPlaying++;
				TimeUnit.SECONDS.sleep(4);
				if (allPlayers.get(2).turnComplete(allPlayers.get(2).getHand()) == true) {
					allPlayers.get(2).setTilesBeenPlayed(true); allPlayers.get(2).setTurnStatus(false);
					allPlayers.get(3).setTilesBeenPlayed(false); allPlayers.get(3).setTurnStatus(true);
					timesTriedPlaying = 0;
				}System.out.println("----------------------------------------");
			}else if (whosTurn() == 4 && timesTriedPlaying < 4) {
				printAll();
				System.out.println("Try #" + (timesTriedPlaying+1) + ": AI3 Playing...");
				timesTriedPlaying++;
				TimeUnit.SECONDS.sleep(4);
				if (allPlayers.get(3).turnComplete(allPlayers.get(3).getHand()) == true) {
					allPlayers.get(3).setTilesBeenPlayed(true); allPlayers.get(3).setTurnStatus(false);
					allPlayers.get(0).setTilesBeenPlayed(false); allPlayers.get(0).setTurnStatus(true);
					timesTriedPlaying = 0;
				}System.out.println("----------------------------------------");
			} else if (timesTriedPlaying >= 4) {
				System.out.println("There seems to be a problem with our AI!");
				System.out.println("Either it had trouble playing or it hasn't been implemented yet.");
				System.out.println("Please try restarting the game.");
				System.out.println("----------------------------------------");
				break;
			} else {
				System.out.println("There seems to be a problem. Please try restarting the game.");
				System.out.println("----------------------------------------");
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

	public void registerObserver(Observer o) {
		observers.add(o);
	}
	
	public void removeObserver(Observer o) {
		int observerIndex = observers.indexOf(o);
		if(observerIndex>=0) {
			observers.remove(observerIndex);
		}
	}
	
	public void notifyObservers() {
		for(int i=0;i<observers.size();i++) {
			Observer observer = (Observer)observers.get(i);
			observer.update(board);
		}
	}
	

	
}
