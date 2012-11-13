package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import clueGame.Card.CardType;

public class ClueGame extends JFrame {
	private JMenuItem exitMenuItem = new JMenuItem("Exit");
	private JMenuItem detectiveNotesMenuItem = new JMenuItem("Show Detective Notes");
	private Board gameBoard;
	private DetectiveNotesDialog detectiveNotes;
	private GameControlPanel controlPanel;
	private PlayerDisplay playerPanel;

	public ClueGame() {
		super();
		// Adding Board to JFrame
		gameBoard = new Board();

		try {
			gameBoard.loadConfigFiles("CR-ClueLegend.txt", "CR-ClueLayout.csv", "weapons.txt", "players.txt");
		} catch (BadConfigFormatException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}

		// Pick solution cards and deal remaining cards to players.
		gameBoard.setSolution(pickSolution(gameBoard.getCards()));
		gameBoard.deal();

		add(new JScrollPane(gameBoard));
		//setLayout(new BorderLayout());
		setSize(gameBoard.getNumColumns()*gameBoard.CELLSIZE+17, gameBoard.getNumRows()*gameBoard.CELLSIZE+17);
		JScrollPane pane = new JScrollPane(gameBoard);
		pane.setSize(this.getWidth(), this.getHeight());

		controlPanel = new GameControlPanel(gameBoard);
		playerPanel = new PlayerDisplay(gameBoard.getHuman());

		//pane.add(gameBoard);
		getContentPane().add(pane, BorderLayout.CENTER);
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
		getContentPane().add(playerPanel, BorderLayout.EAST);


		// Adding the file menu to JFrame
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());		
		gameBoard.addMouseListener(gameBoard);

		// Adding Splash Screen.
		String message = "You are " + gameBoard.getHuman().getName() + ", press Next Player to begin to play.";
		String title = "Welcome to Clue";
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private Solution pickSolution(List<Card> deck) {
		// Code to pick solution
		Collections.shuffle(deck);
		Card person = null, weapon = null, room = null;
		while(person == null || weapon == null || room == null) {
			Card card = deck.remove(0);
			if(card.getType() == CardType.PERSON && person == null) {
				person = card;
			}
			else if(card.getType() == CardType.WEAPON && weapon == null) {
				weapon = card;
			}
			else if(card.getType() == CardType.ROOM && room == null) {
				room = card;
			}
		}
		System.out.println(person.getName() + " " + weapon.getName() + " " + room.getName());
		return new Solution(person, weapon, room);
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");		
		class MenuItemListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==exitMenuItem) System.exit(0);
				else if (e.getSource()==detectiveNotesMenuItem){
					if(detectiveNotes == null) {
						detectiveNotes = new DetectiveNotesDialog(gameBoard);
						detectiveNotes.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
					}
					detectiveNotes.setVisible(true);
				}
			}
		}
		exitMenuItem.addActionListener(new MenuItemListener());
		detectiveNotesMenuItem.addActionListener(new MenuItemListener());
		menu.add(detectiveNotesMenuItem);
		menu.add(exitMenuItem);

		return menu;
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setTitle("The game of Clue-Lah!");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
	}

}
