package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.models.DepartmentApprover;
import ku.cs.models.collections.DepartmentApproverList;
import ku.cs.models.users.officers.DepartmentOfficer;


public class DepartmentApproverComponentController{

    @FXML
    private TextField nameTextField;

    @FXML
    private MenuButton roleMenuButton;

    @FXML
    private Button deleteButton;

    @FXML
    private HBox hBox;

    private DepartmentApprover approver;
    private DepartmentOfficer departmentOfficer;
    private DepartmentApproverList relatedDepartmentApproverList;

    private EventCallback onUpdateCallback = (eventName) -> {};

    private boolean newlyCreated = false;

    public void onUpdate(EventCallback onUpdateCallback){
        this.onUpdateCallback = onUpdateCallback;
    }

    public void initialize(DepartmentApproverList relatedDepartmentApproverList, DepartmentApprover approver, DepartmentOfficer departmentOfficer) {
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
        roleMenuButton.getItems().clear();

        // Populate MenuButton with roles
        for (String role : relatedDepartmentApproverList.getAvailableRole()) {
            String representation = role + "ภาควิชา" + departmentOfficer.getDepartment().getName();
            MenuItem menuItem = new MenuItem(representation);
            menuItem.setOnAction(event -> {
                roleMenuButton.setText(representation);
                onUpdateCallback.onEvent("update-role");
            });
            roleMenuButton.getItems().add(menuItem);
        }

        for (String role : relatedDepartmentApproverList.getUnavailableRole()) {
            String representation = role + "ภาควิชา" + departmentOfficer.getDepartment().getName();
            if (!role.equals(approver.getRole())) {
                MenuItem menuItem = new MenuItem(representation);
                menuItem.setStyle("-fx-text-fill: grey;");
                roleMenuButton.getItems().add(menuItem);
            }
        }

        // Handle delete button action
        deleteButton.setOnAction(event -> {
            onUpdateCallback.onEvent("delete");
        });

        roleMenuButton.setText(approver.getRepresentativeRole());
    }

    public void selectFirstRole(){
        roleMenuButton.setText(relatedDepartmentApproverList.getAvailableRole().get(0) + "ภาควิชา" + departmentOfficer.getDepartment().getName());
    }

    public void confirm(){
        String wholeName = nameTextField.getText();
        // trim whole name
        wholeName = wholeName.trim();
        String[] name = wholeName.split(" ");
        if (name.length > 0)
        approver.setName(name[0]);
        if (name.length > 1)
        approver.setSurname(name[1]);
        String role = roleMenuButton.getText().split("ภาควิชา")[0];
        approver.setRole(role);
    }

    public boolean isNewlyCreated() {
        return newlyCreated;
    }

    public DepartmentApprover getApprover() {
        return approver;
    }
}