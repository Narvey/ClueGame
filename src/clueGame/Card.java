package clueGame;

public class Card {
	String name;
	CardType type;

	public Card() {
		// TODO Auto-generated constructor stub
	}

	public enum CardType {
		ROOM, WEAPON, PERSON
	}

	@Override
	public boolean equals(Object other) {
		// TODO stub
		return false;
	}
}
