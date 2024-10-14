package ku.cs.controllers.components.tables;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class TableRowController {
    private final ArrayList<HBox> componentsDisplayList = new ArrayList<>();
    @FXML
    public HBox tableRowHBox;

    public void addElement(Node widget) {

        HBox componentHBox = new HBox();
        componentHBox.setAlignment(Pos.CENTER);
        componentHBox.getChildren().add(widget);
        componentsDisplayList.add(componentHBox);

        tableRowHBox.getChildren().add(componentHBox);
    }

    public void setRowHeight(int rowHeight) {
        tableRowHBox.setMinHeight(rowHeight);
        tableRowHBox.setPrefHeight(rowHeight);
        tableRowHBox.setMaxHeight(rowHeight);
    }

    public void setElementPrefWidth(int index, int width) {
        componentsDisplayList.get(index).setMinWidth(width);
        componentsDisplayList.get(index).setPrefWidth(width);
        componentsDisplayList.get(index).setMaxWidth(width);
    }
}
