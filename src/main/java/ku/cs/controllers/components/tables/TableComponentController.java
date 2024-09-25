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
import ku.cs.services.SortDirection;
import ku.cs.services.SortToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TableComponentController<MODEL> implements EventCompatible {
    @FXML
    private VBox tableComponentVBox;

    @FXML
    private ScrollPane tableScrollPane;

    private HashMap<String,ArrayList<EventCallback>> eventListeners = new HashMap<>();

    private int columnCount = 0;
    private int rowCount = 0;
    private int rowHeight = 100;

    private int displayRowCount = 0;
    private Pane tablePane;
    private ArrayList<String> tableHeads=new ArrayList<>();

    @FXML
    private HBox tableHeaderHBox;

    private HashMap<String, Comparator<MODEL>> comparators = new HashMap<>();

    // array list
    private ArrayList<Node> components;

    private ArrayList<MODEL> rowModels = new ArrayList<>();

    private HashMap<String, ColumnFactory> callables = new HashMap<>();
    private ArrayList<String> tableHead = new ArrayList<>();

    // list of header size
    private ArrayList<Integer> headerSizeList = new ArrayList<>();

    private ArrayList<MODEL> filterRecovers = new ArrayList<>();

    private class ModelSorter<MODEL> {
        private Comparator<MODEL> comparator;
        private SortDirection sortDirection;
        public ModelSorter(){
            this.comparator = null;
            this.sortDirection = SortDirection.ASCENDING;
        }

        public void setComparator(Comparator<MODEL> comparator) {
            this.comparator = comparator;
        }
        public void setSortDirection(SortDirection sortDirection) {
            this.sortDirection = sortDirection;
        }
        public ArrayList<MODEL> sort(ArrayList<MODEL> models){
            if (comparator != null) {
                models.sort(comparator);
                if (sortDirection.equals(SortDirection.DESCENDING)) {
                    models.sort(comparator.reversed());
                }
            }
            return models;
        }
    }
    private final ModelSorter<MODEL> modelSorter = new ModelSorter();

    public Node addTableHead(ColumnFactory<MODEL> columnFactory, String headName, int size, HeaderMode headerMode){
        Node head = columnFactory.getHeadNode();
        if (headerMode.equals(HeaderMode.SORTABLE)) {
            SortToggleButton sortButton = new SortToggleButton(headName);
            sortButton.onToggle(sortDirection -> {
                SortDirection opposite = sortDirection.equals(SortDirection.ASCENDING) ? SortDirection.DESCENDING : SortDirection.ASCENDING;
                issueEvent("resetSort",sortButton);
                sortBy(headName, (SortDirection) sortDirection);
                sortButton.setAscending(sortDirection.equals(SortDirection.ASCENDING));
            });
            this.addEventListener("resetSort", (event) -> {
                if (event instanceof SortToggleButton) {
                    SortToggleButton button = (SortToggleButton) event;
                    button.getStyleClass().add("table-text");

                    if (!button.equals(sortButton)) {
                        button.setAscending(true);
                    }
                }
            });
            head = sortButton;
        }
        else if (headerMode == HeaderMode.DEFAULT && head == null){
            head = new Label(headName);
            head.getStyleClass().add("table-text");
        }
        head.getStyleClass().add("table-text");
        addTableHead(head,size);
        callables.put(headName, columnFactory);
        tableHead.add(headName);
        return head;
    }

    public void setTableHeadDescriptor(TableHeaderDescriptor<MODEL> descriptor){
        descriptor.setTableComponentController(this);
        ArrayList<TableHeaderPayload<MODEL>> callables = descriptor.getCallables();
        for (TableHeaderPayload<MODEL> payload : callables) {
            addTableHead(payload.getColumnFactory(),payload.getName(),payload.getSize(),payload.getComparator(),payload.getHeaderMode());
        }
    }

    public Node addTableHead(ColumnFactory<MODEL> columnFactory, String headName, int size, Comparator<MODEL> comparator, HeaderMode headerMode){
        Node head = addTableHead(columnFactory,headName,size,headerMode);
        comparators.put(headName, comparator);
        return head;
    }

    public void sortBy(String columnName,boolean isAscending){
        Comparator<MODEL> comparator = comparators.get(columnName);
        if (comparator != null) {
            sortBy(comparator,isAscending ? SortDirection.ASCENDING : SortDirection.DESCENDING);
            updateTable();
        }
    }

    public void sortBy(String columnName, SortDirection isAscending){
        Comparator<MODEL> comparator = comparators.get(columnName);

        if (comparator != null) {
            sortBy(comparator,isAscending);
        }
    }

    public void reSorting(){
        modelSorter.sort(rowModels);
    }

    public void sortBy(Comparator<MODEL> comparator, SortDirection isAscending){
        modelSorter.setComparator(comparator);
        modelSorter.setSortDirection(isAscending);
        reSorting();
    }

    public Node addTableHead(Node widget,int size){
        // create <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0">
        HBox tableCell = new HBox();
        tableCell.setAlignment(Pos.CENTER);
        tableCell.setPrefWidth(size);
        tableCell.setMinWidth(size);
        tableCell.setMaxWidth(size);
        tableCell.getChildren().add(widget);
        tableHeaderHBox.getChildren().add(tableCell);

        if (widget instanceof Label) {
            Label label = (Label) widget;
//            label.getStyleClass().add("table-text");
            tableHeads.add(label.getText());
        }

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


        return widget;
    }

    public ArrayList<String> getTableHeads() {
        return tableHeads;
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

    public void addTableRowControllerAndComponent(TableRowController rowController, Node newRowComponent){
        for (int i = 0; i < columnCount; i++) {
            rowController.setElementPrefWidth(i,getPositionedPreferredWidth(i));
        }
        rowController.setRowHeight(rowHeight);
        tableComponentVBox.getChildren().add(newRowComponent);
        rowCount++;
        this.tableComponentVBox.setPrefHeight(rowHeight*rowCount);
        this.tableComponentVBox.setMinHeight(rowHeight*rowCount);
        this.tableComponentVBox.setMaxHeight(rowHeight*rowCount);
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

    public void clearData(){
        this.rowModels.clear();
        clearDisplay();
    }

    public void clearDisplay(){
        this.tableComponentVBox.getChildren().clear();
        this.rowCount = 0;
    }

    public ArrayList<String> getTableHead() {
        return tableHead;
    }

    public void addRow(MODEL model) throws IOException {
        this.rowModels.add(model);
        addModelToDisplay(model);
    }

    private void addModelToDisplay(MODEL model) throws IOException {
        FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
        AnchorPane tableRowComponent = tableRowFXMLLoader.load();

        TableRowController tableRowController = tableRowFXMLLoader.getController();
        for (String key : getTableHead()) {
            tableRowController.addElement(callables.get(key).getDisplayNode(model));
        }
        this.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
    }

    public void setDisplayModels(ArrayList<MODEL> collection) throws IOException {
        clearData();
        for (MODEL object : collection) {
            addRow(object);
        }
        updateTable();
    }

    public void setDisplayModelsCasting(ArrayList<Object> collection) throws IOException {
        clearData();
        for (Object object : collection) {
            addRow((MODEL) object);
        }
        updateTable();
    }

    public void filteredDisplayModels(ModelFilter<MODEL> filter){
        try {
            ArrayList<MODEL> filteredModels = new ArrayList<>();
            for (MODEL model : rowModels) {
                if (filter.isInclude(model)) {
                    filteredModels.add(model);
                }
            }
            clearDisplay();
            for (MODEL object : filteredModels) {
                addModelToDisplay(object);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetFilter(){
        try {
            setDisplayModels(this.rowModels);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTable(){
        clearDisplay();
        reSorting();
        for (MODEL object : rowModels) {
            try {
                addModelToDisplay(object);
            } catch (IOException e) {
                throw new RuntimeException("can't add row in to model");
            }
        }
    }

    public void setTablePane(Pane tablePane) {
        this.tablePane = tablePane;
    }

    public Pane getTablePane() {
        return this.tablePane;
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
}
