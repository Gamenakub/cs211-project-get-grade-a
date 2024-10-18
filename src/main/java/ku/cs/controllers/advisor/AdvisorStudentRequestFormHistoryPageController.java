package ku.cs.controllers.advisor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.controllers.requestforms.StudentRequestFormHistoryTableDescriptor;
import ku.cs.models.FormDataModel;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.AlertService;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

public class AdvisorStudentRequestFormHistoryPageController {
    @FXML private Pane tablePane;
    @FXML private Pane navBarPane;
    @FXML private AnchorPane anchorPane;
    @FXML private MenuButton filterMenuButton;
    @FXML private Label studentLabel;
    @FXML private TextField searchTextField;

    private SearchController<RequestForm> searchController;
    private TableComponentController<RequestForm> tableController;

    @FXML
    public void initialize() {
        Session session = Session.getSession();
        Student student = (Student) FXRouter.getData();
        session.setNavbarByUserRole(navBarPane);
        session.getThemeProvider().setTheme(anchorPane);
        studentLabel.setText(student.getName() + " " + student.getSurname());

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

                requestActionPopup.addEventListener("close", eventData1 -> {
                    tableController.setDisplayModels(student.getRequestFormList().getRequestForms());
                });
            });

            RequestFormList requestFormList = student.getRequestFormList();
            tableController.setDisplayModels(requestFormList.getRequestForms());
            tablePane.getChildren().add(table);

        } catch (IOException e) {
            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }

        searchController = new SearchController<>(searchTextField, tableController, student.getRequestFormList());
    }

    @FXML
    public void onAllFilterMenuButton() {
        filterMenuButton.setText("ทั้งหมด");
        searchController.resetFilter();
    }

    @FXML
    public void onSuccessFormMenuItem() {
        filterMenuButton.setText("สำเร็จแล้ว");
        searchController.setFilterContext("APPROVED");
        searchController.searchFilter();
    }

    @FXML
    public void onInProgressFormMenuItem() {
        filterMenuButton.setText("กำลังดำเนินการ");
        searchController.setFilterContext("PENDING");
        searchController.searchFilter();
    }

    @FXML
    public void onRejectedFormMenuItem() {
        filterMenuButton.setText("ปฏิเสธ");
        searchController.setFilterContext("REJECTED");
        searchController.searchFilter();
    }

    @FXML
    public void onSearchButton() {
        searchController.searchFilter();
    }

    @FXML
    public void onBackButton() {
        try {
            FXRouter.goTo("advisor-student-information");
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }


}