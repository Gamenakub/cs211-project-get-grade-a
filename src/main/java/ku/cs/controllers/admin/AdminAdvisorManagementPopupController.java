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
import ku.cs.models.Department;
import ku.cs.models.users.Admin;
import ku.cs.models.users.Advisor;
import ku.cs.services.AlertService;
import ku.cs.services.Session;

public class AdminAdvisorManagementPopupController extends BasePopup<Advisor> {
    @FXML private Label titleLabel;
    @FXML private TextField nameTitleTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField defaultPasswordField;
    @FXML private MenuButton facultyMenuButton;
    @FXML private MenuButton departmentMenuButton;
    @FXML private TextField advisorIdTextField;
    @FXML private AnchorPane anchorPane;
    private Admin admin;
    private Advisor advisor;

    public void onPopupOpen() {
        Session.getSession().getTheme().setTheme(anchorPane);
        defaultPasswordField.setSkin(new PasswordFieldSkin(defaultPasswordField));
        admin = (Admin) Session.getSession().getLoggedInUser();
        advisor = getModel();

        FacultyMenuButtonController.addItems(facultyMenuButton, departmentMenuButton, admin.getFacultyList().getFaculties());

        if(advisor != null) {
            titleLabel.setText("แก้ไขข้อมูลอาจารย์ที่ปรึกษา");
            nameTitleTextField.setText(advisor.getNameTitle());
            nameTextField.setText(advisor.getName());
            surnameTextField.setText(advisor.getSurname());
            usernameTextField.setText(advisor.getUsername());
            defaultPasswordField.setText("Test");
            advisorIdTextField.setText(advisor.getAdvisorId());
            FacultyMenuButtonController.setMenuButton(facultyMenuButton, departmentMenuButton, advisor.getFaculty(), advisor.getDepartment());
        } else {
            titleLabel.setText("เพิ่มข้อมูลอาจารย์ที่ปรึกษา");
        }
    }

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
        Department department = (Department) departmentMenuButton.getUserData();
        String advisorId = advisorIdTextField.getText();

        if(nameTitle.isEmpty() || name.isEmpty() || surname.isEmpty() || username.isEmpty() || advisorId.isEmpty()) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
        } else {
            if(advisor == null){
                Advisor advisor = new Advisor(username, password, nameTitle, name, surname, advisorId, department);
                admin.getAdvisorList().addAdvisor(advisor);
            } else {
                advisor.setNameTitle(nameTitle);
                advisor.setName(name);
                advisor.setSurname(surname);
                advisor.setUsername(username);
                advisor.setAdvisorId(advisorId);
                advisor.setDepartment(department);
                if(!password.isEmpty()){
                    if(AlertService.showConfirmation("คุณต้องการแก้ไขรหัสผ่านเริ่มต้นของ" + System.lineSeparator() + advisor.getName() + " " + advisor.getSurname())) {
                        advisor.setPassword(password);
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
