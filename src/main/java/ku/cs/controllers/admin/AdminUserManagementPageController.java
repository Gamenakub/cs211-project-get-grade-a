package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.collections.UserList;
import ku.cs.models.users.Admin;
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


    @FXML private Circle searchButton;
    @FXML private AnchorPane anchorPane;
    Admin admin;

    SearchController searchController;

    @FXML
    public void initialize() {
        Session.getSession().setNavbar(navBarPane);
        Session.getSession().getTheme().setTheme(anchorPane);
        admin = (Admin) Session.getSession().getLoggedInUser();
        UserList userList = admin.getUserList();

        Image searchIcon = new Image(getClass().getResource("/images/search-icon.png").toString());
        searchButton.setFill(new ImagePattern(searchIcon));

        tablePane.getChildren().clear();
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController tableController = fxmlLoader.getController();
            tableController.setHeadHeight(40);
            tableController.setRowHeight(60);
            tableController.setDisplayRowCount(5);

            tableController.setTableHeadDescriptor(new UserTableDescriptor());
            tableController.setTablePane(tablePane);
            tableController.sortBy("วันเวลาที่ใช้งานล่าสุด", SortDirection.DESCENDING);
            tableController.setDisplayModels(userList.getUsers());

            tablePane.getChildren().add(table);

            searchController = new SearchController(searchTextField, tableController, admin.getUserList());

        } catch (IOException e) {
            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(0);
        }
    }

    public void onSearchButtonClick(){
        searchController.searchFilter();
    }

    @FXML
    void onAllRolesMenuItem(){
        filterMenuButton.setText(allRolesMenuItem.getText());
        searchController.resetFilter();
        searchController.searchFilter();
    }

    @FXML
    void onStudentRoleMenuItem(){
        filterMenuButton.setText(studentRoleMenuItem.getText());
        searchController.setFilterContext("student");
        searchController.searchFilter();
    }

    @FXML
    void onAdvisorRoleMenuItem(){
        filterMenuButton.setText(advisorRoleMenuItem.getText());
        searchController.setFilterContext("advisor");
        searchController.searchFilter();
    }

    @FXML
    void onDepartmentOfficerRoleMenuItem(){
        filterMenuButton.setText(departmentOfficerRoleMenuItem.getText());
        searchController.setFilterContext("departmentOfficer");
        searchController.searchFilter();
    }

    @FXML
    void onFacultyOfficerRoleMenuItem(){
        filterMenuButton.setText(facultyOfficerRoleMenuItem.getText());
        searchController.setFilterContext("facultyOfficer");
        searchController.searchFilter();
    }
}
