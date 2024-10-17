package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.models.FacultyApprover;

import java.io.IOException;

public class FacultyApproverComponentController {
    @FXML public TextField nameTitleTextField;
    @FXML public TextField surnameTextField;
    @FXML public TextField facultyTextField;
    @FXML private TextField nameTextField;
    @FXML private MenuButton roleMenuButton;
    @FXML private TextField factionTextField;
    @FXML private Button deleteButton;


    private FacultyApprover approver;
    private EventCallback onUpdateCallback;

    public void onUpdate(EventCallback onUpdateCallback) {
        this.onUpdateCallback = onUpdateCallback;
    }

    public void setFacultyApprover(FacultyApprover approver) {
        this.approver = approver;
        reload();
    }

    public void reload() {
        nameTextField.setText(approver.getName());
        surnameTextField.setText(approver.getSurname());
        nameTitleTextField.setText(approver.getNameTitle());
        String role = approver.getRole();
        if (role.contains("รองคณบดี")) {
            roleMenuButton.setText("รองคณบดี");
            factionTextField.setText(role.replace("รองคณบดี", ""));
            factionTextField.setDisable(false);
        } else {
            roleMenuButton.setText(approver.getRole());
        }
        facultyTextField.setText("คณะ" + approver.getFaculty().getName());
        facultyTextField.setEditable(false);

        deleteButton.setOnAction(event -> onUpdateCallback.onEvent("delete"));
    }

    public void confirm() throws IOException {
        if (nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() || nameTitleTextField.getText().isEmpty() || roleMenuButton.getText().isEmpty()) {
            throw new IOException("กรุณากรอกข้อมูลให้ครบถ้วน");
        }
        approver.setName(nameTextField.getText());
        approver.setSurname(surnameTextField.getText());
        approver.setNameTitle(nameTitleTextField.getText());
        String role;
        if (roleMenuButton.getText().equals("รองคณบดี")) {
            role = roleMenuButton.getText() + factionTextField.getText();
        } else {
            role = roleMenuButton.getText();
        }
        approver.setRole(role);
    }

    public FacultyApprover getApprover() {
        return approver;
    }

    @FXML
    private void onDeanMenuButton() {
        roleMenuButton.setText("คณบดี");
        factionTextField.setText("");
        factionTextField.setDisable(true);
    }

    @FXML
    private void onViceDeanMenuButton() {
        roleMenuButton.setText("รองคณบดี");
        factionTextField.setText("ฝ่าย");
        factionTextField.setDisable(false);
    }

    @FXML
    private void onSubDeanMenuButton() {
        roleMenuButton.setText("รักษาการณ์แทนคณบดี");
        factionTextField.setText("");
        factionTextField.setDisable(true);
    }
}
