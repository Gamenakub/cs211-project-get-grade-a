package ku.cs.controllers.components.tables;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class TableRowController {
    @FXML
    public HBox tableRowHBox;

    private int elementCount = 0;

    private ArrayList<Node> components = new ArrayList<>();

    private ArrayList<HBox> componentsDisplayList = new ArrayList<>();

    public void clearRow(){
        tableRowHBox.getChildren().clear();
        elementCount = 0;
    }

    public void addElement(Node widget){
        elementCount++;
        components.add(widget);

        HBox componentHBox = new HBox();
        componentHBox.setAlignment(Pos.CENTER);
        componentHBox.getChildren().add(widget);
        //componentHBox.setBackground(Background.fill(Paint.valueOf("red")));
        componentsDisplayList.add(componentHBox);

        tableRowHBox.getChildren().add(componentHBox);
    }

    public Node getComponent(int index){
        return components.get(index);
    }

    public void setRowHeight(int width){
        tableRowHBox.setMinHeight(width);
        tableRowHBox.setPrefHeight(width);
        tableRowHBox.setMaxHeight(width);
    }

    public void setElementPrefWidth(int index, int width){
        componentsDisplayList.get(index).setMinWidth(width);
        componentsDisplayList.get(index).setMaxWidth(width);
    }
}
