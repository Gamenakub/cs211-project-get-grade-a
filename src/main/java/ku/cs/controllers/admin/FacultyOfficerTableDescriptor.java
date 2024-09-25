package ku.cs.controllers.admin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.controllers.components.tables.*;
import ku.cs.models.Faculty;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.popup.PopupComponent;

import java.util.Comparator;

public class FacultyOfficerTableDescriptor extends TableHeaderDescriptor<FacultyOfficer> {
    @TableColumn(order = 0, name = "โปรไฟล์", size = 80)
    public ColumnFactory<FacultyOfficer> profileColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(FacultyOfficer officer) {
                Circle profilePictureCircle = new Circle();
                profilePictureCircle.setRadius(20);
                ProfilePictureController.setImageToCircle(profilePictureCircle, officer.getProfilePictureFileName());
                return profilePictureCircle;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("โปรไฟล์");
                label.getStyleClass().add("table-text");
                return label;
            }
        };
    }

    @TableColumn(order = 1, name = "ชื่อ-นามสกุล", size = 240)
    public ColumnFactory<FacultyOfficer> nameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(FacultyOfficer officer) {
                Label label = new Label(officer.getName() + " " + officer.getSurname());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("ชื่อ-นามสกุล");
                label.getStyleClass().add("table-text");
                return label;
            }
        };
    }

    @TableColumn(order = 2, name = "ชื่อผู้ใช้", size = 200)
    public ColumnFactory<FacultyOfficer> usernameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(FacultyOfficer officer) {
                Label label = new Label(officer.getUsername());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("ชื่อผู้ใช้");
                label.getStyleClass().add("table-text");
                return label;
            }
        };

    }

    @TableColumn(order = 3, name = "คณะที่สังกัด", size = 140)
    public ColumnFactory<FacultyOfficer> facultyColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(FacultyOfficer officer) {
                Label label = new Label(officer.getFaculty().getName());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("คณะที่สังกัด");
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<FacultyOfficer> getComparator() {
                return (o1, o2) -> {
                    Faculty f1 = o1.getFaculty();
                    Faculty f2 = o2.getFaculty();
                    return f1.compareTo(f2);
                };
            }
        };
    }


    @TableColumn(order = 4, name = "", size = 100, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<FacultyOfficer> editButtonColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(FacultyOfficer officer) {
                Button editButton = new Button("แก้ไขข้อมูล");
                editButton.getStyleClass().add("green-button");
                Pane tablePane = getTableComponentController().getTablePane();
                editButton.setOnAction(actionEvent -> {
                    PopupComponent<FacultyOfficer> requestActionPopup = new PopupComponent<>(officer, "/ku/cs/views/admin/admin-faculty-officer-management-popup.fxml", "admin-faculty-officer-management-popup", tablePane.getScene().getWindow());
                    requestActionPopup.show();
                    requestActionPopup.getPopupController().addEventListener(
                            "success", new EventCallback() {
                                @Override
                                public void onEvent(Object eventData) {
                                    getTableComponentController().updateTable();
                                }
                            }
                    );
                });
                return editButton;
            }
        };
    }
}
