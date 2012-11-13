package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class AccusationDialog extends JDialog {

	private Board gameBoard;
	private GameControlPanel controlPanel;
	private JComboBox personGuessCombo, roomGuessCombo, weaponGuessCombo;
	private JButton submitButton, cancelButton;

	public AccusationDialog(Board gameBoard, GameControlPanel controlPanel) {
		this.gameBoard = gameBoard;
		this.controlPanel = controlPanel;
		personGuessCombo = new JComboBox();
		roomGuessCombo = new JComboBox();
		weaponGuessCombo = new JComboBox();
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");

		setTitle("Make an Accusation.");
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
		panel.add(new JLabel("Room"));
		for(String str : gameBoard.getRooms().values()) {
			roomGuessCombo.addItem(str);
		}
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
				controlPanel.setSubmitAccusation(true);
				setVisible(false);
				
				// Get selected data.
				String personName = (String) personGuessCombo.getSelectedItem();
				String weaponName = (String) weaponGuessCombo.getSelectedItem();
				String roomName = (String) roomGuessCombo.getSelectedItem();
				Card person = new Card(personName, CardType.PERSON);
				Card weapon = new Card(weaponName, CardType.WEAPON);
				Card room = new Card(roomName, CardType.ROOM);

				// Check if accusation is correct.
				if(gameBoard.checkAccusation(person, weapon, room)) {
					controlPanel.setGameOver(true);
					JOptionPane.showMessageDialog(null, "You win!");
					controlPanel.newGamePrompt();
				}
				else {
					JOptionPane.showMessageDialog(null, "Sorry, not correct.");
				}
				
			}
			else {
				controlPanel.setSubmitAccusation(false);
				setVisible(false);
			}

		}
	}
}
