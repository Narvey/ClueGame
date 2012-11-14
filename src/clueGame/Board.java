package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.junit.runners.ParentRunner;
import clueGame.Card.CardType;
import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel implements MouseListener{
	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character, String> rooms = new TreeMap<Character, String>();
	private Map<Integer, String> labels = new TreeMap<Integer, String>();
	private Map<Integer, LinkedList<Integer>> adjacencies = new HashMap<Integer, LinkedList<Integer>>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<Integer> path = new HashSet<Integer>();
	private List<Player> players = new ArrayList<Player>(); // contains all players
	private List<String> weapons = new ArrayList<String>();
	private HumanPlayer human;
	private boolean humanturn=false;///is it the human's turn?
	private List<Card> cards = new ArrayList<Card>();
	private List<Card> fulldeck;
	private Solution solution;
	public static final int CELLSIZE=30;
	public static final int ROUND=CELLSIZE/2;
	private int numRows=15;
	private int numColumns=15;//Default values avoid miniscule window.
	private GameControlPanel ctrlpanel;
	private SuggestionDialog suggestionDialog;

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(BoardCell o : cells){
			o.draw(g, this);
		}
		for(Integer i : labels.keySet()){
			int x = getCellAt(i).getCol()*CELLSIZE; //(i%numColumns)*CELLSIZE;
			int y = getCellAt(i).getRow()*CELLSIZE; //(i/numColumns)*CELLSIZE;
			g.drawString(labels.get(i), x, y+CELLSIZE/2);
		}
		for(Player p : players){
			p.draw(g, this);
		}
	}

	public void setCtrlpanel(GameControlPanel ct){
		ctrlpanel = ct;
	}

	/**
	 * This function highlights the legal moves.
	 * It should be called when it is time for the human's turn.
	 */
	public void highlight(){
		Graphics g = getGraphics();
		int roll=GameControlPanel.getCurrentRoll();
		targets.clear();
		path.clear();
		path.add(human.getCellIndex());
		calcTargets(human.getCellIndex(), roll);
		g.setColor(new Color(0xAA0055FF, true));
		for(BoardCell c : targets){
			g.fillRoundRect(c.getCol()*CELLSIZE, c.getRow()*CELLSIZE, CELLSIZE, CELLSIZE, ROUND, ROUND);
		}
		humanturn=true;//if we are highlighting the options, it better be the human's turn.
	}

	public void endHumanTurn(){
		humanturn=false;
		repaint();
	}

	public boolean isHumanTurn(){
		return humanturn;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(humanturn){
			int row = e.getY()/CELLSIZE;
			int col = e.getX()/CELLSIZE;
			if(targets.contains(getCellAt(row,col))){
				human.setCellIndex(calcIndex(row, col));
				getParent().repaint();
				BoardCell currentPlayerCell = cells.get(calcIndex(row, col));
				if(currentPlayerCell.isRoom()) {
					suggestionDialog = new SuggestionDialog(this, ctrlpanel);
					suggestionDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
					suggestionDialog.setVisible(true);
				}
				//TODO actually do something.
				humanturn=false;//Now your turn is done
				ctrlpanel.nextPlayer();
			}else JOptionPane.showMessageDialog(getParent(), "Not a valid move");
		}else JOptionPane.showMessageDialog(this, "Not your turn", "Problem", JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(numColumns*CELLSIZE, numRows*CELLSIZE);
	}

	public int calcIndex(int row, int col) {
		return (row * numColumns) + col;
	}

	public List<Card> getFulldeck() {
		return fulldeck;
	}

	private void calcTargets(int calcIndex, int steps) {
		for (Integer neighbor : getAdjList(calcIndex)) {
			if (path.contains(neighbor))
				continue;

			path.add(neighbor);

			// we include the initial cell in the path, so the path size has to exceed steps by one
			if (path.size() > steps || getCellAt(neighbor).isDoorway()) {
				targets.add(getCellAt(neighbor));
			} else {
				calcTargets(neighbor, steps);
			}
			path.remove(neighbor);
		}
	}

	public void deal() {
		if (players.size() == 0) return;

		Collections.shuffle(cards);

		int i = 0;
		while (cards.size() > 0) {
			Card card = cards.remove(0); // the deck is shuffled, so just remove the first card
			//String cardName = card.getName();
			players.get(i).giveCard(card);
			i = (i + 1) % players.size();

		}
	}

	public LinkedList<Integer> getAdjList(int i) {
		if (adjacencies.containsKey(i)) {
			return adjacencies.get(i);
		}

		LinkedList<Integer> neighbors = new LinkedList<Integer>();
		int column = i % numColumns;
		int row = i / numColumns;
		BoardCell adj;
		BoardCell cell = getCellAt(i);

		adjacencies.put(i, neighbors);

		if (cell.isRoom()) {
			// only doorways have adjacencies, and they only have one, so short-circuit

			// if a door was placed facing an edge, this would be invalid, but the board should not be set up that way
			if (cell.isDoorway()) {
				switch (((RoomCell) cell).getDoorDirection()) {
				case RIGHT:
					neighbors.add(i + 1);
					break;
				case LEFT:
					neighbors.add(i - 1);
					break;
				case UP:
					neighbors.add(i - numColumns);
					break;
				case DOWN:
					neighbors.add(i + numColumns);
					break;
				}
			}
			return neighbors;
		}

		if (column > 0) {
			adj = getCellAt(i - 1);// the cell to the left

			if (adj.isWalkway() || (adj.isDoorway() && ((RoomCell) adj).getDoorDirection() == DoorDirection.RIGHT)) {
				neighbors.add(i - 1);
			}
		}

		if (column < numColumns - 1) {
			adj = getCellAt(i + 1);// the cell to the right

			if (adj.isWalkway() || (adj.isDoorway() && ((RoomCell) adj).getDoorDirection() == DoorDirection.LEFT)) {
				neighbors.add(i + 1);
			}
		}

		if (row > 0) {
			adj = getCellAt(i - numColumns);// the cell above

			if (adj.isWalkway() || (adj.isDoorway() && ((RoomCell) adj).getDoorDirection() == DoorDirection.DOWN)) {
				neighbors.add(i - numColumns);
			}
		}

		if (row < numRows - 1) {
			adj = getCellAt(i + numColumns);// the cell below

			if (adj.isWalkway() || (adj.isDoorway() && ((RoomCell) adj).getDoorDirection() == DoorDirection.UP)) {
				neighbors.add(i + numColumns);
			}
		}

		return neighbors;
	}

	public List<Card> getCards() {
		return cards;
	}

	public BoardCell getCellAt(int i) {
		return cells.get(i);
	}

	public BoardCell getCellAt(int row, int col) {
		return cells.get(calcIndex(row, col));
	}

	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public HumanPlayer getHuman() {
		return human;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public int getNumRows() {
		return numRows;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public RoomCell getRoomCellAt(int row, int col) {
		if (cells.get(this.calcIndex(row, col)).isRoom()) {
			return (RoomCell) cells.get(this.calcIndex(row, col));
		} else {
			return null;
		}
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public Solution getSolution() {
		return solution;
	}

	public Set<BoardCell> getTargets(int i, int steps) {
		targets.clear();
		path.clear();
		path.add(i);
		calcTargets(i, steps);
		return targets;
	}

	public boolean checkAccusation(Card person, Card weapon, Card room) {
		if (person.equals(solution.getPerson()) && weapon.equals(solution.getWeapon()) && room.equals(solution.getRoom())) {
			return true;
		} else {
			return false;
		}
	}

	public Card disproveSuggestion(Player from, Card person, Card weapon, Card room) {
		List<Card> foundCards = new LinkedList<Card>();

		for (Player player : players) {
			if (player != from) {
				Card card = player.disproveSuggestion(person, weapon, room);
				if (card != null) {
					foundCards.add(card);
				}
			}
		}

		if (foundCards.size() > 0) {
			Random rand = new Random();
			return foundCards.get(rand.nextInt(foundCards.size()));
		} else {
			return null;
		}
	}

	private void loadBoard(String boardFile) throws BadConfigFormatException, IOException {
		FileReader reader = new FileReader(boardFile);
		Scanner scan = new Scanner(reader);
		// populate cell list
		int j = 0;
		while (scan.hasNext()) {
			String wholeString = scan.nextLine();
			if (!wholeString.contains(",")) {
				throw new BadConfigFormatException("Comma missing in file " + boardFile);
			}
			String[] strArr = wholeString.split(",");
			for (int i = 0; i < strArr.length; i++) {
				String str = strArr[i].trim();
				if (str.equalsIgnoreCase("W")) {
					WalkwayCell wc = new WalkwayCell();
					wc.setCol(i);
					wc.setRow(j);
					wc.setBoardWidth(strArr.length);
					cells.add(wc);
				} else {
					RoomCell rc = new RoomCell();
					if (str.length() == 2) {
						char d = str.charAt(1);
						rc.setDoorDirection(d);
					}
					rc.setCol(i);
					rc.setRow(j);
					rc.setBoardWidth(strArr.length);
					rc.setRoom(str.charAt(0));
					cells.add(rc);
				}
			}
			numColumns = strArr.length;
			++j;
		}
		numRows = j;

		scan.close();
		reader.close();
	}

	private void loadWeapons(String weaponsFile) throws IOException {
		FileReader reader = new FileReader(weaponsFile);
		Scanner scan = new Scanner(reader);

		while (scan.hasNextLine()) {
			String currentLine = scan.nextLine().trim();
			cards.add(new Card(currentLine, CardType.WEAPON));
			weapons.add(currentLine);
		}

		scan.close();
		reader.close();
	}

	public void loadConfigFiles(String legendFile, String boardFile, String weaponsFile, String playersFile) throws BadConfigFormatException, IOException {
		loadBoard(boardFile);
		loadLegend(legendFile);
		loadPlayers(playersFile);
		loadWeapons(weaponsFile);
		// Copy current deck of cards.
		fulldeck = new ArrayList<Card>(cards);
	}

	private void loadLegend(String legendFile) throws BadConfigFormatException, IOException {
		FileReader reader = new FileReader(legendFile);
		Scanner scan = new Scanner(reader);
		// populate legend map
		while (scan.hasNext()) {
			String wholeString = scan.nextLine();
			if (!wholeString.contains(",")) {
				throw new BadConfigFormatException("Comma missing in file " + legendFile);
			}
			String[] stringArr = wholeString.split(",");
			String character = stringArr[0];
			String room = stringArr[1].trim();
			char c = character.charAt(0);			
			if(room.compareTo("Walkway") != 0 && room.compareTo("Closet") != 0)
				rooms.put(c, room);
			if(stringArr[2].trim().equals("X"))continue;
			//X means don't draw the name.
			try{
				labels.put(calcIndex(Integer.parseInt(stringArr[2].trim()),Integer.parseInt(stringArr[3].trim())),room);
			}catch(NumberFormatException e){
				throw new BadConfigFormatException(e.getMessage().substring(18) + " is not an integer.");
			}
			//Add label location from the config file.

		}

		scan.close();
		reader.close();

		for (String room : rooms.values()) {
			cards.add(new Card(room, CardType.ROOM));
		}
	}

	private void loadPlayers(String playersFile) throws BadConfigFormatException, IOException {
		clearPlayers();
		FileReader reader = new FileReader(playersFile);
		Scanner scan = new Scanner(reader);
		human = new HumanPlayer();
		loadPlayer(human, scan);
		players.add(human);
		while (scan.hasNextLine()) {
			ComputerPlayer droid = new ComputerPlayer();
			loadPlayer(droid, scan);
			players.add(droid);
		}

		scan.close();
		reader.close();

		for (Player person : players) {
			cards.add(new Card(person.getName(), CardType.PERSON));
		}
	}

	private void loadPlayer(Player player, Scanner scan) throws BadConfigFormatException {
		String[] line = scan.nextLine().split(",");
		if (line.length != 3) {
			throw new BadConfigFormatException("Wrong number of values in line " + line + " of players file");
		}
		player.setName(line[0].trim());
		player.setPieceColor(Color.decode(line[1].trim()));
		player.setCellIndex(Integer.parseInt(line[2].trim()));
	}

	public void clearPlayers() {
		players.clear();
	}

	public void selectAnswer() {

	}

	public List<String> getWeapons() {
		return weapons;
	}
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	//Don't need to do anything with the following events
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
