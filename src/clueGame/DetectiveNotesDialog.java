package clueGame;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog {

	private Board gameBoard;
	
	private JPanel peoplePanel = new JPanel();
	private JPanel personGuessPanel = new JPanel();
	private JPanel roomsPanel = new JPanel();
	private JPanel roomGuessPanel = new JPanel();
	private JPanel weaponsPanel = new JPanel();
	private JPanel weaponGuessPanel = new JPanel();
	
	public DetectiveNotesDialog(Board b) {
		gameBoard = b;
		setTitle("Detective Notes");
		setLayout(new GridLayout(3, 0));
		setSize(500, 500);
		fillPeoplePanel();
		fillRoomsPanel();
		fillWeaponsPanel();
		fillPersonGuessPanel();
		fillRoomGuessPanel();
		fillWeaponGuessPanel();
		add(peoplePanel);
		add(personGuessPanel);
		add(roomsPanel);
		add(roomGuessPanel);
		add(weaponsPanel);
		add(weaponGuessPanel);
	}
	
	private void fillPeoplePanel () {
		peoplePanel.setLayout(new GridLayout(0,2));
		peoplePanel.setBorder(new TitledBorder("People"));
		for(Player player : gameBoard.getPlayers()) {
			peoplePanel.add(new JCheckBox(player.getName()));
		}
	}
	
	private void fillRoomsPanel () {
		roomsPanel.setLayout(new GridLayout(0,2));
		roomsPanel.setBorder(new TitledBorder("Rooms"));
		for(String str : gameBoard.getRooms().values()) {
			roomsPanel.add(new JCheckBox(str));
		}
	}
	
	private void fillWeaponsPanel () {
		weaponsPanel.setLayout(new GridLayout(0,2));
		weaponsPanel.setBorder(new TitledBorder("Weapons"));
		for(String str : gameBoard.getWeapons()) {
			weaponsPanel.add(new JCheckBox(str));
		}
	}
	
	private void fillPersonGuessPanel () {
		personGuessPanel.setLayout(new GridLayout(0,2));
		personGuessPanel.setBorder(new TitledBorder("Person Guess"));
		JComboBox combo = new JComboBox();
		combo.addItem("Unsure");
		for(Player player : gameBoard.getPlayers()) {
			combo.addItem(player.getName());
		}
		personGuessPanel.add(combo);
	}
	
	private void fillRoomGuessPanel () {
		roomGuessPanel.setLayout(new GridLayout(0,2));
		roomGuessPanel.setBorder(new TitledBorder("Room Guess"));
		JComboBox combo = new JComboBox();
		combo.addItem("Unsure");
		for(String str : gameBoard.getRooms().values()) {
			combo.addItem(str);
		}
		roomGuessPanel.add(combo);
	}
	
	
	private void fillWeaponGuessPanel () {
		weaponGuessPanel.setLayout(new GridLayout(0,2));
		weaponGuessPanel.setBorder(new TitledBorder("Weapons"));
		JComboBox combo = new JComboBox();
		combo.addItem("Unsure");
		for(String str : gameBoard.getWeapons()) {
			combo.addItem(str);
		}
		weaponGuessPanel.add(combo);
	}
	

}
