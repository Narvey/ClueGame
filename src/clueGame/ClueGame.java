package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MenuBar;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {

	public ClueGame() {
		super();
		// Adding Board to JFrame
		Board gameBoard = new Board();
		try {
			gameBoard.loadConfigFiles("ClueBoardLegend.txt", "ClueBoardLayout.csv", "weapons.txt", "players.txt");
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			//TODO human readable
			e.printStackTrace();
		}
		setLayout(new BorderLayout());
		setSize(gameBoard.getNumColumns()*gameBoard.CELLSIZE+17, gameBoard.getNumRows()*gameBoard.CELLSIZE+17);
		add(gameBoard, BorderLayout.CENTER);
		
		// Adding the file menu to JFrame
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
	}

}
