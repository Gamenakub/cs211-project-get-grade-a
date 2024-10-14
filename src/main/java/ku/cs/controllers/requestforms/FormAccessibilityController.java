package ku.cs.controllers.requestforms;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

public class FormAccessibilityController {
    public static void setUnEditable(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof TextField) {

                ((TextField) node).setEditable(false);
            } else if (node instanceof MenuButton) {
                ((MenuButton)node).getItems().clear();

            } else if (node instanceof DatePicker) {
                node.setDisable(true);
            } else if (node instanceof Button) {
                if (((Button) node).getText().equals("เพิ่ม")) {
                    node.setVisible(false);
                }

            } else if (node instanceof TextArea) {
                ((TextArea) node).setEditable(false);
            } else if (node instanceof ScrollPane) {
                Node content = ((ScrollPane) node).getContent();
                if (content instanceof Parent) {
                    setUnEditable((Parent) content);
                }
            } else if (node instanceof Parent) {
                setUnEditable((Parent) node);
            }
        }
    }

}
