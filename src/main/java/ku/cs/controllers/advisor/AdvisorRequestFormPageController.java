package ku.cs.controllers.advisor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.SearchController;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.controllers.officer.RequestFormsTableDescriptor;
import ku.cs.models.FormDataModel;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

public class AdvisorRequestFormPageController {

    @FXML private Pane tablePane;
    @FXML private Pane navBarPane;
    @FXML private TextField searchTextField;
    @FXML private AnchorPane anchorPane;

    private Advisor user;
    private SearchController<RequestForm> searchController;
    private TableComponentController<RequestForm> tableController;

    @FXML
    public void initialize() {
        user = (Advisor) Session.getSession().getLoggedInUser();
        Session session = Session.getSession();
        session.setNavbarByUserRole(navBarPane);
        session.getThemeProvider().setTheme(anchorPane);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));

        try {
            AnchorPane table = fxmlLoader.load();
            tableController = fxmlLoader.getController();
            tableController.setTableAttributes(tablePane, 40, 60, 6, new RequestFormsTableDescriptor());
            tableController.sortBy("แก้ไขล่าสุด", SortDirection.DESCENDING);
            tableController.addEventListener("กดดำเนินการ", eventData -> {
                RequestForm obj = (RequestForm) eventData;
                FormDataModel formDataModel = new FormDataModel(true, obj);

                PopupComponent<FormDataModel> requestActionPopup = null;
                try {
                    requestActionPopup = new PopupComponent<>(formDataModel,
                            "/ku/cs/views/advisor/advisor-request-form-operation-popup.fxml",
                            tablePane.getScene().getWindow()
                    );
                } catch (IOException e) {
                    AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
                    System.exit(1);
                }
                requestActionPopup.show();
                requestActionPopup.addEventListener("close", eventData1 -> {
                    tableController.setDisplayModels(user.getRequestFormList().getRequestForms());
                });
            });

            RequestFormList requestFormList = user.getRequestFormList();
            tableController.setDisplayModels(requestFormList.getRequestForms());
            tablePane.getChildren().add(table);
        } catch (IOException e) {
            AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }

        searchController = new SearchController<>(searchTextField, tableController, user.getRequestFormList());
    }

    @FXML
    public void onSearchButton() {
        searchController.searchFilter();
    }
}

