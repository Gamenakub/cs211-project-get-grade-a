package ku.cs.controllers.officer.department;

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
import ku.cs.models.users.User;
import ku.cs.services.Session;

import java.util.Comparator;


public class DepartmentOfficerStudentTableDescriptor extends TableHeaderDescriptor<Student> {

    @TableColumn(order = 0, name = "รูปโพรไฟล์", size = 81, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Student> profileColumn() {
        return obj -> {
            Circle profile = new Circle();
            profile.setRadius(20);
            ProfilePictureController profilePictureController = new ProfilePictureController();
            profilePictureController.setImageToCircle(profile, obj.getProfilePictureFileName());
            return profile;
        };
    }

    @TableColumn(order = 1, name = "ชื่อ-นามสกุล", size = 152, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Student> nameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {

                Label label = new Label(obj.getName()+" "+obj.getSurname());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<Student> getComparator() {
                return Comparator.comparing(User::getName);
            }
        };
    }

    @TableColumn(order = 2, name = "ชื่อผู้ใช้", size = 152, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Student> usernameColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {

                Label label = new Label(obj.getUsername());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<Student> getComparator() {
                return Comparator.comparing(User::getUsername);
            }
        };
    }

    @TableColumn(order = 3, name = "รหัสนิสิต", size = 142, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Student> studentIdColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {

                Label label = new Label(obj.getStudentId());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<Student> getComparator() {
                return Comparator.comparing(Student::getStudentId);
            }
        };
    }

    @TableColumn(order = 4, name = "อีเมล", size = 100, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Student> emailColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {

                Label label = new Label(obj.getStudentEmail());
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<Student> getComparator() {
                return Comparator.comparing(Student::getStudentEmail);
            }
        };
    }

    @TableColumn(order = 5, name = "อาจารย์ที่ปรึกษา", size = 142, headerMode = HeaderMode.DEFAULT)
    public ColumnFactory<Student> advisorColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {
                Label label;
                if (obj.getAdvisor() == null) {
                    label = new Label("ไม่มี");
                } else {
                    label = new Label(obj.getAdvisor().getName());
                }
                label.getStyleClass().add("table-text");
                return label;
            }

            @Override
            public Comparator<Student> getComparator() {
                return Comparator.comparing(o -> o.getAdvisor().getName());
            }
        };
    }

    @TableColumn(order = 6, name = "", size = 131)
    public ColumnFactory<Student> modifyButtonColumn() {
        return new ColumnFactory<>() {
            @Override
            public Node getDisplayNode(Student obj) {
                Button modifyButton = new Button("แก้ไขข้อมูล");
                modifyButton.getStyleClass().add("green-button");

                modifyButton.setPrefWidth(100);
                modifyButton.setOnAction(actionEvent -> tableComponentController.issueEvent("แก้ไข", obj));
                return modifyButton;
            }

            @Override
            public Node getHeadNode() {
                return new Label("");
            }
        };
    }
}

