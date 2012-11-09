package clueGame;

public class CardSet {
	private Card person;
	private Card weapon;
	private Card room;
	
	public Card getPerson() {
		return person;
	}

	public Card getWeapon() {
		return weapon;
	}

	public Card getRoom() {
		return room;
	}

	public CardSet(Card person, Card weapon, Card room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

	@Override
	public String toString() {
		return "CardSet [person=" + person + ", weapon=" + weapon + ", room="
				+ room + "]";
	}
	
	
}
