package ku.cs.controllers.admin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.tables.*;
import ku.cs.models.Faculty;
import ku.cs.services.popup.PopupComponent;

import java.util.Comparator;

public class FacultyTableDescriptor extends TableHeaderDescriptor<Faculty> {
    @TableColumn(order = 0, name = "ชื่อคณะ", size = 500)
    public ColumnFactory<Faculty> facultyNameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Faculty faculty) {
                Label label = new Label(faculty.getName());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("ชื่อคณะ");
                label.getStyleClass().add("table-text");
                return label;
            }
        };
    }

    @TableColumn(order = 1, name = "รหัสคณะ", size = 300)
    public ColumnFactory<Faculty> facultyIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Faculty faculty) {
                Label label = new Label(faculty.getId());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("รหัสคณะ");
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<Faculty> getComparator() {
                return Comparator.comparing(Faculty::getId);
            }
        };
    }

    @TableColumn(order = 2, name = "", size = 100, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Faculty> editButtonColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Faculty faculty) {
                Button editButton = new Button("แก้ไขข้อมูล");
                editButton.getStyleClass().add("green-button");
                Pane tablePane = getTableComponentController().getTablePane();
                editButton.setOnAction(actionEvent -> {
                    PopupComponent<Faculty> requestActionPopup = new PopupComponent<>(faculty, "/ku/cs/views/admin/admin-faculty-management-popup.fxml", "admin-faculty-management-popup", tablePane.getScene().getWindow());
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
