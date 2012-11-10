package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public static final int DOORWIDTH = 5;///width, in pixels, of the door.
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
		return (this.getInitial() != 'X');
	}

	public boolean isWalkway() {
		return false;
	}

	public boolean isDoorway() {
		return (doorDirection != DoorDirection.NONE);
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
		int x = brd.CELLSIZE*col;
		int y = brd.CELLSIZE*row;
		g.fillRect(x, y, brd.CELLSIZE, brd.CELLSIZE);
		g.setColor(Color.BLUE);
		switch (doorDirection){
		case UP:
			g.fillRect(x, y, brd.CELLSIZE, DOORWIDTH);
			break;
		case DOWN:
			g.fillRect(x, y+brd.CELLSIZE-DOORWIDTH, brd.CELLSIZE, DOORWIDTH);
			break;
		case LEFT:
			g.fillRect(x, y, DOORWIDTH, brd.CELLSIZE);
			break;
		case RIGHT:
			g.fillRect(x+brd.CELLSIZE-DOORWIDTH, y, DOORWIDTH, brd.CELLSIZE);
			break;
		default:
			g.drawString(String.valueOf(initial), x, y+brd.CELLSIZE/2);
			break;
		}
	}

}
