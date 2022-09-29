package minesweeper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

public class BoardTest {
	
	private Board board;
	
	private void checkBoard(Board board, int numberOfRow, int numberOfCol, int numberOfMines) {
		Assertions.assertEquals(numberOfRow, board.getNumberOfRow());
		Assertions.assertEquals(numberOfCol, board.getNumberOfCol());
		Assertions.assertEquals(numberOfMines, board.getNumberOfMines());
	}
	
	private void createBoard() {
		List<Tile> mineList = new ArrayList<Tile>();
		board = new Board(10, 10);
		board.getTile(0, 0).setMine();
		mineList.add(board.getTile(0, 0));
		board.getTile(1, 1).setMine();
		mineList.add(board.getTile(1, 1));
		board.getTile(5, 5).setMine();
		mineList.add(board.getTile(5, 5));
		board.getTile(5, 6).setMine();
		mineList.add(board.getTile(5, 6));
		board.getTile(5, 7).setMine();
		mineList.add(board.getTile(5, 7));
		board.getTile(6, 7).setMine();
		mineList.add(board.getTile(6, 7));
		board.getTile(7, 7).setMine();
		mineList.add(board.getTile(7, 7));
		board.getTile(7, 6).setMine();
		mineList.add(board.getTile(7, 6));
		board.getTile(7, 5).setMine();
		mineList.add(board.getTile(7, 5));
		board.getTile(6, 5).setMine();
		mineList.add(board.getTile(6, 5));
		board.setMineList(mineList);
		board.setAdjecancyNumber();
	}
	
	@BeforeEach
	public void setup() {
		createBoard();
	}

	@Test
	@DisplayName("Check that the constructor creates boards with valid values")
	public void testConstructor() {
		board = new Board(10, 10);
		for (int y = 0; y < board.getNumberOfRow(); y++) {
			for (int x = 0; x < board.getNumberOfCol(); x++) {
				Assertions.assertEquals('O', board.getTile(x, y).getType());
			}
		}
		checkBoard(new Board(10, 10, 10), 10, 10, 10);
		checkBoard(new Board(10, 10, 0), 10, 10, 0);
		checkBoard(new Board(1, 1, 1), 1, 1, 1);
		checkBoard(new Board(1, 100, 100), 1, 100, 100);
		checkBoard(new Board(10, 10, 100), 10, 10, 100);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Board(0, 10, 0);
		}, "Cannot create boards with rows or columns <= 0");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Board(10, 10, -10);
		}, "Cannot create boards with negative amounts of mines");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Board(1, 1, 2);
		}, "Cannot create boards with more mines than tiles");
	}
	
	
	@Test
	@DisplayName("Check for correct type for tiles")
	public void testShowType() {
		board.showTiles(board.getTile(1, 0));
		Assertions.assertEquals('O', board.getTile(0, 9).getType());
		Assertions.assertEquals('2', board.getTile(1, 0).getType());
		Assertions.assertEquals('1', board.getTile(2, 2).getType());
		Assertions.assertEquals('8', board.getTile(6, 6).getType());
		Assertions.assertEquals('x', board.getTile(5, 6).getType());
		Assertions.assertEquals('3', board.getTile(4, 6).getType());
	}
	
	@Test
	@DisplayName("Test for the correct tiles being shown")
	public void testShowTiles() {
		board.showTiles(board.getTile(1, 0));
		Assertions.assertTrue(board.getTile(1, 0).isShown());	
		Assertions.assertFalse(board.getTile(0, 0).isShown());
		
		board.showTiles(board.getTile(0, 9));
		Assertions.assertTrue(board.getTile(0, 8).isShown());	
		Assertions.assertTrue(board.getTile(1, 9).isShown());
		Assertions.assertFalse(board.getTile(0, 0).isShown());
		Assertions.assertFalse(board.getTile(6, 6).isShown());	
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			board.showTiles(board.getTile(10, 10));
		}, "Indexes out of bounds");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			board.showTiles(board.getTile(-1, 0));
		}, "Indexes out of bounds");
		
	}
	
	@Test
	@DisplayName("Test game over situation, the mines should show")
	public void testGameOver() {
		board.showTiles(board.getTile(1, 0));
		board.showTiles(board.getTile(0, 9));
		board.showTiles(board.getTile(6, 6));
		Assertions.assertFalse(board.getGameWon());
		
		board.showTiles(board.getTile(0, 0));
		Assertions.assertTrue(board.getGameOver());
		Assertions.assertTrue(board.getTile(1, 1).isShown());
		Assertions.assertTrue(board.getTile(5, 6).isShown());
		Assertions.assertFalse(board.getTile(0, 1).isShown());
	}
	
	@Test
	@DisplayName("Test game won situation")
	public void testGameWon() {
		board.showTiles(board.getTile(1, 0));
		board.showTiles(board.getTile(0, 1));
		board.showTiles(board.getTile(0, 9));
		board.showTiles(board.getTile(6, 6));
		Assertions.assertTrue(board.getGameWon());
		Assertions.assertFalse(board.getGameOver());
		
	}
}
