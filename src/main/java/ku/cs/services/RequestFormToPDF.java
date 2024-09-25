package ku.cs.services;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RequestFormToPDF {

    public static void nodeToPDF(Node node, String outputPdfPath) throws IOException {
        // Resize node to desired dimensions (adjust if needed)
        node.setScaleX(2.0675);
        node.setScaleY(2.0675);

        // Capture the node as an image
        SnapshotParameters params = new SnapshotParameters();
        Image image = node.snapshot(params, null);

        // Convert Image to BufferedImage
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

        // Save BufferedImage to a temporary file
        File tempImageFile = new File("tempImage.png");
        ImageIO.write(bufferedImage, "png", tempImageFile);

        // Create a new PDF document
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Load image into PDFBox
            PDImageXObject pdImage = PDImageXObject.createFromFile(tempImageFile.getAbsolutePath(), document);

            // Create content stream to add content to the page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Draw the image to the content stream
                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            }

            // Save the document
            document.save(outputPdfPath);
            // Resize node to desired dimensions (adjust if needed)
            node.setScaleX(1.00);
            node.setScaleY(1.00);
        }

        // Clean up temporary image file
        if (!tempImageFile.delete()) {
            System.err.println("Failed to delete temporary image file.");
        }

        System.out.println("PDF created successfully at: " + outputPdfPath);
    }
}
