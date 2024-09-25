package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.models.DepartmentApprover;
import ku.cs.models.FacultyApprover;
import ku.cs.models.FormDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.FileUploader;
import ku.cs.services.ImageCropper;
import ku.cs.services.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class OfficerFacultyRequestFormConfirmationPopupController extends BasePopup<FormDataModel> {
    public ChoiceBox selectFacultyApprover;
    @FXML
    private AnchorPane anchorPane;
    private FileUploader fileUploader;
    private FacultyOfficer officer;
    private FacultyApprover selectedApprover;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        Session.getSession().getTheme().setTheme(anchorPane);
        fileUploader = new FileUploader(ProfilePictureController.imagesPath);

        officer = (FacultyOfficer) Session.getSession().getLoggedInUser();
        for (FacultyApprover approver : officer.getFacultyApproverList().getApprovers()) {
            selectFacultyApprover.getItems().add(approver.getName()+" "+approver.getSurname());
        }
        selectFacultyApprover.setOnAction(
                event -> {
                    selectedApprover = officer.getFacultyApproverList().getApprovers().get(selectFacultyApprover.getSelectionModel().getSelectedIndex());
                }
        );
    }

    @FXML
    public void onSaveButton() throws IOException {
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

    public void onRequestRefuse(ActionEvent actionEvent) {
        RequestFormActionHistory newAction = new RequestFormActionHistory(getModel().getFormObject().getRequestFormId(), officer.getFullName(), RequestForm.Status.REFUSED_BY_FACULTY, RequestFormActionHistory.ApproverType.FACULTY);
        getModel().getFormObject().setStatus(newAction);
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-refuse-popup.fxml","form");
    }

    public void onRequestEndAtDean(ActionEvent actionEvent) {
        RequestFormActionHistory newAction = new RequestFormActionHistory(getModel().getFormObject().getRequestFormId(), selectedApprover.getFullName(), RequestForm.Status.APPROVED_BY_FACULTY, RequestFormActionHistory.ApproverType.FACULTY);
        getModel().getFormObject().setStatus(newAction);
        getModel().setAcceptMessage("สิ้นสุดที่คณะบดี คำร้องดำเนินการครบถ้วน");
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-accept-popup.fxml","form");
    }

    public void onButtonBack(ActionEvent actionEvent) {
        back();
    }
}
