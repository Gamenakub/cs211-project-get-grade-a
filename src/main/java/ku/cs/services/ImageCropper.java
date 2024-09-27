package ku.cs.services;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCropper {

    public static void cropImageFileToSquare(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        int size = (int) Math.min(image.getWidth(), image.getHeight());
        int center = (int) (Math.max(image.getWidth(), image.getHeight()) - size) / 2;
        BufferedImage crop;
        if(image.getWidth() >= image.getHeight()) crop = image.getSubimage(center, 0, size, size);
        else crop = image.getSubimage(0, center, size, size);
        ImageIO.write(crop, "png", imageFile);
    }

    public static Image cropImageToSquare(Image image) {
        if (image.getHeight() == image.getWidth())  return image;
        int size = (int) Math.min(image.getWidth(), image.getHeight());
        int center = (int) (Math.max(image.getWidth(), image.getHeight()) - size) / 2;
        if(image.getWidth() >= image.getHeight()) return new WritableImage(image.getPixelReader(), center, 0, size, size);
        else return new WritableImage(image.getPixelReader(), 0, center, size, size);
    }

}
