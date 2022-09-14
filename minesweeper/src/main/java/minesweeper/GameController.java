package minesweeper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameController {
	
	private Board board;
	private final IGameFileReading fileSupport = new GameFileSupport();
	
	@FXML GridPane spillBrett;
	
	@FXML Text winText = new Text();
	@FXML Text loseText = new Text();	
	
	public final static String SAVE_FOLDER = "C:/Users/juliu/Documents/Objektprosjekt/minesweeper/src/main/java/minesweeper/saves/board.txt";
	

	private void setBoard(final Board board) {
		if (board != null) {
			this.board = board;
			if (board.getGameWon() || board.getGameOver())
				drawBoard();
			else
				createBoard();
		} else {
			System.out.println("The format of the loading file is damaged or incorrect");
		}
	}
	
	@FXML
	private void initialize() {
		board = new Board(10, 10, 10);
		createBoard();
	}
	
	private void createBoard() {
		spillBrett.getChildren().clear();
		for (int y = 0; y < board.getNumberOfRow(); y++) {
			for (int x = 0; x < board.getNumberOfCol(); x++) {

				ImageView tile = new ImageView(board.getGameBoard()[y][x].getImage());
				tile.setFitHeight(30);
				tile.setFitWidth(30);
				tile.setOnMouseClicked(event -> onMouseClicked(tile));
				spillBrett.add(tile, y, x);
			}
		}
	}
	
	private void drawBoard() {
		spillBrett.getChildren().clear();
		for (int y = 0; y < board.getNumberOfRow(); y++) {
			for (int x = 0; x < board.getNumberOfCol(); x++) {
				
				ImageView tile = new ImageView(board.getGameBoard()[y][x].getImage());
				tile.setFitHeight(30);
				tile.setFitWidth(30);
				spillBrett.add(tile, y, x);
			}
		}
		endOfGame();
	}
	
	private void endOfGame() {		
		if (board.getGameWon()) {
			winText.setText("fan som å svinge \n bailltre i jotunheimen,,,");
			winText.setStyle("-fx-font-size: 40px");
			winText.setFill(Color.GREEN);
			winText.setTranslateX(-70.0);
			winText.setTranslateY(320.0);
			spillBrett.getChildren().add(winText);
		} 
		else if (board.getGameOver()) {
			loseText.setText("du sug skjerpings");
			loseText.setStyle("-fx-font-size: 60px");
			loseText.setFill(Color.RED);
			loseText.setTranslateX(-70.0);
			loseText.setTranslateY(300.0);
			spillBrett.getChildren().add(loseText);
		}
	}
	
	@FXML
	private void onMouseClicked(ImageView ImageClicked) {
		Integer y = GridPane.getColumnIndex(ImageClicked);
		Integer x = GridPane.getRowIndex(ImageClicked);
		board.showTiles(board.getTile(y, x));	
		
		if (board.getGameWon() || board.getGameOver()) {
			drawBoard();
		}
		else if (board.getTile(y, x).isAdjacencyNumber()) {
			ImageClicked.setImage(board.getTile(y, x).getImage());
		}
		else {
			createBoard();
		}
	}
	
	@FXML
	private void handleNewGame() {
		initialize();
	}
	
	@FXML
	private void handleSaveButtonAction() {
		try {
			OutputStream output = new FileOutputStream(SAVE_FOLDER);
			fileSupport.writeBoard(board, output);
				
		} catch (final IOException e) {
			System.out.println("This filepath is not valid");
		}
	}
	
	@FXML
	private void handleLoadButtonAction() {
		try {
			InputStream input = new FileInputStream(SAVE_FOLDER);
			setBoard(fileSupport.readBoard(input));
		
		} catch (final IOException e) {
			System.out.println("No saved games / invalid filepath");
		}
	}	
}