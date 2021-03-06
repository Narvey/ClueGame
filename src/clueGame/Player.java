package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import clueGame.Board;

public class Player {
	protected String name;
	protected Color pieceColor;
	protected int cellIndex;
	protected Set<Card> cards = new HashSet<Card>();

	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Draws the Player
	 * @param g the graphics object to draw on
	 * @param brd the board this person is playing on.
	 */
	public void draw(Graphics g, Board brd){
		BoardCell loc = brd.getCellAt(cellIndex);
		g.setColor(pieceColor);
		g.fillOval(loc.getCol()*Board.CELLSIZE, loc.getRow()*Board.CELLSIZE, Board.CELLSIZE, Board.CELLSIZE);
	}

	/**
	 * @return the index of the current location of the player??
	 */
	public int getCellIndex() {
		return cellIndex;
	}
	
	public String getName() {
		return name;
	}

	public Color getPieceColor() {
		return pieceColor;
	}

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPieceColor(Color pieceColor) {
		this.pieceColor = pieceColor;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}

	public Set<Card> getCards() {
		return cards;
	}

	public void giveCard(Card card) {
		cards.add(card);
	}
	
	public Card disproveSuggestion(Card person, Card weapon, Card room) {
		List<Card> hasCards = new LinkedList<Card>();
		
		if (cards.contains(person)) {
			hasCards.add(person);
		}
		
		if (cards.contains(weapon)) {
			hasCards.add(weapon);
		}
		
		if (cards.contains(room)) {
			hasCards.add(room);
		}
		
		if (hasCards.size() > 0) {
			Random rand = new Random();
			return hasCards.get(rand.nextInt(hasCards.size()));
		} else {
			return null;
		}
	}
}
