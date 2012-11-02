package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {

	public enum DoorDirection {
		UP, DOWN, LEFT, RIGHT, NONE
	}

	private DoorDirection doorDirection;
	private char initial;

	public RoomCell() {
		doorDirection = DoorDirection.NONE;
	}

	public void setRoom(char r) {
		this.initial = r;
	}

	public boolean isRoom() {
		if (this.getInitial() != 'X') {
			return true;
		} else {
			return false;
		}
	}

	public boolean isWalkway() {
		return false;
	}

	public boolean isDoorway() {
		if (doorDirection != DoorDirection.NONE) {
			return true;
		} else {
			return false;
		}
	}


	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}

	public void setDoorDirection(char d) {
		if (d == 'U') {
			this.doorDirection = DoorDirection.UP;
		} else if (d == 'D') {
			this.doorDirection = DoorDirection.DOWN;
		} else if (d == 'L') {
			this.doorDirection = DoorDirection.LEFT;
		} else if (d == 'R') {
			this.doorDirection = DoorDirection.RIGHT;
		}
	}

	@Override
	void draw(Graphics g, Board brd) {
		g.setColor(Color.GRAY);
		g.fillRect(brd.CELLSIZE*col, brd.CELLSIZE*row, brd.CELLSIZE, brd.CELLSIZE);
		//TODO add door direction
	}

}
