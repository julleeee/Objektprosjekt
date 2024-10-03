package minesweeper.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MinesweeperModel {

    private MinesweeperSettings settings = new MinesweeperSettings();

    public MinesweeperSettings getSettings() {
        return settings;
    }

    public void setSettings(MinesweeperSettings settings) {
        this.settings = settings;
    }

    private List<Board> boardLists = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("[CarRentalModel #cars=%s]", boardLists.size());
    }
    
    private int indexOfBoard(String name) {
        for (int i = 0; i < boardLists.size(); i++) {
          if (name.equals(boardLists.get(i).getName())) {
            return i;
          }
        }
        return -1;
      }
    
    
}
