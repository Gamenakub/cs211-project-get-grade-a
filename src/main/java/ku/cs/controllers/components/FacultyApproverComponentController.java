package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.models.FacultyApprover;
import ku.cs.models.collections.FacultyApproverList;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.AlertService;

public class FacultyApproverComponentController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField roleTextField;

    @FXML
    private Button deleteButton;

    @FXML
    private HBox hBox;

    private FacultyApprover approver;
    private FacultyOfficer departmentOfficer;
    private FacultyApproverList relatedDepartmentApproverList;

    private EventCallback onUpdateCallback = (eventName) -> {};

    private boolean newlyCreated = false;

    public void onUpdate(EventCallback onUpdateCallback){
        this.onUpdateCallback = onUpdateCallback;
    }

    public void initialize(FacultyApproverList relatedDepartmentApproverList, FacultyApprover approver, FacultyOfficer departmentOfficer) {
        this.approver = approver;
        this.relatedDepartmentApproverList = relatedDepartmentApproverList;
        this.departmentOfficer = departmentOfficer;
        reload();
    }

    public void setNewlyCreated(boolean newlyCreated){
        this.newlyCreated = newlyCreated;
    }


    public void reload() {
        // Implement logic to refresh the page based on the application context
        nameTextField.setText(approver.getName() + " " + approver.getSurname());
        roleTextField.setText(approver.getRole());

        // Handle delete button action
        deleteButton.setOnAction(event -> {
            onUpdateCallback.onEvent("delete");
        });

    }

    public void confirm(){
        String wholeName = nameTextField.getText();
        // trim whole name
        wholeName = wholeName.trim();
        String[] name = wholeName.split(" ");
        if (name.length != 2) {
            AlertService.showError("กรุณากรอกชื่อและนามสกุลให้ถูกต้อง");
            return;
        }
        approver.setName(name[0]);
        approver.setSurname(name[1]);
        String role = roleTextField.getText();
        approver.setRole(role);
    }

    public boolean isNewlyCreated() {
        return newlyCreated;
    }

    public FacultyApprover getApprover() {
        return approver;
    }
}
