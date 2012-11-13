package clueGame;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private Set<Card> seenCards = new HashSet<Card>();
	private Solution accusationCards;
	private boolean foundAccusation;

	public ComputerPlayer() {
		foundAccusation = false;
		// TODO Auto-generated constructor stub
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		if (targets.size() == 0) return null;
		
		// look for a room in the targets
		for (BoardCell target : targets) {
			// if its a room that wasn't the last one visited, choose it
			if (target.isRoom() && target.getInitial() != lastRoomVisited) {
				return target;
			}
		}
		
		Random rand = new Random();
		return (BoardCell) (targets.toArray())[rand.nextInt(targets.size())];
	}

	/**
	 * Can return null if every card of a certain type has been seen
	 */
	public Solution createSuggestion(Card room, List<Card> cards) {
		
		List<Card> weapons = new LinkedList<Card>();
		List<Card> people = new LinkedList<Card>();
		
		for (Card card : cards) {
			if (!seenCards.contains(card) && !getCards().contains(card)) {
				switch(card.getType()) {
				case WEAPON:
					weapons.add(card);
					break;
				case PERSON:
					people.add(card);
					break;
				}
			}
		}
		
		if (weapons.size() == 0 || people.size() == 0) return null;
		
		Random rand = new Random();
		
		Card weapon = weapons.get(rand.nextInt(weapons.size()));
		Card person = people.get(rand.nextInt(people.size()));
		
		return new Solution(person, weapon, room);
	}

	public Solution getAccusationCardsCards() {
		return accusationCards;
	}

	public void setAccusationCards(Solution accusationCards) {
		this.accusationCards = accusationCards;
	}

	public boolean isFoundAccusation() {
		return foundAccusation;
	}

	public void setFoundAccusation(boolean foundAccusation) {
		this.foundAccusation = foundAccusation;
	}

	public void markSeen(Card seen) {
		seenCards.add(seen);
	}

	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
}
