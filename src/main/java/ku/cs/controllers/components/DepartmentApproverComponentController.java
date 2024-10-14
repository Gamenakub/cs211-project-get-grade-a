package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.models.DepartmentApprover;
import ku.cs.models.collections.DepartmentApproverList;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;


public class DepartmentApproverComponentController {

    @FXML private TextField surnameTextField;
    @FXML private TextField nameTitleTextField;
    @FXML private TextField nameTextField;

    @FXML private MenuButton roleMenuButton;

    @FXML private Button deleteButton;

    private DepartmentApprover approver;
    private DepartmentApproverList relatedDepartmentApproverList;
    private DepartmentApproverList awaitDelete;

    private EventCallback onUpdateCallback;

    private String departmentName;

    public void onUpdate(EventCallback onUpdateCallback) {
        this.onUpdateCallback = onUpdateCallback;
    }

    public void initializeDepartmentApproverComponent(DepartmentApproverList relatedDepartmentApproverList, DepartmentApproverList awaitDelete, DepartmentApprover approver, String departmentName) {
        this.approver = approver;
        this.relatedDepartmentApproverList = relatedDepartmentApproverList;
        this.awaitDelete = awaitDelete;
        this.departmentName = departmentName;
        reload();
    }

    public void reload() {


        nameTextField.setText(approver.getName());
        surnameTextField.setText(approver.getSurname());
        nameTitleTextField.setText(approver.getNameTitle());
        roleMenuButton.getItems().clear();
        HashSet<String> roles = new HashSet<>();


        for (String role : relatedDepartmentApproverList.getAvailableRole()) {
            if (role.isEmpty()) {
                continue;
            }
            String representation = role + "ภาควิชา" + departmentName;
            roles.add(representation);
        }

        for (String role : awaitDelete.getAllRole()) {
            if (role.isEmpty()) {
                continue;
            }
            String representation = role + "ภาควิชา" + departmentName;
            roles.add(representation);
        }

        for (String role : roles) {
            addRoleToMenuItem(role, true);
        }

        for (String role : relatedDepartmentApproverList.getUnavailableRole()) {
            String representation = role + "ภาควิชา" + departmentName;
            addRoleToMenuItem(representation, false);
        }


        deleteButton.setOnAction(event -> onUpdateCallback.onEvent("delete"));
        if (!approver.getRole().isEmpty()) {
            roleMenuButton.setText(approver.getRepresentativeRole());
            if (approver.isLeaderOrSubLeader()) {
                addRoleToMenuItem("รักษาการณ์หัวหน้าภาควิชา" + departmentName, true);
                addRoleToMenuItem("หัวหน้าภาควิชา" + departmentName, true);
            }
        }

    }

    private void addRoleToMenuItem(String role, boolean enabled) {
        MenuItem subLeaderMenuItem = new MenuItem(role);
        if (!enabled) {


            subLeaderMenuItem.setDisable(true);
        }

        subLeaderMenuItem.setOnAction(event -> {
            roleMenuButton.setText(role);
            onUpdateCallback.onEvent("update-role");
        });


        for (MenuItem menuItem : roleMenuButton.getItems()) {
            if (Objects.equals(menuItem.getText(), role)) {
                roleMenuButton.getItems().remove(menuItem);
                break;
            }
        }
        if (!enabled) roleMenuButton.getItems().addLast(subLeaderMenuItem);
        else roleMenuButton.getItems().addFirst(subLeaderMenuItem);
    }

    public void confirm() {
        approver.setName(nameTextField.getText());
        approver.setSurname(surnameTextField.getText());
        approver.setNameTitle(nameTitleTextField.getText());
        String role = roleMenuButton.getText().split("ภาควิชา")[0];
        approver.setRole(role);
    }

    public void confirmChecked() throws IOException {

        if (nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() || roleMenuButton.getText().isEmpty()) {
            throw new IOException("กรุณากรอกข้อมูลให้ครบถ้วน");
        }
        confirm();
    }
}