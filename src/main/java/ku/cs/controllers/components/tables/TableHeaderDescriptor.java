package ku.cs.controllers.components.tables;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class TableHeaderDescriptor<MODEL>{
    protected TableComponentController<MODEL> tableComponentController;
    public void setTableComponentController(TableComponentController<MODEL> tableComponentController) {
        this.tableComponentController = tableComponentController;
    }



    public TableComponentController<MODEL> getTableComponentController() {
        return this.tableComponentController;
    }

    public ArrayList<TableHeaderPayload<MODEL>> getCallables() {
        // get all @TableColumn annotated methods sorted by order
        ArrayList<TableHeaderPayload<MODEL>> callables = new ArrayList<>();

        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(TableColumn.class)) {
                try {
                    TableColumn column = method.getAnnotation(TableColumn.class);
                    ColumnFactory<MODEL> columnFactory = (ColumnFactory<MODEL>) method.invoke(this);
                    Comparator<MODEL> comparator = columnFactory.getComparator();
                    if (comparator == null && column.headerMode() == HeaderMode.SORTABLE) {
                        throw new RuntimeException("Comparator is required for sortable columns");
                    }
                    callables.add(new TableHeaderPayload<>(columnFactory, comparator, column.name(), column.size(), column.order(),column.headerMode()));

                } catch (Exception e) {
                    // raise error if method is not a Callable
                    e.printStackTrace();
                    throw new RuntimeException("Method " + method.getName() + " is not a Callable");
                }
            }
        }

        callables.sort(Comparator.comparingInt(TableHeaderPayload::getOrder));

        return callables;
    }

}
