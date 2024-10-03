package minesweeper.json.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import minesweeper.core.Board;

import java.io.IOException;


public class BoardSerializer extends JsonSerializer<Board> {

    @Override
    public void serialize(Board board, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeNumberField("numberOfRow", board.getNumberOfRow());
        jsonGen.writeNumberField("numberOfCol", board.getNumberOfCol());
        jsonGen.writeNumberField("numberOfMines", board.getNumberOfMines());
        jsonGen.writeBooleanField("gameWon", board.getGameWon());
        jsonGen.writeBooleanField("gameLost", board.getGameOver());
        jsonGen.writeStringField("typeString", board.writeTypeString());
        jsonGen.writeStringField("shownString", board.writeShownString());

        jsonGen.writeEndObject();
    }

    
}
