package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JButton nextPlayerButton, accusationButton;
	private JTextField whoseTurnTextBox, dieTextBox, guessTextBox, ResponseTextBox;
	private List<Player> players;
	private Player humanPlayer;
	private Player currentPlayer;
	private boolean gameOver;
	private int playerIndex;
	private int currentRoll;

	public GameControlPanel(Board gameBoard) {
		gameOver = false;
		playerIndex = 0;
		players = gameBoard.getPlayers();
		humanPlayer = gameBoard.getHuman();
		
		nextPlayerButton = new JButton("Next Player");
		accusationButton = new JButton("Make an accusation");
		setLayout(new GridLayout(0,3));
		add(whoseTurnPanel());
		add(nextPlayerButton);
		add(accusationButton);
		add(diePanel());
		add(guessPanel());
		add(responsePanel());
		
		nextPlayerButton.addActionListener(new NextPlayerListener());
		
	}

	private JPanel whoseTurnPanel() {
		whoseTurnTextBox = new JTextField();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.add(new JLabel("Whose turn?"));
		panel.add(whoseTurnTextBox);
		return panel;
	}
	
	private JPanel diePanel() {
		dieTextBox = new JTextField(3);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		panel.add(new JLabel("Roll"));
		panel.add(dieTextBox);
		return panel;
	}
	
	private JPanel guessPanel() {
		guessTextBox = new JTextField();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		panel.add(new JLabel("Guess"));
		panel.add(guessTextBox);
		return panel;
	}	
	
	private JPanel responsePanel() {
		ResponseTextBox = new JTextField();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		panel.add(new JLabel("Response"));
		panel.add(ResponseTextBox);
		return panel;
	}
	
	private class NextPlayerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == nextPlayerButton) {
				// Get current player
				currentPlayer = players.get(playerIndex);
				// Show current player
				whoseTurnTextBox.setText(currentPlayer.getName());
				// Get roll for current player
				currentRoll = (new Random()).nextInt(6)+1;
				// Show current roll.
				dieTextBox.setText(Integer.toString(currentRoll));
				// Set index for next player.
				playerIndex = (playerIndex+1)%players.size();
			}
		}
		
	}
}
