package clueGame;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.attribute.standard.JobName;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog {

	private Board gameBoard;
	private JComboBox personGuessCombo, roomGuessCombo, weaponGuessCombo;
	
	public DetectiveNotesDialog(Board gameBoard) {
		this.gameBoard = gameBoard;
		personGuessCombo = new JComboBox();
		roomGuessCombo = new JComboBox();
		weaponGuessCombo = new JComboBox();
		
		setTitle("Detective Notes");
		setLayout(new GridLayout(3, 0));
		setSize(500, 500);
		
		add(peoplePanel());
		add(personGuessPanel());
		add(roomsPanel());
		add(roomGuessPanel());
		add(weaponsPanel());
		add(weaponGuessPanel());
	}
	
	private JPanel peoplePanel () {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.setBorder(new TitledBorder("People"));
		for(Player player : gameBoard.getPlayers()) {
			JCheckBox cb = new JCheckBox(player.getName());
			cb.addActionListener(new checkBoxListener(personGuessCombo, cb, player.getName()));
			panel.add(cb);
		}
		return panel;
	}
	
	private JPanel roomsPanel () {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.setBorder(new TitledBorder("Rooms"));
		for(String str : gameBoard.getRooms().values()) {
			JCheckBox cb = new JCheckBox(str);
			cb.addActionListener(new checkBoxListener(roomGuessCombo, cb, str));
			panel.add(cb);
		}
		return panel;
	}
	
	private JPanel weaponsPanel () {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.setBorder(new TitledBorder("Weapons"));
		for(String str : gameBoard.getWeapons()) {
			JCheckBox cb = new JCheckBox(str);
			cb.addActionListener(new checkBoxListener(weaponGuessCombo, cb, str));
			panel.add(cb);
		}
		return panel;
	}
	
	private JPanel personGuessPanel() {

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new TitledBorder("Person Guess"));
		personGuessCombo.addItem("Haven't Decided");
		for(Player player : gameBoard.getPlayers()) {
			personGuessCombo.addItem(player.getName());
		}
		panel.add(personGuessCombo, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel roomGuessPanel () {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new TitledBorder("Room Guess"));
		roomGuessCombo.addItem("Haven't Decided");
		for(String str : gameBoard.getRooms().values()) {
			roomGuessCombo.addItem(str);
		}
		panel.add(roomGuessCombo, BorderLayout.CENTER);
		return panel;
	}
	
	
	private JPanel weaponGuessPanel () {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new TitledBorder("Weapon Guess"));
		weaponGuessCombo.addItem("Haven't Decided");
		for(String str : gameBoard.getWeapons()) {
			weaponGuessCombo.addItem(str);
		}
		panel.add(weaponGuessCombo, BorderLayout.CENTER);
		return panel;
	}
	
	private class checkBoxListener implements ActionListener {
		private JComboBox combo;
		private JCheckBox cb;
		private String str;
		
		public checkBoxListener(JComboBox combo, JCheckBox cb, String str) {
			this.combo = combo;
			this.cb = cb;
			this.str = str;
		}
		
		@Override 
		public void actionPerformed(ActionEvent e) {
			if(this.cb.isSelected()) {
				combo.removeItem(str);
			}
			else {
				combo.addItem(str);
			}
		}
	}
	
}
