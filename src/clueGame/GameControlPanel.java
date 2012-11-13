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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JButton nextPlayerButton, accusationButton;
	private JTextField whoseTurnTextBox, dieTextBox, guessTextBox, ResponseTextBox;
	private static List<Player> players;
	private Player humanPlayer;
	private Player currentPlayer;

	private boolean gameOver, submitAccusation;
	private AccusationDialog accusationDialog;
	private Board gameBoard;
	private GameControlPanel controlPanel;
	private static int whichPlayer;
	private static int currentRoll;

	public GameControlPanel(Board gameBoard) {
		this.gameBoard = gameBoard;
		gameOver = false;
		submitAccusation = false;
		whichPlayer = 0;
		players = gameBoard.getPlayers();
		humanPlayer = gameBoard.getHuman();
		currentPlayer = null;
		controlPanel = this;
		
		nextPlayerButton = new JButton("Next Player");
		accusationButton = new JButton("Make an accusation");
		setLayout(new GridLayout(0,3));
		add(whoseTurnPanel());
		add(nextPlayerButton);
		add(accusationButton);
		add(diePanel());
		add(guessPanel());
		add(responsePanel());
		
		nextPlayerButton.addActionListener(new ButtonsListener());
		accusationButton.addActionListener(new ButtonsListener());
		
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void setSubmitAccusation(boolean submitAccusation) {
		this.submitAccusation = submitAccusation;
	}

	private JPanel whoseTurnPanel() {
		whoseTurnTextBox = new JTextField();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Player Turn"));
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

	private class ButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == nextPlayerButton && gameOver == false) {
				// Set reset submit accusation.
				submitAccusation = false;
				// Get current player
				currentPlayer = players.get(whichPlayer);
				// Show current player
				whoseTurnTextBox.setText(currentPlayer.getName());
				// Get roll for current player
				currentRoll = (new Random()).nextInt(6)+1;
				// Show current roll.
				dieTextBox.setText(Integer.toString(currentRoll));
				// Set index for next player.
				whichPlayer = (whichPlayer+1)%players.size();
				// redraw board, etc.
				getParent().repaint();
			}
			else if(e.getSource() == accusationButton && gameOver == false && submitAccusation == false) {
				
				if (accusationDialog == null) {
					accusationDialog = new AccusationDialog(gameBoard, controlPanel);
					accusationDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
				}
				
				if(currentPlayer == null || (currentPlayer.getName()).compareTo(humanPlayer.getName()) != 0) {
					
					JOptionPane.showMessageDialog(null, "It's not your turn.");
				}
				else {
					accusationDialog.setVisible(true);
				}
			}
			else if(e.getSource() == accusationButton && gameOver == false && submitAccusation == true) {
				JOptionPane.showMessageDialog(null, "It's not your turn.");
			}
			else {
				JOptionPane.showMessageDialog(null, "Game over!");
			}
		}
		
	}


	/**
	 * @return the current player
	 */
	public static Player getCurrentPlayer() {
		return players.get(whichPlayer);
	}

	/**
	 * @return the currentRoll
	 */
	public static int getCurrentRoll() {
		return currentRoll;
	}

}
