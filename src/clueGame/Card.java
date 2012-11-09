package clueGame;

public class Card {
	private String name;
	private CardType type;

	public Card(String name, CardType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public CardType getType() {
		return type;
	}

	public enum CardType {
		ROOM("Room"), WEAPON("Weapon"), PERSON("Person");
		
		private String value;
		
		CardType(String value) {
			this.value = value;
		}
		public String toString() {
			return value;
		}
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		
		if (other instanceof Card) {
			Card o = (Card) other;
			return o.name.equals(name) && o.type == type;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return type.toString() + ": " + name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() + type.hashCode();
	}
}
