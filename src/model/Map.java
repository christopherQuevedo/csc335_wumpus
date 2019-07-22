//Chris Quevedo

package model;

import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

/**
 * This class makes the game map/cave which is a 12 by 12 grid of Room objects.
 * It has fields for the main player which is of the Hunter class, the main enemy 
 * of the Wumpus class and a boolean variable that helps distinguish when the game
 * is over.
 */
public class Map extends Observable{
	
	private Room[][] cave;
	private Hunter player;
	private Wumpus wump;
	private boolean gameOver;
	private boolean win;
	
	/**
	 * Construct the map and fill it with Room objects.  Then call a method
	 * initializeCave() which will place the wumpus, pits, and player in the 
	 * grid.  The game is just starting so the gameOver flag is false.
	 */
	public Map() {
		cave = new Room[12][12];
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				cave[i][j] = new Room();
			}
		}
		initializeCave();
		gameOver = false;
		win = false;
		setChanged();
		notifyObservers();
	}
	
	//Getters
	public boolean didWin() {
		return win;
	}
	public boolean isOver() {
		return gameOver;
	}
	public Room[][] getCave(){
		return cave;
	}
	public Hunter getPlayer() {
		return player;
	}
	public Wumpus getWumpus() {
		return wump;
	}
	
	/**
	 * newGame will reset the game's cave and reinitialize it.
	 */
	public void newGame() {
		cave = new Room[12][12];
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				cave[i][j] = new Room();
			}
		}
		initializeCave();
		gameOver = false;
		setChanged();
		notifyObservers();
	}
		
	/**
	 * Method for the initialization of the grid.  It is clear that
	 * first the wumpus is placed, then the pits, then the player.
	 */
	public void initializeCave() {
		placeWumpus();
		placePits();
		placeHunter();
	}
	
	/**
	 * Method to place the wumpus randomly in the 12 by 12 grid.  Blood accompanies
	 * the wumpus is a diamond pattern so the blood is also added to the grid in this
	 * method.
	 */
	private void placeWumpus() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int randI = rand.nextInt(12);
		int randJ = rand.nextInt(12);
		wump = new Wumpus(randI, randJ);
		cave[randI][randJ].addWumpus(wump);
		cave[(randI + 10) % 12][randJ].addBlood();
		cave[(randI + 11) % 12][(randJ + 11) % 12].addBlood();
		cave[(randI + 11) % 12][randJ].addBlood();
		cave[(randI + 11) % 12][(randJ + 1) % 12].addBlood();
		cave[randI][(randJ + 10) % 12].addBlood();
		cave[randI][(randJ + 11) % 12].addBlood();
		cave[randI][(randJ + 1) % 12].addBlood();
		cave[randI][(randJ + 2) % 12].addBlood();
		cave[(randI + 1) % 12][(randJ + 11) % 12].addBlood();
		cave[(randI + 1) % 12][randJ].addBlood();
		cave[(randI + 1) % 12][(randJ + 1) % 12].addBlood();
		cave[(randI + 2) % 12][randJ].addBlood();
	}
	
	/**
	 * Method to place anywhere from 3-5 pits in the board.  The method checks if the 
	 * randomly picked spot has a pit or wumpus and if it does, then it will pick a new 
	 * location.  Slime accompanies pits and is added in this method to the board.
	 */
	private void placePits() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int numPits = rand.nextInt(3) + 3;	//3-5 pits
		for(int i = 0; i < numPits; i++) {
			while(true) {
				int randI = rand.nextInt(12);
				int randJ = rand.nextInt(12);
				if(!cave[randI][randJ].hasWumpus() && !cave[randI][randJ].hasPit()) {
					//room has no wumpus or pit so it is available
					cave[randI][randJ].addPit();
					cave[(randI + 11) % 12][randJ].addSlime();
					cave[randI][(randJ + 11) % 12].addSlime();
					cave[randI][(randJ + 1) % 12].addSlime();
					cave[(randI + 1) % 12][randJ].addSlime();
					break;
				}
			}
		}
	}
	
	/**
	 * Method to place the hunter.  After all the pits and wumpus are placed, the 
	 * hunter/player is placed in a spot without hazards or warnings.
	 */
	private void placeHunter() {
		Random rand = new Random();
		while(true) {
			int randI = rand.nextInt(12);
			int randJ = rand.nextInt(12);
			if(!cave[randI][randJ].hasWumpus() && !cave[randI][randJ].hasPit()) {
				//no wumpus or pit
				if(!cave[randI][randJ].hasBlood() && !cave[randI][randJ].hasSlime()) {
					//spot is totally free
					player = new Hunter(randI, randJ);
					cave[randI][randJ].addPlayer(player);
					break;
				}
			}
		}
	}
	
	/**
	 * Method to print the grid on the console.  Depending on the contents
	 * of each Room object in the grid, a different character is printed.
	 * W for wumpus, P for pit, S for slime, B for blood, G for goop is blood and slime
	 * occupy the same room, and O for the player.  X is for an unvisited room while
	 * a space is an empty but visited room.
	 */
	public void printCave() {
		
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				System.out.print(cave[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * toString will make a string to represent the game.
	 * Will end up looking like a 12 by 12 char array.
	 */
	public String toString() {
		String text = "";
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				text = text + cave[i][j].toString() + " ";
			}
			text = text + "\n";
		}
		return text;
	}
	
	/**
	 * Method which loops with a scanner getting user input.
	 * The user can move n,s,e,w or shoot an arrow in one of the cardinal
	 * directions.
	 */
	public void playGame() {
		String ans;
		Scanner in = new Scanner(System.in);
		while(true) {
			if(gameOver) {
				break;
			}
			
			printCave();
			printMessage();
			if(gameOver) {
				break;
			}
			System.out.print("Move (n, e, s, w, arrow)? ");
			ans = in.next().toLowerCase();
			System.out.println();
			moveHunter(ans);
		}
	}
	
	/**
	 * After the player makes a move, the console will print a message depending 
	 * on the contents of the room the player stepped into.
	 */
	public void printMessage() {
		int row = player.getRow();
		int col = player.getCol();
		if(cave[row][col].hasWumpus()) {
			printCave();
			System.out.println("You walked into the Wumpus.  You lose.\n");
			gameOver = true;
			makeGameOver();
			return;
		}
		else if(cave[row][col].hasPit()) {
			printCave();
			System.out.println("You fell down a bottomless pit.  You lose.\n");
			gameOver = true;
			makeGameOver();
			return;
		}
		if(cave[row][col].hasSlime()) {
			System.out.println("I can hear the wind\n");
		}
		if(cave[row][col].hasBlood()) {
			System.out.println("I smell something foul\n");
		}
		printCave();
	}
	
	/**
	 * Method to move the player in the direction of ans.
	 * @param ans is a String for the direction to move.
	 */
	public void moveHunter(String ans) {
		if(gameOver) {
			return;
		}
		int i, j;
		i = player.getRow();
		j = player.getCol();
		cave[i][j].removePlayer();
		if(ans.equals("n")) {
			cave[(i + 11) % 12][j].addPlayer(player);
			player.setRow((i + 11) % 12);
			player.setCol(j);
		}
		else if(ans.equals("s")) {
			cave[(i + 1) % 12][j].addPlayer(player);
			player.setRow((i + 1) % 12);
			player.setCol(j);
		}
		else if(ans.equals("e")) {
			cave[i][(j + 1) % 12].addPlayer(player);
			player.setRow(i);
			player.setCol((j + 1) % 12);
		}
		else if(ans.equals("w")) {
			cave[i][(j + 11) % 12].addPlayer(player);
			player.setRow(i);
			player.setCol((j + 11) % 12);
		}
		else if(ans.equals("arrow")) {
			System.out.print("Shoot (n, e, s, w)? ");
			Scanner in = new Scanner(System.in);
			String dir = in.next();
			shootArrow(dir);
		}
		else {
			//invalid command does nothing
			//(Just puts the player back where they were.)
			cave[i][j].addPlayer(player);
		}
		printMessage();
		setChanged();
		notifyObservers();
	}

	/**
	 * If the player chooses to shoot the arrow, this method is called and will 
	 * determine who dies once the arrow is shot in direction dir
	 * @param dir is the string representing the direction of the arrow
	 */
	public void shootArrow(String dir) {
		if(dir.equals("n")) {
			if(player.getCol() == wump.getCol()) {
				System.out.println("Your arrow hit the wumpus.  You win.\n");
				win = true;
			}
			else {
				System.out.println("You just shot yourself.  You lose.\n");
			}
		}
		else if(dir.equals("e")) {
			if(player.getRow() == wump.getRow()) {
				System.out.println("Your arrow hit the wumpus.  You win.\n");
				win = true;
			}
			else {
				System.out.println("You just shot yourself.  You lose.\n");
			}
		}
		else if(dir.equals("s")) {
			if(player.getCol() == wump.getCol()) {
				System.out.println("Your arrow hit the wumpus.  You win.\n");
				win = true;
			}
			else {
				System.out.println("You just shot yourself.  You lose.\n");
			}
		}
		else if(dir.equals("w")) {
			if(player.getRow() == wump.getRow()) {
				System.out.println("Your arrow hit the wumpus.  You win.\n");
				win = true;
			}
			else {
				System.out.println("You just shot yourself.  You lose.\n");
			}
		}
		setChanged();
		notifyObservers();
		gameOver = true;
		makeGameOver();
	}

	/**
	 * makeGameOver will make all of the rooms visible so they can be
	 * seen after the game is over
	 */
	private void makeGameOver() {
		/*
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				if(!cave[i][j].isVisible()) {
					cave[i][j].setVisible();
				}
			}
		}
		*/
		printCave();
		setChanged();
		notifyObservers();
	}
  
}