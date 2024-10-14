package ku.cs.controllers.admin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.tables.ColumnFactory;
import ku.cs.controllers.components.tables.HeaderMode;
import ku.cs.controllers.components.tables.TableColumn;
import ku.cs.controllers.components.tables.TableHeaderDescriptor;
import ku.cs.models.Faculty;
import ku.cs.services.AlertService;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;
import java.util.Comparator;

public class FacultyTableDescriptor extends TableHeaderDescriptor<Faculty> {
    @TableColumn(order = 0, name = "ชื่อคณะ", size = 500, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Faculty> facultyNameColumn() {
        return faculty -> {
            Label label = new Label(faculty.getName());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 1, name = "รหัสคณะ", size = 300, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Faculty> facultyIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Faculty faculty) {
                Label label = new Label(faculty.getId());
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
        return faculty -> {
            Button editButton = new Button("แก้ไขข้อมูล");
            editButton.getStyleClass().add("green-button");
            Pane tablePane = getTableComponentController().getTablePane();
            editButton.setOnAction(actionEvent -> {
                PopupComponent<Faculty> requestActionPopup = null;
                try {
                    requestActionPopup = new PopupComponent<>(faculty, "/ku/cs/views/admin/admin-faculty-management-popup.fxml", tablePane.getScene().getWindow());
                } catch (IOException e) {
                    AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
                    System.exit(1);
                }
                requestActionPopup.show();
                requestActionPopup.getPopupController().addEventListener(
                        "success", eventData -> getTableComponentController().updateTable()
                );
            });
            return editButton;
        };
    }
}
