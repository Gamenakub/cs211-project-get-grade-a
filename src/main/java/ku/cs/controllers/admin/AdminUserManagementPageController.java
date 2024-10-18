package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.collections.UserList;
import ku.cs.models.users.Admin;
import ku.cs.models.users.User;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import java.io.IOException;

public class AdminUserManagementPageController {
    @FXML private Pane navBarPane;
    @FXML private Pane tablePane;
    @FXML private TextField searchTextField;
    @FXML private MenuButton filterMenuButton;
    @FXML private MenuItem allRolesMenuItem;
    @FXML private MenuItem studentRoleMenuItem;
    @FXML private MenuItem advisorRoleMenuItem;
    @FXML private MenuItem departmentOfficerRoleMenuItem;
    @FXML private MenuItem facultyOfficerRoleMenuItem;
    @FXML private AnchorPane anchorPane;
    private SearchController<User> searchController;

    @FXML
    public void initialize() {
        Session session = Session.getSession();
        session.setNavbarByUserRole(navBarPane);
        session.getThemeProvider().setTheme(anchorPane);
        Admin admin = (Admin) session.getLoggedInUser();
        UserList userList = admin.getUserList();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController<User> tableController = fxmlLoader.getController();
            tableController.setTableAttributes(tablePane, 40, 60, 6, new UserTableDescriptor());
            tableController.sortBy("วันเวลาที่ใช้งานล่าสุด", SortDirection.DESCENDING);
            tableController.setDisplayModels(userList.getUsers());
            tablePane.getChildren().add(table);
            searchController = new SearchController<>(searchTextField, tableController, admin.getUserList());
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
    public void onAllRolesMenuItem() {
        filterMenuButton.setText(allRolesMenuItem.getText());
        searchController.resetFilter();
        searchController.searchFilter();
    }

    @FXML
    public void onStudentRoleMenuItem() {
        filterMenuButton.setText(studentRoleMenuItem.getText());
        searchController.setFilterContext("student");
        searchController.searchFilter();
    }

    @FXML
    public void onAdvisorRoleMenuItem() {
        filterMenuButton.setText(advisorRoleMenuItem.getText());
        searchController.setFilterContext("advisor");
        searchController.searchFilter();
    }

    @FXML
    public void onDepartmentOfficerRoleMenuItem() {
        filterMenuButton.setText(departmentOfficerRoleMenuItem.getText());
        searchController.setFilterContext("departmentOfficer");
        searchController.searchFilter();
    }

    @FXML
    public void onFacultyOfficerRoleMenuItem() {
        filterMenuButton.setText(facultyOfficerRoleMenuItem.getText());
        searchController.setFilterContext("facultyOfficer");
        searchController.searchFilter();
    }
}
