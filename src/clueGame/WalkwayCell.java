package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {

	public WalkwayCell() {
		super();
	}

	public boolean isWalkway() {
		return true;
	}

	public boolean isRoom() {
		return false;
	}

	public boolean isDoorway() {
		return false;
	}


	@Override
	public char getInitial() {
		return 'W';
	}

	@Override
	void draw(Graphics g, Board brd) {
		int size = brd.CELLSIZE;
		g.setColor(Color.YELLOW);
		g.fillRect(size*row, size*col, size, size);
		g.setColor(Color.BLACK);
		g.drawRect(size*row, size*col, size, size);
	}
}
