package minesweeper.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import minesweeper.json.internal.MinesweeperModule;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import minesweeper.core.MinesweeperModel;


public class MinesweeperPersistence {
    private ObjectMapper mapper;

    public MinesweeperPersistence() {
        mapper = createObjectMapper();
    }

    public static SimpleModule createJacksonModule() {
        return new MinesweeperModule();
    }

    public static ObjectMapper createObjectMapper() {
        return new ObjectMapper().registerModule(createJacksonModule());
    }

    public MinesweeperModel readMinesweeperModel(Reader reader) throws IOException {
        return mapper.readValue(reader, MinesweeperModel.class);
    }

    public void writeMinesweeperModel(MinesweeperModel minesweeperModel, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, minesweeperModel);
    }

    private Path saveFilePath = null;

    public void setSaveFile(String saveFile) {
        this.saveFilePath = Paths.get(System.getProperty("user.home"), saveFile);
    }

    public Path getSaveFilePath() {
        return this.saveFilePath;
    }

    public MinesweeperModel loadMinesweeperModel() throws IOException, IllegalStateException {
        if (saveFilePath == null) {
          throw new IllegalStateException("Save file path is not set, yet");
        }
        try (Reader reader = new FileReader(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
          return readMinesweeperModel(reader);
        }
    }

    public void saveMinesweeperModel(MinesweeperModel minesweeperModel) throws IOException, IllegalStateException {
        if (saveFilePath == null) {
          throw new IllegalStateException("Save file path is not set, yet");
        }
        try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
          writeMinesweeperModel(minesweeperModel, writer);
        }
    }
    
    
    
    
    

    
}
