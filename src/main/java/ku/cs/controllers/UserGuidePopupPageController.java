package ku.cs.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.services.AlertService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class UserGuidePopupPageController extends BasePopup<Object> {
    @FXML private Pane pdfDisplayPane;
    private ImageView pdfImageView;
    private int maxIndexPage;
    private int indexPage = 0;
    private PDDocument pdfDocument;

    @Override
    public void onPopupOpen() {
        indexPage = 0;
        loadPdf("user-guide.pdf");
        displayPdf();
    }

    private void loadPdf(String pdfFileName) {
        try (InputStream pdfStream = getClass().getClassLoader().getResourceAsStream(pdfFileName)) {
            if (pdfStream == null) {
                AlertService.showError("ไม่พบไฟล์ Pdf: " + pdfFileName);
                return;
            }
            pdfDocument = PDDocument.load(pdfStream);
            maxIndexPage = pdfDocument.getNumberOfPages() - 1;
            pdfImageView = new ImageView();
            pdfImageView.setFitHeight(360);
            pdfImageView.setFitWidth(720);
            pdfDisplayPane.getChildren().add(pdfImageView);
        } catch (IOException e) {
            AlertService.showError("พบข้อผิดพลาดในการโหลกไฟล์ Pdf: " + e.getMessage());
        }
    }

    public void onPreviousPageButton() {
        indexPage = (indexPage - 1 + maxIndexPage + 1) % (maxIndexPage + 1);
        displayPdf();
    }

    public void onNextPageButton() {
        indexPage = (indexPage + 1) % (maxIndexPage + 1);
        displayPdf();
    }

    public void displayPdf() {
        if (pdfDocument == null) return;
        try {
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(indexPage, 300);
            pdfImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        } catch (IOException e) {
            AlertService.showError("พบข้อผิดพาดในการแสดงผลไฟล์ Pdf: " + e.getMessage());
        }
    }

    public void onHandbookButton() {
        openHandbookPdf("user-guide.pdf");
    }

    private void openHandbookPdf(String pdfFileName) {
        try (InputStream pdfStream = getClass().getClassLoader().getResourceAsStream(pdfFileName)) {
            if (pdfStream == null) {
                AlertService.showError("ไม่พบไฟล์ Pdf: " + pdfFileName);
                return;
            }
            Path tempFile = Files.createTempFile("tempPdf", ".pdf");
            Files.copy(pdfStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            File pdfFile = tempFile.toFile();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(pdfFile.toURI());
            } else {
                AlertService.showError("ระบบนี้ไม่รองรับการแสดงผลไฟล์ Pdf ผ่านบราวเซอร์");
            }
        } catch (IOException e) {
            AlertService.showError("พบข้อผิดพลาดในการแสดงผลไฟล์ Pdf:" + e.getMessage());
        }
    }
}
