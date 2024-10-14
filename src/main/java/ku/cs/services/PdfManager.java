package ku.cs.services;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import ku.cs.config.Configuration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ku.cs.services.AlertService.showError;

public class PdfManager {

    public static void saveByDefault(Node node, String requestFormId) throws IOException {
        node.setScaleX(2.0675);
        node.setScaleY(2.0675);

        SnapshotParameters params = new SnapshotParameters();
        Image image = node.snapshot(params, null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

        File tempImageFile = new File("tempImage.png");
        ImageIO.write(bufferedImage, "png", tempImageFile);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDImageXObject pdImage = PDImageXObject.createFromFile(tempImageFile.getAbsolutePath(), document);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            }

            File checkingDir = new File(Configuration.getConfiguration().getRequestFormsPdfPath());
            if (!checkingDir.exists()) {
                checkingDir.mkdirs();
            }
            File pdfFile = new File(Configuration.getConfiguration().getRequestFormsPdfPath() + File.separator + requestFormId+".pdf");

            if (!pdfFile.exists() && !pdfFile.isFile()) {
                document.save(new File(Configuration.getConfiguration().getRequestFormsPdfPath() + File.separator + requestFormId + ".pdf"));
            }

            node.setScaleX(1.00);
            node.setScaleY(1.00);
        }
        if (!tempImageFile.delete()) {
            showError("ล้มเหลวในการลบไฟล์ชั่วคราว");
        }
    }

    public static File saveByFileChooser(Node node, String requestFormId) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("บันทึกใบคำร้อง PDF");
        fileChooser.setInitialFileName(requestFormId + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf", "*.PDF"));

        File outputPdfFile = fileChooser.showSaveDialog(node.getScene().getWindow());
        if (outputPdfFile == null) {
            throw new IOException("ยกเลิกการดาวน์โหลด");
        }

        node.setScaleX(2.0675);
        node.setScaleY(2.0675);

        SnapshotParameters params = new SnapshotParameters();
        Image image = node.snapshot(params, null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

        File tempImageFile = new File("tempImage.png");
        ImageIO.write(bufferedImage, "png", tempImageFile);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDImageXObject pdImage = PDImageXObject.createFromFile(tempImageFile.getAbsolutePath(), document);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            }
            document.save(outputPdfFile);
            node.setScaleX(1.00);
            node.setScaleY(1.00);
        }
        if (!tempImageFile.delete()) {
            showError("ล้มเหลวในการลบไฟล์ชั่วคราว");
        }
        return outputPdfFile;
    }
}
