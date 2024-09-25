package ku.cs.controllers.admin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.controllers.components.tables.*;
import ku.cs.models.users.Advisor;
import ku.cs.services.popup.PopupComponent;

import java.util.Comparator;

public class AdvisorTableDescriptor extends TableHeaderDescriptor<Advisor> {
    @TableColumn(order = 0, name = "โปรไฟล์", size = 80)
    public ColumnFactory<Advisor> profileColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Advisor advisor) {
                Circle profilePictureCircle = new Circle();
                profilePictureCircle.setRadius(20);
                ProfilePictureController.setImageToCircle(profilePictureCircle, advisor.getProfilePictureFileName());
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

    @TableColumn(order = 1, name = "ชื่อ-นามสกุล", size = 180)
    public ColumnFactory<Advisor> nameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Advisor advisor) {
                Label label = new Label(advisor.getName() + " " + advisor.getSurname());
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

    @TableColumn(order = 2, name = "ชื่อผู้ใช้", size = 120)
    public ColumnFactory<Advisor> usernameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Advisor advisor) {
                Label label = new Label(advisor.getUsername());
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

    @TableColumn(order = 3, name = "คณะที่สังกัด", size = 120)
    public ColumnFactory<Advisor> facultyColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Advisor advisor) {
                Label label = new Label(advisor.getFaculty().getName());
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

    @TableColumn(order = 4, name = "ภาควิชาที่สังกัด", size = 140)
    public ColumnFactory<Advisor> departmentColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Advisor advisor) {
                Label label = new Label(advisor.getDepartment().getName());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("ภาควิชาที่สังกัด");
                label.getStyleClass().add("table-text");
                return label;
            }
        };
    }

    @TableColumn(order = 5, name = "รหัสอาจารย์ที่ปรึกษา", size = 100)
    public ColumnFactory<Advisor> advisorIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Advisor advisor) {
                Label label = new Label(advisor.getAdvisorId());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("รหัสอาจารย์ที่ปรึกษา");
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
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Advisor advisor) {
                Button editButton = new Button("แก้ไขข้อมูล");
                editButton.getStyleClass().add("green-button");
                Pane tablePane = getTableComponentController().getTablePane();
                editButton.setOnAction(actionEvent -> {
                    PopupComponent<Advisor> requestActionPopup = new PopupComponent<>(advisor, "/ku/cs/views/admin/admin-advisor-management-popup.fxml", "admin-advisor-management-popup", tablePane.getScene().getWindow());
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
