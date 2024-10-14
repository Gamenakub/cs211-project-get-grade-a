package ku.cs.controllers.components.tables;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ku.cs.services.AlertService;
import ku.cs.services.SortDirection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TableComponentController<E> implements EventCompatible {
    private final HashMap<String, ArrayList<EventCallback>> eventListeners = new HashMap<>();
    private final HashMap<String, Comparator<E>> comparators = new HashMap<>();
    private final HashMap<String, ColumnFactory<E>> callables = new HashMap<>();
    private final ArrayList<String> tableHead = new ArrayList<>();

    private final ArrayList<Integer> headerSizeList = new ArrayList<>();
    private final ModelSorter<E> modelSorter = new ModelSorter<>();
    @FXML
    private VBox tableComponentVBox;
    @FXML
    private ScrollPane tableScrollPane;
    private int columnCount = 0;
    private int rowCount = 0;
    private int rowHeight = 100;
    private Pane tablePane;
    @FXML
    private HBox tableHeaderHBox;

    private final ArrayList<E> rowModels = new ArrayList<>();

    public void setDisplayRowCount(int displayRowCount) {
        this.tableScrollPane.setPrefHeight(rowHeight * displayRowCount);
        this.tableScrollPane.setMinHeight(rowHeight * displayRowCount);
        this.tableScrollPane.setMaxHeight(rowHeight * displayRowCount);

    }

    public void addTableHead(ColumnFactory<E> columnFactory, String headName, int size, HeaderMode headerMode) {
        Node head = columnFactory.getHeadNode();
        if (headerMode == HeaderMode.DEFAULT && head == null) {
            head = new Label(headName);
        }
        head.getStyleClass().add("table-header");
        addTableHead(head, size);
        callables.put(headName, columnFactory);
        tableHead.add(headName);
    }

    public void setTableHeadDescriptor(TableHeaderDescriptor<E> descriptor) {
        descriptor.setTableComponentController(this);
        ArrayList<TableHeaderPayload<E>> callables = null;
        try {
            callables = descriptor.getCallables();
        } catch (InvocationTargetException | IllegalAccessException e) {
            AlertService.showError("เกิดข้อผิดพลาดในการอ่านข้อมูลของตาราง กรุณาแจ้งผู้พัฒนา");
        }
        for (TableHeaderPayload<E> payload : callables) {
            addTableHead(payload.columnFactory(), payload.name(), payload.size(), payload.comparator(), payload.headerMode());
        }
    }

    public void addTableHead(ColumnFactory<E> columnFactory, String headName, int size, Comparator<E> comparator, HeaderMode headerMode) {
        addTableHead(columnFactory, headName, size, headerMode);
        comparators.put(headName, comparator);
    }

    public void sortBy(String columnName, SortDirection isAscending) {
        Comparator<E> comparator = comparators.get(columnName);

        if (comparator != null) {
            sortBy(comparator, isAscending);
        }
    }

    public void reSorting() {
        modelSorter.sort(rowModels);
    }

    public void sortBy(Comparator<E> comparator, SortDirection isAscending) {
        modelSorter.setComparator(comparator);
        modelSorter.setSortDirection(isAscending);
        reSorting();
        updateTable();
    }

    public void addTableHead(Node widget, int size) {
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
        this.tableScrollPane.getStyleClass().add("table-component-vbox");


        this.tableComponentVBox.setPrefWidth(width);
        this.tableComponentVBox.setMinWidth(width);
        this.tableComponentVBox.setMaxWidth(width);
        this.tableComponentVBox.getStyleClass().add("table-component-vbox");

        this.tableHeaderHBox.setPrefWidth(width);
        this.tableHeaderHBox.setMinWidth(width);
        this.tableHeaderHBox.setMaxWidth(width);


    }

    private int getTotalWidth() {
        int totalWidth = 0;
        for (Integer width : headerSizeList) {
            totalWidth += width + 2;
        }
        return totalWidth;
    }

    private Integer getPositionedPreferredWidth(int index) {
        return headerSizeList.get(index);
    }

    public void addTableRowControllerAndComponent(TableRowController rowController, Node newRowComponent) {
        for (int i = 0; i < columnCount; i++) {
            rowController.setElementPrefWidth(i, getPositionedPreferredWidth(i));
        }
        rowController.setRowHeight(rowHeight);
        tableComponentVBox.getChildren().add(newRowComponent);
        rowCount++;
        this.tableComponentVBox.setPrefHeight(rowHeight * rowCount);
        this.tableComponentVBox.setMinHeight(rowHeight * rowCount);
        this.tableComponentVBox.setMaxHeight(rowHeight * rowCount);
    }

    public void setHeadHeight(int newHeadHeight) {
        tableHeaderHBox.setMinHeight(newHeadHeight);
        tableHeaderHBox.setPrefHeight(newHeadHeight);
        tableHeaderHBox.setMaxHeight(newHeadHeight);
    }

    public void setRowHeight(int newRowHeight) {
        rowHeight = newRowHeight;
    }

    public void clearData() {
        this.rowModels.clear();
        clearDisplay();
    }

    public void clearDisplay() {
        this.tableComponentVBox.getChildren().clear();
        this.rowCount = 0;
    }

    public ArrayList<String> getTableHead() {
        return tableHead;
    }

    public void addRow(E model) {
        this.rowModels.add(model);
        addModelToDisplay(model);
    }

    private void addModelToDisplay(E model) {
        FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
        AnchorPane tableRowComponent = null;
        try {
            tableRowComponent = tableRowFXMLLoader.load();
        } catch (IOException e) {
            AlertService.showError("เกิดข้อผิดพลาดในขณะอ่านไฟล์โปรแกรม กรุณาตรวจสอบความสมบูรณ์ของไฟล์โปรแกรมของท่าน");
        }

        TableRowController tableRowController = tableRowFXMLLoader.getController();
        for (String key : getTableHead()) {
            ColumnFactory<E> columnFactory = callables.get(key);
            tableRowController.addElement(columnFactory.getDisplayNode(model));
        }
        this.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
    }

    public void setDisplayModels(ArrayList<E> collection) {
        clearData();
        for (E object : collection) {
            addRow(object);
        }
        updateTable();
    }

    public void updateTable() {
        clearDisplay();
        reSorting();
        for (E object : rowModels) {
            addModelToDisplay(object);
        }
    }

    public Pane getTablePane() {
        return this.tablePane;
    }

    public void setTablePane(Pane tablePane) {
        this.tablePane = tablePane;
    }

    @Override
    public void issueEvent(String eventName, Object eventData) {
        if (eventListeners.containsKey(eventName)) {
            for (EventCallback callback : eventListeners.get(eventName)) {
                callback.onEvent(eventData);
            }
        }
    }

    @Override
    public void issueEvent(String eventName) {
        if (eventListeners.containsKey(eventName)) {
            for (EventCallback callback : eventListeners.get(eventName)) {
                callback.onEvent(null);
            }
        }
    }


    @Override
    public void addEventListener(String eventName, EventCallback callback) {
        if (!eventListeners.containsKey(eventName)) {
            eventListeners.put(eventName, new ArrayList<>());
        }
        eventListeners.get(eventName).add(callback);
    }

    public void setTableAttributes(Pane tablePane, int headHeight, int rowHeight, int displayRowCount) {
        setHeadHeight(headHeight);
        setRowHeight(rowHeight);
        setDisplayRowCount(displayRowCount);
        setTablePane(tablePane);
    }

    public void setTableAttributes(Pane tablePane, int headHeight, int rowHeight, int displayRowCount, TableHeaderDescriptor<E> descriptor) {
        this.setTableAttributes(tablePane, headHeight, rowHeight, displayRowCount);
        this.setTableHeadDescriptor(descriptor);
    }
}
