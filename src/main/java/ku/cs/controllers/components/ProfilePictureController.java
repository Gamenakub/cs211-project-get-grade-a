package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.config.Configuration;

import java.io.File;

public class ProfilePictureController {

    @FXML
    public static void setImageToCircle(Circle profilePictureCircle, String imageName) {
        File imageFile = new File(Configuration.getConfiguration().getImagesPath() + File.separator + imageName);
        if (!imageFile.exists() || !imageFile.isFile()) {
            setImageToCircle(profilePictureCircle, new Image(Configuration.getConfiguration().getDefaultImagePath()));
        } else {
            setImageToCircle(profilePictureCircle, new Image("file:" + imageFile.getPath()));
        }

    }

    @FXML
    public static void setImageToCircle(Circle profilePictureCircle, Image image) {
        if(image != null) {
            profilePictureCircle.setFill(new ImagePattern(image));
        }
    }
}
