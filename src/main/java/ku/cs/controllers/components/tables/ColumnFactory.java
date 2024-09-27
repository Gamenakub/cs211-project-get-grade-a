package ku.cs.controllers.components.tables;

import javafx.scene.Node;

import java.util.Comparator;

public interface ColumnFactory<MODEL> {
    Node getDisplayNode(MODEL obj);
    default Node getHeadNode(){
        return null;
    }

    default Comparator<MODEL> getComparator() {
        return null;
    }
}
