package ku.cs.controllers.components.tables;

public class TableColumnAnnotationAttribute {
    private String name;
    private int size;
    private int order;
    private HeaderMode headerMode;


    public TableColumnAnnotationAttribute(int order, String name, int size) {
        this.name = name;
        this.size = size;
        this.order = order;
        this.headerMode = HeaderMode.USER_DEFINED;
    }
    public TableColumnAnnotationAttribute(int order, String name, int size,HeaderMode headerMode) {
        this.name = name;
        this.size = size;
        this.order = order;
        this.headerMode = headerMode;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getSize() {
        return size;
    }

    public HeaderMode getHeaderMode() {
        return headerMode;
    }
}
