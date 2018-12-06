package com.COMP3004.Rummikub.models;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.COMP3004.Rummikub.controller.RummikubController;
import com.COMP3004.Rummikub.models.Hand.SortByValue;

public class AI1 implements PlayerType {
	Hand h;
	private boolean initialMeldPlayed = false;
	private boolean myTurn = false;
	private boolean hasTileBeenPlaced = false;
	public ArrayList<Meld> melds;
	public Subject game;
	public ArrayList<Spot> spotsTaken;
	public ArrayList<Tile> turnTiles;
	public ArrayList<Meld> turnMelds;
	public ArrayList<Tile> turnMoves;
	public ArrayList<Tile> usedInMeld;
	public int turnPoints;
	private Board board;
	private boolean isAI = true;

	
	public AI1(Deck deck, RummikubController rummikubController) {
		System.out.println("We got an AI1");
		h = new Hand();
		h.createHand(deck);
		//h.sortHand();
		melds = new ArrayList<Meld>();
		rummikubController.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();
		turnMoves = new ArrayList<Tile>();
		usedInMeld = new ArrayList<Tile>();
	}
	
	public Hand getHand() { return this.h; }
	
	public boolean myTurnStatus() { return this.myTurn; }
	
	public void setTurnStatus(boolean b) { this.myTurn = b; }
	
	public boolean hasTilesBeenPlayed() { return this.hasTileBeenPlaced; }
	
	public void setTilesBeenPlayed(boolean b) { this.hasTileBeenPlaced = b; }

	public boolean turnComplete(Hand h) {
		// Initial Meld: ASAP
		// Gameplay: Plays all tiles when possible
		//h.meldExists();
		return false;
	}

	@Override
	public void update(Board board) {
		// TODO Auto-generated method stub
		this.spotsTaken = board.filledSpots;
		this.board = board;	
		turnTiles.clear();
		turnMelds.clear();
		turnMoves.clear();
		melds.clear();
		usedInMeld.clear();
		turnPoints = 0;
	}
	@Override
	public void play(Scanner reader) {
		//System.out.println("Play");
		this.findAllMelds();
		//System.out.println(melds.size());
		setTurnPoints();
		System.out.println(getTurnPoints());
		if(this.hasInitialMeldBeenPlayed() == false) {
			if(getTurnPoints()>=5) {
				if(melds.size()>0) {
					for(int i=0;i<melds.size();i++) {
						playMeld(melds.get(i),reader);
						//this.hasTileBeenPlaced = true;
						this.setTilesBeenPlayed(true);
					}
					this.setHasInitialMeldBeenPlayed(true);
					//this.setTurnStatus(false);
				}
				
			}
		}
		else {
			if(melds.size()>0) {
				for(int i=0;i<melds.size();i++) {
					playMeld(melds.get(i),reader);
					//this.hasTileBeenPlaced = true;
					this.setTilesBeenPlayed(true);
				}
				//this.setTurnStatus(false);
			}
		}
	}
	
	public void setTurnPoints() {
		for(int i=0; i<melds.size();i++) {
			turnPoints += melds.get(i).getMeldValue();	
		}
	}

	
	public int getTurnPoints() {
		return turnPoints;
	}
	
	
	public void playMeld(Meld meld, Scanner reader) {
		System.out.println("Playing1");
		int x = ThreadLocalRandom.current().nextInt(0, 12 + 1);
		int y = ThreadLocalRandom.current().nextInt(0, 12 + 1);
	//	System.out.println(x);
	//	System.out.println(y);
		Spot beginningSpot = board.getSpot(x, y);
		System.out.println("Playing2");
		if(beginningSpot!=null) {
			if(canWePlaceMeld(meld,x,y)==true) {
				for(int i=0;i<meld.getNumberOfTiles();i++) {
					
					Tile tile = meld.getTileInMeld(i);
					Spot spot = board.getSpot(x+i,y);
					spot.playTile(tile);
					tile.setSpot(spot);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
					h.removeTile(tile);
				}
				System.out.println("Playing3");
				board.meldsOnBoard.add(meld);
				board.numberOfMelds++;	
				turnMelds.add(meld);
			}
			else {
				System.out.println("Meld cannot be placed here. Please try a different Location. ");
				playMeld(meld, reader);
			}
		}
		
	}

	@Override
	public void addTile(Tile tile, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/*public boolean canWePlaceMeld(Meld meld, int x, int y) {
		for(int a=0;a<meld.getMeldSize();a++) {
			if(x+a<board.getX()) {
				//int nextX = x+meld.getMeldsize;
				if(board.getSpot(x-1, y).isTaken != true && board.getSpot(x+meld.getMeldSize(), y).isTaken != true) {
					Spot spot = board.getSpot(x+a, y);
					if(this.spotsTaken.contains(spot)) {
						return false;
						}
				}
				else {
					System.out.println("ERROR: Your meld cannot be touching other melds.");
					return false;
				}
			}
			else {
				System.out.println("ERROR: Make sure your meld is placed within the board spots.");
				return false;
			}
		}
		return true;	
	}*/
	
	public boolean canWePlaceMeld(Meld meld, int x, int y) {
		// Spot beginningSpot = board.getSpot(x, y);
		for (int a = 0; a < meld.getMeldSize(); a++) {
			if (x + a >= board.getSpot(0, 0).getSpotX() && x + meld.getMeldSize() <= board.getX()) {
				if (x == 0) {
					if (board.getSpot(x + meld.getMeldSize(), y).isTaken = true) {
						Spot spot = board.getSpot(x + a, y);
						if (this.spotsTaken.contains(spot)) {
							return false;
						}
					} else {
						System.out.println("ERROR: Your meld cannot be touching other melds.");
						return false;
					}
				} else if (x == 12) {
					if (board.getSpot(x + meld.getMeldSize() - 1, y).isTaken = true) {
						Spot spot = board.getSpot(x + a, y);
						if (this.spotsTaken.contains(spot)) {
							return false;
						}
					} else {
						System.out.println("ERROR: Your meld cannot be touching other melds.");
						return false;
					}
				} else {
					if (board.getSpot(x - 1, y).isTaken != true
							&& board.getSpot(x + meld.getMeldSize(), y).isTaken != true) {
						Spot spot = board.getSpot(x + a, y);
						if (this.spotsTaken.contains(spot)) {
							return false;
						}
					} else {
						System.out.println("ERROR: Your meld cannot be touching other melds.");
						return false;
					}
				}
			} else {
				System.out.println("ERROR: Make sure your meld is placed within the board spots.");
				return false;
			}
		}
		return true;
	}
	
	public class SortByValue implements Comparator<Tile> {
		public int compare(Tile x, Tile y) {
			return x.getValue() - y.getValue();
		}
	}
	
	public void sortByValue(ArrayList<Tile> hand) {
		Collections.sort(hand, new SortByValue());
	}
	
	@Override
	public void undoTurn() {
		// TODO Auto-generated method stub
		if(turnTiles.size()>0) {	
			for(int i=0; i<turnTiles.size();i++) {
				undoAddTile(turnTiles.get(i));
			}
		}
		if(turnMelds.size()>0) {	
			for(int x=0; x<turnMelds.size();x++) {
				Meld meld = turnMelds.get(x);
				undoPlayMeld(meld);
			}
		}
		
		h.sortHand();
		turnTiles.clear();
		turnMelds.clear();
		turnMoves.clear();
		
	}

	@Override
	public void undoAddTile(Tile tile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undoPlayMeld(Meld meld) {
		for(int i=0; i<meld.getMeldSize();i++) {
			Tile tile = meld.getTileInMeld(i);
			Spot spot = tile.getSpot();
			spot.removeTile();
			tile.removeSpot(spot);
			board.numberOfTilesOnBoard--;
			board.filledSpots.remove(spot);
			h.addTile(tile);
		}
		board.meldsOnBoard.remove(meld);
		board.numberOfMelds--;	
		meld=null;
		
	}

	@Override
	public void moveTile(Tile tile, Spot newSpot) {
		// TODO Auto-generated method stub
		
	}
	public void findAllMelds() {
		findAllSets();
		findAllRuns();
		
		
	}
	
	/*public void findAllSets() {
		Meld tempMeld = new Meld();
		ArrayList<Integer> completedValues = new ArrayList<Integer>();

		for (int i = 0; i < this.h.size; i++) {
			Tile aTile = this.h.getTile(i);
			tempMeld.addTile(aTile);
			int currValue = this.h.getTile(i).getValue();
			for (int x = 0; x < this.h.size; x++) {
				Tile toCompare = this.h.getTile(x);
				//System.out.println(toCompare.getColour());
				if (toCompare.getValue() == currValue && aTile.getColour() != toCompare.getColour() && !(completedValues.contains(toCompare.getValue()))) {
					tempMeld.addTile(toCompare);
					// tempMeld.addTile(toCompare);
				}
			}
			completedValues.add(currValue);
			//System.out.println(tempMeld.meldToString());
			if (tempMeld.getMeldSize() > 2 && tempMeld.getMeldSize()<5) {
				if (tempMeld.isValidSet() == true) {
					melds.add(tempMeld);
				}
				else {
					tempMeld = new Meld();
				}
			}
			tempMeld = new Meld();
			

		}
	}*/
	
	public void findAllSets() {
		Meld tempMeld = new Meld();
		ArrayList<Integer> completedValues = new ArrayList<Integer>();
		ArrayList<Tile> clone = (ArrayList<Tile>) this.h.getPlayerHand().clone();

		for (int i = 0; i < clone.size(); i++) {
			//if(!(usedInMeld.contains(this.h.getTile(i)))) {
				Tile aTile = clone.get(i);
				tempMeld.addTile(aTile);
				//System.out.println(aTile.tileToString());
				int currValue = aTile.getValue();
				for (int x = i; x < clone.size(); x++) {
					Tile toCompare = clone.get(x);
					if (toCompare.getValue() == currValue && aTile.getColour() != toCompare.getColour() && !(completedValues.contains(toCompare.getValue())) && !(usedInMeld.contains(toCompare))) {
							tempMeld.addTile(toCompare);
							//System.out.println("We in");
							//usedInMeld.add(toCompare);
							clone.remove(x);
					}
				}
			//	System.out.println(tempMeld.meldToString());
				//completedValues.add(currValue);
				//System.out.println(tempMeld.meldToString());
				
				if (tempMeld.getMeldSize() > 2 && tempMeld.getMeldSize()<5) {
					if (tempMeld.isValidSet() == true) {
						melds.add(tempMeld);
						clone.remove(i);
						for(int y=0; y<tempMeld.getMeldSize();y++) {
							usedInMeld.add(tempMeld.getTileInMeld(y));
						}
						//System.out.println(clone);
					}
					else {
						tempMeld = new Meld();
					//	for(int a=0; a<tempMeld.getMeldSize(); a++) {
					//		usedInMeld.remove(tempMeld.getTileInMeld(a));
					//	}
					}
				}
				tempMeld = new Meld();
		}
	//	}
	}
	
	
	
	public void findAllRuns() {
		int numRuns = 0;
		ArrayList<Tile> redTiles = new ArrayList<Tile>();
		ArrayList<Tile> greenTiles = new ArrayList<Tile>();
		ArrayList<Tile> blueTiles = new ArrayList<Tile>();
		ArrayList<Tile> orangeTiles = new ArrayList<Tile>();

		for (int i = 0; i < this.h.size; i++) {
			Tile tile = this.h.getTile(i);
			if (tile.getColour() == "Green") {
				greenTiles.add(tile);
			} else if (tile.getColour() == "Red") {
				redTiles.add(tile);
			} else if (tile.getColour() == "Blue") {
				blueTiles.add(tile);
			} else if (tile.getColour() == "Orange") {
				orangeTiles.add(tile);
			}
		}

		if (redTiles.size() > 2) {
			Meld tempMeld = new Meld();
			for (int i = 0; i <= redTiles.size() - 1; i++) {
				
				Tile tile = redTiles.get(i);
				//System.out.println(tile.tileToString());
				if(!(usedInMeld.contains(tile))) {
				// System.out.println(tile.tileToString());
					if (i != redTiles.size() - 1) {
						if (redTiles.get(i).getValue() != redTiles.get(i + 1).getValue()) {
							if (redTiles.get(i + 1).getValue() - tile.getValue() == 1) {
								// System.out.println("Subtracting red");
								tempMeld.addTile(tile);
							} else if (tempMeld.getMeldSize() < 3 && redTiles.get(i + 1).getValue() - tile.getValue() != 1) {
								tempMeld.getTiles().clear();
							} else if (tempMeld.getMeldSize() >= 3 && redTiles.get(i + 1).getValue() - tile.getValue() != 1) {
								tempMeld.addTile(tile);
								// System.out.println("WERE IN THE LOOP");
								melds.add(tempMeld);
								// tempMeld.getTiles().clear();
								tempMeld = new Meld();
							}
						}
	
					} else if (i == redTiles.size() - 1) {
						
						int num = 0;
						if (!(usedInMeld.contains(redTiles.get(i-1))) && redTiles.get(i).getValue() != redTiles.get(i - 1).getValue()) {
							
							num = 1;
							if (redTiles.get(i).getValue() - redTiles.get(i - 1).getValue() == 1) {
								//System.out.println("WE ARE IN THE LAST ONE 1");
								if (tempMeld.getMeldSize() >= 2) {
									tempMeld.addTile(redTiles.get(i));
									melds.add(tempMeld);
								}
							} else {
								// System.out.println("BLAHBLAHBLAH");
							}
						} else if ( redTiles.get(i).getValue() != redTiles.get(i - 2).getValue() && num != 1) {
							//System.out.println("WE ARE IN THE LAST ONE 2");
							num = 2;
							if (redTiles.get(i).getValue() - redTiles.get(i - 2).getValue() == 1) {
								if (tempMeld.getMeldSize() >= 2) {
									tempMeld.addTile(redTiles.get(i));
									melds.add(tempMeld);
								}
							}
						} else {
							System.out.println("Impossible. ");
						}
					}
				}
				else {
					//System.out.println("Already used: " + tile.tileToString()) ;
					if(tempMeld.isValidRun() == true) {
						melds.add(tempMeld);	
					}
					
					tempMeld = new Meld();
				}
				//System.out.println(tempMeld.meldToString());
			}
		}
		

		if (greenTiles.size() > 2) {
			Meld tempMeld = new Meld();
			for (int i = 0; i <= greenTiles.size() - 1; i++) {
				
				Tile tile = greenTiles.get(i);
				//System.out.println(tile.tileToString());
				if(!(usedInMeld.contains(tile))) {
				// System.out.println(tile.tileToString());
					if (i != greenTiles.size() - 1) {
						if (greenTiles.get(i).getValue() != greenTiles.get(i + 1).getValue()) {
							if (greenTiles.get(i + 1).getValue() - tile.getValue() == 1) {
								// System.out.println("Subtracting green");
								tempMeld.addTile(tile);
							} else if (tempMeld.getMeldSize() < 3 && greenTiles.get(i + 1).getValue() - tile.getValue() != 1) {
								tempMeld.getTiles().clear();
							} else if (tempMeld.getMeldSize() >= 3 && greenTiles.get(i + 1).getValue() - tile.getValue() != 1) {
								tempMeld.addTile(tile);
								// System.out.println("WERE IN THE LOOP");
								melds.add(tempMeld);
								// tempMeld.getTiles().clear();
								tempMeld = new Meld();
							}
						}
	
					} else if (i == greenTiles.size() - 1) {
						
						int num = 0;
						if (!(usedInMeld.contains(greenTiles.get(i-1))) && greenTiles.get(i).getValue() != greenTiles.get(i - 1).getValue()) {
							
							num = 1;
							if (greenTiles.get(i).getValue() - greenTiles.get(i - 1).getValue() == 1) {
								//System.out.println("WE ARE IN THE LAST ONE 1");
								if (tempMeld.getMeldSize() >= 2) {
									tempMeld.addTile(greenTiles.get(i));
									melds.add(tempMeld);
								}
							} else {
								// System.out.println("BLAHBLAHBLAH");
							}
						} else if ( greenTiles.get(i).getValue() != greenTiles.get(i - 2).getValue() && num != 1) {
							//System.out.println("WE ARE IN THE LAST ONE 2");
							num = 2;
							if (greenTiles.get(i).getValue() - greenTiles.get(i - 2).getValue() == 1) {
								if (tempMeld.getMeldSize() >= 2) {
									tempMeld.addTile(greenTiles.get(i));
									melds.add(tempMeld);
								}
							}
						} else {
							System.out.println("Impossible. ");
						}
					}
				}
				else {
					//System.out.println("Already used: " + tile.tileToString()) ;
					if(tempMeld.isValidRun() == true) {
						melds.add(tempMeld);	
					}
					
					tempMeld = new Meld();
				}
				//System.out.println(tempMeld.meldToString());
			}
		}

		if (blueTiles.size() > 2) {
			Meld tempMeld = new Meld();
			for (int i = 0; i <= blueTiles.size() - 1; i++) {
				
				Tile tile = blueTiles.get(i);
				//System.out.println(tile.tileToString());
				if(!(usedInMeld.contains(tile))) {
				// System.out.println(tile.tileToString());
					if (i != blueTiles.size() - 1) {
						if (blueTiles.get(i).getValue() != blueTiles.get(i + 1).getValue()) {
							if (blueTiles.get(i + 1).getValue() - tile.getValue() == 1) {
								// System.out.println("Subtracting blue");
								tempMeld.addTile(tile);
							} else if (tempMeld.getMeldSize() < 3 && blueTiles.get(i + 1).getValue() - tile.getValue() != 1) {
								tempMeld.getTiles().clear();
							} else if (tempMeld.getMeldSize() >= 3 && blueTiles.get(i + 1).getValue() - tile.getValue() != 1) {
								tempMeld.addTile(tile);
								// System.out.println("WERE IN THE LOOP");
								melds.add(tempMeld);
								// tempMeld.getTiles().clear();
								tempMeld = new Meld();
							}
						}
	
					} else if (i == blueTiles.size() - 1) {
						
						int num = 0;
						if (!(usedInMeld.contains(blueTiles.get(i-1))) && blueTiles.get(i).getValue() != blueTiles.get(i - 1).getValue()) {
							
							num = 1;
							if (blueTiles.get(i).getValue() - blueTiles.get(i - 1).getValue() == 1) {
								//System.out.println("WE ARE IN THE LAST ONE 1");
								if (tempMeld.getMeldSize() >= 2) {
									tempMeld.addTile(blueTiles.get(i));
									melds.add(tempMeld);
								}
							} else {
								// System.out.println("BLAHBLAHBLAH");
							}
						} else if ( blueTiles.get(i).getValue() != blueTiles.get(i - 2).getValue() && num != 1) {
							//System.out.println("WE ARE IN THE LAST ONE 2");
							num = 2;
							if (blueTiles.get(i).getValue() - blueTiles.get(i - 2).getValue() == 1) {
								if (tempMeld.getMeldSize() >= 2) {
									tempMeld.addTile(blueTiles.get(i));
									melds.add(tempMeld);
								}
							}
						} else {
							System.out.println("Impossible. ");
						}
					}
				}
				else {
					//System.out.println("Already used: " + tile.tileToString()) ;
					if(tempMeld.isValidRun() == true) {
						melds.add(tempMeld);	
					}
					
					tempMeld = new Meld();
				}
				//System.out.println(tempMeld.meldToString());
			}
		}

		if (orangeTiles.size() > 2) {
			Meld tempMeld = new Meld();
			for (int i = 0; i <= orangeTiles.size() - 1; i++) {
				
				Tile tile = orangeTiles.get(i);
				//System.out.println(tile.tileToString());
				if(!(usedInMeld.contains(tile))) {
				// System.out.println(tile.tileToString());
					if (i != orangeTiles.size() - 1) {
						if (orangeTiles.get(i).getValue() != orangeTiles.get(i + 1).getValue()) {
							if (orangeTiles.get(i + 1).getValue() - tile.getValue() == 1) {
								// System.out.println("Subtracting orange");
								tempMeld.addTile(tile);
							} else if (tempMeld.getMeldSize() < 3 && orangeTiles.get(i + 1).getValue() - tile.getValue() != 1) {
								tempMeld.getTiles().clear();
							} else if (tempMeld.getMeldSize() >= 3 && orangeTiles.get(i + 1).getValue() - tile.getValue() != 1) {
								tempMeld.addTile(tile);
								// System.out.println("WERE IN THE LOOP");
								melds.add(tempMeld);
								// tempMeld.getTiles().clear();
								tempMeld = new Meld();
							}
						}
	
					} else if (i == orangeTiles.size() - 1) {
						
						int num = 0;
						if (!(usedInMeld.contains(orangeTiles.get(i-1))) && orangeTiles.get(i).getValue() != orangeTiles.get(i - 1).getValue()) {
							
							num = 1;
							if (orangeTiles.get(i).getValue() - orangeTiles.get(i - 1).getValue() == 1) {
								//System.out.println("WE ARE IN THE LAST ONE 1");
								if (tempMeld.getMeldSize() >= 2) {
									tempMeld.addTile(orangeTiles.get(i));
									melds.add(tempMeld);
								}
							} else {
								// System.out.println("BLAHBLAHBLAH");
							}
						} else if ( orangeTiles.get(i).getValue() != orangeTiles.get(i - 2).getValue() && num != 1) {
							//System.out.println("WE ARE IN THE LAST ONE 2");
							num = 2;
							if (orangeTiles.get(i).getValue() - orangeTiles.get(i - 2).getValue() == 1) {
								if (tempMeld.getMeldSize() >= 2) {
									tempMeld.addTile(orangeTiles.get(i));
									melds.add(tempMeld);
								}
							}
						} else {
							System.out.println("Impossible. ");
						}
					}
				}
				else {
					//System.out.println("Already used: " + tile.tileToString()) ;
					if(tempMeld.isValidRun() == true) {
						melds.add(tempMeld);	
					}
					
					tempMeld = new Meld();
				}
				//System.out.println(tempMeld.meldToString());
			}
		}
	}

	@Override
	public boolean hasInitialMeldBeenPlayed() {
		return this.initialMeldPlayed;
		// TODO Auto-generated method stub
	}

	@Override
	public void setHasInitialMeldBeenPlayed(boolean b) {
		this.initialMeldPlayed = b;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canWePlaceTile(Tile tile, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean oneMeldFirstTurn() {
		if (h.numberOfMelds()==1)
			return true;
		else
			return false;
	}

	public boolean severalMeldsFirstTurn() {
		if (h.numberOfMelds()>1)
			return true;
		else
			return false;
	}
	
	public Hand drawsOnFirstTurn() {
		//System.out.println("first in draw " + h.handToString());
		if (! (h.meldExists()) ) {
			Deck deck = new Deck();
			//h.createHand(deck);
			h.dealTile(deck);
			//System.out.println("in draw " + h.handToString());
		}
		
		return h;
		
	}
	
	public boolean oneMeldSubsequentTurn(){
		Deck deck = new Deck();
		h.dealTile(deck);
		return this.oneMeldFirstTurn();
		
	}
	
	public boolean severaltMeldsSubsequentTurn(){
		Deck deck = new Deck();
		h.dealTile(deck);
		return this.severalMeldsFirstTurn();
		
	}

	public Hand drawsOnSubsequentTurn() {
		//System.out.println("first in draw " + h.handToString());
				if (! (h.meldExists()) ) {
					Deck deck1 = new Deck();
					h.dealTile(deck1);
					if (! (h.meldExists()) ) {
						Deck deck2 = new Deck();
						h.dealTile(deck2);
					}
				}
					//System.out.println("in draw " + h.handToString());
				
				return h;
				
			}

	@Override
	public Meld combineMelds(Meld meld1, Meld meld2, Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undoMove(Tile tile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean makeAPlay(Scanner reader) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void play(Scanner reader, Deck deck) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public boolean isAI() { return isAI; }

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	
	
}
