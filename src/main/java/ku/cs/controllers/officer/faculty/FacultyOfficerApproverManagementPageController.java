package ku.cs.controllers.officer.faculty;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ku.cs.controllers.components.FacultyApproverComponentController;
import ku.cs.models.FacultyApprover;
import ku.cs.models.collections.FacultyApproverList;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import java.io.IOException;
import java.util.ArrayList;

public class FacultyOfficerApproverManagementPageController {
    private final ArrayList<FacultyApprover> newFacultyApprover = new ArrayList<>();
    private final ArrayList<FacultyApproverComponentController> facultyApproverComponentControllers = new ArrayList<>();
    private final ArrayList<FacultyApprover> awaitDeleteApprover  = new ArrayList<>();
    @FXML private AnchorPane anchorPane;
    @FXML private Pane navBarPane;
    @FXML private VBox approversVBox;
    @FXML private Button addApproverButton;
    private FacultyOfficer facultyOfficer;
    private FacultyApproverList relatedFacultyApproverList;

    @FXML
    public void initialize() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        Session session = Session.getSession();
        Session.getSession().setNavbarByUserRole(navBarPane);

        facultyOfficer = (FacultyOfficer) session.getLoggedInUser();
        reloadPage();
    }

    public void reloadPage() {
        approversVBox.getChildren().clear();
        facultyApproverComponentControllers.clear();
        newFacultyApprover.clear();
        addApproverButton.setDisable(false);
        relatedFacultyApproverList = facultyOfficer.getFacultyApproverList().getRelatedToFacultyApproverList(facultyOfficer.getFaculty());
        for (FacultyApprover approver : relatedFacultyApproverList.getApprovers()) {
            VBox vBox = getApproverComponent(approver);
            approversVBox.getChildren().add(vBox);
        }
    }

    public void onAddApproverButton() {

        FacultyApprover newApprover = new FacultyApprover(
                "",
                "",
                "",
                "",
                facultyOfficer.getFaculty()
        );
        newFacultyApprover.add(newApprover);
        relatedFacultyApproverList.addApprover(newApprover);
        VBox vBox = getApproverComponent(newApprover);
        approversVBox.getChildren().add(vBox);
    }

    public void onSaveDataButton() {
        ArrayList<String> allRole = new ArrayList<>();
        try {
            for (FacultyApproverComponentController facultyApproverComponentController : facultyApproverComponentControllers) {
                facultyApproverComponentController.confirm();


                String currentRole = facultyApproverComponentController.getApprover().getRole();
                if (allRole.contains(currentRole)) {
                    AlertService.showError("มีบทบาทที่ซ้ำกัน การบันทึกข้อมูลล้มเหลว");
                    return;
                }
                allRole.add(currentRole);
            }
            if (allRole.contains("รักษาการณ์แทนคณบดี") && allRole.contains("คณบดี")) {
                AlertService.showError("หากมีคณบดีอยู่แล้ว จะไม่สามารถมีรักษาการณ์แทนคณบดีได้");
                return;
            }
        } catch (IOException e) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
            return;
        }

        for (FacultyApprover approver : awaitDeleteApprover) {
            facultyOfficer.getFacultyApproverList().removeApprover(approver);
        }

        for (FacultyApprover approver : newFacultyApprover) {
            facultyOfficer.getFacultyApproverList().addApprover(approver);
        }

        reloadPage();
        AlertService.showInfo("บันทึกข้อมูลสำเร็จ");

    }

    public VBox getApproverComponent(FacultyApprover approver) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/faculty-approver-component.fxml"));
        VBox vBox = null;
        try {
            vBox = fxmlLoader.load();
            FacultyApproverComponentController facultyApproverComponentController = fxmlLoader.getController();
            facultyApproverComponentController.setFacultyApprover(approver);
            VBox finalVBox = vBox;
            facultyApproverComponentController.onUpdate((eventName) -> {
                if (eventName.equals("delete")) {

                    newFacultyApprover.remove(approver);
                    awaitDeleteApprover.add(approver);
                    approversVBox.getChildren().remove(finalVBox);
                    facultyApproverComponentControllers.remove(facultyApproverComponentController);
                }
            });
            facultyApproverComponentControllers.add(facultyApproverComponentController);
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
        return vBox;
    }
}
