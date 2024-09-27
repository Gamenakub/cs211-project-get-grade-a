package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.models.DepartmentApprover;
import ku.cs.models.FormDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.FileUploader;
import ku.cs.services.Session;

import java.io.File;
import java.io.IOException;

public class OfficerDepartmentRequestFormConfirmationPopupController extends BasePopup<FormDataModel> {

    @FXML
    private AnchorPane anchorPane;
    private FileUploader fileUploader;
    private DepartmentOfficer officer;
    private DepartmentApprover selectedApprover;

    @FXML
    private ChoiceBox<String> selectTargetDepartmentApprover;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        fileUploader = new FileUploader(ProfilePictureController.imagesPath);
        Session.getSession().getTheme().setTheme(anchorPane);
        officer = (DepartmentOfficer) Session.getSession().getLoggedInUser();
        // selectDepartmentApprover = new ChoiceBox();
        for (DepartmentApprover approver : officer.getDepartmentApproverList().getApprovers()) {
            selectTargetDepartmentApprover.getItems().add(approver.getName()+" "+approver.getSurname());
        }
        selectTargetDepartmentApprover.setOnAction(
                event -> {
                    selectedApprover = officer.getDepartmentApproverList().getApprovers().get(selectTargetDepartmentApprover.getSelectionModel().getSelectedIndex());
                }
        );
    }

    public void onRequestSubmitEndAtDepartmentLeader(ActionEvent actionEvent) {
        if (selectedApprover == null) {
            AlertService.showError("กรุณาเลือกผู้อนุมัติ จึงจะมีการดำเนินการต่อได้");
        } else{
        try {
            onSave();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String formId = getModel().getFormObject().getRequestFormId();
        if (selectedApprover == null) {
            AlertService.showError("กรุณาเลือกผู้อนุมัติ จึงจะมีการดำเนินการต่อได้");
        }
        else{
            RequestFormActionHistory newAction = new RequestFormActionHistory(formId,selectedApprover.getFullName(), RequestForm.Status.APPROVED_BY_DEPARTMENT, RequestFormActionHistory.ApproverType.DEPARTMENT);
            getModel().getFormObject().setStatus(newAction);
            getModel().setAcceptMessage("สิ้นสุดที่หัวหน้าภาควิชา คำร้องดำเนินการครบถ้วน");
            changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-accept-popup.fxml","form");
        }
    }}

    public void onRequestSubmitSetEndToDean(ActionEvent actionEvent) {
        try {
            onSave();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String formId = getModel().getFormObject().getRequestFormId();
        RequestFormActionHistory newAction = new RequestFormActionHistory(formId,selectedApprover.getFullName(), RequestForm.Status.PENDING_TO_FACULTY, RequestFormActionHistory.ApproverType.DEPARTMENT);
        getModel().getFormObject().setStatus(newAction);
        ((DepartmentOfficer)Session.getSession().getLoggedInUser()).getRequestFormList().removeForm(getModel().getFormObject());

        getModel().setAcceptMessage("อนุมัติโดยหัวหน้าภาควิชา คำร้องส่งต่อให้คณบดี");
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-accept-popup.fxml","form");
    }

    public void onRequestRefuse(ActionEvent actionEvent) {
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-refuse-popup.fxml","form");
    }

    public void onButtonBack(ActionEvent actionEvent) {
        back();
    }

    public void onSave() throws IOException {
        if(fileUploader.getFile() != null) {
            String fileName = getModel().getFormObject().getRequestFormId() + "-document.pdf";
            fileUploader.saveFile(fileName);
            //ImageCropper.cropImageFileToSquare(new File(ProfilePictureController.imagesPath + File.separator + user.getProfilePictureFileName()));
        }
        this.close();
    }

    @FXML public void onUploadButton(MouseEvent event){
        Node source = (Node) event.getSource();
        fileUploader.setFileTypeDescription("PDF");
        fileUploader.addFileType("*.pdf");
        fileUploader.addFileType("*.PDF");

        File file = fileUploader.fileChooserUpload(source);
        fileUploader.setFile(file);
    }

    @FXML public void dropOnUploadButton(DragEvent event) throws IOException {
        Dragboard dragboard = event.getDragboard();
        if(dragboard.hasImage() || dragboard.hasFiles()) {
            File file = dragboard.getFiles().getFirst();
            fileUploader.setFile(file);
        }
    }

    @FXML public void dragOverUploadButton(DragEvent event){
        Dragboard dragboard = event.getDragboard();
        if(dragboard.hasImage() || dragboard.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }
}
