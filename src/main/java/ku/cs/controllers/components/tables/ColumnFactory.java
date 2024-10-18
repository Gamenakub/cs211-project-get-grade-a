package ku.cs.controllers.components.tables;

import javafx.scene.Node;

import java.util.Comparator;

public interface ColumnFactory<E> {
    Node getDisplayNode(E obj);

    default Node getHeadNode() {
        return null;
    }

    default Comparator<E> getComparator() {
        return null;
    }
}
