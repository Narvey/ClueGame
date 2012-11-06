package clueGame;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog {

	private JPanel peoplePanel = new JPanel();
	private JPanel personGuessPanel = new JPanel();
	private JPanel RoomsPanel = new JPanel();
	private JPanel roomGuessPanel = new JPanel();
	private JPanel WeaponsPanel = new JPanel();
	private JPanel weaponGuessPanel = new JPanel();
	private Board gameBoard;
	
	public DetectiveNotesDialog(Board b) {
		gameBoard =b;
		setLayout(new GridLayout(3, 0));
		setSize(500, 800);
		fillPeoplePanel();
		fillRoomsPanel();
		fillWeaponsPanel();
		add(peoplePanel);
		add(personGuessPanel);
		add(RoomsPanel);
		add(roomGuessPanel);
		add(WeaponsPanel);
		add(weaponGuessPanel);
		
		//TODO add 
	}
	
	private void fillPeoplePanel () {
		peoplePanel.setLayout(new GridLayout(0,2));
		peoplePanel.setBorder(new TitledBorder("People"));
		for(Player p : gameBoard.getPlayers()) {
			peoplePanel.add(new Checkbox(p.getName()));
		}
	}
	private void fillRoomsPanel () {
		RoomsPanel.setLayout(new GridLayout(0,2));
		RoomsPanel.setBorder(new TitledBorder("Rooms"));
		for(String s : gameBoard.getRooms().values()) {
			RoomsPanel.add(new Checkbox(s));
		}
	}
	private void fillWeaponsPanel () {
		WeaponsPanel.setLayout(new GridLayout(0,2));
		WeaponsPanel.setBorder(new TitledBorder("Weapons"));
		for(String p : gameBoard.getWeapons()) {
			WeaponsPanel.add(new Checkbox(p));
		}
	}

}
