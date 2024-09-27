package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.models.users.Admin;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.User;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.Session;
import ku.cs.services.popup.PopupComponent;

public class UserPersonalInformationManagementPageController {
    @FXML private Pane navBarPane;
    @FXML private AnchorPane anchorPane;
    @FXML private Circle profilePictureCircle;
    @FXML private Label nameLabel;
    @FXML private Label surnameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;
    @FXML private Label facultyLabel;
    @FXML private Label departmentLabel;
    @FXML private Label IdLabel;
    User user;

    @FXML
    public void initialize() {
        Session.getSession().getTheme().setTheme(anchorPane);
        Session.getSession().setNavbar(navBarPane);
        user = Session.getSession().getLoggedInUser();

        ProfilePictureController.setImageToCircle(profilePictureCircle, user.getProfilePictureFileName());

        nameLabel.setText(user.getNameTitle() + " " + user.getName());
        surnameLabel.setText(user.getSurname());
        usernameLabel.setText("ชื่อผู้ใช้ " + user.getUsername());
        roleLabel.setText("บทบาท " + switch (user.getRole()) {
            case "admin" -> "ผู้ดูแลระบบ";
            case "advisor" -> "อาจารย์ที่ปรึกษา";
            case "student" -> "นิสิต";
            case "departmentOfficer" -> "เจ้าหน้าที่ภาควิชา";
            case "facultyOfficer" -> "เจ้าหน้าที่คณะ";
            default -> user.getRole();
        });

        if (user instanceof Admin) {
            facultyLabel.setVisible(false);
            departmentLabel.setVisible(false);
            IdLabel.setVisible(false);
        } else if (user instanceof Student) {
            facultyLabel.setText("คณะ " + ((Student) user).getFaculty().getName());
            departmentLabel.setText("ภาควิชา " + ((Student) user).getDepartment().getName());
            IdLabel.setText("รหัสนิสิต " + ((Student) user).getStudentId());
        } else if (user instanceof Advisor) {
            facultyLabel.setText("คณะ " + ((Advisor) user).getFaculty().getName());
            departmentLabel.setText("ภาควิชา " + ((Advisor) user).getDepartment().getName());
            IdLabel.setText("รหัสอาจารย์ที่ปรึกษา " + ((Advisor) user).getAdvisorId());
        } else if (user instanceof DepartmentOfficer) {
            facultyLabel.setText("คณะ " + ((DepartmentOfficer) user).getFaculty().getName());
            departmentLabel.setText("ภาควิชา " + ((DepartmentOfficer) user).getDepartment().getName());
            IdLabel.setVisible(false);
        } else if (user instanceof FacultyOfficer) {
            facultyLabel.setText("คณะ" + ((FacultyOfficer) user).getFaculty().getName());
            departmentLabel.setVisible(false);
            IdLabel.setVisible(false);
        }
    }

    @FXML
    public void onChangeProfilePictureButton() {
        PopupComponent<User> requestActionPopup = new PopupComponent<>(Session.getSession().getLoggedInUser() ,"/ku/cs/views/user-change-profile-picture-popup.fxml","user-change-profile-picture-popup",navBarPane.getScene().getWindow());
        requestActionPopup.show();
        requestActionPopup.onEvent((eventName, eventData) -> {
            ProfilePictureController.setImageToCircle(profilePictureCircle, user.getProfilePictureFileName());
            Session.getSession().setNavbar(navBarPane);
        });
    }

    public void onChangePasswordButton() {
        PopupComponent<User> requestActionPopup = new PopupComponent<>(Session.getSession().getLoggedInUser(),"/ku/cs/views/user-change-password-popup.fxml","user-change-password-popup",navBarPane.getScene().getWindow());
        requestActionPopup.show();
    }
}
