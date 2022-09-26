package minesweeper;

import java.util.List;
import javafx.scene.image.Image;

public class Tile {
	
	private int y;
	private int x;
	private int adjacencyNumber = 0;
	private char type = 'O';
	public static final List<Character> legitTypes = List.of('O', 'x', '1', '2', '3', '4', '5', '6', '7', '8');
	
	private boolean shown = false;	
	private Image image;
	
	public Tile(int y, int x) {
		checkValidCoordinate(y, x);
		this.y = y;
		this.x = x;
	}
	
	public void setMine() {
		type = 'x';
	}
	
	public void setShown() {
		shown = true;
	}
	
	public boolean isMine() {
		return type == 'x';
	}
	
	public boolean isSpace() {
		return type == 'O';
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void addAdjacencyNumber() {
		if (adjacencyNumber <= 7) {
			adjacencyNumber ++;
			type = (char) (adjacencyNumber + '0');
		}
		else
			throw new IllegalStateException("A tile cannot have more than 8 neighbours");
	}
	
	public boolean isAdjacencyNumber() {
		return Character.isDigit(type);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public char getType() {
		return type;
	}
	
	public void setType(char type) {
		if (legitTypes.contains(type))
			this.type = type;
		else
			throw new IllegalArgumentException("This type do not refer to a tile");
	}
	
	public Image getImage() {
		char image = getType();
		
		if (this.isShown()) {
			switch (image) {
			case 'x' :
				Image ImageMine = new Image(getClass().getResourceAsStream("/Minesweeper_pics/ghostyghost.png"));

				this.image = ImageMine;
				break;
			case 'O' :
				Image ImageSpace = new Image(getClass().getResourceAsStream("/Minesweeper_pics/Uncovered.jpg"));
				this.image = ImageSpace;
				break;
			case '1' :
				Image Image1 = new Image(getClass().getResourceAsStream("/Minesweeper_pics/1-tile.jpg"));
				this.image = Image1;
				break;
			case '2' :
			Image Image2 = new Image(getClass().getResourceAsStream("/Minesweeper_pics/2-tile.jpg"));
				this.image = Image2;
				break;
			case '3' :
				Image Image3 = new Image(getClass().getResourceAsStream("/Minesweeper_pics/3-tile.jpg"));
				this.image = Image3;
				break;
			case '4' :
				Image Image4 = new Image(getClass().getResourceAsStream("/Minesweeper_pics/4-tile.jpg"));
				this.image = Image4;
				break;
			case '5' :
				Image Image5 = new Image(getClass().getResourceAsStream("/Minesweeper_pics/5-tile.jpg"));
				this.image = Image5;
				break;
			case '6' :
				Image Image6 = new Image(getClass().getResourceAsStream("/Minesweeper_pics/6-tile.jpg"));
				this.image = Image6;
				break;
			case '7' :
				Image Image7 = new Image(getClass().getResourceAsStream("/Minesweeper_pics/7-tile.jpg"));
				this.image = Image7;
				break;
			case '8' :
				Image Image8 = new Image(getClass().getResourceAsStream("/Minesweeper_pics/8-tile.jpg"));
				this.image = Image8;
				break;
			}	
		}else {
			Image ImageCovered = new Image(getClass().getResourceAsStream("/Minesweeper_pics/Covered.png"));
			this.image = ImageCovered;
		}
		return this.image;
	}
	
	private void checkValidCoordinate(int y, int x) {
		if (y < 0 || x < 0)
			throw new IllegalArgumentException("Invalid coordinate");
	}
	@Override
    public String toString() {
        return Character.toString(type);
    }
}