package ku.cs.controllers.components;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;

public class ProfilePictureController {

    public static final String imagesPath = "data" + File.separator + "profile-pictures";
    public static final String defaultImagePath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "images" + File.separator + "default-image.jpg";

    public static void setImageToCircle(Circle profilePictureCircle, String imageName) {
        Image image;
        if(imageName.equals("default-image.jpg")) image = new Image("file:" + defaultImagePath);
        else image = new Image("file:" + imagesPath + File.separator + imageName);
        setImageToCircle(profilePictureCircle, image);
    }

    public static void setImageToCircle(Circle profilePictureCircle, Image image) {
        profilePictureCircle.setFill(new ImagePattern(image));
    }
}
