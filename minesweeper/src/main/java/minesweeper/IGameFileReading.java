package minesweeper;

import java.io.InputStream;
import java.io.OutputStream;

public interface IGameFileReading {
	
	Board readBoard(InputStream is);

	void writeBoard(Board board, OutputStream os);
	
}