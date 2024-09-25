package ku.cs.controllers.officer.department;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ku.cs.controllers.components.DepartmentApproverComponentController;
import ku.cs.controllers.components.navigationbars.DepartmentOfficerNavigationBarController;
import ku.cs.models.DepartmentApprover;
import ku.cs.models.collections.DepartmentApproverList;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.Session;

import java.io.IOException;
import java.util.ArrayList;

public class DepartmentOfficerApproverManagementPageController {
    @FXML private Button addApproverButton;
    @FXML private AnchorPane anchorPane;
    @FXML
    public MenuButton selectFirstApproverName;
    @FXML
    public MenuButton selectFirstApproverRole;
    @FXML
    public Button deleteFirstApprover;
    @FXML
    public MenuButton selectSecondApproverName;
    @FXML
    public MenuButton selectSecondApproverRole;
    @FXML
    public Button deleteSecondApprover;
    @FXML
    public MenuButton selectThirdApproverName;
    @FXML
    public MenuButton selectThirdApproverRole;
    @FXML
    public Button deleteThirdApprover;
    @FXML
    private Pane navBarPane;
    @FXML
    private VBox approversVBox;
    private DepartmentOfficer departmentOfficer;
    private DepartmentApproverList relatedDepartmentApproverList;
    private final ArrayList<DepartmentApprover> awaitDeleteApprover = new ArrayList<>();

    private boolean isAddApproverButtonDisabled = false;
    private ArrayList<DepartmentApproverComponentController> departmentApproverComponentControllers = new ArrayList<>();

    @FXML public void initialize() {
        Session session = Session.getSession();

        departmentOfficer = (DepartmentOfficer) session.getLoggedInUser();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getChildren().clear();
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            DepartmentOfficerNavigationBarController departmentOfficerNavigationBarController = navBarFxmlLoader.getController();
            departmentOfficerNavigationBarController.onChangePage(eventData -> {
                reloadPage(true);
            });
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reloadPage(true);
    }

    public void reloadPage(boolean resetData){
        approversVBox.getChildren().clear();
        if (resetData) {
            relatedDepartmentApproverList = departmentOfficer.getDepartmentApproverList().getRelatedToDepartmentApproverList(departmentOfficer.getDepartment());
        }

        for (DepartmentApprover approver : relatedDepartmentApproverList.getApprovers()) {
            approversVBox.getChildren().add(getApproverComponent(approver));
        }

        boolean isNewlyCreatedInList = false;
        for (DepartmentApproverComponentController departmentApproverComponentController : departmentApproverComponentControllers) {
            if (departmentApproverComponentController.isNewlyCreated() && !awaitDeleteApprover.contains(departmentApproverComponentController.getApprover())) {
                isNewlyCreatedInList = true;
                break;
            }
        }

        if (relatedDepartmentApproverList.getAvailableRole().size() == 0 || isNewlyCreatedInList) {
            // disable add approver button
            addApproverButton.setDisable(true);
            isAddApproverButtonDisabled = true;
        }
        else{
            addApproverButton.setDisable(false);
            isAddApproverButtonDisabled = false;
        }
        reloadAll();
    }

    public void onAddApproverButtonClick(ActionEvent actionEvent) {
        if (isAddApproverButtonDisabled) {
            return;
        }
        String firstAvailableRole = relatedDepartmentApproverList.getAvailableRole().get(0);
        DepartmentApprover approver = new DepartmentApprover("","",firstAvailableRole,departmentOfficer.getDepartment());
        relatedDepartmentApproverList.getApprovers().add(approver);
        approversVBox.getChildren().add(getApproverComponent(approver));
        DepartmentApproverComponentController departmentApproverComponentController = departmentApproverComponentControllers.getLast();
        departmentApproverComponentController.setNewlyCreated(true);
        reloadPage(false);
    }

    public void onSaveDataButtonClick(ActionEvent actionEvent) {
        for (DepartmentApproverComponentController departmentApproverComponentController : departmentApproverComponentControllers) {
            departmentApproverComponentController.confirm();
        }
        DepartmentApproverList originalDepartmentApproverList = departmentOfficer.getDepartmentApproverList();
        for (DepartmentApprover approver : awaitDeleteApprover) {
            originalDepartmentApproverList.getApprovers().remove(approver);
        }
        for (DepartmentApprover approver : relatedDepartmentApproverList.getApprovers()){
            if (!originalDepartmentApproverList.getApprovers().contains(approver)){
                originalDepartmentApproverList.getApprovers().add(approver);
            }
        }
        departmentApproverComponentControllers.clear();
        awaitDeleteApprover.clear();
        reloadPage(true);
    }

    public void reloadAll(){
        for (DepartmentApproverComponentController departmentApproverComponentController : departmentApproverComponentControllers) {
            departmentApproverComponentController.reload();
        }
    }

    public HBox getApproverComponent(DepartmentApprover approver) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-approver-component.fxml"));
        HBox hBox = null;
        try {
            hBox = fxmlLoader.load();
            DepartmentApproverComponentController departmentApproverComponentController = fxmlLoader.getController();
            departmentApproverComponentController.setNewlyCreated(false);
            departmentApproverComponentController.initialize(relatedDepartmentApproverList, approver, departmentOfficer);
            HBox finalHBox = hBox;
            departmentApproverComponentController.onUpdate((eventName) -> {
                if (eventName.equals("delete")) {
                    relatedDepartmentApproverList.getApprovers().remove(approver);
                    awaitDeleteApprover.add(approver);
                    approversVBox.getChildren().remove(finalHBox);
                    reloadPage(false);
                }
                else if (eventName.equals("update-role")) {
                    //reloadAll();
                }
            });
            departmentApproverComponentControllers.add(departmentApproverComponentController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hBox;
    }
}
