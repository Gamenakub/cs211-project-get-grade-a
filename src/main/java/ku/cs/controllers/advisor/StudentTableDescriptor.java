package ku.cs.controllers.advisor;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.controllers.components.tables.ColumnFactory;
import ku.cs.controllers.components.tables.TableColumn;
import ku.cs.controllers.components.tables.TableHeaderDescriptor;
import ku.cs.models.users.Student;
import ku.cs.services.SortDirection;
import ku.cs.services.SortToggleButton;

public class StudentTableDescriptor extends TableHeaderDescriptor {
    @TableColumn(order = 0,name="โปรไฟล์",size = 200)
    public ColumnFactory<Student> studentProfileColumn() {
        return new ColumnFactory<>() {
            @Override

            public Node getDisplayNode(Student obj) {
                Circle profilePictureCircle=new Circle(15);
                ProfilePictureController.setImageToCircle(profilePictureCircle, obj.getProfilePictureFileName());
                return profilePictureCircle;
            }

            @Override
            public Node getHeadNode() {
                SortToggleButton sortButton = new SortToggleButton("โปรไฟล์");
                sortButton.getStyleClass().add("table-text");

                getTableComponentController().addEventListener("resetSort", (event) -> {
                    sortButton.setToggle(false);
                });
                return sortButton;
            }
        };
    }

    @TableColumn(order = 1,name="ชื่อ",size = 200)
    public ColumnFactory<Student> studentName() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {
                return new Label(obj.getName()+" "+obj.getSurname());
            }

            @Override
            public Node getHeadNode() {
                SortToggleButton sortButton = new SortToggleButton("ชื่อ-สกุล");
                sortButton.getStyleClass().add("table-text");

                sortButton.onToggle(sortDirection -> {
                    getTableComponentController().sortBy("ชื่อ-สกุล", (SortDirection) sortDirection);
                });
                getTableComponentController().addEventListener("resetSort", (event) -> {
                    sortButton.setToggle(false);
                });
                return sortButton;
            }
        };
    }

    @TableColumn(order = 2,name="รหัสนิสิต",size = 200)
    public ColumnFactory<Student> studentId() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {
                Label studentIdLabel = new Label(obj.getStudentId());
//                studentIdLabel.getStyleClass().add("table-text");
                return new Label(obj.getStudentId());
            }

            @Override
            public Node getHeadNode() {
                SortToggleButton sortButton = new SortToggleButton("รหัสนิสิต");
                sortButton.getStyleClass().add("table-text");

                sortButton.onToggle(sortDirection -> {
                    getTableComponentController().sortBy("รหัสนิสิต", (SortDirection) sortDirection);
                });
                getTableComponentController().addEventListener("resetSort", (event) -> {
                    sortButton.setToggle(false);
                });
                return sortButton;
            }
        };
    }

    @TableColumn(order = 3,name="",size = 200)
    public ColumnFactory<Student> actionColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {
                Button actionButton = new Button("ดูประวัติคำร้อง");
                actionButton.getStyleClass().add("green-button");
                actionButton.setOnAction(actionEvent -> {
                    getTableComponentController().issueEvent("ดูประวัติคำร้อง", obj);
                });
                return actionButton;
            }

            @Override
            public Node getHeadNode() {
                return new Label("");
            }
        };
    }

}
