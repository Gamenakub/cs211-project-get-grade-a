package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.collections.DepartmentOfficerList;
import ku.cs.models.users.Admin;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

public class AdminDepartmentOfficerManagementPageController {
    @FXML private Pane navBarPane;
    @FXML private Pane tablePane;
    @FXML private TextField searchTextField;
    @FXML private Circle searchButton;
    @FXML private AnchorPane anchorPane;
    private Admin admin;
    DepartmentOfficerList departmentOfficerList;
    TableComponentController<DepartmentOfficer> tableController;

    SearchController searchController;

    @FXML
    public void initialize() {
        Session.getSession().setNavbar(navBarPane);
        Session.getSession().getTheme().setTheme(anchorPane);
        admin = (Admin) Session.getSession().getLoggedInUser();

        departmentOfficerList = admin.getDepartmentOfficerList();

        Image searchIcon = new Image(getClass().getResource("/images/search-icon.png").toString(), false);
        searchButton.setFill(new ImagePattern(searchIcon));

        tablePane.getChildren().clear();
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setHeadHeight(40);
            tableController.setRowHeight(60);
            tableController.setDisplayRowCount(5);

            tableController.setTableHeadDescriptor(new DepartmentOfficerTableDescriptor());
            tableController.setTablePane(tablePane);
            tableController.sortBy("ภาควิชาที่สังกัด", SortDirection.ASCENDING);
            tableController.setDisplayModels(departmentOfficerList.getDepartmentOfficers());

            tablePane.getChildren().add(table);
            searchController = new SearchController(searchTextField, tableController, admin.getDepartmentOfficerList());

        } catch (IOException e) {
            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(0);
        }
    }

    public void onSearchButtonClick(){
        searchController.searchFilter();
    }

    @FXML
    public void onAddDepartmentStaffButton() {
        PopupComponent<DepartmentOfficer> requestActionPopup = new PopupComponent<>(null, "/ku/cs/views/admin/admin-department-officer-management-popup.fxml","admin-department-officer-management-popup",navBarPane.getScene().getWindow());
        requestActionPopup.show();
        requestActionPopup.getPopupController().addEventListener(
                "success", new EventCallback() {
                    @Override
                    public void onEvent(Object eventData) {
                        try {
                            tableController.setDisplayModels(departmentOfficerList.getDepartmentOfficers());
                        } catch (IOException e) {
                            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
                            System.exit(0);
                        }
                        searchController.searchFilter();
                    }
                }
        );
        searchController.searchFilter();
    }
}
