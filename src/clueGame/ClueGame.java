package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class ClueGame extends JFrame {
	JMenuItem exitMenuItem = new JMenuItem("Exit");
	JMenuItem detectiveNotesMenuItem = new JMenuItem("Show Detective Notes");
	Board gameBoard;
	
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
		getContentPane().add(new JScrollPane(gameBoard));
		//setLayout(new BorderLayout());
		setSize(gameBoard.getNumColumns()*gameBoard.CELLSIZE+17, gameBoard.getNumRows()*gameBoard.CELLSIZE+17);
		JScrollPane pane = new JScrollPane(gameBoard);
		pane.setSize(this.getWidth(), this.getHeight());
		//pane.add(gameBoard);
		getContentPane().add(pane, BorderLayout.CENTER);
		
		// Adding the file menu to JFrame
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");		
		class MenuItemListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==exitMenuItem) System.exit(0);
				else if (e.getSource()==detectiveNotesMenuItem){
					DetectiveNotesDialog d = new DetectiveNotesDialog(gameBoard);
					d.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
					d.setVisible(true);
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
