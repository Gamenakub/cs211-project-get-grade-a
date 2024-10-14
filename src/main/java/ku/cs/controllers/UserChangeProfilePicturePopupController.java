package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import ku.cs.config.Configuration;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.models.users.User;
import ku.cs.services.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class UserChangeProfilePicturePopupController extends BasePopup<User> {
    @FXML private AnchorPane anchorPane;
    @FXML private Circle profilePictureCircle;
    @FXML private Label fileNameLabel;

    private FileUploader profilePictureUploader;
    private User user;


    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        user = Session.getSession().getLoggedInUser();
        ProfilePictureController.setImageToCircle(profilePictureCircle, user.getProfilePictureFileName());
        profilePictureUploader = new FileUploader(Configuration.getConfiguration().getImagesPath());
        user = getModel();
    }

    @FXML
    public void onSaveButton() {
        if (profilePictureUploader.getFile() != null) {
            user.setProfilePictureFileName();
            try {
                profilePictureUploader.saveFile(user.getProfilePictureFileName());
                ImageCropper.cropImageFileToSquare(new File(Configuration.getConfiguration().getImagesPath() + File.separator + user.getProfilePictureFileName()));
            } catch (IOException e) {
                AlertService.showError("เกิดปัญหาการเข้าถึงไฟล์ กรุณาอัพโหลดใหม่อีกครั้ง");
            }
        }
        DataProvider.getDataProvider().saveUser();
        issueEvent("success");
        AlertService.showInfo("เปลี่ยนรูปโพรไฟล์เรียบร้อยแล้ว");
        this.close();
    }

    @FXML
    public void onResetProfilePictureFileNameButton() {
        if (!(user.getProfilePictureFileName().equals(Configuration.getConfiguration().getDefaultProfilePictureFileName()))) {
            try {
                if (AlertService.showConfirmation("ระบบจะทำการลบรูปโพรไฟล์ออกจากระบบ และใช้รูปโพรไฟล์พื้นฐาน")) {
                    Files.delete(Path.of(Configuration.getConfiguration().getImagesPath() + File.separator + user.getProfilePictureFileName()));
                    user.resetProfilePictureFileName();
                    ProfilePictureController.setImageToCircle(profilePictureCircle, user.getProfilePictureFileName());
                    DataProvider.getDataProvider().saveUser();
                    fileNameLabel.setText("ยังไม่ได้อัพโหลดไฟล์");
                    issueEvent("success");
                    AlertService.showInfo("เปลี่ยนรูปโพรไฟล์เป็นรูปโพรไฟล์พื้นฐานเรียบร้อยแล้ว");
                } else {
                    AlertService.showWarning("การดำเนินการถูกยกเลิก");
                }
            } catch (IOException e) {
                AlertService.showError("การลบรูปรูปโพรไฟล์เดิมผิดพลาด กรุณาลองใหม่อีกครั้ง");
            }
        } else {
            AlertService.showInfo("รูปโพรไฟล์เป็นรูปโพรไฟล์พื้นฐานแล้ว");
        }

        this.close();
    }

    @FXML
    public void onUploadButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        profilePictureUploader.setFileTypeDescription("Images");
        profilePictureUploader.addFileType("*.png");
        profilePictureUploader.addFileType("*.jpeg");
        profilePictureUploader.addFileType("*.jpg");
        profilePictureUploader.addFileType("*.PNG");
        profilePictureUploader.addFileType("*.JPEG");
        profilePictureUploader.addFileType("*.JPG");

        try {
            File file = profilePictureUploader.fileChooserUpload(source);
            profilePictureUploader.setFile(file);
            FileInputStream inputStream = new FileInputStream(file);
            Image croppedImage = ImageCropper.cropImageToSquare(new Image(inputStream));
            ProfilePictureController.setImageToCircle(profilePictureCircle, croppedImage);
            fileNameLabel.setText(file.getName());
        } catch (IOException e) {
            AlertService.showError("เกิดปัญหาการเข้าถึงไฟล์ กรุณาอัพโหลดใหม่อีกครั้ง");
        }
    }

    @FXML
    public void dropOnUploadButton(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage() || dragboard.hasFiles()) {
            File file = dragboard.getFiles().getFirst();
            profilePictureUploader.setFile(file);
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Image croppedImage = ImageCropper.cropImageToSquare(new Image(inputStream));
                ProfilePictureController.setImageToCircle(profilePictureCircle, croppedImage);
                fileNameLabel.setText(file.getName());
            } catch (FileNotFoundException e) {
                AlertService.showError("เกิดปัญหาการเข้าถึงไฟล์ กรุณาอัพโหลดใหม่อีกครั้ง");
            }
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
}
