package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TableComponentController {
    @FXML
    private VBox tableComponentVBox;
    @FXML
    private ScrollPane tableScrollPane;

    private int columnCount = 0;
    private int rowCount = 0;
    private int rowHeight = 100;

    private int displayRowCount = 0;
    @FXML
    private HBox tableHeaderHBox;

    // array list
    private ArrayList<Node> components;

    // list of header size
    private ArrayList<Integer> headerSizeList = new ArrayList<>();

    public Node addTableHead(Node widget,int size){
        // create <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0">
        HBox tableCell = new HBox();
        tableCell.setAlignment(Pos.CENTER);
        tableCell.setPrefWidth(size);
        tableCell.setMinWidth(size);
        tableCell.setMaxWidth(size);
        tableCell.getChildren().add(widget);

        tableHeaderHBox.getChildren().add(tableCell);




        headerSizeList.add(size);
        columnCount++;

        int width = getTotalWidth();
        this.tableScrollPane.setPrefWidth(width);
        this.tableScrollPane.setMinWidth(width);
        this.tableScrollPane.setMaxWidth(width);

        this.tableComponentVBox.setPrefWidth(width);
        this.tableComponentVBox.setMinWidth(width);
        this.tableComponentVBox.setMaxWidth(width);

        this.tableHeaderHBox.setPrefWidth(width);
        this.tableHeaderHBox.setMinWidth(width);
        this.tableHeaderHBox.setMaxWidth(width);


        return widget;
    }

    private int getTotalWidth(){
        int totalWidth = 0;
        for (Integer width : headerSizeList){
            totalWidth += width + 2;
        }
        return totalWidth;
    }

    private Integer getPositionedPreferredWidth(int index){
        return headerSizeList.get(index);
    }

    public void addTableRowControllerAndComponent(TableRowController rowController,Node newRowComponent){
        for (int i = 0; i < columnCount; i++) {
            rowController.setElementPrefWidth(i,getPositionedPreferredWidth(i));
        }
        rowController.setRowHeight(rowHeight);
        tableComponentVBox.getChildren().add(newRowComponent);
        this.tableComponentVBox.setPrefHeight(rowHeight*rowCount);
        this.tableComponentVBox.setMinHeight(rowHeight*rowCount);
        this.tableComponentVBox.setMaxHeight(rowHeight*rowCount);
        rowCount++;
    }


    public void setHeadHeight(int newHeadHeight) {
        tableHeaderHBox.setMinHeight(newHeadHeight);
        tableHeaderHBox.setPrefHeight(newHeadHeight);
        tableHeaderHBox.setMaxHeight(newHeadHeight);
    }

    public void setRowHeight(int newRowHeight) {
        rowHeight = newRowHeight;
    }

    public void setDisplayRowCount(int displayRowCount) {
        this.displayRowCount = displayRowCount;
        this.tableScrollPane.setPrefHeight(rowHeight*displayRowCount);
        this.tableScrollPane.setMinHeight(rowHeight*displayRowCount);
        this.tableScrollPane.setMaxHeight(rowHeight*displayRowCount);
    }
}
