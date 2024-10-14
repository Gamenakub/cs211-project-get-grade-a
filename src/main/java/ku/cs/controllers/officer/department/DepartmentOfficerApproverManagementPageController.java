package ku.cs.controllers.officer.department;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ku.cs.controllers.components.DepartmentApproverComponentController;
import ku.cs.models.DepartmentApprover;
import ku.cs.models.collections.DepartmentApproverList;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.Session;

import java.io.IOException;
import java.util.ArrayList;

public class DepartmentOfficerApproverManagementPageController {
    private final DepartmentApproverList awaitDeleteApprover = new DepartmentApproverList();
    private final ArrayList<DepartmentApproverComponentController> departmentApproverComponentControllers = new ArrayList<>();
    private final DepartmentApproverList newDepartmentApprover = new DepartmentApproverList();
    @FXML private Button addApproverButton;
    @FXML private AnchorPane anchorPane;
    @FXML private Pane navBarPane;
    @FXML private VBox approversVBox;
    private DepartmentOfficer departmentOfficer;
    private DepartmentApproverList relatedDepartmentApproverList;
    private DepartmentApproverList pageDepartmentApproverList;

    @FXML
    public void initialize() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        Session.getSession().setNavbarByUserRole(navBarPane);
        Session session = Session.getSession();

        departmentOfficer = (DepartmentOfficer) session.getLoggedInUser();
        pageDepartmentApproverList = departmentOfficer.getDepartmentApproverList().clone();
        
        navBarPane.getChildren().clear();
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            AlertService.showError("เกิดข้อผิดพลาดในขณะอ่านไฟล์โปรแกรม กรุณาตรวจสอบความสมบูรณ์ของไฟล์โปรแกรมของท่าน");
        }
        reloadPage();
    }

    public void reloadPage() {
        approversVBox.getChildren().clear();
        relatedDepartmentApproverList = pageDepartmentApproverList.getRelatedToDepartmentApproverList(departmentOfficer.getDepartment());
        approversVBox.getChildren().clear();
        departmentApproverComponentControllers.clear();
        newDepartmentApprover.clear();

        for (DepartmentApprover approver : relatedDepartmentApproverList.getApprovers()) {
            HBox departmentAproverComponentHBox = getApproverComponent(approver);
            approversVBox.getChildren().add(departmentAproverComponentHBox);
        }

        addApproverButton.setDisable(relatedDepartmentApproverList.getAvailableRole().isEmpty());
    }

    public void onAddApproverButton() {
        addApproverButton.setDisable(true);
        DepartmentApprover newApprover = new DepartmentApprover(
                "",
                "",
                "",
                "",
                departmentOfficer.getDepartment()
        );
        pageDepartmentApproverList.addApprover(newApprover);
        reloadPage();
    }

    public void onSaveDataButton() {
        try {
            for (DepartmentApproverComponentController departmentApproverComponentController : departmentApproverComponentControllers) {
                departmentApproverComponentController.confirmChecked();
            }
        } catch (IOException e) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
            return;
        }

        for (DepartmentApprover original : departmentOfficer.getDepartmentApproverList().getRelatedToDepartmentApproverList(departmentOfficer.getDepartment()).getApprovers()) {
            departmentOfficer.getDepartmentApproverList().removeApprover(original);
        }

        for (DepartmentApprover newApprover : pageDepartmentApproverList.getApprovers()) {
            departmentOfficer.getDepartmentApproverList().addApprover(newApprover);
        }

        reloadPage();
        AlertService.showInfo("บันทึกข้อมูลสำเร็จ");

    }


    public HBox getApproverComponent(DepartmentApprover approver) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-approver-component.fxml"));
        HBox departmentApproverComponentHBox = null;

        try {
            departmentApproverComponentHBox = fxmlLoader.load();
            DepartmentApproverComponentController departmentApproverComponentController = fxmlLoader.getController();
            departmentApproverComponentController.initializeDepartmentApproverComponent(relatedDepartmentApproverList, awaitDeleteApprover, approver, departmentOfficer.getDepartment().getName());
            departmentApproverComponentController.onUpdate((eventName) -> {
                if (eventName.equals("delete")) {
                    pageDepartmentApproverList.removeApprover(approver);
                    reloadPage();
                } else if (eventName.equals("update-role")) {
                    departmentApproverComponentController.confirm();
                    reloadPage();
                }
            });
            departmentApproverComponentControllers.add(departmentApproverComponentController);
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
        return departmentApproverComponentHBox;
    }
}
