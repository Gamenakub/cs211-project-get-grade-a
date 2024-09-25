package ku.cs.controllers.admin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.controllers.components.tables.*;
import ku.cs.models.users.User;
import ku.cs.services.Utility;
import ku.cs.services.popup.PopupComponent;

import java.util.Comparator;

public class UserTableDescriptor extends TableHeaderDescriptor<User> {
    @TableColumn(order = 0, name = "โปรไฟล์", size = 80)
    public ColumnFactory<User> profileColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                Circle profilePictureCircle = new Circle();
                profilePictureCircle.setRadius(20);
                ProfilePictureController.setImageToCircle(profilePictureCircle, user.getProfilePictureFileName());
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

    @TableColumn(order = 1, name = "ชื่อผู้ใช้", size = 120)
    public ColumnFactory<User> usernameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                Label label = new Label(user.getUsername());
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

    @TableColumn(order = 2, name = "ชื่อ-นามสกุล", size = 180)
    public ColumnFactory<User> nameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                Label label = new Label(user.getName() + " " + user.getSurname());
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

    @TableColumn(order = 3, name = "บทบาท", size = 140)
    public ColumnFactory<User> roleColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                String roleString = switch (user.getRole()) {
                    case "admin" -> "ผู้ดูแลระบบ";
                    case "advisor" -> "อาจารย์ที่ปรึกษา";
                    case "student" -> "นิสิต";
                    case "departmentOfficer" -> "เจ้าหน้าที่ภาควิชา";
                    case "facultyOfficer" -> "เจ้าหน้าที่คณะ";
                    default -> user.getRole();
                };
                Label label = new Label(roleString);
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("บทบาท");
                label.getStyleClass().add("table-text");
                return label;
            }
        };
    }

    @TableColumn(order = 4, name = "วันเวลาที่ใช้งานล่าสุด", size = 160)
    public ColumnFactory<User> dateColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                Label label = new Label(Utility.getTimeStamp(user.getRecentTime()));
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("วันเวลาที่ใช้งานล่าสุด");
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<User> getComparator() {
                return Comparator.comparing(User::getRecentTime);
            }
        };
    }


    @TableColumn(order = 5, name = "สถานะ", size = 100)
    public ColumnFactory<User> statusColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                String statusString = user.getStatus() ? "ปกติ" : "ถูกระงับสิทธิ์";
                Label label = new Label(statusString);
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("สถานะ");
                label.getStyleClass().add("table-text");
                return label;
            }
        };
    }

    @TableColumn(order = 6, name = "สีสถานะ", size = 20)
    public ColumnFactory<User> statusCircleColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                Circle statusCircle = new Circle();
                statusCircle.setRadius(10);
                if(user.getStatus()) statusCircle.setFill(Color.rgb(0,154,33));
                else statusCircle.setFill(Color.rgb(218, 49, 49));
                return statusCircle;
            }

            @Override
            public Node getHeadNode() {
                Label label = new Label("");
                label.getStyleClass().add("table-text");
                return label;
            }
        };
    }

    @TableColumn(order = 7, name = "", size = 100, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<User> permissionManagementColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                Button permissionManagementButton = new Button("จัดการสิทธิ์");
                permissionManagementButton.getStyleClass().add("green-button");
                Pane tablePane = getTableComponentController().getTablePane();
                permissionManagementButton.setOnAction(actionEvent -> {
                    PopupComponent<User> requestActionPopup = new PopupComponent<>(user, "/ku/cs/views/admin/admin-user-management-popup.fxml", "admin-user-management-popup", tablePane.getScene().getWindow());
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
                return permissionManagementButton;
            }
        };
    }
}
