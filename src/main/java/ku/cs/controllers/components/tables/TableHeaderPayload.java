package ku.cs.controllers.components.tables;

import java.util.Comparator;

public record TableHeaderPayload<E>(
        ColumnFactory<E> columnFactory, Comparator<E> comparator, String name,
        int size, int order, HeaderMode headerMode) {
}