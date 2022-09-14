package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	
	private int numberOfRow;
	private int numberOfCol;
	private int numberOfMines;
	private Tile[][] gameBoard;
    private boolean GameWon = false;
    private boolean GameOver = false;
    private List<Tile> mineList = new ArrayList<Tile>();
	
	public Board(int numberOfRow, int numberOfCol, int numberOfMines) {
		checkValidBoardSize(numberOfRow, numberOfCol);
		checkValidNumberOfMines(numberOfRow, numberOfCol, numberOfMines);
		this.numberOfRow = numberOfRow;
		this.numberOfCol = numberOfCol;
		this.numberOfMines = numberOfMines;
		this.gameBoard = new Tile[numberOfRow][numberOfCol];
		for (int y = 0; y < numberOfRow; y++) {
			for (int x = 0; x < numberOfCol; x++) {
				gameBoard[y][x] = new Tile(y, x);
			}
		}
		randomMineGenerator();
		setAdjecancyNumber();
	}
	
	public Board(int numberOfRow, int numberOfCol) {
		checkValidBoardSize(numberOfRow, numberOfCol);
		this.numberOfRow = numberOfRow;
		this.numberOfCol = numberOfCol;
		this.gameBoard = new Tile[numberOfRow][numberOfCol];
		for (int y = 0; y < numberOfRow; y++) {
			for (int x = 0; x < numberOfCol; x++) {
				gameBoard[y][x] = new Tile(y, x);
			}
		}
	}
	
	private List<Tile> getSurroundingTiles(Tile tile) {
		List<Tile> surroundingTiles = new ArrayList<Tile>();
		int y = tile.getY();
		int x = tile.getX();
		for (int p = (y-1); p < (y+2); p++) {			// pq er tellevariabler for å iterere xy
			for (int q = (x-1); q < (x+2); q++) {
				if ((p!=y || q!=x) && isTile(p, q)) {
					surroundingTiles.add(gameBoard[p][q]);
				}
			}
		}
		return new ArrayList<>(surroundingTiles);
	}
	
	public void setAdjecancyNumber() {		          // Burde vært private
		for (Tile mines : getMineList()) {
			for (Tile tiles : getSurroundingTiles(mines)) {
				if (isTile(tiles.getY(), tiles.getX()) && !tiles.isMine()) 
					tiles.addAdjacencyNumber();
			}			
		}
	}
	
	private void randomMineGenerator() {
		List<Tile> randomTiles = new ArrayList<Tile>();
		Random rand = new Random();
		
		for (int i = 0; i < getNumberOfMines(); i++) {
			int rand_inty = rand.nextInt(getNumberOfRow());
			int rand_intx = rand.nextInt(getNumberOfCol());	
			if (!randomTiles.contains(gameBoard[rand_inty][rand_intx])) {
				randomTiles.add(gameBoard[rand_inty][rand_intx]);
				gameBoard[rand_inty][rand_intx].setMine();
			} else {
				i--;
			}
		}
		setMineList(randomTiles);
	}
	
	public void showTiles(Tile tile) {
		tile.setShown();
		if (tile.isMine()) {
			setGameOver();
			for (Tile mines : getMineList()) {
				mines.setShown();	
			}
		}	
		else if (tile.isSpace()) {
			for (Tile adjecancyTile : getSurroundingTiles(tile)) {
				if (!adjecancyTile.isShown())
					showTiles(adjecancyTile);
			}
			isGameWon();
		}
		else  
			isGameWon();
	}
	
	private void isGameWon() {
		int i = 0;
		for (int y = 0; y < getNumberOfRow(); y++) {
			for (int x = 0; x < getNumberOfCol(); x++) {	
				if (gameBoard[y][x].isMine() || gameBoard[y][x].isShown())
					i ++;
			}
		}
		if (i == (getNumberOfRow() * getNumberOfCol()))
			setGameWon();
	}
	
	public Tile getTile(int y, int x) {
		if (!isTile(y, x)) {
			throw new IllegalArgumentException("Coordinates out of bounds");
		}
		return gameBoard[y][x];
	}
	
	public Tile[][] getGameBoard() {
		return gameBoard;
	}
	
	public int getNumberOfRow() {
		return numberOfRow;
	}
	
	public int getNumberOfCol() {
		return numberOfCol;
	}
	
	public int getNumberOfMines() {
		return numberOfMines;
	}
	
	public void setMineList(List<Tile> mineList) {
		this.mineList = mineList;
	}
	
	public List<Tile> getMineList() {
		return new ArrayList<>(mineList);
	}
	
	public boolean getGameWon() {
		return GameWon;
	}
	
	public boolean getGameOver() {
		return GameOver;
	}
	
	public void setGameWon() {
		GameWon = true;
	}
	
	public void setGameOver() {
		GameOver = true;
	}
	
	private void checkValidNumberOfMines(int numberOfRow, int numberOfCol, int numberOfMines) {
		if ((numberOfRow * numberOfCol) < numberOfMines)
			throw new IllegalArgumentException("Too many mines");
		else if (numberOfMines < 0) 
			throw new IllegalArgumentException("Too few mines");
	}
	
	private void checkValidBoardSize(int numberOfRow, int numberOfCol) {
		if (numberOfRow < 1 || numberOfCol < 1)
			throw new IllegalArgumentException("Invalid board size");
	}
	
	private boolean isTile(int y, int x) {
		return y >= 0 && x >= 0 && y < getNumberOfRow() && x < getNumberOfCol();
	}
	
	@Override
	public String toString() {
		String boardString = "";
		
		for (int y = 0; y < getNumberOfRow(); y++) {
			for (int x = 0; x < getNumberOfCol(); x++) {
				
				boardString += getTile(y, x).getType();
				
				if (getTile(y, x).isShown()) {
					boardString += getTile(y, x);
					boardString += "   ";
				}else {
					boardString += "▉";
					boardString += "   ";
				}
			}
			boardString += "\n";
		}
		if (getGameWon()) {
			boardString += "\n\nGame won, you sweeped all the mines";
		} else if (getGameOver()) {
			boardString += "\n\n Game over, you just stepped on a mine";
        }
		return boardString;
	}
}
