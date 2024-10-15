package ku.cs.controllers.admin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.controllers.components.tables.ColumnFactory;
import ku.cs.controllers.components.tables.HeaderMode;
import ku.cs.controllers.components.tables.TableColumn;
import ku.cs.controllers.components.tables.TableHeaderDescriptor;
import ku.cs.models.users.Advisor;
import ku.cs.services.AlertService;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;
import java.util.Comparator;

public class AdvisorTableDescriptor extends TableHeaderDescriptor<Advisor> {
    @TableColumn(order = 0, name = "รูปโพรไฟล์", size = 80, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Advisor> profileColumn() {
        return advisor -> {
            Circle profilePictureCircle = new Circle();
            profilePictureCircle.setRadius(20);
            ProfilePictureController profilePictureController = new ProfilePictureController();
            profilePictureController.setImageToCircle(profilePictureCircle, advisor.getProfilePictureFileName());
            return profilePictureCircle;
        };
    }

    @TableColumn(order = 1, name = "ชื่อ-นามสกุล", size = 180, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Advisor> nameColumn() {
        return advisor -> {
            Label label = new Label(advisor.getName() + " " + advisor.getSurname());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 2, name = "ชื่อผู้ใช้", size = 120, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Advisor> usernameColumn() {
        return advisor -> {
            Label label = new Label(advisor.getUsername());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 3, name = "คณะที่สังกัด", size = 140, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Advisor> facultyColumn() {
        return advisor -> {
            Label label = new Label(advisor.getFaculty().getName());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 4, name = "ภาควิชาที่สังกัด", size = 160, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Advisor> departmentColumn() {
        return advisor -> {
            Label label = new Label(advisor.getDepartment().getName());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 5, name = "รหัสอาจารย์ที่ปรึกษา", size = 120, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Advisor> advisorIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Advisor advisor) {
                Label label = new Label(advisor.getAdvisorId());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<Advisor> getComparator() {
                return (o1, o2) -> {
                    String id1 = o1.getAdvisorId();
                    String id2 = o2.getAdvisorId();
                    return id1.compareTo(id2);
                };
            }
        };
    }

    @TableColumn(order = 6, name = "", size = 100, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Advisor> editButtonColumn() {
        return advisor -> {
            Button editButton = new Button("แก้ไขข้อมูล");
            editButton.getStyleClass().add("green-button");
            Pane tablePane = getTableComponentController().getTablePane();
            editButton.setOnAction(actionEvent -> {
                PopupComponent<Advisor> requestActionPopup = null;
                try {
                    requestActionPopup = new PopupComponent<>(advisor, "/ku/cs/views/admin/admin-advisor-management-popup.fxml", tablePane.getScene().getWindow());
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
