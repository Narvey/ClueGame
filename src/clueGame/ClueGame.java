package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame {

	public ClueGame() {
		Board gameBoard = new Board();
		setLayout(new BorderLayout());
		setSize(gameBoard.getNumColumns()*gameBoard.CELLSIZE, gameBoard.getNumRows()*gameBoard.CELLSIZE);
		add(gameBoard, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		JFrame game = new JFrame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
	}

}
