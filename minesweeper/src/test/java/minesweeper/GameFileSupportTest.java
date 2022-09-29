package minesweeper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

public class GameFileSupportTest {
	
	private Board board;
	
	private final IGameFileReading fileSupport  = new GameFileSupport();
	
	public final static String SAVE_FOLDER = "src/test/java/test/saves/save-actual.txt";
	
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
		board.showTiles(board.getTile(0, 9));
	}
	
	@BeforeEach
	public void setup() {
		createBoard();
	}
	
	@Test
	@DisplayName("Check the save function")
	public void testSave() throws IOException {
		
		OutputStream output = new FileOutputStream(SAVE_FOLDER);
		fileSupport.writeBoard(board, output);
	
		List<String> testFile = null;
		List<String> newFile = null;
		
		testFile = Files.readAllLines(Path.of(SAVE_FOLDER));
		newFile = Files.readAllLines(Path.of("src/test/java/test/saves/save-expct.txt"));
			
		Assertions.assertNotNull(testFile);
		Assertions.assertNotNull(newFile);
		Assertions.assertLinesMatch(testFile, newFile);

	}
	
	@Test
	@DisplayName("Check the load function")
	public void testLoad() throws FileNotFoundException {
		
		InputStream input = new FileInputStream("src/test/java/test/saves/load-expct.txt");
		Board savedNewGame = (fileSupport.readBoard(input));
		Assertions.assertEquals(board.toString(), savedNewGame.toString());
		Assertions.assertFalse(board.getGameOver());
		Assertions.assertFalse(savedNewGame.getGameOver());
		Assertions.assertFalse(board.getGameWon());
		Assertions.assertFalse(savedNewGame.getGameWon());
		Assertions.assertEquals(board.getNumberOfCol(), savedNewGame.getNumberOfCol());
		Assertions.assertEquals(board.getNumberOfRow(), savedNewGame.getNumberOfRow());
		Assertions.assertEquals(board.getNumberOfMines(), savedNewGame.getNumberOfMines());
	}
}
