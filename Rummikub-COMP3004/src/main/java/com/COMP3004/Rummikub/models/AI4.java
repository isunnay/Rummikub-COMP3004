package com.COMP3004.Rummikub.models;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import com.COMP3004.Rummikub.controller.RummikubController;

public class AI4 implements PlayerType{
	
	Hand h;
	private boolean initialMeldPlayed = false;
	private boolean myTurn = false;
	private boolean hasTileBeenPlaced = false;
	public Subject game;
	public int turnPoints;
	private Board board;
	public ArrayList<Meld> melds;
	public ArrayList<Spot> spotsTaken;
	public ArrayList<Tile> turnTiles;
	public ArrayList<Meld> turnMelds;
	public ArrayList<Tile> turnMoves;
	public ArrayList<Tile> usedInMeld;
	//int numberOfMelds;
	private boolean isAI = true;
	//new
	public ArrayList<Tile> meldTiles = new ArrayList<Tile>();
	
	public AI4(Deck deck, RummikubController rummikubController) {
		h = new Hand();
		h.createHand(deck);
		h.sortHand();
		melds = new ArrayList<Meld>();
		rummikubController.registerObserver(this);
		spotsTaken = new ArrayList<Spot>();
		turnTiles = new ArrayList<Tile>();
		turnMelds = new ArrayList<Meld>();
		turnMoves = new ArrayList<Tile>();
		usedInMeld = new ArrayList<Tile>();
		//meldTiles = new ArrayList<Tile>();
	}
	
	public int getAiType() {
		return 4;
	}
	
	public Hand getHand() { return this.h; }
	
	public boolean myTurnStatus() { return this.myTurn; }
	
	public void setTurnStatus(boolean b) { this.myTurn = b; }
	
	public boolean hasTilesBeenPlayed() { return this.hasTileBeenPlaced; }
	
	public void setTilesBeenPlayed(boolean b) { this.hasTileBeenPlaced = b; }

	public boolean turnComplete(Hand h) {
		// Initial Meld: After another played has placed tiles
		// Gameplay: Only uses existing melds on the board. Keeps melds not do not require current tiles on board.
		return false;
	}

	@Override
	public boolean hasInitialMeldBeenPlayed() {
		// TODO Auto-generated method stub
		return this.initialMeldPlayed;
	}

	@Override
	public void setHasInitialMeldBeenPlayed(boolean b) {
		// TODO Auto-generated method stub
		this.initialMeldPlayed = b;
		
	}

	@Override
	public void update(Board board) {
			this.spotsTaken = board.filledSpots;
			this.board = board;	
			turnTiles.clear();;
			turnMelds.clear();				
			turnMoves.clear();
			melds.clear();
			usedInMeld.clear();
			turnPoints = 0;		
	}
/*
	@Override
	public void play(Scanner reader) {
		
		System.out.println(this.h.handToString());
		System.out.println(this.initialMeldPlayed);
		//System.out.println("Play");
				this.findAllMelds();
				//System.out.println(melds.size());
				setTurnPoints();
				System.out.println(getTurnPoints());
				if(this.hasInitialMeldBeenPlayed() == false) {
					if(board.numberOfMelds > 0) {
						//CHANGE THIS VALUE
						if(getTurnPoints()>=0) {
							if(melds.size()>0) {
								for(int i=0;i<melds.size();i++) {
									playMeld(melds.get(i),reader);
									this.hasTileBeenPlaced = true;
								}
								this.setHasInitialMeldBeenPlayed(true);
						}
					  }
					}
				}
				else {
					if(melds.size()>0) {
						for(int i=0;i<melds.size();i++) {
							playMeld(melds.get(i),reader);
							this.hasTileBeenPlaced = true;
						}
					}
					
					
				}
			}
		// TODO Auto-generated method stub
		*/
	
	@Override
	public void play(Scanner reader) {
		
		System.out.println(this.h.handToString());
		System.out.println(this.initialMeldPlayed);
		//System.out.println("Play");
				this.findAllMelds();
				//System.out.println(melds.size());
				setTurnPoints();
				System.out.println(getTurnPoints());
				if(this.hasInitialMeldBeenPlayed() == false) {
					if(board.numberOfMelds > 2) {
						//CHANGE THIS VALUE
						if(getTurnPoints()>=30) {
							if(melds.size()>0) {
								for(int i=0;i<melds.size();i++) {
									playMeld(melds.get(i),reader);
									this.hasTileBeenPlaced = true;
								}
								this.setHasInitialMeldBeenPlayed(true);
						}
					  }
					}
				}
				
					if(melds.size()>0) {
						for(int i=0;i<melds.size();i++) {
							playMeld(melds.get(i),reader);
							//this.hasTileBeenPlaced = true;
							this.setTilesBeenPlayed(true);
						}
						//this.setTurnStatus(false);
					}
				
				else  { //play single tiles if there are tiles not including melds
					this.placeSingleTile();
				}
	}
	
	
	

	@Override
	public void playMeld(Meld meld, Scanner reader) {
		// TODO Auto-generated method stub
		int x = ThreadLocalRandom.current().nextInt(0, 12 + 1);
		int y = ThreadLocalRandom.current().nextInt(0, 12 + 1);
	//	System.out.println(x);
	//	System.out.println(y);
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
					turnTiles.add(tile);
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
		//this.placeSingleTile();
	}
		
	


	@Override
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
		
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setTurnPoints() {
		for(int i=0; i<melds.size();i++) {
			turnPoints += melds.get(i).getMeldValue();	
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTurnPoints() {
		// TODO Auto-generated method stub
		return turnPoints;
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
	public boolean canWePlaceTile(Tile tile, int x, int y) {
		// Adding a tile between two melds
				//System.out.println("Adding between two melds");
				if (x > 0 && x < 14 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken
						&& board.getSpot(x, y).isTaken == false) {
					Spot prevSpot = board.getSpot(x - 1, y);
					Tile prevTile = prevSpot.getTile();
					Meld prevTileMeld = prevTile.getMemberOfMeld();
					//Spot beginningOfMeld = prevTileMeld.getTiles().get(0).getSpot();
					Spot nextSpot = board.getSpot(x + 1, y);
					//int newX = beginningOfMeld.getSpotX();
					Tile nextTile = nextSpot.getTile();
					Meld nextTileMeld = nextTile.getMemberOfMeld();
					Meld newMeld = combineMelds(prevTileMeld, nextTileMeld, tile);
					if (newMeld.checkIfValidMeld() != true) {
						return false;
					}
						
					}
				// Adding a tile at the end of an existing meld
				else if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken == false) {
					//System.out.println("Adding after a meld");
					Spot prevSpot = board.getSpot(x - 1, y);
					Tile prevTile = prevSpot.getTile();
					Meld prevTileMeld = prevTile.getMemberOfMeld();
					prevTileMeld.addTile(tile);
					if (prevTileMeld.checkIfValidMeld() != true) {
						return false;
					}
				}
				
				// Adding a tile at the beginning of an existing meld
				else if (x >= 0 && board.getSpot(x + 1, y).isTaken) {
					//System.out.println("Adding before a meld");
					Spot nextSpot = board.getSpot(x + 1, y);
					Tile nextTile = nextSpot.getTile();
					Meld nextTileMeld = nextTile.getMemberOfMeld();
					nextTileMeld.addTile(tile);
					if (nextTileMeld.checkIfValidMeld() != true) {
						return false;
					}
				}
				return true;	
	}

	@Override
	public void addTile(Tile tile, int x, int y) {
		// Adding a tile between two melds
		//System.out.println("Adding between two melds");
		if (x > 0 && x < 14 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken && board.getSpot(x, y).isTaken == false) {
			Spot prevSpot = board.getSpot(x - 1, y);
			Tile prevTile = prevSpot.getTile();
			Meld prevTileMeld = prevTile.getMemberOfMeld();
			Spot beginningOfMeld = prevTileMeld.getTiles().get(0).getSpot();
			Spot nextSpot = board.getSpot(x + 1, y);
			int newX = beginningOfMeld.getSpotX();
			Tile nextTile = nextSpot.getTile();
			Meld nextTileMeld = nextTile.getMemberOfMeld();
			Meld newMeld = combineMelds(prevTileMeld, nextTileMeld, tile);
			if (newMeld.checkIfValidMeld() == true) {
				board.deleteMeld(prevTileMeld);
				//board.deleteMeld(nextTileMeld);
				for (int i = 0; i < newMeld.getNumberOfTiles(); i++) {
					Tile newTile = newMeld.getTileInMeld(i);
					Spot spot = board.getSpot(newX + i, y);
					spot.playTile(newTile);
					newTile.setSpot(spot);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
				}
				board.meldsOnBoard.add(newMeld);
				board.numberOfMelds++;
				turnTiles.add(tile);
				h.removeTile(tile);
				//board.deleteMeld(prevTileMeld);
				//board.deleteMeld(nextTileMeld);
				this.setTilesBeenPlayed(true);
			} else {
				System.out.println("ERROR: You can't play this here. ");
				//undoAddTile(tile);
			}
		}
		
		//ADDING TILE AFTER A MELD
		else if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken == false) {
			System.out.println("Adding after a meld");
			Spot prevSpot = board.getSpot(x - 1, y);
			Tile prevTile = prevSpot.getTile();
			Meld prevTileMeld = prevTile.getMemberOfMeld();
			//prevTileMeld.addTile(tile);
			//if (prevTileMeld.checkIfValidMeld() == true) {
			
			//Checking if possible run
			if (tile.getValue() - prevTile.getValue() == 1) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				prevTileMeld.addTile(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnTiles.add(tile);
				h.removeTile(tile);
				this.setTilesBeenPlayed(true);
			}
			//checking if possible set
			else if(prevTile.getValue() == tile.getValue() && tile.getColour()!= prevTile.getColour()) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				prevTileMeld.addTile(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnTiles.add(tile);
				h.removeTile(tile);
				this.setTilesBeenPlayed(true);
			}
			else {
				System.out.println("ERROR: You cannot play this here.");
			}
		}	

		//ADDING TILE BEFORE A MELD
		else if (x >= 0 && board.getSpot(x + 1, y).isTaken) {
			System.out.println("Adding before an existing meld");
			Spot nextSpot = board.getSpot(x + 1, y);
			Tile nextTile = nextSpot.getTile();
			Meld nextTileMeld = nextTile.getMemberOfMeld();
			//Checking if possible run
			if (nextTile.getValue() - tile.getValue() == 1) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				nextTileMeld.addTileFront(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnTiles.add(tile);
				h.removeTile(tile);
				this.setTilesBeenPlayed(true);
			
			} 
			//checking if possible set
			else if(nextTile.getValue() == tile.getValue() && tile.getColour()!= nextTile.getColour()) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				nextTileMeld.addTileFront(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnTiles.add(tile);
				h.removeTile(tile);
				this.setTilesBeenPlayed(true);	
			}
			else {
				System.out.println("ERROR: You cannot play this here.");
				//nextTileMeld.removeTile(tile);
			}	
		}
		//Creating a new meld
		else {
			Spot spot = board.getSpot(x, y);
			spot.playTile(tile);
			tile.setSpot(spot);
			System.out.println("Creating a New meld");
			Meld meld = new Meld();
			meld.addTile(tile);
			board.meldsOnBoard.add(meld);
			board.numberOfMelds++;
			turnMelds.add(meld);
			board.numberOfTilesOnBoard++;
			board.filledSpots.add(spot);
			//turnTiles.add(tile);
			h.removeTile(tile);
			this.setTilesBeenPlayed(true);
		}
		
	}
	
	@Override
	public void moveTile(Tile tile, Spot newSpot) {
		// Moving a tile between two melds
				//System.out.println("Moving between two melds");
		Spot oldSpot = tile.getSpot();
		int x = newSpot.getSpotX();
		int y = newSpot.getSpotY();
		if (x > 0 && x < 14 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken
				&& board.getSpot(x, y).isTaken == false) {
			oldSpot.removeTile();
			tile.removeSpot(oldSpot);
			board.filledSpots.remove(oldSpot);
			Spot prevSpot = board.getSpot(x - 1, y);
			Tile prevTile = prevSpot.getTile();
			Meld prevTileMeld = prevTile.getMemberOfMeld();
			Spot beginningOfMeld = prevTileMeld.getTiles().get(0).getSpot();
			Spot nextSpot = board.getSpot(x + 1, y);
			int newX = beginningOfMeld.getSpotX();
			Tile nextTile = nextSpot.getTile();
			Meld nextTileMeld = nextTile.getMemberOfMeld();
			Meld newMeld = combineMelds(prevTileMeld, nextTileMeld, tile);
			if (newMeld.checkIfValidMeld() == true) {
				board.deleteMeld(prevTileMeld);
				board.deleteMeld(nextTileMeld);
				// board.deleteMeld(nextTileMeld);
				for (int i = 0; i < newMeld.getNumberOfTiles(); i++) {
					Tile newTile = newMeld.getTileInMeld(i);
					Spot spot = board.getSpot(newX + i, y);
					spot.playTile(newTile);
					newTile.setSpot(spot);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(spot);
				}
				board.meldsOnBoard.add(newMeld);
				board.numberOfMelds++;
				turnMoves.add(tile);
				// board.deleteMeld(prevTileMeld);
				// board.deleteMeld(nextTileMeld);
				// this.setTilesBeenPlayed(true);
			} else {
				System.out.println("ERROR: You can't play this here. ");
				// undoMoveTile();
			}
		}
		
		//Moving TILE AFTER A MELD
		else if (x > 0 && board.getSpot(x - 1, y).isTaken && board.getSpot(x + 1, y).isTaken == false) {
			oldSpot.removeTile();
			tile.removeSpot(oldSpot);
			board.filledSpots.remove(oldSpot);
			System.out.println("Moving to after a meld");
			Spot prevSpot = board.getSpot(x - 1, y);
			Tile prevTile = prevSpot.getTile();
			Meld prevTileMeld = prevTile.getMemberOfMeld();
			//prevTileMeld.addTile(tile);
			//if (prevTileMeld.checkIfValidMeld() == true) {
			
			//Checking if possible run
			if (tile.getValue() - prevTile.getValue() == 1) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				prevTileMeld.addTile(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnMoves.add(tile);
				//h.removeTile(tile);
				//this.setTilesBeenPlayed(true);
			}
			//checking if possible set
			else if(prevTile.getValue() == tile.getValue() && tile.getColour()!= prevTile.getColour()) {
				Spot spot = board.getSpot(x, y);
				spot.playTile(tile);
				tile.setSpot(spot);
				prevTileMeld.addTile(tile);
				board.numberOfTilesOnBoard++;
				board.filledSpots.add(spot);
				turnMoves.add(tile);
				//h.removeTile(tile);
				//this.setTilesBeenPlayed(true);
			}
			else {
				System.out.println("ERROR: You cannot play this here.");
				//undoMove(tile);
			}
		}	
		
		//Moving TILE BEFORE A MELD
				else if (x >= 0 && board.getSpot(x + 1, y).isTaken) {
					oldSpot.removeTile();
					tile.removeSpot(oldSpot);
					board.filledSpots.remove(oldSpot);
					System.out.println("Moving before an existing meld");
					Spot nextSpot = board.getSpot(x + 1, y);
					Tile nextTile = nextSpot.getTile();
					Meld nextTileMeld = nextTile.getMemberOfMeld();
					//Checking if possible run
					if (nextTile.getValue() - tile.getValue() == 1) {
						Spot spot = board.getSpot(x, y);
						spot.playTile(tile);
						tile.setSpot(spot);
						nextTileMeld.addTileFront(tile);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
						turnMoves.add(tile);
						//h.removeTile(tile);
						//this.setTilesBeenPlayed(true);
					} 
					//checking if possible set
					else if(nextTile.getValue() == tile.getValue() && tile.getColour()!= nextTile.getColour()) {
						Spot spot = board.getSpot(x, y);
						spot.playTile(tile);
						tile.setSpot(spot);
						nextTileMeld.addTileFront(tile);
						board.numberOfTilesOnBoard++;
						board.filledSpots.add(spot);
						turnMoves.add(tile);
						//h.removeTile(tile);
						//this.setTilesBeenPlayed(true);	
					}
					else {
						System.out.println("ERROR: You cannot play this here.");
						//nextTileMeld.removeTile(tile);
					}
					
				}
				//Creating a new meld
				else {
					oldSpot.removeTile();
					tile.removeSpot(oldSpot);
					board.filledSpots.remove(oldSpot);
					newSpot.playTile(tile);
					tile.setSpot(newSpot);
					System.out.println("Creating a New meld");
					Meld meld = new Meld();
					meld.addTile(tile);
					board.meldsOnBoard.add(meld);
					board.numberOfMelds++;
					turnMelds.add(meld);
					board.numberOfTilesOnBoard++;
					board.filledSpots.add(newSpot);
					//turnMoves.add(tile);
				//	h.removeTile(tile);
					//this.setTilesBeenPlayed(true);
				}
		}
	
	
	@Override
	public void undoAddTile(Tile tile) {
		Spot spot = tile.getSpot();
		spot.removeTile();
		tile.removeSpot(spot);
		board.numberOfTilesOnBoard--;
		if (tile.getMemberOfMeld() != null) {
			Meld meld = tile.getMemberOfMeld();
			meld.removeTile(tile);
		}
		board.filledSpots.remove(spot);
		h.addTile(tile);
	}
	@Override
	public void undoMove(Tile tile) {
		Spot currentSpot = tile.getOldSpot();
		Spot oldSpot = tile.getSpot();
		oldSpot.removeTile();
		board.filledSpots.remove(oldSpot);
		tile.removeSpot(oldSpot);
		oldSpot = null;
		currentSpot.playTile(tile);
		tile.setSpot(currentSpot);
		board.filledSpots.add(currentSpot);
	}
	
	
	@Override
	public Meld combineMelds(Meld meld1, Meld meld2, Tile tile) {
		Meld newMeld = new Meld();
		newMeld.getTiles().addAll(meld1.getTiles());
		newMeld.addTile(tile);
		newMeld.getTiles().addAll(meld2.getTiles());
		board.deleteMeld(meld1);
		board.deleteMeld(meld2);
		return newMeld;
	}
	

	public char[] playMeldAfterSomeone() {
		
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	//new method
	public void placeSingleTile()
	{

		for (int i=0; i< board.numberOfMelds; i++)
		{
			Meld bMeld = board.meldsOnBoard.get(i);
	
			if (bMeld.isValidRun()) // same colour, different values
			{
				System.out.println("ehjbfdsb");
				//bf = boardFirst AKA first tile of the meld
				Tile bfTile = bMeld.getTiles().get(0);
				int bfColor = bfTile.getIntColor();
				int bfValue = bfTile.getValue();

				//bl = boardLast AKA last tile of the meld
				int nTiles = bMeld.getNumberOfTiles();
				Tile blTile = bMeld.getTiles().get(nTiles-1);
				int blValue = blTile.getValue();
	
				for (int j=0; j<h.size; j++)
				{
					Tile hTile = h.getPlayerHand().get(j);
					
					if (!(meldTiles.contains(hTile))) { // avoid placing melds in hand
						
					
					if (hTile.getIntColor() == bfColor)
					{
						if (hTile.getValue() == bfValue - 1) 
						{
							Spot bSpot = board.getLocationOfTile(bfTile);
							int x = bSpot.x;
							int y = bSpot.y;
							//place hTile at x-1,y
							if(x>0&&!(board.isSpotFilled(x-1, y))) {
							//if(this.canWePlaceTile(hTile, x-1, y) == true) {	
								this.addTile(hTile, x-1, y);
								//turnTiles.add(hTile);
							}
							else {
								System.out.println("Spot is not empty"+ (x-1) +","+y);
							}
						}				
						
						else if (hTile.getValue() == blValue + 1) 
						{
							Spot bSpot = board.getLocationOfTile(blTile);
							int x = bSpot.x;
							int y = bSpot.y;
							//place hTile at x+1,y
							//if(this.canWePlaceTile(hTile, x+1, y) == true) {	
							if(x<14&&!(board.isSpotFilled(x+1, y))) {
								this.addTile(hTile, x+1, y);
							}
							else {
								System.out.println("Spot is not empty"+ (x+1) +","+y);
							}
						}
					}
					
					} //if (!(meldTiles.contains(hTile))) 
				} //for (int j=0; j<h.size; j++)
				
			}//if (bMeld.isValidRun())
					
			
			if (bMeld.isValidSet()) //same value different colours
			{
				//ArrayList<Integer> arl = new ArrayList<Integer>();
				ArrayList<Integer> bColor = new ArrayList<Integer>();
				Tile bTile;
				int nTiles = bMeld.getNumberOfTiles();
				for ( int x=0; x<nTiles; x++)
				{
					
					bTile = bMeld.getTiles().get(x);
					bColor.add(bTile.getIntColor());
				}
	
				bTile = bMeld.getTiles().get(nTiles-1);
				int bValue = bTile.getValue();
	
				
				for (int j=0; j<h.size; j++)
				{
					Tile hTile = h.getPlayerHand().get(j);
					if (hTile.getValue() == bValue)
					{
						
						// check if the color is different
						if (!(bColor.contains(hTile.getIntColor())))
						{
							boolean iPlaced = false;
							//CHECK IF YOU CAN PLACE AT THE END OR BEGINNING OF MELD.. Either or, if you cant, dont place it. 
							// bTile is the last one in the Meld
							Spot bSpot = board.getLocationOfTile(bTile);
							int x = bSpot.x;
							int y = bSpot.y;
							/*
							if(this.canWePlaceTile(hTile, x+1, y) == true) {	
								this.addTile(hTile, x+1, y);
								iPlaced = true;
							}
							else if(iPlaced == false && this.canWePlaceTile(hTile, x-1, y) == true) {
								this.addTile(hTile, x-1, y);
								iPlaced = true;
								
							}
							else {
								System.out.println("A tile cannot be placed in this meld");
							}
							
							*/
							if(x<14 && x>1) {
								if(x<14&&!(board.isSpotFilled(x+1, y))) {
									this.addTile(hTile, x+1, y);
								}
								else {
									bTile = bMeld.getTiles().get(0);
									bValue = bTile.getValue();
									bSpot = board.getLocationOfTile(bTile);
									x = bSpot.x;
									y = bSpot.y;
									
									if(x>0&&!(board.isSpotFilled(x-1, y))) {
										this.addTile(hTile, x-1, y);
									}
									else {
										System.out.println("Both beginning and end sports are not empty");
									}
								}
								
							}	
						}
						
					}
					
				}	//for (int j=0; j<h.size; j++)
				
			}	//if (bMeld.isValidSet())
			
		} //for (int i=0; i< board.numberOfMelds; i++)
		
	}
		
	public boolean isAI() { return isAI; }

	@Override
	public boolean makeAPlay(Scanner reader) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void play(Scanner reader, Deck deck) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Tile> getTurnTiles() {
		// TODO Auto-generated method stub
		return null;
	}

}
