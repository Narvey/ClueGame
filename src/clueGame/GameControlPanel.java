package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.junit.runner.Computer;

import clueGame.Card.CardType;

public class GameControlPanel extends JPanel {
	private static JButton nextPlayerButton, accusationButton;
	private static JTextField whoseTurnTextBox, dieTextBox, guessTextBox, responseTextBox;
	private static List<Player> players;
	private static HumanPlayer humanPlayer;
	private static Player currentPlayer;

	private static boolean gameOver, submitAccusation;
	private static AccusationDialog accusationDialog;
	private static Board gameBoard;
	private static GameControlPanel controlPanel;
	private static int whichPlayer;
	private static int currentRoll;
	private static Map<Character, String> rooms;
	private static ClueGame clueGame;

	public GameControlPanel(Board gameBoardIn, ClueGame clueGameIn) {
		clueGame = clueGameIn;
		gameBoard = gameBoardIn;
		gameOver = false;
		submitAccusation = false;
		whichPlayer = 0;
		players = gameBoard.getPlayers();
		humanPlayer = gameBoard.getHuman();
		rooms = gameBoard.getRooms();
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

	public void setGuessText(String txt) {
		guessTextBox.setText(txt);
	}

	public JTextField getResponseTextBox() {
		return responseTextBox;
	}
	public void nextPlayer(){
		// Set index for next player.
		whichPlayer = (whichPlayer+1)%players.size();
	}

	public void setGameOver(boolean gameOvers) {
		gameOver = gameOvers;
	}

	public void setSubmitAccusation(boolean submitAccusations) {
		submitAccusation = submitAccusations;
		if(submitAccusation)gameBoard.endHumanTurn();
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
		responseTextBox = new JTextField();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		panel.add(new JLabel("Response"));
		panel.add(responseTextBox);
		return panel;
	}

	private class ButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == nextPlayerButton && gameOver == false) {
				// Reset Dialog
				responseTextBox.setText("");
				guessTextBox.setText("");
				// Reset submit accusation.
				submitAccusation = false;
				// Reset accusation dialog
				accusationDialog = null;
				// Get current player
				currentPlayer = players.get(whichPlayer);
				// Show current player
				whoseTurnTextBox.setText(currentPlayer.getName());
				// Get roll for current player
				currentRoll = (new Random()).nextInt(6)+1;
				// Show current roll.
				dieTextBox.setText(Integer.toString(currentRoll));
				// if computer player then makeMove;
				if(gameBoard.isHumanTurn()){//human never finished their turn
					JOptionPane.showMessageDialog(getParent(), "You must make a move");
				}else if(!currentPlayer.equals(humanPlayer)) {
					makeMove();
				}else {
					gameBoard.highlight();
				}

			}
			else if(e.getSource() == accusationButton && gameOver == false && submitAccusation == false) {
				if(gameBoard.isHumanTurn()){

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
				}else //It is not the human's turn
					JOptionPane.showMessageDialog(getParent(), "You can't make an accusation when not your turn.");
			}
			else if(e.getSource() == accusationButton && gameOver == false && submitAccusation == true) {
				JOptionPane.showMessageDialog(null, "It's not your turn to move.");
			}
			else {
				newGamePrompt();
			}
		}
	}

	/**
	 * Have the computer player make a move
	 */
	private void makeMove() {

		if(!((ComputerPlayer) currentPlayer).isFoundAccusation()) {
			List<Card> cards = new LinkedList<Card>(gameBoard.getFulldeck());
			// Pick a location from the calculated list.
			BoardCell cell = ((ComputerPlayer) currentPlayer).pickLocation(gameBoard.getTargets(currentPlayer.getCellIndex(), currentRoll));
			// moves to that location.
			currentPlayer.setCellIndex(cell.getIndex());
			// causes the board to repaint.
			getParent().repaint();
			// if location is room
			if(cell.isRoom()) {
				char key = cell.getInitial();
				((ComputerPlayer) currentPlayer).setLastRoomVisited(key);
				String name = rooms.get(key);
				Card room = new Card(name, CardType.ROOM);
				// Get suggestionCards
				Solution suggestionCards = ((ComputerPlayer) currentPlayer).createSuggestion(room, cards);
				Card person = suggestionCards.getPerson();
				Card weapon = suggestionCards.getWeapon();

				Card disproveCard = gameBoard.disproveSuggestion(currentPlayer, person, weapon, room);
				guessTextBox.setText(suggestionCards.toString());
				if(disproveCard != null) {
					responseTextBox.setText(disproveCard.getName());
					((ComputerPlayer) currentPlayer).markSeen(disproveCard);
				}
				else {
					responseTextBox.setText("None");
					((ComputerPlayer) currentPlayer).setFoundAccusation(true);
					((ComputerPlayer) currentPlayer).setAccusationCards(suggestionCards);
				}
			}
		}
		else {
			// Make Accusation.
			Solution accusationCards = ((ComputerPlayer) currentPlayer).getAccusationCardsCards();
			Card person = accusationCards.getPerson();
			Card weapon = accusationCards.getWeapon();
			Card room = accusationCards.getRoom();
			// check if accusation is right.
			if (gameBoard.checkAccusation(person, weapon, room)) {
				gameOver = true;
				JOptionPane.showMessageDialog(null, currentPlayer.getName() + "'s has won. The answer is " + accusationCards.toString() + ".");
				newGamePrompt();
			}
			else {

				JOptionPane.showMessageDialog(null, currentPlayer.getName() + "'s accusation is incorrect.");
				((ComputerPlayer) currentPlayer).setFoundAccusation(false);
			}
		}
		nextPlayer();
	}



	public void newGamePrompt() {
		int newGame = JOptionPane.showConfirmDialog(null, "Start a new game?");
		if (newGame == JOptionPane.YES_OPTION) {
			clueGame.setVisible(false);
			clueGame = new ClueGame();
			clueGame.setTitle("The game of Clue-Lah!");
			clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			clueGame.setVisible(true);
		}
		else if (newGame == JOptionPane.NO_OPTION){
			System.exit(0);
		}
	}

	/**
	 * @return the current player
	 */
	public static Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @return the currentRoll
	 */
	public static int getCurrentRoll() {
		return currentRoll;
	}


}
