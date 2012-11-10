package clueGame;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerDisplay extends JPanel {
	private Set<Card> cards;

	public PlayerDisplay(Player player) {
		setLayout(new GridLayout(4,0));
		cards = player.getCards();
		add(new JLabel("My cards"));		
		for(Card c : cards) {
			add(cardPanel(c));
		}
		
	}
	
	private JPanel cardPanel(Card card) {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), card.getType().toString()));
		panel.add(new JTextField(card.getName()));
		return panel;
	}


}
