package model;

public class Hunter {
	
	private int row;
	private int col;

	/**
	 * Create a hunter object at loc newRow, newCol
	 * @param newRow
	 * @param newCol
	 */
	public Hunter(int newRow, int newCol) {
		row = newRow;
		col = newCol;
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
	public void setRow(int newRow) {
		row = newRow;
	}
	public void setCol(int newCol) {
		col = newCol;
	}
	
	public String toString() {
		return "O";
	}

}
