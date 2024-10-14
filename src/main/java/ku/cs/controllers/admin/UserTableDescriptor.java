package ku.cs.controllers.admin;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.controllers.components.tables.ColumnFactory;
import ku.cs.controllers.components.tables.HeaderMode;
import ku.cs.controllers.components.tables.TableColumn;
import ku.cs.controllers.components.tables.TableHeaderDescriptor;
import ku.cs.models.users.User;
import ku.cs.services.AlertService;
import ku.cs.services.ThaiStringConverter;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;

public class UserTableDescriptor extends TableHeaderDescriptor<User> {
    @TableColumn(order = 0, name = "รูปโพรไฟล์", size = 80, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<User> profileColumn() {
        return user -> {
            Circle profilePictureCircle = new Circle();
            profilePictureCircle.setRadius(20);
            ProfilePictureController.setImageToCircle(profilePictureCircle, user.getProfilePictureFileName());
            return profilePictureCircle;
        };
    }

    @TableColumn(order = 1, name = "ชื่อผู้ใช้", size = 120, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<User> usernameColumn() {
        return user -> {
            Label label = new Label(user.getUsername());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 2, name = "ชื่อ-นามสกุล", size = 180, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<User> nameColumn() {
        return user -> {
            Label label = new Label(user.getName() + " " + user.getSurname());
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 3, name = "บทบาท", size = 140, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<User> roleColumn() {
        return user -> {
            Label label = new Label(ThaiStringConverter.getThaiUserRoleString(user));
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 4, name = "วันเวลาที่ใช้งานล่าสุด", size = 160, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<User> dateColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                Label label;
                try {
                    label = new Label(ThaiStringConverter.getThaiTimeStampFormattedString(user.getRecentTime()));
                } catch (NullPointerException e) {
                    label = new Label("ไม่พบวันเวลาที่ใช้งานล่าสุด");
                }
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<User> getComparator() {
                return (o1, o2) -> {
                    LocalDateTime t1 = o1.getRecentTime();
                    LocalDateTime t2 = o2.getRecentTime();
                    if (t1 == null && t2 == null) {
                        return 0;
                    } else if (t1 == null) {
                        return -1;
                    } else if (t2 == null) {
                        return 1;
                    } else {
                        return t1.compareTo(t2);
                    }
                };
            }
        };
    }


    @TableColumn(order = 5, name = "สถานะ", size = 100, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<User> statusColumn() {
        return user -> {
            String statusString = user.getStatus() ? "ปกติ" : "ถูกระงับสิทธิ์";
            Label label = new Label(statusString);
            label.getStyleClass().add("table-text");
            return label;
        };
    }

    @TableColumn(order = 6, name = "สีสถานะ", size = 20)
    public ColumnFactory<User> statusCircleColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(User user) {
                Circle statusCircle = new Circle();
                statusCircle.setRadius(10);
                if (user.getStatus()) statusCircle.setFill(Color.rgb(0, 154, 33));
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
        return user -> {
            Button permissionManagementButton = new Button("จัดการสิทธิ์");
            permissionManagementButton.getStyleClass().add("green-button");
            Pane tablePane = getTableComponentController().getTablePane();
            permissionManagementButton.setOnAction(actionEvent -> {
                PopupComponent<User> requestActionPopup = null;
                try {
                    requestActionPopup = new PopupComponent<>(user, "/ku/cs/views/admin/admin-user-management-popup.fxml", tablePane.getScene().getWindow());
                } catch (IOException e) {
                    AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
                    System.exit(1);
                }
                requestActionPopup.show();
                requestActionPopup.getPopupController().addEventListener(
                        "success", eventData -> getTableComponentController().updateTable()
                );
            });
            return permissionManagementButton;
        };
    }
}
