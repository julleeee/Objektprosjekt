package minesweeper.json.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import minesweeper.core.Board;

import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;


import java.io.IOException;


public class BoardDeserializer extends JsonDeserializer<Board> {

    @Override
    public Board deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    Board deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            Board board = new Board();

            JsonNode rowNode = objectNode.get("numberOfRow");
            if (rowNode instanceof IntNode) {
              board.setNumberOfRow(rowNode.asInt());
            }

            JsonNode colNode = objectNode.get("numberOfCol");
            if (colNode instanceof IntNode) {
              board.setNumberOfCol(colNode.asInt());;
            }

            JsonNode mineNode = objectNode.get("numberOfMines");
            if (mineNode instanceof IntNode) {
              board.setNumberOfMines(mineNode.asInt());
            }

            JsonNode wonNode = objectNode.get("gameWon");
            if (wonNode instanceof BooleanNode) {
              board.setGameWon();
            }

            JsonNode lostNode = objectNode.get("gameLost");
            if (lostNode instanceof BooleanNode) {
              board.setGameOver();
            }

            JsonNode typeStringNode = objectNode.get("typeString");
            if (typeStringNode instanceof TextNode) {
              board.writeTypeString();
            }

            JsonNode shownStringNode = objectNode.get("shownString");
            if (shownStringNode instanceof TextNode) {
              board.writeShownString();
            }
            
            return board;
          }
          return null;
      
    }

    
}
