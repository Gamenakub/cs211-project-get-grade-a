package ku.cs.controllers.components.tables;

import javafx.scene.Node;

public interface Callable<MODEL> {
    Node getDisplayNode(MODEL obj);
    String getHeadName();
    Node getHeadNode();
}
