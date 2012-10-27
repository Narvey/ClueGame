package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Computer;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.WalkwayCell;

public class GameSetupTests {
	private static Board board;
	private static Map<String,Color> names;
	
	@BeforeClass 
	public static void setUp() throws Exception {
		board = new Board();
		board.loadConfigFiles("ClueBoardLegend.txt", "ClueBoardLayout.csv");
		board.loadPlayers("players.txt");
		board.loadCards("cards.txt");
		
		names = new HashMap<String,Color>(6);
		names.put("Colonel Mustard",Color.yellow);
		names.put("Professor Plum",new Color(0xFF00FF));
		names.put("Miss White",Color.white);
		names.put("Mrs. Peacock",Color.blue);
		names.put("Mr. Green",Color.green);
		names.put("Miss Scarlet",Color.red);
	}

	@Test
	public void peopleLoaded() {
		Set<Player> opp = board.getPlayers();
		
		assertEquals(6, opp.size());
		
		if(names.remove(board.getHuman().getName())==null) fail("Your name is invalid");
		
		for(Player comp : opp){
			assertTrue(comp.getName()/*message*/,names.containsKey(comp.getName()));
			//assure all the computer players' names are in the list.
			assertTrue("Not in walkway",board.getCellAt(comp.getCellIndex()) instanceof WalkwayCell);
			
		}
		
	}
	
	@Test
	public void cardsLoaded() {
		assertEquals(12+board.getRooms().size(), board.getDeck().size());
		//6 people, 6 weapons, and however many rooms we have.
		
	}

	@Test
	public void testDeal(){
		board.deal();
		assertEquals(0, board.getDeck().size());
		//now that we dealt, the deck should be empty.

	}
}
