package ku.cs.controllers.admin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.tables.*;
import ku.cs.models.Department;
import ku.cs.services.popup.PopupComponent;

import java.util.Comparator;

public class DepartmentTableDescriptor extends TableHeaderDescriptor<Department> {
    @TableColumn(order = 0, name = "ชื่อภาควิชา", size = 300)
    public ColumnFactory<Department> departmentNameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Department department) {
                Label label = new Label(department.getName());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("ชื่อภาควิชา");
                label.getStyleClass().add("table-text");
                return label;
            }
        };
    }

    @TableColumn(order = 1, name = "คณะที่สังกัด", size = 300)
    public ColumnFactory<Department> facultyNameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Department department) {
                Label label = new Label(department.getFaculty().getName());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("คณะที่สังกัด");
                label.getStyleClass().add("table-text");
                return label;
            }
        };
    }

    @TableColumn(order = 2, name = "รหัสของภาควิชา", size = 200)
    public ColumnFactory<Department> departmentIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Department department) {
                Label label = new Label(department.getId());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("รหัสของภาควิชา");
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<Department> getComparator() {
                return Comparator.comparing(Department::getId);
            };
        };
    }

    @TableColumn(order = 3, name = "", size = 100, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Department> editButtonColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Department department) {
                Button editButton = new Button("แก้ไขข้อมูล");
                editButton.getStyleClass().add("green-button");
                Pane tablePane = getTableComponentController().getTablePane();
                editButton.setOnAction(actionEvent -> {
                    PopupComponent<Department> requestActionPopup = new PopupComponent<>(department, "/ku/cs/views/admin/admin-department-management-popup.fxml", "admin-department-management-popup", tablePane.getScene().getWindow());
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
