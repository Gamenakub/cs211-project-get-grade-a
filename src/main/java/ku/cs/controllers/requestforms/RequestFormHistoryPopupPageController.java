package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.FormDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import ku.cs.services.SortDirection;

import java.io.IOException;

public class RequestFormHistoryPopupPageController extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private Pane tablePane;
    @FXML private Button downloadButton;
    private RequestForm requestForm;

    @Override
    public void onPopupOpen() {
        requestForm = getModel().getFormObject();
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        downloadButton.setDisable(requestForm.getStatus() == RequestForm.Status.PENDING_TO_ADVISOR || requestForm.getStatus() == RequestForm.Status.REJECTED_BY_ADVISOR);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController<RequestFormActionHistory> tableController = fxmlLoader.getController();
            tableController.setTableAttributes(tablePane, 40, 60, 5, new RequestFormHistoryTableDescriptor());
            tableController.sortBy("เวลา", SortDirection.ASCENDING);
            tableController.setDisplayModels(requestForm.getRequestFormActionHistoryList().getRequestFormApprovingHistories());
            tableController.updateTable();

            tablePane.getChildren().add(table);
        } catch (
                IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }

    @FXML
    public void onViewDetailButton() {
        String fxmlPath = "/ku/cs/views/request-forms/";
        if (requestForm instanceof AddDropRequestForm) {
            fxmlPath = fxmlPath + "add-drop-request-form-popup-page1.fxml";
        } else if (requestForm instanceof AbsenceRequestForm) {
            fxmlPath = fxmlPath + "absence-request-form-popup-page1.fxml";
        } else if (requestForm instanceof CoEnrollRequestForm) {
            fxmlPath = fxmlPath + "co-enroll-request-form-popup-page1.fxml";
        }
        changeScene(getModel(), fxmlPath);
    }

    public void onDownloadButton() {
        changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml");
    }
}
