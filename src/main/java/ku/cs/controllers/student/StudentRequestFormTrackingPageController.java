package ku.cs.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.advisor.StudentRequestFormHistoryTableDescriptor;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

import static ku.cs.services.AlertService.showError;

public class StudentRequestFormTrackingPageController {
    @FXML private AnchorPane anchorPane;
    @FXML private Pane tablePane;
    @FXML private Pane navBarPane;
    @FXML private TextField searchTextField;
    @FXML private MenuButton statusMenuButton;
    private TableComponentController<RequestForm> tableController;
    private SearchController<RequestForm> searchController;
    private Student student;

    @FXML
    public void initialize() {
        Session session = Session.getSession();
        student = (Student) session.getLoggedInUser();

        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());

        Session.getSession().setNavbarByUserRole(navBarPane);
        Session.getSession().getThemeProvider().setTheme(anchorPane);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setTableAttributes(tablePane, 40, 60, 6, new StudentRequestFormHistoryTableDescriptor());
            tableController.sortBy("แก้ไขล่าสุด", SortDirection.DESCENDING);

            tableController.addEventListener("ดูคำร้อง", eventData -> {
                RequestForm obj = (RequestForm) eventData;
                FormDataModel formDataModel = new FormDataModel(true, obj);

                String fxmlPath = "/ku/cs/views/request-forms/request-form-history.fxml";
                PopupComponent<FormDataModel> requestActionPopup = null;
                try {
                    requestActionPopup = new PopupComponent<>(formDataModel,
                            fxmlPath,
                            tablePane.getScene().getWindow()
                    );
                } catch (IOException e) {
                    AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
                    System.exit(1);
                }
                requestActionPopup.show();
            });
            tableController.setDisplayModels(student.getRequestFormList().getRequestForms());
            tableController.updateTable();

            tablePane.getChildren().add(table);
        } catch (IOException e) {
            showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
        }
        searchController = new SearchController<>(searchTextField, tableController, student.getRequestFormList());
    }

    @FXML
    public void onCreateFormButton() {
        PopupComponent<Object> popup = null;
        try {
            popup = new PopupComponent<>("/ku/cs/views/request-forms/create-request-form-popup.fxml", navBarPane.getScene().getWindow());
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
        popup.addEventListener("close", eventData1 -> {
            tableController.setDisplayModels(student.getRequestFormList().getRequestForms());
        });
        popup.show();
    }

    @FXML
    public void onSearchButton() {
        searchController.searchFilter();
    }

    @FXML
    public void onPendingMenuItem() {
        statusMenuButton.setText("รอการอนุมัติ");
        searchController.setFilterContext("PENDING");
        searchController.searchFilter();
    }

    @FXML
    public void onApprovedMenuItem() {
        statusMenuButton.setText("ได้รับการอนุมัติ");
        searchController.setFilterContext("APPROVED");
        searchController.searchFilter();
    }

    @FXML
    public void onRejectedMenuItem() {
        statusMenuButton.setText("ได้รับการปฏิเสธ");
        searchController.setFilterContext("REJECTED");
        searchController.searchFilter();
    }

    @FXML
    public void onAllStatusMenuItem() {
        statusMenuButton.setText("สถานะคำร้อง");
        searchController.resetFilter();
    }
}
