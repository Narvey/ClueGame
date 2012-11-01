package clueGame;

public abstract class BoardCell {

	int row;
	int col;
	public int boardWidth;

	public abstract boolean isWalkway();

	public abstract boolean isRoom();

	public abstract boolean isDoorway();

	public abstract char getInitial();

	abstract void draw();

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public void setBoardWidth(int w) {
		this.boardWidth = w;
	}

	public int getIndex() {
		return row * boardWidth + col;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}
}
