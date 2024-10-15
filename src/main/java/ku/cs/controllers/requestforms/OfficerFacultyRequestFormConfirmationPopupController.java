package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import ku.cs.config.Configuration;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FacultyApprover;
import ku.cs.models.FormDataModel;
import ku.cs.models.OfficerConfirmationPopupDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.FileUploader;
import ku.cs.services.Session;
import java.io.File;
import java.io.IOException;

public class OfficerFacultyRequestFormConfirmationPopupController extends BasePopup<OfficerConfirmationPopupDataModel> {
    @FXML private ChoiceBox<String> selectFacultyApprover;
    @FXML private AnchorPane anchorPane;
    @FXML private Label documentUploadLabel;
    private FileUploader fileUploader;
    private FacultyOfficer officer;
    private FacultyApprover selectedApprover;

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        fileUploader = new FileUploader(Configuration.getConfiguration().getRequestFormsPdfPath());

        officer = (FacultyOfficer) Session.getSession().getLoggedInUser();
        for (FacultyApprover approver : officer.getFacultyApproverList().getApprovers()) {
            if (approver.getFaculty().equals(officer.getFaculty())) {
                selectFacultyApprover.getItems().add(approver.getFullRepresentationString());
            }
        }
        selectFacultyApprover.setOnAction(
                event -> selectedApprover = officer.getFacultyApproverList().getApprovers().get(selectFacultyApprover.getSelectionModel().getSelectedIndex())
        );
    }

    @FXML
    public boolean onSaveButton() throws IOException {
        if (fileUploader.getFile() != null) {
            String fileName = getModel().formObject().getStudent().getStudentId() + "_" + getModel().formObject().getRequestFormId() + ".pdf";
            fileUploader.saveFile(fileName);
            return true;
        }
        return false;
    }

    @FXML
    public void onUploadButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        fileUploader.setFileTypeDescription("PDF");
        fileUploader.addFileType("*.pdf");
        fileUploader.addFileType("*.PDF");
        File file = fileUploader.fileChooserUpload(source);
        fileUploader.setFile(file);
        setUploadedFile(file.getName());
    }

    @FXML
    public void dropOnUploadButton(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage() || dragboard.hasFiles()) {
            File file = dragboard.getFiles().getFirst();
            fileUploader.setFile(file);
            setUploadedFile(file.getName());
        }
    }

    @FXML
    public void dragOverUploadButton(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage() || dragboard.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    private boolean checkInvalidConfirmation() {
        if (selectedApprover == null) {
            AlertService.showError("กรุณาเลือกผู้อนุมัติ จึงจะมีการดำเนินการต่อได้");
            return true;
        }
        try {
            if (!onSaveButton()) {
                AlertService.showError("กรุณาอัพโหลดไฟล์ PDF ก่อนกดยืนยัน");
                return true;
            }
        } catch (IOException e) {
            AlertService.showError("เกิดข้อผิดพลาดขณะบันทึกไฟล์ กรุณาลองใหม่อีกครั้ง");
            return true;
        }
        return false;
    }

    @FXML
    public void onPreviousPageButton() {
        back(new FormDataModel(true, getModel().formObject()));
    }

    private void setUploadedFile(String fileName) {
        documentUploadLabel.setText(fileName);
        documentUploadLabel.setStyle("-fx-text-fill: #0000FF; -fx-underline: true;");
    }

    @FXML
    public void onRequestSubmitButton() {
        if (checkInvalidConfirmation()) return;
        FormDataModel nextPopupFormDataModel = new FormDataModel(true, getModel().formObject());
        RequestFormActionHistory newAction = new RequestFormActionHistory(getModel().formObject().getRequestFormId(), selectedApprover.getFullName(), RequestForm.Status.APPROVED_BY_FACULTY, "คณบดีคณะ" + getModel().formObject().getStudent().getFaculty().getName());
        nextPopupFormDataModel.getFormObject().setStatus(newAction);
        nextPopupFormDataModel.setAcceptMessage("สิ้นสุดที่คณบดี คำร้องดำเนินการครบถ้วน");
        officer.getRequestFormList().removeForm(nextPopupFormDataModel.getFormObject());
        DataProvider.getDataProvider().saveUser();
        this.close();
        changeScene(nextPopupFormDataModel, "/ku/cs/views/request-forms/officer-form-accept-popup.fxml");
    }
}
