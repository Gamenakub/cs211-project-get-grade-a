package ku.cs.controllers.officer.department;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.controllers.requestforms.RequestFormsTableDescriptor;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;
import java.util.Objects;

public class DepartmentOfficerRequestManagementPageController {
    SearchController<RequestForm> searchController;
    @FXML private TextField searchTextField;
    @FXML private Pane tablePane;
    @FXML private Pane navBarPane;
    @FXML private AnchorPane anchorPane;
    private DepartmentOfficer departmentOfficer;
    private TableComponentController<RequestForm> tableController;

    @FXML
    public void initialize() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        Session session = Session.getSession();
        Session.getSession().setNavbarByUserRole(navBarPane);
        departmentOfficer = (DepartmentOfficer) session.getLoggedInUser();
        navBarPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ku/cs/views/styles/main-style.css")).toString());
        anchorPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ku/cs/views/styles/main-style.css")).toString());
        tablePane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ku/cs/views/styles/main-style.css")).toString());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setTableAttributes(tablePane, 40, 60, 6, new RequestFormsTableDescriptor());
            tableController.addEventListener("กดดำเนินการ", eventData -> {
                RequestForm obj = (RequestForm) eventData;
                FormDataModel formDataModel = new FormDataModel(true, obj);
                PopupComponent<FormDataModel> requestActionPopup = null;
                try {
                    requestActionPopup = new PopupComponent<>(formDataModel,
                            "/ku/cs/views/officer/officer-request-action-popup.fxml",
                            tablePane.getScene().getWindow()
                    );
                } catch (IOException e) {
                    AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
                    System.exit(1);
                }
                requestActionPopup.addEventListener("close", eventData1 -> {
                    tableController.setDisplayModels(departmentOfficer.getRequestFormList().getRequestForms());
                });
                requestActionPopup.show();
            });

            tableController.setDisplayModels(departmentOfficer.getRequestFormList().getRequestForms());
            tableController.updateTable();
            searchController = new SearchController<>(searchTextField, tableController, departmentOfficer.getRequestFormList());

            tablePane.getChildren().add(table);
        } catch (IOException e) {
            AlertService.showError("เกิดข้อผิดพลาดในการดึงข่้อมูลใบคำร้องใหม่");
        }

        tableController.updateTable();
        tableController.sortBy("แก้ไขล่าสุด", SortDirection.DESCENDING);
    }

    public void onSearchButton() {
        searchController.searchFilter();
    }

}
