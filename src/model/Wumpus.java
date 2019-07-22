package model;

public class Wumpus {
	
	private int row;
	private int col;

	/**
	 * Creates a wumpus object with location newRow, newCol
	 *  * @param newRow
	 * @param newCol
	 */
	public Wumpus(int newRow, int newCol) {
		row = newRow;
		col = newCol;
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
	public String toString() {
		return "W";
	}

}
