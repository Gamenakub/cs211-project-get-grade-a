package ku.cs.controllers.components.tables;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class TableHeaderDescriptor<E> {
    protected TableComponentController<E> tableComponentController;

    public TableComponentController<E> getTableComponentController() {
        return this.tableComponentController;
    }

    public void setTableComponentController(TableComponentController<E> tableComponentController) {
        this.tableComponentController = tableComponentController;
    }

    public ArrayList<TableHeaderPayload<E>> getCallables() throws InvocationTargetException, IllegalAccessException {

        ArrayList<TableHeaderPayload<E>> callables = new ArrayList<>();

        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(TableColumn.class)) {
                TableColumn column = method.getAnnotation(TableColumn.class);
                ColumnFactory<E> columnFactory = (ColumnFactory<E>) method.invoke(this);
                Comparator<E> comparator = columnFactory.getComparator();
                callables.add(new TableHeaderPayload<>(columnFactory, comparator, column.name(), column.size(), column.order(), column.headerMode()));
            }
        }

        callables.sort(Comparator.comparingInt(TableHeaderPayload::order));

        return callables;
    }

}
