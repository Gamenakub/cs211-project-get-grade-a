package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.FacultyMenuButtonController;
import ku.cs.controllers.components.PasswordFieldSkin;
import ku.cs.models.Faculty;
import ku.cs.models.users.Admin;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.Session;

public class AdminFacultyOfficerManagementPopupController extends BasePopup<FacultyOfficer> {
    @FXML private Label titleLabel;
    @FXML private TextField nameTitleTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField defaultPasswordField;
    @FXML private MenuButton facultyMenuButton;
    @FXML private AnchorPane anchorPane;
    private Admin admin;
    private FacultyOfficer facultyOfficer;

    public void onPopupOpen() {
        Session.getSession().getTheme().setTheme(anchorPane);
        defaultPasswordField.setSkin(new PasswordFieldSkin(defaultPasswordField));
        admin = (Admin) Session.getSession().getLoggedInUser();
        facultyOfficer = getModel();

        FacultyMenuButtonController.addItems(facultyMenuButton, admin.getFacultyList().getFaculties());

        if(facultyOfficer != null) {
            titleLabel.setText("แก้ไขข้อมูลเจ้าหน้าที่คณะ");
            nameTitleTextField.setText(facultyOfficer.getNameTitle());
            nameTextField.setText(facultyOfficer.getName());
            surnameTextField.setText(facultyOfficer.getSurname());
            usernameTextField.setText(facultyOfficer.getUsername());
            FacultyMenuButtonController.setMenuButton(facultyMenuButton, facultyOfficer.getFaculty());
        } else {
            titleLabel.setText("เพิ่มข้อมูลเจ้าหน้าที่คณะ");
        }
    }

    @FXML
    public void onCancelButton(){
        AlertService.showWarning("ระบบไม่ได้บันทึกข้อมูล");
        this.close();
    }

    @FXML
    public void onConfirmButton(){
        String nameTitle = nameTitleTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String username = usernameTextField.getText();
        String password = defaultPasswordField.getText();
        Faculty faculty = (Faculty) facultyMenuButton.getUserData();

        if(nameTitle.isEmpty() || name.isEmpty() || surname.isEmpty() || username.isEmpty()) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
        } else {
            if(facultyOfficer == null){
                facultyOfficer = new FacultyOfficer(username, password, nameTitle, name, surname, faculty);
                admin.getFacultyOfficerList().addOfficer(facultyOfficer);
            } else {
                facultyOfficer.setNameTitle(nameTitle);
                facultyOfficer.setName(name);
                facultyOfficer.setSurname(surname);
                facultyOfficer.setUsername(username);
                facultyOfficer.setFaculty(faculty);

                if(!password.isEmpty()){
                    if(AlertService.showConfirmation("คุณต้องการแก้ไขรหัสผ่านเริ่มต้นของ" + System.lineSeparator() + facultyOfficer.getName() + " " + facultyOfficer.getSurname())) {
                        facultyOfficer.setPassword(password);
                        AlertService.showInfo("แก้ไขรหัสผ่านเรียบร้อยแล้ว");
                    } else {
                        AlertService.showWarning("รหัสผ่านไม่ถูกแก้ไข");
                    }
                }
            }
            AlertService.showInfo("บันทึกข้อมูลเรียบร้อยแล้ว");
            this.issueEvent("success");
            this.close();
        }


    }
}
