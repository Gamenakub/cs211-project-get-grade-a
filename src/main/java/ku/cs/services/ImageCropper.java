package ku.cs.services;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageCropper {
    public static void cropImageFileToSquare(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        if (image == null) {
            throw new IOException("Failed to read the image file: " + imageFile.getPath());
        }

        BufferedImage croppedImage = cropBufferedImageToSquare(image);
        ImageIO.write(croppedImage, "png", imageFile);
    }

    public static Image cropImageToSquare(Image image) {
        if (image == null || image.getHeight() <= 0 || image.getWidth() <= 0) {
            throw new IllegalArgumentException("Invalid image provided for cropping.");
        }

        if (image.getHeight() == image.getWidth()) {
            return image;
        }

        int size = (int) Math.min(image.getWidth(), image.getHeight());
        int xOffset = (int) ((image.getWidth() - size) / 2);
        int yOffset = (int) ((image.getHeight() - size) / 2);

        return new WritableImage(image.getPixelReader(), xOffset, yOffset, size, size);
    }

    private static BufferedImage cropBufferedImageToSquare(BufferedImage image) {
        int size = Math.min(image.getWidth(), image.getHeight());
        int xOffset = (image.getWidth() - size) / 2;
        int yOffset = (image.getHeight() - size) / 2;

        return image.getSubimage(xOffset, yOffset, size, size);
    }
}

