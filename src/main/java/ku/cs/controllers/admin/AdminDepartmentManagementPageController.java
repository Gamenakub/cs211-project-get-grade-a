package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.Department;
import ku.cs.models.collections.DepartmentList;
import ku.cs.models.users.Admin;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

public class AdminDepartmentManagementPageController {
    @FXML private Pane navBarPane;
    @FXML private Pane tablePane;
    @FXML private TextField searchTextField;
    @FXML private AnchorPane anchorPane;
    private TableComponentController<Department> tableController;
    private SearchController<Department> searchController;
    private DepartmentList departmentList;

    @FXML
    public void initialize() {
        Session session = Session.getSession();
        session.setNavbarByUserRole(navBarPane);
        session.getThemeProvider().setTheme(anchorPane);
        Admin admin = (Admin) session.getLoggedInUser();

        departmentList = admin.getDepartmentList();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setTableAttributes(tablePane, 40, 60, 6, new DepartmentTableDescriptor());
            tableController.sortBy("รหัสของภาควิชา", SortDirection.ASCENDING);
            tableController.setDisplayModels(departmentList.getDepartments());
            tablePane.getChildren().add(table);
            searchController = new SearchController<>(searchTextField, tableController, admin.getDepartmentList());
        } catch (IOException e) {
            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }

    @FXML
    public void onSearchButton() {
        searchController.searchFilter();
    }

    @FXML
    public void onAddDepartmentButton() {
        PopupComponent<Department> requestActionPopup = null;
        try {
            requestActionPopup = new PopupComponent<>("/ku/cs/views/admin/admin-department-management-popup.fxml", navBarPane.getScene().getWindow());
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
        requestActionPopup.show();
        requestActionPopup.getPopupController().addEventListener(
                "success", eventData -> {
                    tableController.setDisplayModels(departmentList.getDepartments());
                    searchController.searchFilter();
                }
        );
    }
}
