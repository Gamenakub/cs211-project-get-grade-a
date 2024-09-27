package ku.cs.controllers.officer.faculty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ku.cs.controllers.components.FacultyApproverComponentController;
import ku.cs.controllers.components.navigationbars.FacultyOfficerNavigationBarController;
import ku.cs.models.FacultyApprover;
import ku.cs.models.collections.FacultyApproverList;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.Session;

import java.io.IOException;
import java.util.ArrayList;

public class FacultyOfficerApproverManagementPageController {
    @FXML private AnchorPane anchorPane;
    @FXML
    public MenuButton selectFirstApproverName;
    @FXML
    public MenuButton selectFirstApproverRole;
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
    @FXML
    private Button addApproverButton;

    private FacultyOfficer facultyOfficer;
    private FacultyApproverList relatedFacultyApproverList;
    private final ArrayList<FacultyApprover> awaitDeleteApprover = new ArrayList<>();

    private boolean isAddApproverButtonDisabled = false;
    private ArrayList<FacultyApproverComponentController> facultyApproverComponentControllers = new ArrayList<>();

    @FXML public void initialize() {
        Session session = Session.getSession();

        facultyOfficer = (FacultyOfficer) session.getLoggedInUser();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getChildren().clear();
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/faculty-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            FacultyOfficerNavigationBarController departmentOfficerNavigationBarController = navBarFxmlLoader.getController();
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
            relatedFacultyApproverList = facultyOfficer.getFacultyApproverList().getRelatedToFacultyApproverList(facultyOfficer.getFaculty());
        }

        for (FacultyApprover approver : relatedFacultyApproverList.getApprovers()) {
            approversVBox.getChildren().add(getApproverComponent(approver));
        }

        boolean isNewlyCreatedInList = false;
        for (FacultyApproverComponentController departmentApproverComponentController : facultyApproverComponentControllers) {
            if (departmentApproverComponentController.isNewlyCreated() && !awaitDeleteApprover.contains(departmentApproverComponentController.getApprover())) {
                isNewlyCreatedInList = true;
                break;
            }
        }

        if (isNewlyCreatedInList) {
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
        FacultyApprover approver = new FacultyApprover("","","", facultyOfficer.getFaculty());
        relatedFacultyApproverList.getApprovers().add(approver);
        approversVBox.getChildren().add(getApproverComponent(approver));
        FacultyApproverComponentController departmentApproverComponentController = facultyApproverComponentControllers.getLast();
        departmentApproverComponentController.setNewlyCreated(true);
        reloadPage(false);
    }

    public void onSaveDataButtonClick(ActionEvent actionEvent) {
        for (FacultyApproverComponentController departmentApproverComponentController : facultyApproverComponentControllers) {
            departmentApproverComponentController.confirm();
        }
        FacultyApproverList originalDepartmentApproverList = facultyOfficer.getFacultyApproverList();
        for (FacultyApprover approver : awaitDeleteApprover) {
            originalDepartmentApproverList.getApprovers().remove(approver);
        }
        for (FacultyApprover approver : relatedFacultyApproverList.getApprovers()){
            if (!originalDepartmentApproverList.getApprovers().contains(approver)){
                originalDepartmentApproverList.getApprovers().add(approver);
            }
        }
        facultyApproverComponentControllers.clear();
        awaitDeleteApprover.clear();
        reloadPage(true);
    }

    public void reloadAll(){
        for (FacultyApproverComponentController departmentApproverComponentController : facultyApproverComponentControllers) {
            departmentApproverComponentController.reload();
        }
    }

    public HBox getApproverComponent(FacultyApprover approver) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/faculty-approver-component.fxml"));
        HBox hBox = null;
        try {
            hBox = fxmlLoader.load();
            FacultyApproverComponentController facultyApproverComponentController = fxmlLoader.getController();
            facultyApproverComponentController.setNewlyCreated(false);
            facultyApproverComponentController.initialize(relatedFacultyApproverList, approver, facultyOfficer);
            HBox finalHBox = hBox;
            facultyApproverComponentController.onUpdate((eventName) -> {
                if (eventName.equals("delete")) {
                    relatedFacultyApproverList.getApprovers().remove(approver);
                    awaitDeleteApprover.add(approver);
                    approversVBox.getChildren().remove(finalHBox);
                    reloadPage(false);
                }
                else if (eventName.equals("update-role")) {
                    //reloadAll();
                }
            });
            facultyApproverComponentControllers.add(facultyApproverComponentController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hBox;
    }
}
