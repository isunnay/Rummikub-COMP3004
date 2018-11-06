package com.COMP3004.Rummikub;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.COMP3004.Rummikub.Hand.SortByValue;

public class AI1 implements PlayerType {
	Hand h;
	private boolean myTurn = false;
	//private boolean isTurn = false;
	private boolean hasTileBeenPlaced = false;
	public ArrayList<Meld> melds;
	public ArrayList<Meld> sets;
	public Subject game;
	public ArrayList<Spot> spotsTaken;
	public ArrayList<Tile> turnTiles;
	public ArrayList<Meld> turnMelds;
	public ArrayList<Tile> turnMoves;
	private Board board;

	
	public AI1(Deck deck, Game game) {
		h = new Hand();
		h.createHand(deck);
		h.sortHand();
		melds = new ArrayList<Meld>();
		//game.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();
		turnMoves = new ArrayList<Tile>();
		sets = new ArrayList<Meld>();
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
	public void update(Board board) {
		// TODO Auto-generated method stub
		this.spotsTaken = board.filledSpots;
		this.board = board;		
	}
	@Override
	public void play(Scanner reader) {
		//System.out.println("Play");
		this.findAllMelds();
		//System.out.println(melds.size());
		if(melds.size()>0) {
			for(int i=0;i<melds.size();i++) {
				playMeld(melds.get(i),reader);
				this.hasTileBeenPlaced = true;
			}
		}
	}
	
	
	public void playMeld(Meld meld, Scanner reader) {
		int x = ThreadLocalRandom.current().nextInt(0, 12 + 1);
		int y = ThreadLocalRandom.current().nextInt(0, 12 + 1);
		System.out.println(x);
		System.out.println(y);
		Spot beginningSpot = board.getSpot(x, y);
		
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
	public boolean canWePlaceMeld(Meld meld, int x, int y) {
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
	
	public void findAllSets() {
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
	}
	
	
	
	/*public void findAllSets() {
		for(int i=0; i<this.h.size; i++) {
			if(i < this.h.size - 3) {
				int handValue = this.h.getTile(i).getValue();
				if(handValue == this.h.getTile(i+1).getValue()) {
					if(handValue == this.h.getTile(i+2).getValue()) {
						if(handValue == this.h.getTile(i+3).getValue()) {
							String color = this.h.getTile(i).getColour();
							String color2 = this.h.getTile(i+1).getColour();
							String color3 = this.h.getTile(i+2).getColour();
							String color4 = this.h.getTile(i+3).getColour();
							if((color != color2) && (color != color3)&& (color != color4) && (color2 != color3) && (color2 != color4) && (color3!=color4)) {
								Meld newMeld = new Meld();
								newMeld.addTile(this.h.getTile(i));
								newMeld.addTile(this.h.getTile(i+1));
								newMeld.addTile(this.h.getTile(i+2));
								newMeld.addTile(this.h.getTile(i+3));
								//System.out.println(newMeld.checkIfValidMeld());
								this.melds.add(newMeld);
								//System.out.println(testMelds.size());
							}
						}
					}
				}
			}
		}
	}*/
	
	
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
				// System.out.println(tile.tileToString());
				if (i != redTiles.size() - 1) {
					if (redTiles.get(i).getValue() != redTiles.get(i + 1).getValue()) {
						if (redTiles.get(i + 1).getValue() - tile.getValue() == 1) {
							// System.out.println("Subtracting red");
							tempMeld.addTile(tile);
						} else if (tempMeld.getMeldSize() < 3
								&& redTiles.get(i + 1).getValue() - tile.getValue() != 1) {
							tempMeld.getTiles().clear();
						} else if (tempMeld.getMeldSize() >= 3
								&& redTiles.get(i + 1).getValue() - tile.getValue() != 1) {
							tempMeld.addTile(tile);
							// System.out.println("WERE IN THE LOOP");
							melds.add(tempMeld);
							// tempMeld.getTiles().clear();
							tempMeld = new Meld();
						}
					}

					else {
						// System.out.println("Same value");
					}
				} else if (i == redTiles.size() - 1) {
					int num = 0;
					if (redTiles.get(i).getValue() != redTiles.get(i - 1).getValue()) {
						num = 1;
						if (redTiles.get(i).getValue() - redTiles.get(i - 1).getValue() == 1) {
							if (tempMeld.getMeldSize() > 2) {
								tempMeld.addTile(redTiles.get(i));
								melds.add(tempMeld);
							}
						} else {
							// System.out.println("BLAHBLAHBLAH");
						}
					} else if (redTiles.get(i).getValue() != redTiles.get(i - 2).getValue() && num != 1) {
						num = 2;
						if (redTiles.get(i).getValue() - redTiles.get(i - 2).getValue() == 1) {
							if (tempMeld.getMeldSize() > 2) {
								tempMeld.addTile(redTiles.get(i));
								melds.add(tempMeld);
							}
						}
					} else {
						System.out.println("Impossible. ");
					}
				}
			}
		}

		if (greenTiles.size() > 2) {
			Meld tempMeld = new Meld();
			for (int i = 0; i <= greenTiles.size() - 1; i++) {
				Tile tile = greenTiles.get(i);
				// System.out.println(tile.tileToString());
				if (i != greenTiles.size() - 1) {
					if (greenTiles.get(i).getValue() != greenTiles.get(i + 1).getValue()) {
						if (greenTiles.get(i + 1).getValue() - tile.getValue() == 1) {
							// System.out.println("Subtracting green");
							tempMeld.addTile(tile);
						} else if (tempMeld.getMeldSize() < 3
								&& greenTiles.get(i + 1).getValue() - tile.getValue() != 1) {
							tempMeld.getTiles().clear();
						} else if (tempMeld.getMeldSize() >= 3
								&& greenTiles.get(i + 1).getValue() - tile.getValue() != 1) {
							tempMeld.addTile(tile);
							// System.out.println("WERE IN THE LOOP");
							melds.add(tempMeld);
							// tempMeld.getTiles().clear();
							tempMeld = new Meld();
						}
					}

					else {
						System.out.println("Same value");
					}
				} else if (i == greenTiles.size() - 1) {
					int num = 0;
					if (greenTiles.get(i).getValue() != greenTiles.get(i - 1).getValue()) {
						num = 1;
						if (greenTiles.get(i).getValue() - greenTiles.get(i - 1).getValue() == 1) {
							if (tempMeld.getMeldSize() > 2) {
								tempMeld.addTile(greenTiles.get(i));
								melds.add(tempMeld);
							}
						} else {
							// System.out.println("BLAHBLAHBLAH");
						}
					} else if (greenTiles.get(i).getValue() != greenTiles.get(i - 2).getValue() && num != 1) {
						num = 2;
						if (greenTiles.get(i).getValue() - greenTiles.get(i - 2).getValue() == 1) {
							if (tempMeld.getMeldSize() > 2) {
								tempMeld.addTile(greenTiles.get(i));
								melds.add(tempMeld);
							}
						}
					} else {
						System.out.println("Impossible. ");
					}
				}
			}
		}

		if (blueTiles.size() > 2) {
			Meld tempMeld = new Meld();
			for (int i = 0; i <= blueTiles.size() - 1; i++) {
				Tile tile = blueTiles.get(i);
				// System.out.println(tile.tileToString());
				if (i != blueTiles.size() - 1) {
					if (blueTiles.get(i).getValue() != blueTiles.get(i + 1).getValue()) {
						if (blueTiles.get(i + 1).getValue() - tile.getValue() == 1) {
							// System.out.println("Subtracting green");
							tempMeld.addTile(tile);
						} else if (tempMeld.getMeldSize() < 3
								&& blueTiles.get(i + 1).getValue() - tile.getValue() != 1) {
							tempMeld.getTiles().clear();
						} else if (tempMeld.getMeldSize() >= 3
								&& blueTiles.get(i + 1).getValue() - tile.getValue() != 1) {
							tempMeld.addTile(tile);
							// System.out.println("WERE IN THE LOOP");
							melds.add(tempMeld);
							// tempMeld.getTiles().clear();
							tempMeld = new Meld();
						}
					}

					else {
						System.out.println("Same value");
					}
				} else if (i == blueTiles.size() - 1) {
					int num = 0;
					if (blueTiles.get(i).getValue() != blueTiles.get(i - 1).getValue()) {
						num = 1;
						if (blueTiles.get(i).getValue() - blueTiles.get(i - 1).getValue() == 1) {
							if (tempMeld.getMeldSize() > 2) {
								tempMeld.addTile(blueTiles.get(i));
								melds.add(tempMeld);
							}
						} else {
							// System.out.println("BLAHBLAHBLAH");
						}
					} else if (blueTiles.get(i).getValue() != blueTiles.get(i - 2).getValue() && num != 1) {
						num = 2;
						if (blueTiles.get(i).getValue() - blueTiles.get(i - 2).getValue() == 1) {
							if (tempMeld.getMeldSize() > 2) {
								tempMeld.addTile(blueTiles.get(i));
								melds.add(tempMeld);
							}
						}
					} else {
						System.out.println("Impossible. ");
					}
				}

			}
		}

		if (orangeTiles.size() > 2) {
			Meld tempMeld = new Meld();
			for (int i = 0; i <= orangeTiles.size() - 1; i++) {
				Tile tile = orangeTiles.get(i);
				// System.out.println(tile.tileToString());
				if (i != orangeTiles.size() - 1) {
					if (orangeTiles.get(i).getValue() != orangeTiles.get(i + 1).getValue()) {
						if (orangeTiles.get(i + 1).getValue() - tile.getValue() == 1) {
							// System.out.println("Subtracting green");
							tempMeld.addTile(tile);
						} else if (tempMeld.getMeldSize() < 3
								&& orangeTiles.get(i + 1).getValue() - tile.getValue() != 1) {
							tempMeld.getTiles().clear();
						} else if (tempMeld.getMeldSize() >= 3
								&& orangeTiles.get(i + 1).getValue() - tile.getValue() != 1) {
							tempMeld.addTile(tile);
							// System.out.println("WERE IN THE LOOP");
							melds.add(tempMeld);
							// tempMeld.getTiles().clear();
							tempMeld = new Meld();
						}
					}

					else {
						System.out.println("Same value");
					}
				} else if (i == orangeTiles.size() - 1) {
					int num = 0;
					if (orangeTiles.get(i).getValue() != orangeTiles.get(i - 1).getValue()) {
						num = 1;
						if (orangeTiles.get(i).getValue() - orangeTiles.get(i - 1).getValue() == 1) {
							if (tempMeld.getMeldSize() > 2) {
								tempMeld.addTile(orangeTiles.get(i));
								melds.add(tempMeld);
							}
						} else {
							// System.out.println("BLAHBLAHBLAH");
						}
					} else if (orangeTiles.get(i).getValue() != orangeTiles.get(i - 2).getValue() && num != 1) {
						num = 2;
						if (orangeTiles.get(i).getValue() - orangeTiles.get(i - 2).getValue() == 1) {
							if (tempMeld.getMeldSize() > 2) {
								tempMeld.addTile(orangeTiles.get(i));
								melds.add(tempMeld);
							}
						}
					} else {
						System.out.println("Impossible. ");
					}
				}

			}
		}
	}

	@Override
	public boolean hasInitialMeldBeenPlayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHasInitialMeldBeenPlayed(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	/*public void findAllMelds() {
		int numMelds = 0;
		Collections.sort(this.h.getPlayerHand(), new SortByValue());
		
		for(int i=0; i<this.getHand().size; i++) {
			if(i < this.getHand().size - 2) {
				if(this.getHand().getTile(i).getValue() == this.getHand().getTile(i+1).getValue() - 1) {
					if(this.getHand().getTile(i+1).getValue() == this.getHand().getTile(i+2).getValue() - 1) {
						if(this.getHand().getTile(i+2).getValue() == this.getHand().getTile(i+3).getValue() - 1) {
							String color1 = this.getHand().getTile(i).getColour();
							String color2 = this.getHand().getTile(i+1).getColour();
							String color3 = this.getHand().getTile(i+2).getColour();
							if((color1 == color2) && (color2 == color3)) {
								
								Meld newMeld = new Meld();
								newMeld.addTile(this.getHand().getTile(i));
								newMeld.addTile(this.getHand().getTile(i+1));
								newMeld.addTile(this.getHand().getTile(i+2));
								//System.out.println(newMeld.checkIfValidMeld());
								this.testMelds.add(newMeld);
								//System.out.println(testMelds.size());
							}
						}
					}
				}
			}
			//System.out.println("i = "+i);
		}	

		for(int i=0; i<this.getHand().size; i++) {
			if(i < this.getHand().size - 2) {
				int handValue = this.getHand().getTile(i).getValue();
				if(handValue == this.getHand().getTile(i+1).getValue()) {
					if(handValue == this.getHand().getTile(i+2).getValue()) {
						String color = this.getHand().getTile(i).getColour();
						String color2 = this.getHand().getTile(i+1).getColour();
						String color3 = this.getHand().getTile(i+2).getColour();
						if((color != color2) && (color != color3) && (color2 != color3)) {
							Meld newMeld = new Meld();
							newMeld.addTile(this.getHand().getTile(i));
							newMeld.addTile(this.getHand().getTile(i+1));
							newMeld.addTile(this.getHand().getTile(i+2));
							//System.out.println(newMeld.checkIfValidMeld());
							this.testMelds.add(newMeld);
							//System.out.println(testMelds.size());
						}
					}
				}
			}
		}
		
		
		
		//return numMelds;
	}*/
	
	
	

	
	
}
