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
import ku.cs.models.DepartmentApprover;
import ku.cs.models.FormDataModel;
import ku.cs.models.OfficerConfirmationPopupDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.FileUploader;
import ku.cs.services.Session;
import java.io.File;
import java.io.IOException;

public class OfficerDepartmentRequestFormConfirmationPopupController extends BasePopup<OfficerConfirmationPopupDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private ChoiceBox<String> selectTargetDepartmentApprover;
    @FXML private Label documentUploadLabel;
    private FileUploader fileUploader;
    private DepartmentOfficer officer;
    private DepartmentApprover selectedApprover;


    @Override
    public void onPopupOpen() {
        fileUploader = new FileUploader(Configuration.getConfiguration().getRequestFormsPdfPath());
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        officer = (DepartmentOfficer) Session.getSession().getLoggedInUser();
        for (DepartmentApprover approver : officer.getDepartmentApproverList().getApprovers()) {
            if (approver.getDepartment().equals(officer.getDepartment())) {
                selectTargetDepartmentApprover.getItems().add(approver.getFullRepresentationString());
            }
        }
        selectTargetDepartmentApprover.setOnAction(
                event -> selectedApprover = officer.getDepartmentApproverList().getApprovers().get(selectTargetDepartmentApprover.getSelectionModel().getSelectedIndex())
        );
    }

    @FXML
    public void onRequestSubmitButton() {
        if (checkInvalidConfirmation()) return;

        if (getModel().nextStatus().equals(RequestForm.Status.APPROVED_BY_DEPARTMENT)) {
            onRequestSubmitEndAtDepartmentLeaderButton();
        } else {
            onRequestSubmitSetEndToDeanButton();
        }
    }

    @FXML
    public void onRequestSubmitEndAtDepartmentLeaderButton() {
        if (checkInvalidConfirmation()) return;

        String formId = getModel().formObject().getRequestFormId();
        FormDataModel nextPagePopUpFormModel = new FormDataModel(true, getModel().formObject());
        RequestFormActionHistory newAction = new RequestFormActionHistory(formId, selectedApprover.getFullName(), getModel().nextStatus(), "หัวหน้าภาควิชา" + getModel().formObject().getStudent().getDepartment().getName());
        nextPagePopUpFormModel.getFormObject().setStatus(newAction);
        nextPagePopUpFormModel.setAcceptMessage("สิ้นสุดที่หัวหน้าภาควิชา คำร้องดำเนินการครบถ้วน");
        officer.getRequestFormList().removeForm(getModel().formObject());
        DataProvider.getDataProvider().saveUser();
        changeScene(nextPagePopUpFormModel, "/ku/cs/views/request-forms/officer-form-accept-popup.fxml");
    }

    @FXML
    public void onRequestSubmitSetEndToDeanButton() {
        if (checkInvalidConfirmation()) return;

        String formId = getModel().formObject().getRequestFormId();
        FormDataModel nextPagePopUpFormModel = new FormDataModel(true, getModel().formObject());
        RequestFormActionHistory newAction = new RequestFormActionHistory(formId, selectedApprover.getFullName(), RequestForm.Status.PENDING_TO_FACULTY, "หัวหน้าภาควิชา" + getModel().formObject().getStudent().getDepartment().getName());
        nextPagePopUpFormModel.getFormObject().setStatus(newAction);
        ((DepartmentOfficer) Session.getSession().getLoggedInUser()).getRequestFormList().removeForm(getModel().formObject());
        nextPagePopUpFormModel.setAcceptMessage("อนุมัติโดยหัวหน้าภาควิชา คำร้องส่งต่อให้คณบดี");
        officer.getRequestFormList().removeForm(getModel().formObject());
        DataProvider.getDataProvider().saveUser();
        this.close();
        changeScene(nextPagePopUpFormModel, "/ku/cs/views/request-forms/officer-form-accept-popup.fxml");
    }

    private boolean checkInvalidConfirmation() {
        if (selectedApprover == null) {
            AlertService.showError("กรุณาเลือกผู้อนุมัติ จึงจะมีการดำเนินการต่อได้");
            return true;
        }
        try {
            if (!saveFile()) {
                AlertService.showError("กรุณาอัพโหลดไฟล์ PDF ก่อนกดยืนยัน");
                return true;
            }
        } catch (IOException e) {
            AlertService.showError("เกิดข้อผิดพลาดขณะบันทึกไฟล์ กรุณาลองใหม่อีกครั้ง");
            return true;
        }
        return false;
    }

    public void onPreviousPageButton() {
        back(new FormDataModel(true, getModel().formObject()));
    }

    public boolean saveFile() throws IOException {
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

    private void setUploadedFile(String fileName) {
        documentUploadLabel.setText(fileName);
        documentUploadLabel.setStyle("-fx-text-fill: #0000FF; -fx-underline: true;");
    }
}
