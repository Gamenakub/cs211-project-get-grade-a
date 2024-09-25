package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.services.RequestFormToPDF;
import ku.cs.services.Session;

import java.io.IOException;

import static ku.cs.services.AlertService.*;

public class PreviewPDFController extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML
    private AnchorPane pdf;

    private RequestForm requestForm;
    private String pathFXML;

    @Override
    public void onPopupOpen() {
        requestForm = getModel().getFormObject();
        if(requestForm instanceof AddDropRequestForm) {
            pathFXML = "/ku/cs/views/request-forms/add-drop-form.fxml";
        } else if(requestForm instanceof AbsenceRequestForm) {
            pathFXML = "/ku/cs/views/request-forms/absence-form.fxml";
        } else if(requestForm instanceof CoEnrollRequestForm) {
            pathFXML = "/ku/cs/views/request-forms/co-enroll-form.fxml";
        } else {
            System.out.println("RequestForm not implemented yet");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathFXML));
        AnchorPane loadedPane;
        try {
            loadedPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Scale the loadedPane
        loadedPane.setScaleX(0.48367593712);
        loadedPane.setScaleY(0.48367593712);

        // Create a StackPane to center the loadedPane
        StackPane wrapperPane = new StackPane();
        wrapperPane.setAlignment(Pos.CENTER);  // Center content in StackPane
        wrapperPane.getChildren().add(loadedPane);

        // Set the StackPane to fill the AnchorPane
        AnchorPane.setTopAnchor(wrapperPane, 0.0);
        AnchorPane.setBottomAnchor(wrapperPane, 0.0);
        AnchorPane.setLeftAnchor(wrapperPane, 0.0);
        AnchorPane.setRightAnchor(wrapperPane, 0.0);

        // Add the StackPane to the pdf AnchorPane
        pdf.getChildren().setAll(wrapperPane);
    }

    @FXML
    public void initialize() {
        Session.getSession().getTheme().setTheme(anchorPane);
    }

    @FXML
    public void onSaveButtonClicked() {
        try {
            String pdfPath = "data/pdf/" + requestForm.getRequestFormId() + ".pdf";
            RequestFormToPDF.nodeToPDF(this.pdf, pdfPath);
            if (showInfo("ข้อมูลบันทึกเรียบร้อย \nเก็บไฟล์ PDF ที่โฟลเดอร์ \"data/pdf/" + requestForm.getRequestFormId() + "\"")) {this.close();}
        } catch (IOException e) {
            showError("ไม่มีโฟลเดอร์ \"data/pdf/\" \nไม่สามารถบันทึกข้อมูลได้");
        }
    }

    @FXML
    public void onCancelButtonClicked() {
        if (showConfirmation("ท่านไม่ต้องการบันทึกใบคำร้องเป็นไฟล์ PDF ใช่หรือไม่")) {this.close();}
    }
}
