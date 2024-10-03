package minesweeper.json.internal;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import minesweeper.core.Board;

@SuppressWarnings("serial")
public class MinesweeperModule extends SimpleModule {

    private static final String NAME = "MinesweeperModule";

    public MinesweeperModule() {
        super(NAME, Version.unknownVersion());
        
        addSerializer(Board.class, new BoardSerializer());
        addDeserializer(Board.class, new BoardDeserializer());
    }

}
