package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.models.users.User;
import ku.cs.services.AlertService;
import ku.cs.services.FileUploader;
import ku.cs.services.ImageCropper;
import ku.cs.services.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class UserChangeProfilePicturePopupController extends BasePopup<User> {
    @FXML private AnchorPane anchorPane;
    @FXML private Circle profilePictureCircle;
    private FileUploader profilePictureUploader;
    private User user;


    public void onPopupOpen() {
        Session.getSession().getTheme().setTheme(anchorPane);
        user = Session.getSession().getLoggedInUser();
        ProfilePictureController.setImageToCircle(profilePictureCircle, user.getProfilePictureFileName());
        profilePictureUploader = new FileUploader(ProfilePictureController.imagesPath);
        user = getModel();
    }

    @FXML
    public void onSaveButton() throws IOException {
        if(profilePictureUploader.getFile() != null) {
            user.setProfilePictureFileName();
            profilePictureUploader.saveFile(user.getProfilePictureFileName());
            ImageCropper.cropImageFileToSquare(new File(ProfilePictureController.imagesPath + File.separator + user.getProfilePictureFileName()));
        }
        AlertService.showInfo("เปลี่ยนรูปโปรไฟล์เรียบร้อยแล้ว");
        this.close();
    }

    @FXML
    public void onResetProfilePictureFileNameButton() {
        if(!(user.getProfilePictureFileName().equals(User.defaultProfilePictureFileName))) {
            try {
                if(AlertService.showConfirmation("ระบบจะทำการลบรูปโปรไฟล์ออกจากระบบ และใช้รูปโปรไฟล์พื้นฐาน")){
                    Files.delete(Path.of(ProfilePictureController.imagesPath + File.separator + user.getProfilePictureFileName()));
                    user.resetProfilePictureFileName();
                    ProfilePictureController.setImageToCircle(profilePictureCircle, user.getProfilePictureFileName());
                } else {
                    AlertService.showInfo("เปลี่ยนรูปโปรไฟล์เป็นรูปโปรไฟล์พื้นฐานเรียบร้อยแล้ว");
                }
            } catch (IOException e) {
                AlertService.showError("การลบรูปโปรไฟล์เดิมผิดพลาด กรุณาลองใหม่อีกครั้ง");
            }
        } else {
            AlertService.showInfo("รูปโปรไฟล์เป็นรูปโปรไฟล์พื้นฐานแล้ว");
        }

        this.close();
    }

    @FXML public void onUploadButton(MouseEvent event){
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
        } catch (IOException e) {
            AlertService.showError("เกิดปัญหาการเข้าถึงไฟล์ กรุณาอัพโหลดใหม่อีกครั้ง");
        }
    }

    @FXML public void dropOnUploadButton(DragEvent event) throws IOException {
        Dragboard dragboard = event.getDragboard();
        if(dragboard.hasImage() || dragboard.hasFiles()) {
            File file = dragboard.getFiles().getFirst();
            profilePictureUploader.setFile(file);
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Image croppedImage = ImageCropper.cropImageToSquare(new Image(inputStream));
                ProfilePictureController.setImageToCircle(profilePictureCircle, croppedImage);
            } catch (FileNotFoundException e) {
                AlertService.showError("เกิดปัญหาการเข้าถึงไฟล์ กรุณาอัพโหลดใหม่อีกครั้ง");
            }
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
