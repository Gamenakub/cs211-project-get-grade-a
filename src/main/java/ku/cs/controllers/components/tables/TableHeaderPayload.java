package ku.cs.controllers.components.tables;

import java.util.Comparator;

public class TableHeaderPayload<MODEL> {
    private ColumnFactory<MODEL> columnFactory;
    private Comparator<MODEL> comparator;
    private String name;
    private int size;
    private int order;
    private HeaderMode headerMode;
    public TableHeaderPayload(ColumnFactory<MODEL> columnFactory, Comparator<MODEL> comparator, String name, int size, int order, HeaderMode headerMode) {
        this.columnFactory = columnFactory;
        this.comparator = comparator;
        this.name = name;
        this.size = size;
        this.order = order;
        this.headerMode = headerMode;
    }

    public ColumnFactory<MODEL> getColumnFactory() {
        return columnFactory;
    }

    public Comparator<MODEL> getComparator() {
        return comparator;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getOrder() {
        return order;
    }

    public HeaderMode getHeaderMode() {
        return headerMode;
    }
}