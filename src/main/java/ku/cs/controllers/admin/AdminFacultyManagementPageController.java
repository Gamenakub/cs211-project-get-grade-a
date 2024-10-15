package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.Faculty;
import ku.cs.models.collections.FacultyList;
import ku.cs.models.users.Admin;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import ku.cs.services.popup.PopupComponent;
import java.io.IOException;

public class AdminFacultyManagementPageController {
    @FXML private Pane navBarPane;
    @FXML private Pane tablePane;
    @FXML private TextField searchTextField;
    @FXML private AnchorPane anchorPane;
    private TableComponentController<Faculty> tableController;
    private SearchController<Faculty> searchController;
    private FacultyList facultyList;

    @FXML
    public void initialize() {
        Session session = Session.getSession();
        session.setNavbarByUserRole(navBarPane);
        session.getThemeProvider().setTheme(anchorPane);
        Admin admin = (Admin) session.getLoggedInUser();
        facultyList = admin.getFacultyList();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setTableAttributes(tablePane, 40, 60, 6, new FacultyTableDescriptor());
            tableController.sortBy("รหัสคณะ", SortDirection.ASCENDING);
            tableController.setDisplayModels(facultyList.getFaculties());
            tablePane.getChildren().add(table);
            searchController = new SearchController<>(searchTextField, tableController, admin.getFacultyList());
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
    public void onAddFacultyButton() {
        PopupComponent<Faculty> requestActionPopup = null;
        try {
            requestActionPopup = new PopupComponent<>("/ku/cs/views/admin/admin-faculty-management-popup.fxml", navBarPane.getScene().getWindow());
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
        requestActionPopup.show();
        requestActionPopup.getPopupController().addEventListener(
                "success", eventData -> {
                    tableController.setDisplayModels(facultyList.getFaculties());
                    searchController.searchFilter();
                }
        );
    }
}
