module minesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive com.fasterxml.jackson.databind;

    exports minesweeper.core;
    exports minesweeper.json;


    opens minesweeper to javafx.graphics, javafx.fxml;
}
