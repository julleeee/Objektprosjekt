package minesweeper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class TileTest {
	
	Tile tile;
	
	private void checkTile(Tile tile, int y, int x) {
		Assertions.assertEquals(y, tile.getY());
		Assertions.assertEquals(x, tile.getX());
	}
	
	private void checkType(Tile tile, char type) {
		Assertions.assertEquals(type, tile.getType());
	}
	
	@BeforeEach
	public void setup() {
		tile = new Tile(0, 0);
	}
	
	@Test
	@DisplayName("Check that the constructor creates tiles with valid values")
	public void testConstructor() {
		checkTile(new Tile(0, 0), 0, 0);
		checkTile(new Tile(2, 3), 2, 3);
		checkTile(new Tile(0, 10), 0, 10);
		checkTile(new Tile(150, 130), 150, 130);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Tile(-1, 0);
		}, "Cannot make tiles with negative values");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Tile(-1, -10);
		}, "Cannot make tiles with negative values");
	}
	
	
	@Test
	@DisplayName("Check for correct adjacency-numbers")
	public void testAddAdjacencyNumber() {
		tile.addAdjacencyNumber();
		tile.addAdjacencyNumber();
		checkType(tile, '2');
		tile.addAdjacencyNumber();
		tile.addAdjacencyNumber();
		tile.addAdjacencyNumber();
		checkType(tile, '5');
		tile.addAdjacencyNumber();
		tile.addAdjacencyNumber();
		tile.addAdjacencyNumber();
		
		Assertions.assertThrows(IllegalStateException.class, () -> {
			tile.addAdjacencyNumber();
		}, "A tile cannot have more than 8 neighbours");
		
	}
	
	@Test
	@DisplayName("Check for shown tiles")
	public void testShown() {
		tile.setShown();
		Assertions.assertTrue(tile.isShown());
	}
	
	@Test
	@DisplayName("Test the types of different tiles")
	public void testSetValidType() {
		checkType(tile, 'O');
		tile.setType('x');
		Assertions.assertTrue(tile.isMine());
		tile.setType('8');
		Assertions.assertTrue(tile.isAdjacencyNumber());
		
		Assertions.assertThrows( IllegalArgumentException.class, () -> {
			tile.setType('9');
		}, "No tiles can have a value greater than 8");
		
		Assertions.assertThrows( IllegalArgumentException.class, () -> {
			tile.setType('=');
		}, "This should be an illegal type");
	}
}
