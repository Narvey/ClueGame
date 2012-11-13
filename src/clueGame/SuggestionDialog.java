package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Card.CardType;

public class SuggestionDialog extends JDialog {

	private Board gameBoard;
	private GameControlPanel controlPanel;
	private JComboBox personGuessCombo, roomGuessCombo, weaponGuessCombo;
	private JButton submitButton, cancelButton;
	private String currentRoom;
	private String personName;
	private static List<Player> players;
	private Map<Character, String> rooms;
	private Player currentPlayer;

	public SuggestionDialog(Board gameBoard, GameControlPanel controlPanel) {
		this.gameBoard = gameBoard;
		this.controlPanel = controlPanel;
		rooms = gameBoard.getRooms();
		players = gameBoard.getPlayers();
		currentPlayer = controlPanel.getCurrentPlayer();
		personGuessCombo = new JComboBox();
		roomGuessCombo = new JComboBox();
		weaponGuessCombo = new JComboBox();
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");

		setTitle("Make an Suggestion.");
		setLayout(new GridLayout(4, 0));
		setSize(300, 200);
		add(roomGuessPanel());
		add(personGuessPanel());
		add(weaponGuessPanel());
		add(buttonPanel());

		submitButton.addActionListener(new ButtonsListener());
		cancelButton.addActionListener(new ButtonsListener());

	}

	private JPanel personGuessPanel() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(new JLabel("Person"));
		for(Player player : gameBoard.getPlayers()) {
			personGuessCombo.addItem(player.getName());
		}
		panel.add(personGuessCombo);
		return panel;
	}

	private JPanel roomGuessPanel () {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(new JLabel("Your Room"));
		BoardCell currentPlayerCell = gameBoard.getCellAt(currentPlayer.getCellIndex());
		currentRoom = rooms.get(currentPlayerCell.getInitial());
		roomGuessCombo.addItem(currentRoom);
		panel.add(roomGuessCombo);
		return panel;
	}

	private JPanel weaponGuessPanel () {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(new JLabel("Weapon"));
		for(String str : gameBoard.getWeapons()) {
			weaponGuessCombo.addItem(str);
		}
		panel.add(weaponGuessCombo);
		return panel;
	}

	private JPanel buttonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(submitButton);
		panel.add(cancelButton);
		return panel;
	}

	private class ButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submitButton) {				

				// Get selected data.
				personName = (String) personGuessCombo.getSelectedItem();
				String weaponName = (String) weaponGuessCombo.getSelectedItem();
				String roomName = (String) roomGuessCombo.getSelectedItem();
				Card person = new Card(personName, CardType.PERSON);
				Card weapon = new Card(weaponName, CardType.WEAPON);
				Card room = new Card(roomName, CardType.ROOM);
				CardSet guessCards = new CardSet(person, weapon, room);

				// Move the suggested player to the room.
				Player target = findPlayerByName(personName);
				target.setCellIndex(currentPlayer.getCellIndex());
				getParent().repaint();

				// Check if suggestion is correct.
				Card disproveCard = gameBoard.disproveSuggestion(currentPlayer, person, weapon, room);
				controlPanel.getGuessTextBox().setText(guessCards.toString());
				if(disproveCard != null) {
					controlPanel.getResponseTextBox().setText(disproveCard.getName());
				}
				else {
					controlPanel.getResponseTextBox().setText("None");
				}

			}

			setVisible(false);
		}

		public Player findPlayerByName(String name) {
			for(Player p : players) {
				if(p.getName().compareTo(name) == 0) {
					return p;
				}
			}
			return null;
		}
	}
}
