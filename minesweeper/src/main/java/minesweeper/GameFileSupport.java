package minesweeper;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameFileSupport implements IGameFileReading {

	@Override
	public void writeBoard(Board board, OutputStream target) {
		
	    var writer = new PrintWriter(target);
        writer.println(board.getNumberOfRow());
        writer.println(board.getNumberOfCol());
        writer.println(board.getGameWon());
        writer.println(board.getGameOver());
           
        for (int y = 0; y < board.getNumberOfRow(); y++) {
        	for (int x = 0; x < board.getNumberOfCol(); x++) {
   				writer.print(board.getTile(y, x).getType());    				
   			}
        }
        writer.println();
        for (int y = 0; y < board.getNumberOfRow(); y++) {
   			for (int x = 0; x < board.getNumberOfCol(); x++) {
   				if (board.getTile(y, x).isShown())
   					writer.print('s');
   				else
   					writer.print('o');
   			}
        }
        writer.flush();
        writer.close();       
	}
	
	@Override
	public Board readBoard(InputStream source) {
		var scanner = new Scanner(source);
		try {
			int numberOfRow = scanner.nextInt();
			int numberOfCol = scanner.nextInt();
			Board board = new Board(numberOfRow, numberOfCol);
			if (scanner.nextBoolean())
				board.setGameWon();
	
			if (scanner.nextBoolean())
				board.setGameOver();
		
			scanner.nextLine();
			String type = scanner.next();
			List<Tile> mineList = new ArrayList<Tile>();
			for (int y = 0; y < numberOfRow; y++) {
				for (int x = 0; x < numberOfCol; x++) {
					char symbol = type.charAt(y * numberOfCol + x);
					if (symbol == 'x')
						mineList.add(board.getTile(y, x));
					board.getTile(y, x).setType(symbol);
					
				}
			}
			board.setMineList(mineList);
			scanner.nextLine();
			String shown = scanner.next();
			for (int y = 0; y < numberOfRow; y++) {
				for (int x = 0; x < numberOfCol; x++) {
					char symb = shown.charAt(y * numberOfCol + x);
					if (symb == 's')
						board.getTile(y, x).setShown();
				}
			}
			scanner.close();
			return board;
		} catch (Exception e) {
			return null;
		}
	}
}
