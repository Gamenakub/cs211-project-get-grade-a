package ku.cs.services;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

public class SetEditable {
    public static void setEditable(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            // ถ้า node เป็น TextField ให้ตั้งค่าเป็นไม่สามารถแก้ไขได้
            if (node instanceof TextField) {
                ((TextField) node).setEditable(false);
            }
            else if (node instanceof ScrollPane) {
                Node content = ((ScrollPane) node).getContent();
                if (content instanceof Parent) {
                    setEditable((Parent) content); // เรียกใช้ recursive function กับ content
                }
            }
            else if (node instanceof MenuButton) {
                ((MenuButton) node).setDisable(true);
            }
            else if (node instanceof DatePicker) {
                ((DatePicker) node).setDisable(true);
            }
            else if (node instanceof TextArea) {
                ((TextArea) node).setEditable(false);
            }
            // ถ้า node เป็น Parent ให้เรียกใช้ recursive function
            else if (node instanceof Parent) {
                setEditable((Parent) node);
            }

        }
    }
}
