package ku.cs.controllers.advisor;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.controllers.components.tables.ColumnFactory;
import ku.cs.controllers.components.tables.HeaderMode;
import ku.cs.controllers.components.tables.TableColumn;
import ku.cs.controllers.components.tables.TableHeaderDescriptor;
import ku.cs.models.users.Student;

public class StudentTableDescriptor extends TableHeaderDescriptor<Student> {
    @TableColumn(order = 0, name = "โปรไฟล์", size = 225, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Student> studentProfileColumn() {
        return obj -> {
            Circle profilePictureCircle = new Circle(15);
            ProfilePictureController.setImageToCircle(profilePictureCircle, obj.getProfilePictureFileName());
            return profilePictureCircle;
        };
    }

    @TableColumn(order = 1, name = "ชื่อ", size = 225, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Student> studentName() {
        return obj -> {
            Label label = new Label(obj.getName());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 2, name = "รหัสนิสิต", size = 225, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Student> studentId() {
        return obj -> {
            Label label = new Label(obj.getStudentId());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 3, name = "", size = 225)
    public ColumnFactory<Student> actionColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {
                Button actionButton = new Button("ดูประวัติคำร้อง");
                actionButton.getStyleClass().add("green-button");
                actionButton.setOnAction(actionEvent -> getTableComponentController().issueEvent("ดูประวัติคำร้อง", obj));
                return actionButton;
            }

            @Override
            public Node getHeadNode() {
                return new Label("");
            }
        };
    }

}
